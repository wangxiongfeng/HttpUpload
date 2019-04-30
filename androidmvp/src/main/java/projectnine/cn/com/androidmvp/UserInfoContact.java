package projectnine.cn.com.androidmvp;


import projectnine.cn.com.androidmvp.base.BaseView;
import rx.Observable;

/**
 * Created by wang on 2018/6/11.
 */

public class UserInfoContact {

    interface UserInfoView extends BaseView{  //接口可以继承多个接口
        void  onLoading();
        void  onError();
        void  onSuccess(UserInfo userInfo);
    }

    interface UserInfoPresenter{
        void  getUsers(String token);
    }


    interface UserInfoModel{
       Observable<UserInfo> getUsers(String token);
    }


}
