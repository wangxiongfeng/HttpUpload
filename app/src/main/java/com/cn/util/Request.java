package com.cn.util;

import android.text.TextUtils;

import com.cn.binary.Binary;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by wang on 2018/3/7.
 */

public abstract class Request<T> {

    private String url;

    private RequestMethod method;

    private List<KeyValue> keyValues;

    public Request(String url) {
        this(url, RequestMethod.GET);
    }

    private SSLSocketFactory sslSocketFactory;

    private HostnameVerifier hostnameVerifier;


    /**
     * 设置ssl证书
     *
     * @param sslSocketFactory
     */
    public void setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
    }

    /**
     * 设置服务器主机认证规则
     *
     * @param hostnameVerifier
     */
    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    public Request(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
        mRequestHead = new HashMap<>();
        keyValues = new ArrayList<>();
    }


    public void add(String key, int value) {
        keyValues.add(new KeyValue(key, Integer.toString(value)));
    }

    public void add(String key, long value) {
        keyValues.add(new KeyValue(key, Long.toString(value)));
    }

    public void add(String key, String value) {
        keyValues.add(new KeyValue(key, value));
    }

    /**
     * 添加文件
     *
     * @param key
     * @param value
     */
    public void add(String key, Binary value) {
        keyValues.add(new KeyValue(key, value));
    }

    @Override
    public String toString() {
        return "url=" + url + "  method=" + method + "  params=" + keyValues.toString();
    }

    /**
     * 拿到请求的完整的url
     *
     * @return
     */
    public String getUrl() {
        StringBuilder urlstring = new StringBuilder(url);
        String params = buildParamsString();
        if (!method.isOutputMethod()) {
            //1 url:http://www.hhhhh.com?name=123
            if (params.length() > 0 && url.contains("?") && url.contains("=")) {
                urlstring.append("&");
            } else if (params.length() > 0 && url.endsWith("?")) { //2 http://www.hhhhh.com
                urlstring.append("?");
            }
            urlstring.append(params);
        }
        return urlstring.toString();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    List<KeyValue> getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(List<KeyValue> keyValues) {
        this.keyValues = keyValues;
    }


    private Map<String, String> mRequestHead;
    private String mContentType;
    private boolean enableFormData;
    private String mCharSet = "utf-8";

    private String boundary = "IMooc" + UUID.randomUUID();
    private String startboundary = "--" + boundary;
    private String endboundary = startboundary + "--";

    public void setHeader(String key, String value) {
        mRequestHead.put(key, value);
    }

    /**
     * 设置contentType
     *
     * @param mContentType
     */
    public void setContentType(String mContentType) {
        this.mContentType = mContentType;
    }

    /**
     * 拿到请求头
     *
     * @return
     */
    public Map<String, String> getmRequestHead() {
        return mRequestHead;
    }

    /**
     * 提交参数的编码格式
     *
     * @param mCharSet
     */
    public void setCharSet(String mCharSet) {
        this.mCharSet = mCharSet;
    }

    /**
     * @return
     */
    public String getContentType() {
        if (!TextUtils.isEmpty(mContentType)) {
            return mContentType;
        } else if (enableFormData || hasFile()) {  //是否强制表单提交 文件提交
            return "multipart/form-data;boundary=" + boundary;
        }
        //一般提交
        return "application/x-www-form-urlencoded";
    }


    /**
     * 判断是否有文件
     *
     * @return
     */
    protected boolean hasFile() {
        for (KeyValue keyValue : keyValues) {
            Object value = keyValue.getValue();
            if (value instanceof Binary) {
                return true;
            }
        }
        return false;
    }


    /**
     * 拿到包体的大小
     *
     * @return
     */
    public long getContentLength() {
        //  post 类型请求的时候才需要知道 一般是上传文件的时候
        //  form  :1:普通string的表单  2 带文件的表单
        CounterOutputStream counterOutputStream = new CounterOutputStream();
        try {
            onWriteBody(counterOutputStream);
        } catch (Exception e) {
            return 0;
        }
        return counterOutputStream.get();
    }

    /**
     * 写出包体的方法
     *
     * @param outputStream
     */
    public void onWriteBody(OutputStream outputStream) {
        try {
            if (enableFormData || hasFile()) {  //有表单或文件
                writeFormData(outputStream);
            } else {
                writeStringData(outputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 写出普通数据
     *
     * @param counterOutputStream
     */
    private void writeStringData(OutputStream counterOutputStream) {
        try {
            String params = buildParamsString();
            counterOutputStream.write(params.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写出表单数据
     *
     * @param counterOutputStream
     */
    private void writeFormData(OutputStream counterOutputStream) {

        try {
            for (KeyValue keyValue : keyValues) {
                String key = keyValue.getKey();
                Object value = keyValue.getValue();
                if (value instanceof Binary) {
                    writeFormFileData(counterOutputStream, key, (Binary) value);
                } else {
                    writeFormStringData(counterOutputStream, key, (String) value);
                }
                counterOutputStream.write("\r\n".getBytes());
            }
            counterOutputStream.write(endboundary.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 写出表单中的string item
     *
     * @param counterOutputStream
     * @param key
     * @param value
     */
    private void writeFormStringData(OutputStream counterOutputStream, String key, String value) {

        StringBuilder builder = new StringBuilder();
        builder.append(startboundary).append("\r\n");
        builder.append("Content-Disposition: form-data; name=\"")
                .append(key).append("\"").append("\r\n");
        builder.append("Content-Type: text/plain;charset=\"")
                .append(mCharSet).append("\"").append("\r\n");
        builder.append("\r\n\r\n");
        builder.append(value);
        try {
            counterOutputStream.write(builder.toString().getBytes(mCharSet));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 写出表单中的文件item
     *
     * @param counterOutputStream
     * @param key
     * @param value
     */
    private void writeFormFileData(OutputStream counterOutputStream, String key, Binary value) {

        //(请求体1)-boundary
        //Content-Disposition: form-data;name="keyname";filename="1.jpg"
        //Content-Type:image/jpeg;
        //
        //file stream
        //(请求体2)-boundary
        //Content-Disposition: form-data;name="keyname";filename="1.jpg"
        //Content-Type:image/jpeg;
        //
        //file stream
        //(请求结束标志)--boundary--

        String fileName = value.getFileName();
        String mimeType = value.getMimeType();

        StringBuilder builder = new StringBuilder();
        builder.append(startboundary).append("\r\n");
        builder.append("Content-Disposition: form-data; name=\"")
                .append(key).append("\";filename=\"")
                .append(fileName).append("\"").append("\r\n");
        builder.append("Content-Type: ").append(mimeType);
        builder.append("\r\n\r\n");

        String ss = startboundary + "\r\n" + "Content-Disposition: form-data; name=\"" + key + "\";filename=\"" + fileName + "\"" + "\r\n" +
                "Content-Type: " + mimeType + "\r\n";

        try {
            counterOutputStream.write(builder.toString().getBytes(mCharSet));
            if (counterOutputStream instanceof CounterOutputStream) {
                ((CounterOutputStream) counterOutputStream).write(value.getBinaryLength());
            } else {
                value.onWriteBinary(counterOutputStream);  //写入文件
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 构建用户添加的所有string参数
     *
     * @return
     */
    protected String buildParamsString() {
        StringBuilder builder = new StringBuilder();
        for (KeyValue keyValue : keyValues) {
            Object value = keyValue.getValue();
            if (value instanceof String) {
                builder.append("&");
                try {
                    builder.append(URLEncoder.encode(keyValue.getKey(), mCharSet));
                    builder.append("=");
                    builder.append(URLEncoder.encode((String) value, mCharSet));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        if (builder.length() > 0) {
            //结果是 &key=value&key1=value1
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    /**
     * 是否强制表单提交
     *
     * @param enable
     */
    public void formData(boolean enable) {
        if (!method.isOutputMethod()) {
            throw new IllegalArgumentException(method.value());
        }
        enableFormData = enable;
    }

    /**
     * 解析服务器的数据
     *
     * @param responseBody
     * @return
     */
    public abstract T parseResponse(byte[] responseBody);

}
