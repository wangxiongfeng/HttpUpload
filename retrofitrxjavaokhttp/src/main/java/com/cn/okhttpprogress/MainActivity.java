package com.cn.okhttpprogress;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import projectnine.cn.com.retrofitrxjavaokhttp.R;

/**
 * Created by wang on 2018/7/16.
 * okhttp 监听上传文件进度   30s 缓存
 */

public class MainActivity extends AppCompatActivity {

    private File file;
    private OkHttpClient httpClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//        RxPermissions rxPermissions = new RxPermissions(this);
//        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean aBoolean) throws Exception {
//                        if (aBoolean) {
//                            //权限申请   并且用户给了权限
//
//                        }
//                    }
//                });


        //上传的文件
        file = new File(Environment.getExternalStorageDirectory(), "fix.dex");
        httpClient = new OkHttpClient();
        uploadFile();
    }


    private void uploadFile() {

        String url = "https://api.saiwuquan.com/api/appv2/sceneModel"; // 404

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        builder.addFormDataPart("platform", "android");
        builder.addFormDataPart("file", file.getName()
                , RequestBody.create(MediaType.parse(guessMimeType(file.getAbsolutePath())), file));
        RequestBody requestBody = builder.build();
        //静态代理  做监听
        ExRequestBody exRequestBody = new ExRequestBody(requestBody, new UploadProgressListener() {
            @Override
            public void uploadProgress(long currentLength, long contextLength) {

            }
        });
        final Request request = new Request.Builder().url(url).post(exRequestBody).build(); //post请求
        Call call = httpClient.newCall(request);

//        Response response=call.execute();

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null) {
                    Log.e("TAG", " --- " + response.networkResponse());
                }
            }
        });

    }


    /**
     * 获得上传文件的类型
     *
     * @param filePath
     * @return
     */
    private String guessMimeType(String filePath) {

        Log.e("mimType", filePath + "");
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimType = fileNameMap.getContentTypeFor(filePath);
        Log.e("mimType", mimType + "");
        if (TextUtils.isEmpty(mimType)) {
            return "application/octet-stream";
        }
        return mimType;

    }


}
