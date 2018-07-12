package test.sdk.mugu.autocreateviewplugin;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by xiang on 2018/6/12.
 */

class InnerParamArrayModule extends InnerParamModule {
    public ArrayList<InnerParamModule> innerList = new ArrayList<InnerParamModule>();

    private LinearLayout mViewContianer;

    @Override
    protected void onCreate() {
        if (fieldvalue != null){
            for (int nIndex = 0; nIndex < ((Object[])fieldvalue).length; nIndex++){
                Object obj = ((Object[])fieldvalue)[nIndex];

                if (obj != null){
                     ArrayModuleFactory moduleFactory = new ArrayModuleFactory((Object[])fieldvalue, nIndex);
                     InnerParamModule module = InnerParamModuleCreator.create(moduleFactory);

                     if (module != null) {
                         innerList.add(module);
                     }
                }
            }
        }

        super.onCreate();
    }


    @Override
    protected View onCreateView(Context context) {
        mViewContianer = new LinearLayout(context);
        mViewContianer.setOrientation(LinearLayout.VERTICAL);

        TextView text = new TextView(context);
        text.setText(getShowName());

        mViewContianer.addView(text);

        if (innerList != null) {
            for (InnerParamModule module : innerList){
                View child = null;

                if (module != null){
                    child = module.createView(context);
                }

                if (child != null) {
                    mViewContianer.addView(child);
                }
            }
        }

        return mViewContianer;
    }
}
