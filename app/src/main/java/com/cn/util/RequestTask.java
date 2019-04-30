package com.cn.util;

import android.util.Log;

import com.cn.exception.TimeOutError;
import com.cn.factory.URLConnectionFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by wang on 2018/3/7.
 */

public class RequestTask<T> implements Runnable {

    private Request<T> request;

    private HttpListener<T> httpListener;

    public RequestTask(Request<T> request, HttpListener<T> httpListener) {
        this.request = request;
        this.httpListener = httpListener;
    }

    @Override
    public void run() {

        Exception exception = null;
        int responseCode = -1;
        String urlStr = request.getUrl();
        //请求方式
        RequestMethod requestMethod = request.getMethod();
        Log.e("-------", urlStr + "  " + requestMethod);
        HttpURLConnection httpURLConnection = null;
        OutputStream outputStream = null;

        byte[] responseData = new byte[2048];
        try {
            //1 建立连接
            URL url = new URL(urlStr);
            // httpURLConnection = (HttpURLConnection) url.openConnection();
            // 一句话区分okhttp和urlconnection
            httpURLConnection = URLConnectionFactory.getInstance().openUrl(url);
            // https处理
            if (httpURLConnection instanceof HttpsURLConnection) {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) httpURLConnection;
                SSLSocketFactory sslSocketFactory = request.getSslSocketFactory();
                if (sslSocketFactory != null) {
                    httpsURLConnection.setSSLSocketFactory(sslSocketFactory); //https证书相关信息
                }
                HostnameVerifier hostnameVerifier = request.getHostnameVerifier();
                if (hostnameVerifier != null) {
                    httpsURLConnection.setHostnameVerifier(hostnameVerifier); //服务器主机认证
                }
            }
            //设置请求头的基础信息
            httpURLConnection.setRequestMethod(requestMethod.value());
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(requestMethod.isOutputMethod());
            setHeader(httpURLConnection, request);
            //2 设置请求体数据
            if (requestMethod.isOutputMethod()) {
                outputStream = httpURLConnection.getOutputStream();  // post  输出流
                request.onWriteBody(outputStream);
            }
            //3 读取响应
            int response = httpURLConnection.getResponseCode();
            if (hasResponseBody(requestMethod, response)) {
                InputStream inputstream = getInputStream(httpURLConnection, response);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int len;
                byte[] ll = new byte[2048];
                while ((len = inputstream.read(ll)) != -1) {
                    byteArrayOutputStream.write(ll, 0, len);
                }
                byteArrayOutputStream.close();
                responseData = byteArrayOutputStream.toByteArray();
            }
        } catch (SocketTimeoutException e) {
            exception = new TimeOutError("超时");
        } catch (Exception e) {
            exception = e;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //4  解析服务器的数据
        T t = request.parseResponse(responseData);
        Response<T> response = new Response<>(request, responseCode, httpURLConnection.getHeaderFields(), exception);
        response.setResposeResult(t);
        //发送响应数据到主线程
        Message message = new Message(response, httpListener);
        Poster.getinstance().post(message);

    }

    /**
     * 判断是否有包体
     *
     * @param method
     * @param resposecose
     * @return
     */
    private boolean hasResponseBody(RequestMethod method, int resposecose) {
        return method != RequestMethod.HEAD  //head请求没有包体
                && !(100 <= resposecose && resposecose < 200) //不在100到200之间
                && (resposecose != 204)
                && (resposecose != 205)
                && !(300 <= resposecose && resposecose < 400);
    }

    /**
     * 拿到服务器的流
     *
     * @param httpURLConnection
     * @param response
     * @return
     */
    private InputStream getInputStream(HttpURLConnection httpURLConnection, int response) {
        InputStream inputstream;
        try {
            if (response >= 400) {
                inputstream = httpURLConnection.getErrorStream();
            } else {
                inputstream = httpURLConnection.getInputStream();
            }
            String contentEncoding = httpURLConnection.getContentEncoding();
            if (contentEncoding != null && contentEncoding.contains("gzip")) {
                inputstream = new GZIPInputStream(inputstream);
            }
            return inputstream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 给URLConnection设置请求头
     *
     * @param httpURLConnection
     * @param request
     */
    private void setHeader(HttpURLConnection httpURLConnection, Request request) {

        Map<String, String> requestHead = request.getmRequestHead();  //得到请求头
        //处理contentType
        String contentType = request.getContentType();
        requestHead.put("Content-Type", contentType);
        long contentLength = request.getContentLength();
        requestHead.put("Content-Length", contentLength + "");
        for (Map.Entry<String, String> stringStringEntry : requestHead.entrySet()) {
            String headkey = stringStringEntry.getKey();
            String headvalue = stringStringEntry.getValue();
            Log.e("eeee", headkey + "       " + headvalue);
            httpURLConnection.setRequestProperty(headkey, headvalue);
        }

    }
}
