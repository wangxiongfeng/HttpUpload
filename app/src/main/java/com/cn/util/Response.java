package com.cn.util;

import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2018/3/7.
 */

public class Response<T> {

    private int responseCode;
    private T result;
    private Exception exception;
    private Request<T> request;

    Response(Request request, int responseCode, Map<String, List<String>> responseHeaders, Exception exception) {
        this.responseCode = responseCode;
        this.exception = exception;
        this.request = request;
        this.responseHeaders = responseHeaders;
    }

    private Map<String, List<String>> responseHeaders;


    public Request getRequest() {
        return request;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    public int getResponseCode() {
        return responseCode;
    }

    /**
     * 设置响应
     *
     * @param result
     */
    void setResposeResult(T result) {
        this.result = result;
    }

    /**
     * 拿到服务器的响应
     *
     * @return
     */
    public T get() {
        return result;
    }

    Exception getException() {
        return exception;
    }

}
