package com.cn.fanxinginterface;

public class A<T> extends FruitGenerator<T> {

    private T t;

    @Override
    public T next() {
        return t;
    }
}
