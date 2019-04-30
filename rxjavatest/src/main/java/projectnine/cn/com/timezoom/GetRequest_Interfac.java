package projectnine.cn.com.timezoom;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by wang on 2018/3/19.
 */

public interface GetRequest_Interfac {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getCall();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation2> getCall2();
}
