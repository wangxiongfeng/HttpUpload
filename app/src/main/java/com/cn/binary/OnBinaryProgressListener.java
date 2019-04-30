package com.cn.binary;

/**
 * Created by wang on 2018/3/14.
 */

public interface OnBinaryProgressListener {

    void onProgress(int what,int progress);

    void onError(int what);
}
