package com.cn.skindemo;

import android.app.Activity;
import android.content.Context;

import com.cn.skin.attr.SkinView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wang on 2018/6/30.
 */

public class SkinManager {

    private static SkinManager instance;
    private Context mContext;
    private Map<Activity, List<SkinView>> mskinviews = new HashMap<>();
    private SkinResource skinResource;

    static {
        instance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return instance;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 加载皮肤
     *
     * @param skinPath
     * @return
     */
    public int loadSkin(String skinPath) {

        //校验签名  （增量更新）

        //加密 复制走

        //初始化资源管理
         skinResource = new SkinResource(mContext, skinPath);
        //改变皮肤
        Set<Activity> keys = mskinviews.keySet();
        for (Activity key : keys) {
            List<SkinView> skinViews = mskinviews.get(key);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }
        }
        return 0;
    }

    /**
     * 恢复默认
     *
     * @return
     */
    public int resetDefault() {
        return 0;
    }

    /**
     * 获取skinview通过activity
     *
     * @param activity
     * @return
     */
    public List<SkinView> getSkinViews(Activity activity) {
        return mskinviews.get(activity);
    }

    /**
     * 注册
     *
     * @param activity
     * @param skinViews
     */
    public void reginster(Activity activity, List<SkinView> skinViews) {
        mskinviews.put(activity, skinViews);
    }

    /**
     * 获取当前皮肤资源
     * @return
     */
    public SkinResource getSkinResource() {
        return skinResource;
    }
}
