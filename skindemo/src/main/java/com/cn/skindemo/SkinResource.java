package com.cn.skindemo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Created by wang on 2018/6/30.
 */

public class SkinResource {

    //资源都是通过这个获取的
    private Resources resources;
    private String packageName;

    public SkinResource(Context context, String skinPath) {
        try {
            Resources superRes = context.getResources();

            AssetManager asset = null;
            asset = AssetManager.class.newInstance();

            //添加本地下载好的资源文件 native层是c和c++怎么搞的
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            method.invoke(asset, Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "red.skin");
            resources = new Resources(asset, superRes.getDisplayMetrics(), superRes.getConfiguration());

            //skinpath 获取包名
            packageName = context.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public Drawable getDrawableByName(String resName) {

        try {
            int resId = resources.getIdentifier(resName, "drawable", packageName);
            Drawable drawable = resources.getDrawable(resId);
            return drawable;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public ColorStateList getColorByName(String resName) {

        try {
            int resId = resources.getIdentifier(resName, "color", packageName);
            ColorStateList colorStateList = resources.getColorStateList(resId);
            return colorStateList;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
