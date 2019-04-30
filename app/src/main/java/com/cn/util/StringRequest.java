package com.cn.util;

/**
 * Created by wang on 2018/3/13.
 */
public class StringRequest extends Request<String> {


    public StringRequest(String url) {
        this(url, RequestMethod.GET);
    }

    public StringRequest(String url, RequestMethod method) {
        super(url, method);
        setHeader("Accept", "*");
    }

    @Override
    public String parseResponse(byte[] responseBody) {
        if (responseBody != null && responseBody.length > 0) {
            return new String(responseBody);
        }
        return null;
    }
}
