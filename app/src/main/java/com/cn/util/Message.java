package com.cn.util;

/**
 * Created by wang on 2018/3/7.
 */

public class Message implements Runnable {

    /**
     * 响应
     */
    private Response response;

    private HttpListener httpListener;

    public Message(Response response, HttpListener httpListener) {
        this.response = response;
        this.httpListener = httpListener;
    }

    @Override
    public void run() {
        //这里被回调到主线程
        Exception exception = response.getException();
        if (exception != null) {
            httpListener.onError(exception);
        } else {
            httpListener.onSucceed(response);
        }
    }
}
