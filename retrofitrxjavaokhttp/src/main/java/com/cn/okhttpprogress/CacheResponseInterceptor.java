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

public class CacheResponseInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);
        response = response.newBuilder().removeHeader("Cache-Control")
                .removeHeader("Pragma")
                .addHeader("Cache-Control", "max-age=" + 30).build();  //max-age 即代表缓存策略  又代表缓存过期时间

        return response;
    }

}
