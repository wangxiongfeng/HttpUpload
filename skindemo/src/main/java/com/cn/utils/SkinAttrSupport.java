package com.cn.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.cn.skin.attr.SkinAttr;
import com.cn.skin.attr.SkinType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2018/6/29.
 * 皮肤属性解析的支持类
 */

public class SkinAttrSupport {

    /**
     * 获取skinattr的属性
     *
     * @param context
     * @param attrs
     * @return
     */
    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
        // background  src  text
        List<SkinAttr> skinAttrs = new ArrayList<>();
        int length = attrs.getAttributeCount();
        for (int i = 0; i < length; i++) {
            //获取名称 值
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            //只获取重要
            SkinType skinType = getSkinType(attrName);
            if (skinType != null) {
                //资源名称   目前只有attrValue 是一个@int类型
                String resName = getResName(context, attrValue);
                if (TextUtils.isEmpty(resName)) {
                    continue;
                }
                SkinAttr skinAttr = new SkinAttr(resName, skinType);
                skinAttrs.add(skinAttr);
            }
        }
        return skinAttrs;
    }

    /**
     * 根据资源id拿到资源的名称
     *
     * @param context
     * @param attrValue
     * @return
     */
    private static String getResName(Context context, String attrValue) {
        if (attrValue.startsWith("@")) {
            attrValue = attrValue.substring(1);
            int resId = Integer.parseInt(attrValue);
            return context.getResources().getResourceEntryName(resId);
        }
        return null;
    }

    private static SkinType getSkinType(String attrName) {
        SkinType[] skinTypes = SkinType.values();
        for (SkinType skinType : skinTypes) {
            if (skinType.getResName().equals(attrName)) {
                return skinType;
            }
        }
        return null;
    }


}
