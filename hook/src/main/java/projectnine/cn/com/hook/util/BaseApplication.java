package projectnine.cn.com.hook.util;

import android.app.Application;

import projectnine.cn.com.hook.ProxyActivity;
import projectnine.cn.com.hook.Start2Activity;

/**
 * Created by wang on 2018/5/17.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HookStartActivityUtil hookStartActivityUtil = new HookStartActivityUtil(this, ProxyActivity.class);
        try {
            hookStartActivityUtil.hookStartActivity();
            hookStartActivityUtil.hookLaunchActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
