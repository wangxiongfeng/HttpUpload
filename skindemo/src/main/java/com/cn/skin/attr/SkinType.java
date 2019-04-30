package com.cn.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.skindemo.SkinManager;
import com.cn.skindemo.SkinResource;

/**
 * Created by wang on 2018/6/29.
 */

public enum SkinType {

    TEXT_COLOR("textColor") {
        @Override
        public void skin(View view, String resName) {
            SkinResource skinResource = getSkinResource();
            ColorStateList color = skinResource.getColorByName(resName);
            if (color == null) {
                return;
            }

            TextView textView = (TextView) view;
            textView.setTextColor(color);
        }
    }, BACKGROUND("background") {
        @Override
        public void skin(View view, String resName) {
            //背景是图片 也可能是颜色
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(resName);
            if (drawable != null) {
                ImageView imageView = (ImageView) view;
                imageView.setBackgroundDrawable(drawable);
                return;
            }

            //可能是颜色
            ColorStateList color = skinResource.getColorByName(resName);
            if (color != null) {
                view.setBackgroundColor(color.getDefaultColor());
                return;
            }
        }
    }, SRC("src") {
        @Override
        public void skin(View view, String resName) {
            //获取资源
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(resName);
            if (drawable != null) {
                ImageView imageView = (ImageView) view;
                imageView.setImageDrawable(drawable);
                return;
            }

        }
    };

    SkinType(String resName) {
        this.resName = resName;
    }

    //根据名字调对应的方法
    private String resName;


    public abstract void skin(View view, String resName);

    public String getResName() {
        return resName;
    }


    public SkinResource getSkinResource() {
        return SkinManager.getInstance().getSkinResource();
    }
}
