package test.sdk.mugu.autocreateviewplugin;

/**
 * Created by xiang on 2018/6/13.
 */

class InnerArraySimpleItemModule extends InnerParamModule {
    private Object[] mArrays;
    private int mIndex;

    public InnerArraySimpleItemModule(Object[] arrays, int nIndex) {
        mArrays = arrays;
        mIndex = nIndex;
    }

    @Override
    boolean validateValueType(String text) {
        return super.validateValueType(text);
    }

    @Override
    void updateValue(String text) {
        try {
            if (fieldType != null && fieldType.getSimpleName().equalsIgnoreCase("string")) {
                mArrays[mIndex] = text;
            }

            if (fieldType != null && fieldType.getSimpleName().equalsIgnoreCase("double")) {
                mArrays[mIndex] = Double.parseDouble(text);
            }

            if (fieldType != null && (fieldType.getSimpleName().equalsIgnoreCase("integer") || fieldType.getSimpleName().equalsIgnoreCase("int"))) {
                mArrays[mIndex] = Integer.parseInt(text);
            }

            if (fieldType != null && fieldType.getSimpleName().equalsIgnoreCase("boolean")) {
                mArrays[mIndex] = Boolean.parseBoolean(text);
            }

            if (fieldType != null && fieldType.getSimpleName().equalsIgnoreCase("float")) {
                mArrays[mIndex] = Float.parseFloat(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

