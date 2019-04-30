package com.cn.retrofitsimple2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wang on 2018/5/2.
 *
 * 请求后台数据的请求
 */

public interface MovieService {

    @GET("top250")   //尾址top250
    Call<Result<UserInfo>> getTop250(@Query("start") int start, @Query("count") int count);

}
