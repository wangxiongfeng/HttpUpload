package com.cn.okhttpprogress;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import projectnine.cn.com.retrofitrxjavaokhttp.R;

/**
 * Created by wang on 2018/7/16.
 */
public class MainActivity2 extends AppCompatActivity {

    private OkHttpClient httpClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        File cacheFile = new File(Environment.getExternalStorageDirectory(), "cache");
        Cache cache = new Cache(cacheFile, 10 * 1024 * 1024);

        httpClient = new OkHttpClient.Builder()
                .cache(cache)       //  开启缓存
//                .addInterceptor()    拦截器  加在最前
                .addInterceptor(new CacheRequestInterceptor(this))     //  网络监听
                .addNetworkInterceptor(new CacheResponseInterceptor()) //  拦截器 加在最后
                .build();

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 *     ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();
                 *     sThreadLocal.set(new Looper(quitAllowed)); 给当前线程指定Looper
                 *
                 //          public void set(T value) {
                 //                 Thread t = Thread.currentThread();
                 //                 ThreadLocalMap map = getMap(t);  //拿到当前线程的ThreadLocalMap
                 //                 if (map != null)
                 //                 map.set(this, value);  --> set(ThreadLocal key, Object value)
                 //                 else
                 //                 createMap(t, value);
                 //          }
                 *
                 */
                Looper.prepare();


                /**
                 *        Handler里的  mLooper = Looper.myLooper();  -->  sThreadLocal.get();
                 *
                 //         public T get() {
                 //                 Thread t = Thread.currentThread();
                 //                 ThreadLocalMap map = getMap(t);
                 //                 if (map != null) {
                 //                 ThreadLocalMap.Entry e = map.getEntry(this);
                 //                 if (e != null)
                 //                 return (T)e.value;
                 //                 }
                 //                 return setInitialValue();
                 //         }
                 */
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                    }
                };

                /**
                 *    final Looper me = myLooper();
                 *    final MessageQueue queue = me.mQueue;
                 *    for (;;) {
                 *
                 *           ....
                 *          Message msg = queue.next()
                 *           ....
                 *     }
                 */
                Looper.loop();


                /**
                 *   msg.target = this;      msg.target 是handler
                 *   queue.enqueueMessage(msg, uptimeMillis);  消息添加到MessageQueue
                 */
                handler.sendEmptyMessage(0x1001);
            }
        }).start();

    }




    private void download() {
        String url = "https://peertime.oss-cn-shanghai.aliyuncs.com/P49/Attachment/D17592/img-12a5a47722146aaf8f92841e898d0f4e.pdf";

        final Request request = new Request.Builder().url(url).build(); //post请求
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null) {
                    /**
                     * 客户端在请求一个文件的时候，发现自己缓存的文件有 Last Modified ，那么在请求中会包含 If Modified Since ，这个时间就是缓存文件的 Last Modified 。
                     * 因此，如果请求中包含 If Modified Since，就说明已经有缓存在客户端。
                     * 服务端只要判断这个时间和当前请求的文件的修改时间就可以确定是返回 304 还是 200 。
                     */
                    /**
                     * Response{protocol=http/1.1, code=200, message=OK, url=https://peertime.oss-cn-shanghai.aliyuncs.com/P49/Attachment/D17592/img-12a5a47722146aaf8f92841e898d0f4e.pdf}
                     * ---
                     * Response{protocol=http/1.1, code=304, message=Not Modified, url=https://peertime.oss-cn-shanghai.aliyuncs.com/P49/Attachment/D17592/img-12a5a47722146aaf8f92841e898d0f4e.pdf}
                     */
                    Log.e("TAG", response.cacheResponse() + " --- " + response.networkResponse());
                }
            }

        });

    }


}
