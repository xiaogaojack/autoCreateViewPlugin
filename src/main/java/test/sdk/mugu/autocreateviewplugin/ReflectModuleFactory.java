package test.sdk.mugu.autocreateviewplugin;

import java.lang.reflect.Field;

/**
 * Created by xiang on 2018/6/13.
 */

class ReflectModuleFactory implements IModuleFactory {
    private Object mInvoker;
    private Field mField;

    ReflectModuleFactory(Object invoker, Field field){
        mInvoker = invoker;
        mField = field;
    }

    @Override
    public InnerParamModule createModule() {
        if (mInvoker == null | mInvoker == null){
            return null;
        }

        if (!isParamLegal(mField.getName())){
            return null;
        }

        InnerParamModule module = createNeccessaryInnerParamModule(mField.getType());

        if (module != null) {
            InnerAnnotion annotion = getAnnotationWithField(mField);
            module.innerAnnotion = annotion;
            module.fieldname = mField.getName();
            module.fieldType = mField.getType();
            module.invoker = mInvoker;

            mField.setAccessible(true);

            try {
                Object objParam = mField.get(mInvoker);
                module.fieldvalue = objParam;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            module.onCreate();

            return module;
        }

        return null;
    }

    private static boolean isParamLegal(String fieldname){
        return !("$change".equalsIgnoreCase(fieldname) || "serialVersionUID".equalsIgnoreCase(fieldname));
    }

    private static InnerAnnotion getAnnotationWithField(Field field) {
        if (field == null) {
            return null;
        }

        InnerAnnotion annotion = new InnerAnnotion();

        ForceValidate force = field.getAnnotation(ForceValidate.class);

        if (force != null){
            annotion.force = true;
        }else{
            annotion.force = false;
        }

        IgnoreBind ignore = field.getAnnotation(IgnoreBind.class);

        if (ignore != null){
            annotion.ignore = true;
        }else{
            annotion.ignore = false;
        }

        BindAttribute attribute = field.getAnnotation(BindAttribute.class);

        if (attribute != null){
            annotion.name = attribute.name();
        }

        return annotion;
    }

    static InnerParamModule createNeccessaryInnerParamModule(Class<?> clazz){
        if (clazz == null){
            return null;
        }

        InnerParamModule module = null;

        if (clazz.isArray()){
            module = new InnerParamArrayModule();
        }else if (InnerParamModule.isClassSimpleType(clazz)){
            module = new InnerParamModule();
        }else{
            module = new InnerParamObjectModule();
        }

        return module;
    }
}
