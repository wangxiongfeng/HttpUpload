package com.cn.rxjava2;

/**
 * Created by wang on 2018/5/30.
 */

public interface ObservableSource<T> {

    void subscribe(Observer<T> observer);   // ? 为T 的父类  泛型的上下边界

}
