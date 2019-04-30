package com.cn.util;

import com.cn.binary.Binary;

/**
 * Created by wang on 2018/3/7.
 */

public class KeyValue {

    private String key;

    private Object value;


    public KeyValue(String key, String value) {

        this.key = key;
        this.value = value;

    }

    public KeyValue(String key, Binary value) {

        this.key = key;
        this.value = value;

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "key=" + key + "  " + "value=" + value;
    }
}
