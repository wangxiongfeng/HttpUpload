package com.cn.skindemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;

import com.cn.skin.attr.SkinAttr;
import com.cn.skin.attr.SkinView;
import com.cn.utils.SkinAttrSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LayoutInflaterFactory {

    private SkinAppCompatViewInflater mAppCompatViewInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory(layoutInflater, this);

        super.onCreate(savedInstanceState);

        SkinManager.getInstance().init(this);

        setContentView(R.layout.activity_main);

    }


    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        //拦截 view的创建  获取view之后要去解析
        // 1 创建view
        // If the Factory didn't handle it, let our createView() method try
        View view = createView(parent, name, context, attrs);
        // 2 解析属性
        Log.e("mainactivity", view + "");
        //一个activity的布局肯定对应多个这样的skinview
        if (view != null) {
            List<SkinAttr> skinAttrs = SkinAttrSupport.getSkinAttrs(this, attrs);
            SkinView skinView = new SkinView(view, skinAttrs);
            // 3 统一交给skinmanager管理
            managerSkinView(skinView);
        }
        return view;
    }


    public void skin(View view) { //换肤
        String skinPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "red.skin";
        //
        int result = SkinManager.getInstance().loadSkin(skinPath);

    }


    public void skin1(View view) { //回复默认
        int result = SkinManager.getInstance().resetDefault();

    }


    public void skin2(View view) { //跳转
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    /**
     * 统一管理skinview
     *
     * @param skinView
     */
    private void managerSkinView(SkinView skinView) {
        List<SkinView> skinViews = SkinManager.getInstance().getSkinViews(this);
        if (skinViews == null) {
            skinViews = new ArrayList<>();
            SkinManager.getInstance().reginster(this, skinViews);
        }
        skinViews.add(skinView);
    }


    public View createView(View parent, final String name, @NonNull Context context,
                           @NonNull AttributeSet attrs) {
        final boolean isPre21 = Build.VERSION.SDK_INT < 21;

        if (mAppCompatViewInflater == null) {
            mAppCompatViewInflater = new SkinAppCompatViewInflater();
        }

        // We only want the View to inherit its context if we're running pre-v21
        final boolean inheritContext = isPre21 && true && shouldInheritContext((ViewParent) parent);

        return mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                isPre21, /* Only read android:theme pre-L (L+ handles this anyway) */
                true /* Read read app:theme as a fallback at all times for legacy reasons */
        );
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        final View windowDecor = getWindow().getDecorView();
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == windowDecor || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }


}
