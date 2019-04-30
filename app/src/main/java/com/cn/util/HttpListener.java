package com.cn.util;

/**
 * Created by wang on 2018/3/7.
 */

public interface HttpListener<T> {

    void onSucceed(Response<T> response);

    void onError(Exception e);

}
