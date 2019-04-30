package projectnine.cn.com.androidmvp;

import projectnine.cn.com.androidmvp.base.BasePresenter;
import rx.Subscriber;

/**
 * Created by wang on 2018/6/11.
 */

public class UserInfoPresenter extends BasePresenter<UserInfoContact.UserInfoView> implements UserInfoContact.UserInfoPresenter {

    private UserInfoContact.UserInfoModel mModel;  // 持有model层对象

    public UserInfoPresenter() {
        mModel = new UserInfoModel();
    }

    //解绑  代码写起来越来越多
    //问题 很多代码是公用反复的  attach  detch 每个Presenter都要有
    // view  的attach  detch 每个view都要有

    //拿数据
    @Override
    public void getUsers(String token) {

        getmView().onLoading();
        mModel.getUsers(token).subscribe(new Subscriber<UserInfo>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                  //  每次进来都要判断
//                if (getmView() != null) {
                    // 通用代码View!=null 同一处理 这个是AOP (ASPECTJ,动态代理)
                    getmView().onError();
//                }
            }

            @Override
            public void onNext(UserInfo userInfo) {
//                if (getmView() != null) {
                    getmView().onSuccess(userInfo);
//                }
            }

        });

    }


}
