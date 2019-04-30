package projectnine.cn.com.androidmvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by wang on 2018/6/11.
 */

public abstract class BaseMvpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    private P mPresenter;  //  view 持有表示层

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();

        //创建 p  交给子类  每个activity不一样
        mPresenter = createPresenter();
        mPresenter.attach(this); //綁定presenter
        initView();
        initData();

    }

    //    交给子类创建
    protected abstract P createPresenter();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void setContentView();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detch();
    }


    public P getmPresenter() {
        return mPresenter;
    }
}
