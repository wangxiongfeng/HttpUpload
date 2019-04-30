package projectnine.cn.com.androidandfix;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.alipay.euler.andfix.patch.PatchManager;

/**
 * Created by wang on 2018/4/16.
 */

public class BaseApplication extends Application {

    public static PatchManager mPatchManager;

    @Override
    public void onCreate() {
        super.onCreate();
/*

        //初始化阿里热修复
        mPatchManager = new PatchManager(this);
        //拿到当前应用的版本
        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            mPatchManager.init(info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //加载之前的apatch 包
        mPatchManager.loadPatch();
*/

        FixDexManager fixDexManager=new FixDexManager(this);
        //加载所有修复的dex包
        try {
            fixDexManager.loadFixDex();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    //给bitmap加水印
    void addText(Bitmap bitmap, Bitmap waterbitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawBitmap(waterbitmap, 3, 5, null);
        //  保存
        canvas.save(Canvas.ALL_SAVE_FLAG);
        //回到上一个save调用之前的状态，如果restore调用的次数大于save方法，会出错。
        //存储
        canvas.restore();
    }





}
