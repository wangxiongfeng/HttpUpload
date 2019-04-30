package projectnine.cn.com.androidiocclibrary;

import android.app.Activity;
import android.view.View;

/**
 * Created by wang on 2018/4/18.
 * view的findviewbyid的辅助类
 */

public class ViewFinder {

    private Activity activity;

    private View view;

    public ViewFinder(Activity activity) {
        this.activity = activity;
    }

    public ViewFinder(View view) {
        this.view = view;
    }

    public View findViewById(int viewId) {
        return activity != null ? activity.findViewById(viewId) : view.findViewById(viewId);
    }


}
