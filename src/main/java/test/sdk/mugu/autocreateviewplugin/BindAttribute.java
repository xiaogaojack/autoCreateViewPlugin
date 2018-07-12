package test.sdk.mugu.autocreateviewplugin;

/**
 * Created by xiang on 2018/6/8.
 *注解：可以设置某个参数的最终展示名称
 */

public @interface BindAttribute {
    /**
     * 展示名称
     * @return
     */
    public String	name();
}
