package com.cn.rxjava2;

import io.reactivex.functions.Function;

/**
 * Created by wang on 2018/5/31.
 */

public class ObservableMap<T, R> extends Observable<R> {

    final Observable<T> source;// 前面的 Observable    这里指ObservableJust
    final Function<T, R> function; //当前转换

    public ObservableMap(Observable<T> source, Function<T, R> function) {
        this.source = source;
        this.function = function;
    }

    //  Main3Activity  subscribe时调用
    @Override
    protected void subscribeActual(Observer<R> observer) { // 对 observer 包裹了一层，静态代理包裹
        MapObserver mapObserver = new MapObserver(observer, function); //
        source.subscribe(mapObserver);  // 走指ObservableJust里的subscribeActual
    }

    private class MapObserver<T> implements Observer<T> {

        final Observer<R> observer;  // 下一层 observer
        final Function<T, R> function;

        public MapObserver(Observer<R> source, Function<T, R> function) {
            this.observer = source;
            this.function = function;
        }

        @Override
        public void onSubscribe() {
            observer.onSubscribe();
        }

        @Override
        public void onNext(T item) {  // 4.第四步 function.apply
            try {
                R applyR = function.apply(item); // 6. 第六步，调用 onNext
                observer.onNext(applyR);
            } catch (Exception e) {
                e.printStackTrace();
                observer.onError(e);
            }
        }

        @Override
        public void onError(Throwable e) {
            observer.onError(e);
        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }
    }

}

