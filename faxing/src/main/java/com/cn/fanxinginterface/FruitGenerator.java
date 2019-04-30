package com.cn.fanxinginterface;

public class FruitGenerator<T> implements Generator<T> {

    private  T t;

    @Override
    public T next() {
        return t;
    }


    public void setT(T t){
        this.t=t;
    }



}
