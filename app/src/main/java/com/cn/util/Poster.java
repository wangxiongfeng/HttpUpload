package com.cn.util;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by wang on 2018/3/7.
 */

public class Poster extends Handler {

    private static Poster instance;

    public static Poster getinstance() {
        if (instance == null) {
            synchronized (Poster.class) {
                if (instance == null) {
                    instance = new Poster();
                }
            }
        }
        return instance;
    }


    private Poster() {
        super(Looper.getMainLooper());
    }


}
