package test.sdk.mugu.autocreateviewplugin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by xiang on 2018/6/8.
 * main entrance of plugin
 */

public class ViewBindPlugin {
    /**
     * auto create view by plugin ability
     * @param context
     * @param data   the module depend on which create the view
     * @return
     */
    public static View bindAutoCreate(Context context, IBaseBindData data){
        if (data != null){
            ArrayList<InnerParamModule> modules = ReflectEngin.parseData(data);
            View view = ViewHelper.createView(context, modules);

            return view;
        }

        return null;
    }

    /**
     *
     * @param context
     * @param bindData  we should define the module which implements IBaseBindData,  and override the IBaseBindData.createBindView()
     * @param listener  with the listener, we can get the final module result
     */
    public static void showBindDialog(Context context, IBaseBindData bindData, final IBindDialogListener<IBaseBindData> listener){
        if (bindData == null){
            Toast.makeText(context, "open failure, binddata should not be null", Toast.LENGTH_SHORT).show();
        }

        final View container = bindData.createBindView(context);
        final IBaseBindData data = bindData;

        AlertDialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(container).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                if (listener != null){
                    listener.onConfirm(data);
                }
            }
        });

        builder.create().show();
    }

    static class ViewHelper {
        private static View createView(Context context, ArrayList<InnerParamModule> modules) {
            if (modules == null || modules.size() == 0) {
                return null;
            }

            LinearLayout linearlayout = new LinearLayout(context);
            linearlayout.setOrientation(LinearLayout.VERTICAL);

            for (InnerParamModule module : modules) {
                if (module != null) {
                    View node = module.createView(context);

                    if (node != null) {
                        linearlayout.addView(node);
                    }
                }
            }

            return linearlayout;
        }
    }

}

