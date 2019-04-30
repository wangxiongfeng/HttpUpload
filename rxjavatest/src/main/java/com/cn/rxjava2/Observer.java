package com.cn.rxjava2;

/**
 * Created by wang on 2018/5/30.
 * 观察者
 */

public interface Observer<T> {

    void onSubscribe();

    void onNext(T t);

    void onError(Throwable e);

    void onComplete();

}
