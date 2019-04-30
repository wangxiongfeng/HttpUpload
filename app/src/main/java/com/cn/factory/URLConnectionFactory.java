package com.cn.factory;

import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.internal.huc.OkHttpURLConnection;
import okhttp3.internal.huc.OkHttpsURLConnection;

/**
 * Created by wang on 2018/3/8.
 */

public class URLConnectionFactory {

    private static URLConnectionFactory instance;

    public static URLConnectionFactory getInstance() {
        if (instance == null) {
            synchronized (URLConnectionFactory.class) {
                if (instance == null) {
                    instance = new URLConnectionFactory();
                }
            }
        }
        return instance;
    }

    private OkHttpClient okHttpClient;


    public URLConnectionFactory() {
        okHttpClient = new OkHttpClient();
    }

    public HttpURLConnection openUrl(URL url) {
        return openUrl(url, null);
    }

    /**
     * @param url
     * @param proxy 代理设置
     * @return
     */
    public HttpURLConnection openUrl(URL url, Proxy proxy) {
        String protocol = url.getProtocol();
        OkHttpClient copy = okHttpClient.newBuilder().proxy(proxy).build();
        if (protocol.equals("http")) {
            return new OkHttpURLConnection(url, copy);
        }
        if (protocol.equals("https")) {
            return new OkHttpsURLConnection(url, copy);
        }
        throw new IllegalArgumentException("------" + protocol);
    }

}
