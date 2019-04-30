package projectnine.cn.com.timezoom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Rxjava";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        init();
//        netinit();
//        mapinit();
//        netinit2();


//        combineinit();
        combinedatasourse();


    }


    /**
     * 将事件的参数从 整型 变换成 字符串类型 为例子说明
     */
    private void mapinit() {

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).map(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) throws Exception {
                return o + "  sss";
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Log.e(TAG, o.toString());
            }
        });

    }


    private void init() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "对Next事件" + value + "作出响应");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });

    }


    /**
     * 网络请求
     */
    private void netinit() {
        // 参数说明：
        // 参数1 = 第1次延迟时间；
        // 参数2 = 间隔时间数字；
        // 参数3 = 时间单位；
        Observable s = Observable.interval(3, 1, TimeUnit.SECONDS);
        s.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Long value) {

                Log.d(TAG, "接收到了事件" + value);
                if (value == 10) {

                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });


        Observable.interval(2, 1, TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.d(TAG, "第 " + aLong + " 次轮询");
                // a. 创建Retrofit对象
                Retrofit retrofit = new Retrofit.Builder().baseUrl("url").addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
                // b. 创建 网络请求接口 的实例
                GetRequest_Interfac request = retrofit.create(GetRequest_Interfac.class);

                Observable<Translation> d = request.getCall();
                d.subscribeOn(Schedulers.io())  // 切换到IO线程进行网络请求
                        .observeOn(AndroidSchedulers.mainThread()) // 切换回到主线程 处理请求结果
                        .subscribe(new Observer<Translation>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Translation value) {
                                value.show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "onerror");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    /**
     * 网络请求嵌套回調
     */
    private void netinit2() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("url").addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        GetRequest_Interfac request = retrofit.create(GetRequest_Interfac.class);
        Observable<Translation> observable1 = request.getCall();
        final Observable<Translation2> observable2 = request.getCall2();
        observable1.

                subscribeOn(Schedulers.io())   //之前在子线程

                .observeOn(AndroidSchedulers.mainThread())//之后再主线程中执行

                .doOnNext(new Consumer<Translation>() {
                    @Override
                    public void accept(Translation translation) throws Exception {
                        Log.e(TAG, "第一次请求成功");
                    }
                })
                .observeOn(Schedulers.io()) //切换到io线程
                .flatMap(new Function<Translation, ObservableSource<Translation2>>() {
                    @Override
                    public ObservableSource<Translation2> apply(Translation translation) throws Exception {
                        // 将网络请求1转换成网络请求2，即发送网络请求2
                        return observable2;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())  //切换到住线程
                .subscribe(new Consumer<Translation2>() {
                    @Override
                    public void accept(Translation2 o) throws Exception {
                        Log.e(TAG, "第二次请求成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "请求失败");
                    }
                });


    }


    /**
     * 组合 / 合并操作符
     */
    private void combineinit() {

        // concat（） / concatArray（）   注：串行执行
        //concat（）组合被观察者数量≤4个，而concatArray（）则可＞4个
        Observable.concat(Observable.just(1, 2, 3), Observable.just(3, 4, 5), Observable.just(4, 7, 8)).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "接收到了事件" + value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        });

        //merge（） / mergeArray（） 按时间线并行执行
        Observable.merge(Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS),
                // 从0开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
                Observable.intervalRange(6, 3, 1, 2, TimeUnit.SECONDS))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件---" + value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // concatDelayError（） / mergeDelayError（）  第1个被观察者发送Error事件后，第2个被观察者则不会继续发送事件


        //Zip（）  合并多个事件
        Observable<Integer> observice1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
            }
        }).subscribeOn(Schedulers.io());

        Observable<Integer> observice2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(2);
            }
        }).subscribeOn(Schedulers.newThread());
        //注：创建BiFunction对象传入的第3个参数 = 合并后数据的数据类型
        Observable.zip(observice1, observice2, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "接收到了事件---rrr" + value);

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {

            }
        });


        // combineLatest()
//        与Zip（）的区别：Zip（） = 按个数合并，即1对1合并；CombineLatest（） = 按时间合并，即在同一个时间点上合并


    }


    String result = "";

    /**
     * 合并数据源  同时展示
     */
    private void combinedatasourse() {
        Observable<String> network = Observable.just("网络");
        Observable<String> file = Observable.just("本地文件");
        Observable.merge(network, file).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "数据源有： " + value);
                result += value + " + ";

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, result);
            }
        });

    }


    /**
     * 功能防抖
     */
    private void fangdou() {

        Button btn = (Button) findViewById(R.id.button);
        RxView.clicks(btn).throttleFirst(2, TimeUnit.SECONDS) // 两秒内只发生一次请求
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        Log.e(TAG, "发送了网络请求");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "发送了网络请求");
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "发送了网络请求");
                    }

                });


    }

    /**
     * 过滤操作符
     */
    private void filter() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onNext(5);
            }
        }).filter(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                return integer > 3;
                // 本例子 = 过滤了整数≤3的事件
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.d(TAG, "获取到的整型事件元素是： " + integer);
            }
        });

        Observable.just(1, "dd", "ho", 5).ofType(Integer.class).subscribe(new Consumer<Integer>() {  //简便式的观察者模式
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.d(TAG, "获取到的整型事件元素是： " + integer);
            }
        });

        //skip（） / skipLast（）
        //distinct（） / distinctUntilChanged（）
        Observable.just(1, 1, 2, 3, 3).distinct().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {

            }
        });

        //take（2）指定观察者最多能接受事件的数量
        //takeLast（2） 指定观察者接受被观察者发送的最后几个事件
        //throttleFirst（）/ throttleLast（）在某段时间内，只发送该段时间内第1次事件 / 最后1次事件
        //Sample（） 在某段时间内，只发送该段时间内最新（最后）1次事件
        //throttleWithTimeout （） / debounce（） 发送数据事件时，若2次发送事件的间隔＜指定时间，就会丢弃前一次的数据，直到指定时间内都没有新数据发射时才会发送后一次的数据


    }


}
