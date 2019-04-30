package projectnine.cn.com.androidmvp;

import android.widget.TextView;

import projectnine.cn.com.androidmvp.base.BaseMvpActivity;

public class MainActivity extends BaseMvpActivity<UserInfoPresenter> implements UserInfoContact.UserInfoView {

    private TextView mUserinfoTv;

    //多Presenter 处理问题
    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    protected void initData() {  //  getmPresenter()获取 父类中的 presenter
        getmPresenter().getUsers("");
    }

    @Override
    protected void initView() {
        mUserinfoTv = (TextView) findViewById(R.id.tv);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }


    @Override
    public void onLoading() {

    }

    @Override
    public void onError() {
    }

    @Override
    public void onSuccess(UserInfo userInfo) {
        //成功  这个时候Activity有可能会被关掉
        // 采用解绑（通用）
        mUserinfoTv.setText(userInfo.userName);
    }

}
