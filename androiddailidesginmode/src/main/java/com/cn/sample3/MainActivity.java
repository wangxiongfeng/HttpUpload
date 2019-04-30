package com.cn.sample3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;

import com.cn.sample1.BankWorker;
import com.cn.sample1.Man;

import projectnine.cn.com.androiddailidesginmode.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Dretrofit dretrofit = new Dretrofit();

        ServiceInterface serviceInterface = dretrofit.create(ServiceInterface.class);

        String result=serviceInterface.userLogin();

    }
}
