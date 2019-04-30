package com.cn.skin.attr;

import android.view.View;

/**
 * Created by wang on 2018/6/29.
 */

public class SkinAttr {


    private String mResName;

    private SkinType mType;

    public SkinAttr(String resName, SkinType skinType) {
        this.mResName=resName;
        this.mType=skinType;
    }

    public void skin(View mView) {
        mType.skin(mView,mResName);
    }

}
