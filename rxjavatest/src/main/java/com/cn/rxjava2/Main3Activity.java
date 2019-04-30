package com.cn.rxjava2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.functions.Function;
import projectnine.cn.com.timezoom.R;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main3);

        Observable.just("url")  // 返回ObservableJust   继承 Observable  实现 ObservableSource 接口
                // 接口中的subscribe在抽象类Observable中实现了  并调用ObservableJust里的 subscribeActual -> onNext() 最终调Observer的onNext()方法
                .map(new Function<String, Bitmap>() {  //返回 ObservableMap1 继承 Observable  里面包含 ObservableJust  MapObserver(MapObserver(Observer))
                    @Override
                    public Bitmap apply(String s) throws Exception {
                        Log.e("TAG1", Thread.currentThread().getName() + "   子线程");
                        return null;
                    }
                })  //ObservableMap 中的 subscribeActual 不断调上一层的中的subscribeActual  并包装Observer&MapObserver给上一层
                .map(new Function<Bitmap, Bitmap>() {  // 返回 ObservableMap2 继承 Observable  里面包含 ObservableMap1   MapObserver(Observer)
                    @Override
                    public Bitmap apply(Bitmap bitmap) throws Exception {
                        Log.e("TAG2", Thread.currentThread().getName() + "   子线程");
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())   // 返回 ObservableSchedulers 里面包含 ObservableMap2
                .observeOn(Schedulers.mainThread())  // 返回 ObservableMain 里面包含 ObservableSchedulers
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe() {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        Log.e("TAG3", Thread.currentThread().getName() + "   主线程");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
