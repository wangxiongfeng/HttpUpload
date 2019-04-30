package com.cn.bufferknife;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import projectnine.cn.com.androidioc.R;

/**
 * Created by wang on 2018/6/26.
 * <p>
 * butterKnife 的使用
 */
public class LoginActivity extends Activity {

    @BindView(R.id.tv1)
    TextView textView1;

    @BindView(R.id.tv2)
    TextView textView2;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mUnbinder = ButterKnife.bind(this);
        textView1.setText("dddddddddd");
        textView2.setText("ttttttttttt");

    }

    // 180775079
    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
