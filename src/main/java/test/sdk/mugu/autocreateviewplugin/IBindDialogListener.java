package test.sdk.mugu.autocreateviewplugin;

/**
 * Created by xiang on 2018/6/8.
 *
 * 监听最终bind数据的结果
 */

public interface IBindDialogListener<T extends IBaseBindData> {
    /**
     * 最终返回绑定数据
     * @param t
     */
    public void onConfirm(T t);
}
