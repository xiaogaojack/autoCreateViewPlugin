package test.sdk.mugu.autocreateviewplugin;

/**
 * Created by xiang on 2018/6/12.
 */

class InnerParamModuleCreator {
    /**
     * @param factory  工厂
     * @return
     */
    public static InnerParamModule create(IModuleFactory factory){
        if (factory != null){
            return factory.createModule();
        }

        return null;
    }
}
