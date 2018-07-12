package test.sdk.mugu.autocreateviewplugin;

import android.util.Log;

/**
 * Created by xiang on 2018/6/8.
 */

class LogUtils {
    private static boolean isDebug = true;
    private static String TAG = "auto_create_plugin";

    public static void log(String text){
        if (!isDebug){
            return;
        }

        Log.v(TAG, text);
    }
}
