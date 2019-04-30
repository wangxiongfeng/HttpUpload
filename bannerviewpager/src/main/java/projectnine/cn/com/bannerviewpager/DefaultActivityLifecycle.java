package projectnine.cn.com.bannerviewpager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

/**
 * Created by wang on 2018/4/16.
 */

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
@RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class DefaultActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

}
