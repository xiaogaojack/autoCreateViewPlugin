package test.sdk.mugu.autocreateviewplugin;

/**
 * Created by xiang on 2018/6/13.
 */

class ArrayModuleFactory implements IModuleFactory {
    private Object[] mArray;
    private int mIndex;
    private Object mObject;

    public ArrayModuleFactory(Object[] array, int nIndex){
        mArray = array;
        mIndex = nIndex;

        if (mArray != null && mArray.length > nIndex) {
            mObject = mArray[nIndex];
        }
    }

    @Override
    public InnerParamModule createModule() {
        if (mArray == null || mObject == null){
            return null;
        }

        InnerParamModule module = createNeccessaryInnerParamModule();

        if (module != null) {
            module.fieldname = mObject.getClass().getSimpleName();
            module.fieldType = mObject.getClass();
            module.fieldvalue = mObject;
            module.invoker = mObject;
            module.onCreate();

            return module;
        }

        return null;
    }

    InnerParamModule createNeccessaryInnerParamModule(){
        InnerParamModule module = null;

        Class clazz = mObject.getClass();

        if (clazz.isArray()){
            module = new InnerParamArrayModule();
        }else if (InnerParamModule.isClassSimpleType(clazz)){
            module = new InnerArraySimpleItemModule(mArray, mIndex);
        }else{
            module = new InnerParamObjectModule();
        }

        return module;
    }
}
