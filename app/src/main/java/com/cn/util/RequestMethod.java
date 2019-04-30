package com.cn.util;

/**
 * Created by wang on 2018/3/7.
 */

public enum RequestMethod {

    GET("GET"),
    POST("POST"),
    HEAD("HEAD"),
    DELETE("DELETE");

    private String value;

    RequestMethod(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }


    public String value() {
        return value;
    }

    public boolean isOutputMethod() {
        switch (this) {

            case POST:
            case DELETE:
                return true;
            default:
                return false;
        }
    }


}
