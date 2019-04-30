package com.cn.myeventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cn.eventbean.MyEvent;


import projectnine.cn.com.eventbustest.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MyEvent(" activitity bnt clicked"));
                //Sticky事件只指事件消费者在事件发布之后才注册的也能接收到该事件的特殊类型

                finish();



            }
        });

    }
}
