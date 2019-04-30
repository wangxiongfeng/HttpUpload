package com.cn.okhttpprogress;

import android.content.Context;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wang on 2018/7/16.
 */

public class CacheRequestInterceptor implements Interceptor {

    private Context context;

    public CacheRequestInterceptor(Context context) {
        this.context = context;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!isNet(context)) {
            // 没网 只读缓存
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE).build();
        }
        return chain.proceed(request);
    }

    private boolean isNet(Context context) {
        return true;
    }
}
