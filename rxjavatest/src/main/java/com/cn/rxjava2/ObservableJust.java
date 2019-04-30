package com.cn.rxjava2;

/**
 * Created by wang on 2018/5/30.
 */

public class ObservableJust<T> extends Observable<T> {

    private T item;   // value

    public ObservableJust(T item) {
        this.item = item;   //url
    }


    @Override
    protected void subscribeActual(Observer<T> observer) {  // observer指 mapObserver
        //代理对象  方便代码扩展
        ProxyTest proxyTest = new ProxyTest(observer, item);
        observer.onSubscribe();
        proxyTest.run();
    }


    // 静态代理
    public class ProxyTest<T> {

        private Observer<T> observer;
        private T item;

        public ProxyTest(Observer<T> observer, T item) {
            this.observer = observer;
            this.item = item;
        }

        public void run() {
            try {
                observer.onNext(item);
            } catch (Exception e) {
                observer.onError(e);
            }
            observer.onComplete();
        }

    }


}
