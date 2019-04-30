package com.cn.rxjava2;

/**
 * Created by wang on 2018/5/31.
 */
public class ObservableMain<T> extends Observable<T> {

    Observable<T> observable; // ObservableSchedulers
    Schedulers schedulers;

    public ObservableMain(Observable<T> observable, Schedulers schedulers) {
        this.observable = observable;
        this.schedulers = schedulers;
    }

    @Override
    protected void subscribeActual(Observer<T> observer) {
        MainTaskObserver mainTaskObserver = new MainTaskObserver(observer, schedulers); // 包装observer
        observable.subscribe(mainTaskObserver);
    }

    private class MainTaskObserver<T> implements Observer<T>, Runnable {

        Observer<T> observer;
        Schedulers schedulers;
        T value;

        public MainTaskObserver(Observer<T> observer, Schedulers schedulers) {
            this.observer = observer;
            this.schedulers = schedulers;
        }

        @Override
        public void onSubscribe() {
            observer.onSubscribe();
        }

        @Override
        public void onNext(T t) {
            value = t;
            //走Schedulers -->  MainSchedulers -->  scheduleDirect方法  Handler  执行run方法
            schedulers.scheduleDirect(this);
        }

        @Override
        public void onError(Throwable e) {
            observer.onError(e);

        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }

        @Override
        public void run() {
            //主线程或者其他
            observer.onNext(value);   // 最下游的observer
        }

    }
}
