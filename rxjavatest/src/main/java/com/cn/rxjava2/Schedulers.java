package com.cn.rxjava2;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by wang on 2018/5/31.
 */
public abstract class Schedulers {

    static Schedulers MAIN_THREAD;

    static Schedulers IO;

    //单例模式
    static {
        IO = new IOSchedulers();
        MAIN_THREAD = new MainSchedulers(new Handler(Looper.getMainLooper()));
    }

    public static Schedulers io() {
        return IO;
    }

    public static Schedulers mainThread() {
        return MAIN_THREAD;
    }


    public abstract void scheduleDirect(Runnable schedulerTask);


    private static class IOSchedulers extends Schedulers {

        /**
         定长线程池（FixedThreadPool）
         定时线程池（ScheduledThreadPool ）

         可缓存线程池（CachedThreadPool）
         单线程化线程池（SingleThreadExecutor）
         */

        ExecutorService service;

        public IOSchedulers() {
            service = Executors.newScheduledThreadPool(1, new ThreadFactory() {  //定时线程池
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r);
                }
            });
        }

        @Override
        public void scheduleDirect(Runnable schedulerTask) {
            service.execute(schedulerTask);
        }

    }


    private static class MainSchedulers extends Schedulers {

        private Handler handler;

        public MainSchedulers(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void scheduleDirect(Runnable runnable) {
            // 交给主线程执行
            Message message = Message.obtain(handler, runnable);
            handler.sendMessage(message);
        }

    }
}
