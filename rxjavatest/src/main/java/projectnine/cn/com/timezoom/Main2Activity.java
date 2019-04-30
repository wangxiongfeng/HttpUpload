package projectnine.cn.com.timezoom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Main2Activity extends AppCompatActivity {

    private Subscription mSubscription;
    private final String TAG="Main2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "发送事件 1");
                e.onNext(1);
                Log.d(TAG, "发送事件 2");
                e.onNext(2);
                Log.d(TAG, "发送事件 3");
                e.onNext(3);
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())   //异步订阅   有缓存区
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mSubscription = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSubscription.request(2); //每次接收两个事件
            }
        });
    }



    private void init(){
        //Rxjava 背压策略   控制事件流速的策略
        // 背压的作用域 = 异步订阅关系，即 被观察者 & 观察者处在不同线程中

        //同步订阅  工作在同一个线程
        //异步订阅  工作在不同线程

        //对于异步订阅关系，存在 被观察者发送事件速度 与观察者接收事件速度 不匹配的情况  如下：
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(10);
                    e.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        //由于被观察者发送事件速度 > 观察者接收事件速度，所以出现流速不匹配问题，从而导致OOM

        //Flowable的基础使用

        //创建被观察者   =flowable
        Flowable<Integer> upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
            }
        }, BackpressureStrategy.BUFFER); // 需要传入背压参数BackpressureStrategy，下面会详细讲解
        // 创建观察者  =subscriber
        Subscriber<Integer> downstream = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(3); //决定观察者能接受多少事件
            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
        //建立订阅关系
        upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(downstream);

    }





}
