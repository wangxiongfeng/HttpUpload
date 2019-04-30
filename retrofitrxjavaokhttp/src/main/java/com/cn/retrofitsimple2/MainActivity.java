package com.cn.retrofitsimple2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import projectnine.cn.com.retrofitrxjavaokhttp.R;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://api.douban.com/v2/movie/";
    private static final long DEFAULT_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                //添加解析转化工厂
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieService movieService = retrofit.create(MovieService.class);
        Call<Result<UserInfo>> call = movieService.getTop250(0, 20);
        call.enqueue(new HttpCallBack<UserInfo>(){
            @Override
            public void onSuccess(UserInfo result) {
                Toast.makeText(MainActivity.this,result.toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(int code, String msg) {
                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
            }
        });
    }


}
