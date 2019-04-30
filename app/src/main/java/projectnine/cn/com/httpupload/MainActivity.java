package projectnine.cn.com.httpupload;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.cn.binary.FileBinary;
import com.cn.binary.OnBinaryProgressListener;
import com.cn.exception.TimeOutError;
import com.cn.util.HttpListener;
import com.cn.util.Request;
import com.cn.util.RequestExecutor;
import com.cn.util.RequestMethod;
import com.cn.util.Response;
import com.cn.util.StringRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.getbnt).setOnClickListener(this);
        findViewById(R.id.postbnt).setOnClickListener(this);
        findViewById(R.id.alibaba).setOnClickListener(this);
        findViewById(R.id.customview).setOnClickListener(this);
        findViewById(R.id.customview).setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.getbnt:
                requestGet();
                break;
            case R.id.alibaba:
                startActivity(new Intent(MainActivity.this, VlayoutActivity.class));
                break;
            case R.id.customview:
                Log.e("--------000", "onClick");


                String g = "ddd";
                char[] sd = g.toCharArray();


                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.customview:
                Log.e("--------000", "onTouch");
                break;
        }
        return true;
    }

    private void requestGet() {

        Request<String> request = new StringRequest("http://www.yanzhenjie.com", RequestMethod.POST);
        request.add("username", "www");
        File file1 = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/image/01.jpg");
        File file2 = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/image/02.jpg");

        FileBinary fileBinary1 = new FileBinary(file1);
        fileBinary1.setProgressListener(1, onBinaryProgressListener);

        FileBinary fileBinary2 = new FileBinary(file2);
        fileBinary2.setProgressListener(2, onBinaryProgressListener);

        request.add("image1", fileBinary1);
        request.add("image2", fileBinary2);

        RequestExecutor.INTANCE.execute(request, new HttpListener<String>() {
            @Override
            public void onSucceed(Response<String> response) {
                String requestbody = response.get();
                Log.e("-------", response.get() + "   " + response.getResponseCode() + "   " + new String(requestbody));
            }

            @Override
            public void onError(Exception e) {
                if (e instanceof TimeOutError) {
                    e.printStackTrace();
                }
                Log.e("-------", "请求失败");
            }
        });
    }


    private OnBinaryProgressListener onBinaryProgressListener = new OnBinaryProgressListener() {
        @Override
        public void onProgress(int what, int progress) {
            Log.e("--------", "第" + what + "  文件上传进度 " + progress);
        }

        @Override
        public void onError(int what) {

        }

    };

    //------get请求代码------
    private void getRequest() {

//        RequestExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                executeGet();
//            }
//        });

    }


    private void executeGet() {
        try {
            URL url = new URL("");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection instanceof HttpURLConnection) {

            }
            urlConnection.setRequestMethod("GET");
//          urlConnection.setRequestProperty();
//          urlConnection.getOutputStream(); //get  head
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = urlConnection.getInputStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                byte[] j = new byte[1024];
                int len = 0;
                while ((len = inputStream.read(j)) != -1) {
                    buffer.write(j, 0, len);
                }
                buffer.close();
                inputStream.close();
                String ss = new String(buffer.toByteArray(), "utf-8");
                Log.e("get", ss);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    //------post请求代码---
    private void postRequest() {
//        RequestExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
    }

    private void executePost() {
        try {
            URL url = new URL("");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection instanceof HttpURLConnection) {

            }
            urlConnection.setRequestMethod("POST");
//          urlConnection.setRequestProperty();
//          urlConnection.getOutputStream(); //get  head
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write("ddd".getBytes());
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = urlConnection.getInputStream();

                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                byte[] j = new byte[1024];
                int len = 0;
                while ((len = inputStream.read(j)) != -1) {
                    buffer.write(j, 0, len);
                }
                buffer.close();
                inputStream.close();
                String ss = new String(buffer.toByteArray(), "utf-8");
                Log.e("get", ss);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }


}
