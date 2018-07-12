package test.sdk.mugu.autocreateviewplugin;

import android.content.Context;
import android.view.View;

/**
 * Created by xiang on 2018/6/8.
 * you can only use the module which implements IBaseBindData,，and must override createBindView()
 */

public interface IBaseBindData {
    /**
     * you can only use the module which implements IBaseBindData, so you can use the plugin auto create view;
     * and invoke ViewBindPlugin.bindAutoCreate(Context context, IBaseBindData data) can help you real auto create;
     * you can alse define youself
     * @param context
     * @return
     */
    public View createBindView(Context context);
}
