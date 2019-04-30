package projectnine.cn.com.retrofitrxjavaokhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

        Call<MovieSubject> call = movieService.getTop250(0, 20);
        call.enqueue(new Callback<MovieSubject>() {
            @Override
            public void onResponse(Call<MovieSubject> call, Response<MovieSubject> response) {
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();
//                mMovieAdapter.setMovies(response.body().subjects);
//                mMovieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieSubject> call, Throwable t) {
                Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_LONG).show();
            }
            
        });
    }

    private void rxjava() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())  //GsonConverterFactory 是默认提供的Gson 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);

        Subscription subscription = movieService.getTop250rxjava(0, 20)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieSubject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieSubject movieSubject) {
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();
//                        mMovieAdapter.setMovies(movieSubject.subjects);
//                        mMovieAdapter.notifyDataSetChanged();
                    }
                });

    }


    private void okhttp() {
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间
        builder.addInterceptor(new Interceptor() {  //设置拦截器 打印请求数据和 响应数据
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request request = chain.request();

                okhttp3.Response response = chain.proceed(request);

                return response;
            }
        });
        OkHttpClient okHttpClient = builder.build();

//        // 添加公共参数拦截器
//        BasicParamsInterceptor  basicParamsInterceptor = new BasicParamsInterceptor.Builder()
//                .addHeaderParam("userName","")
//                .addHeaderParam("device","") .build();
//        builder.addInterceptor(basicParamsInterceptor);

        Retrofit mRetrofit = new Retrofit.Builder()
                //添加okhttpclient 不添加 就是默认光杆okhttpclient
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).build();
        final MovieService movieService = mRetrofit.create(MovieService.class);
        Observable observable = movieService.getTop250rxjava(0, 20);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieSubject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieSubject movieSubject) {
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();
                    }
                });

    }


}
