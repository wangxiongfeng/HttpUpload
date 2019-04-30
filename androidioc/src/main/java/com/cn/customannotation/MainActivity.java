package com.cn.customannotation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wang on 2018/7/10.
 * butterknife 源码分析
 *
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectHelper.inject(this);

    }
}
