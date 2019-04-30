package com.cn.okhttpprogress;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;

/**
 * Created by wang on 2018/7/16.
 * //静态代理
 */

public class ExRequestBody extends RequestBody {

    private RequestBody requestBody;
    private long currentLength;
    private UploadProgressListener uploadProgressListener;


    public ExRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public ExRequestBody(RequestBody requestBody, UploadProgressListener uploadProgressListener) {
        this.requestBody = requestBody;
        this.uploadProgressListener = uploadProgressListener;
    }


    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        //上传进度  整的长度
        final long contextLength = contentLength();
        //获取当前泄漏多少数据(BufferedSink  就是 io)

        ForwardingSink forwardingSink = new ForwardingSink(sink) {
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                currentLength += byteCount;
                Log.e("TAG", "  总长度   " + contextLength + "  ：   当前进度" + currentLength);
                uploadProgressListener.uploadProgress(currentLength, contextLength);
                super.write(source, byteCount);
            }
        };
        BufferedSink bufferedSink = Okio.buffer(forwardingSink);
        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }


    //又来一个代理
//    private  class SSkin implements   BufferedSink{
//
//
//
//    }


}
