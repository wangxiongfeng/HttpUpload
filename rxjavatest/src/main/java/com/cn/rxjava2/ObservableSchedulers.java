package com.cn.rxjava2;

/**
 * Created by wang on 2018/5/31.
 */
public class ObservableSchedulers<T> extends Observable<T> {

    final Observable<T> observable;  // 这里指上游的ObservableMap2

    final Schedulers schedulers;

    public ObservableSchedulers(Observable<T> source, Schedulers schedulers) {
        this.observable = source;
        this.schedulers = schedulers;
    }

    @Override
    protected void subscribeActual(Observer<T> observer) {
        schedulers.scheduleDirect(new SchedulerTask(observer));  // 这里 observer指  MainTaskObserver
    }

    private class SchedulerTask implements Runnable {

        final Observer<T> observer;

        public SchedulerTask(Observer<T> observer) {
            this.observer = observer;
        }

        @Override
        public void run() {
            //线程池最终回来执行 这行代码  会执行上游的subscribe()
            //而这个run方法在子线程中执行
            observable.subscribe(observer);
        }

    }


}
