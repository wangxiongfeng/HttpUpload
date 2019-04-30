package com.cn.skin.attr;

import android.view.View;

import java.util.List;

/**
 * Created by wang on 2018/6/29.
 */

public class SkinView {

    private View mView;

    private List<SkinAttr> mAttrs;   // 背景  文本 ...

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.mView = view;
        this.mAttrs = skinAttrs;
    }

    public void skin() {
        for (SkinAttr attr : mAttrs) {
            attr.skin(mView);
        }
    }

}
