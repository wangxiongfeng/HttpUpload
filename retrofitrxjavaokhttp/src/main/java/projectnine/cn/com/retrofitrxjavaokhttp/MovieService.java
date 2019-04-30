package projectnine.cn.com.retrofitrxjavaokhttp;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wang on 2018/5/2.
 *
 * 请求后台数据的请求
 */

public interface MovieService {

    //接口涉及带解耦  没有任何实现代码

    @GET("top250")   //尾址top250
    Call<MovieSubject> getTop250(@Query("start") int start,@Query("count")int count);


    //注意：使用POST 方式时注意2点，1，必须加上 @FormUrlEncoded标签，否则会抛异常。2，使用POST方式时，必须要有参数，否则会抛异常,
    @FormUrlEncoded
    @POST("top250")   //尾址top250
    Call<MovieSubject> getTop250post(@Query("start") int start,@Query("count")int count);



    // 配合RxJava 使用
    @GET("top250")   //尾址top250
    Observable<MovieSubject> getTop250rxjava(@Query("start") int start, @Query("count")int count);


}
