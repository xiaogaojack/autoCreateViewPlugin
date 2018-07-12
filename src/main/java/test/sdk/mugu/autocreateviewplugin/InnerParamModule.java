package test.sdk.mugu.autocreateviewplugin;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by xiang on 2018/6/12.
 */
class InnerParamModule {
    Object invoker;

    String fieldname;
    Object fieldvalue;
    Class fieldType;
    InnerAnnotion innerAnnotion;

    /**
     * you can invoke when the InnerParammodule has been initialized
     */
    protected void onCreate() {
    }

    public final View createView(Context context) {
        if (isIgnore()) {
            return null;
        }

        return onCreateView(context);
    }

    protected View onCreateView(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams layoutparamText = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutparamText.weight = 1;

        TextView textName = new TextView(context);
        textName.setGravity(Gravity.CENTER);
        textName.setText(getShowName());
        textName.setLayoutParams(layoutparamText);

        layout.addView(textName);

        if (isForce()) {
            LinearLayout.LayoutParams layoutTextForce = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView textForce = new TextView(context);
            textForce.setTextColor(Color.RED);
            textForce.setGravity(Gravity.CENTER);
            textForce.setText("*");

            layout.addView(textForce);
        }

        LinearLayout.LayoutParams layoutParamEdit = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamEdit.weight = 3;

        final EditText editText = new EditText(context);
        editText.setTag(this);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (validateValueType(s.toString())) {
                    updateValue(s.toString());
                }
            }
        });

        editText.setText(getShowValue());

        editText.setLayoutParams(layoutParamEdit);

        layout.addView(editText);

        return layout;
    }


    boolean isComplexType() {
        if (fieldType != null) {

            return fieldType.isArray() || !(fieldType.getName().equalsIgnoreCase("java.lang.String") || fieldType.getName().equalsIgnoreCase("java.lang.Integer") ||
                    fieldType.getName().equalsIgnoreCase("java.lang.Double") || fieldType.getName().equalsIgnoreCase("java.lang.Float") || fieldType.isPrimitive());
        }

        return false;
    }

    public boolean isForce() {
        return innerAnnotion != null ? innerAnnotion.force : false;
    }

    public boolean isIgnore() {
        return innerAnnotion != null ? innerAnnotion.ignore : false;
    }

    public String getShowName() {
        String name = innerAnnotion != null ? (!TextUtils.isEmpty(innerAnnotion.name) ? innerAnnotion.name : fieldname) : fieldname;
        String type = fieldType.getSimpleName();

        return name + "  (" + type + ")";
    }

    public String getShowValue() {
        if (!isComplexType()) {
            if (fieldvalue != null) {
                return String.valueOf(fieldvalue);
            }
        }

        return "";
    }

    boolean validateValueType(String text) {
        if (fieldType != null && fieldType.getSimpleName().equalsIgnoreCase("string")) {
            return true;
        }

        if (fieldType != null && fieldType.getSimpleName().equalsIgnoreCase("double")) {
            try {
                Double.parseDouble(text);
                return true;
            } catch (Exception e) {
            }
        }

        if (fieldType != null && (fieldType.getSimpleName().equalsIgnoreCase("integer") || fieldType.getSimpleName().equalsIgnoreCase("int"))) {
            try {
                Integer.parseInt(text);
                return true;
            } catch (Exception e) {
            }
        }

        if (fieldType != null && fieldType.getSimpleName().equalsIgnoreCase("boolean")) {
            try {
                if ("true".equalsIgnoreCase(text) || "false".equalsIgnoreCase(text)) {
                    return true;
                }
            } catch (Exception e) {
            }
        }

        if (fieldType != null && fieldType.getSimpleName().equalsIgnoreCase("float")) {
            try {
                Float.parseFloat(text);
                return true;
            } catch (Exception e) {
            }
        }

        LogUtils.log("the param not validated, expect type: " + fieldType.getName() + " but real result: " + text);

        return false;
    }

    void updateValue(String text) {
        if (invoker == null ) {
            return;
        }

        Field field = null;

        try {
            field = invoker.getClass().getField(fieldname);
            field.setAccessible(true);

            if (fieldType != null && fieldType.getSimpleName().equalsIgnoreCase("string")) {
                field.set(invoker, text);
            }

            if (fieldType != null && fieldType.getSimpleName().equalsIgnoreCase("double")) {
                if (TextUtils.isEmpty(text)){
                    text = String.valueOf(0);
                }

                field.set(invoker, Double.parseDouble(text));
            }

            if (fieldType != null && (fieldType.getSimpleName().equalsIgnoreCase("integer") || fieldType.getSimpleName().equalsIgnoreCase("int"))) {
                if (TextUtils.isEmpty(text)){
                    text = String.valueOf(0);
                }

                field.set(invoker, Integer.parseInt(text));
            }

            if (fieldType != null && fieldType.getSimpleName().equalsIgnoreCase("boolean")) {
                if (TextUtils.isEmpty(text)){
                    text = String.valueOf(false);
                }

                field.set(invoker, Boolean.parseBoolean(text));
            }

            if (fieldType != null && fieldType.getSimpleName().equalsIgnoreCase("float")) {
                if (TextUtils.isEmpty(text)){
                    text = String.valueOf(0);
                }

                field.set(invoker, Float.parseFloat(text));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isClassSimpleType(Class classDesire){
        if (classDesire == null){
            return false;
        }

        return classDesire.isPrimitive() || classDesire.getSimpleName().equalsIgnoreCase("String") ||
                classDesire.getSimpleName().equalsIgnoreCase("Integer") || classDesire.getSimpleName().equalsIgnoreCase("Boolean") || classDesire.getSimpleName().equalsIgnoreCase("Double")
                ||classDesire.getSimpleName().equalsIgnoreCase("Float");
    };
}