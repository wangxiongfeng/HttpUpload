package com.cn.rxjava2;


import io.reactivex.functions.Function;

/**
 * Created by wang on 2018/5/30.
 * 被观察者
 * <p>
 */
public abstract class Observable<T> implements ObservableSource<T> {

    /**
     * 泛型方法  <T>
     * @param source
     * @param <T>
     * @return 什么也没干
     */
    private static <T> Observable<T> onAssemply(Observable<T> source) {
        return source;
    }

    /**
     * 抽象类可以不实现接口里的方法
     * @param observer
     */
    @Override
    public void subscribe(Observer<T> observer) {
        subscribeActual(observer);
    }

    protected abstract void subscribeActual(Observer<T> observer);

    public static <T> Observable<T> just(T item) {   // ObservaleJust
        return onAssemply(new ObservableJust<T>(item));
    }


    public <R> Observable<R> map(Function<T, R> function) {   //  map操作符   this 表示 上一层的Observable 提前设置参数
        return onAssemply(new ObservableMap<>(this, function));
    }

    public Observable<T> subscribeOn(Schedulers schedulers) {   // 子线程
        return onAssemply(new ObservableSchedulers(this, schedulers));
    }


    public Observable<T> observeOn(Schedulers schedulers) {  //主线程
        return onAssemply(new ObservableMain(this, schedulers));
    }



}
