package projectnine.cn.com.androidmvp;


import rx.Observable;

/**
 * Created by wang on 2018/6/11.
 */

public class UserInfoModel implements UserInfoContact.UserInfoModel {

    //访问网络
    @Override
    public Observable<UserInfo> getUsers(String token) {


        return null;
    }
}
