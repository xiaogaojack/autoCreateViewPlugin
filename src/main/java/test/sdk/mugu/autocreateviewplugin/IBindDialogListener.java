package test.sdk.mugu.autocreateviewplugin;

/**
 * Created by xiang on 2018/6/8.
 *
 * ��������bind���ݵĽ��
 */

public interface IBindDialogListener<T extends IBaseBindData> {
    /**
     * ���շ��ذ�����
     * @param t
     */
    public void onConfirm(T t);
}
