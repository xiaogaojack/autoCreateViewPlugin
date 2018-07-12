package test.sdk.mugu.autocreateviewplugin;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by xiang on 2018/6/12.
 */

class InnerParamObjectModule extends InnerParamModule {
    public ArrayList<InnerParamModule> items;

    private LinearLayout mViewContianer;

    @Override
    protected void onCreate() {
        items = ReflectEngin.parseData(fieldvalue);

        super.onCreate();
    }

    @Override
    protected View onCreateView(Context context) {
        mViewContianer = new LinearLayout(context);
        mViewContianer.setPadding(5, 5, 5, 5);
        mViewContianer.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutparamText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView text = new TextView(context);
        text.setTypeface(Typeface.DEFAULT_BOLD);
        text.setText(getShowName());
        text.setGravity(Gravity.CENTER);
        text.setLayoutParams(layoutparamText);

        mViewContianer.addView(text);

        if (items != null) {
            for (InnerParamModule module : items){
                View child = null;

                if (module != null){
                    child = module.createView(context);
                }

                if (child != null) {
                    mViewContianer.addView(child);
                }
            }
        }

        return mViewContianer;
    }
}
