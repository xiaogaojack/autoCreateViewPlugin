package test.sdk.mugu.autocreateviewplugin;

import java.lang.reflect.Field;
import java.util.ArrayList;

class ReflectEngin {
    static ArrayList<InnerParamModule> parseData(Object invoker){
        if (invoker == null){
            return null;
        }

        ArrayList<InnerParamModule> arraylist = new ArrayList<InnerParamModule>();

        try{
            Field[] fields = invoker.getClass().getFields();

            for (Field field: fields){
                if (field != null){
                    ReflectModuleFactory reflectInvoker = new ReflectModuleFactory(invoker, field);
                    InnerParamModule module = InnerParamModuleCreator.create(reflectInvoker);

                    if (module != null) {
                        arraylist.add(module);
                    }
                }
            }

            return arraylist;

        }catch (Exception e){

        }

        return null;
    }
}
