package com.cn.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wang on 2018/3/7.
 */

public enum RequestExecutor {

    /**
     * 单例线程池
     */
    INTANCE;

    private ExecutorService sExecutorService;

    RequestExecutor() {
        sExecutorService = Executors.newSingleThreadExecutor();

    }

    public void execute(Request request, HttpListener httpListener) {
        sExecutorService.execute(new RequestTask(request, httpListener));
    }


}
