package com.cn.architect3;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import projectnine.cn.com.androiddailidesginmode.R;

public class MainActivity extends Activity {

    private WrapRecyclerView mRecyclerView;

    private List<String> mItems;

    Handler handler1, handler2;


    //
    private LocalBroadcastManager mLocalBroadcastManager;   //本地广播管理
    private LocalReceiver mLocalReceiver;

    private class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (WrapRecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mItems = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mItems.add(i + "");
        }

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
        //   inflate(int resource, ViewGroup root, boolean attachToRoot)
        //   当第三个参数为true时，会自动将第一个参数所指定的View添加到第二个参数所指定的View中
        View head = LayoutInflater.from(this).inflate(R.layout.head, mRecyclerView, false);
        //false表示 在 mRecyclerView中 给控件指定layout_width和layout_height 但不添加到mRecyclerView

        TextView tv2 = new TextView(this);
        tv2.setText("foot");
        tv2.setBackgroundColor(Color.GRAY);
        tv2.setTextColor(Color.BLACK);

        mRecyclerView.setAdapter2(recyclerAdapter);
        mRecyclerView.addHeadView(head);
        mRecyclerView.addFootView(tv2);

        initLocalBroadReceiver();
        testHandler();

        Log.e("Tag1--->", mRecyclerView.getMeasuredHeight() + "");  // 0
        mRecyclerView.post(new Runnable() { //保存在queue中  会在dispatchAttachedToWindow（测量完毕之后调用）中执行
            @Override
            public void run() {
                Log.e("Tag2--->", mRecyclerView.getMeasuredHeight() + ""); // onMeasure()设置宽高之后 才能拿到 height
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Tag3--->", mRecyclerView.getMeasuredHeight() + "");  // 0
    }


    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(MainActivity.this).inflate(R.layout.itemview, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.text.setText("position=" + mItems.get(position));
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItems.remove(position);
                    notifyDataSetChanged();

                    Message message = Message.obtain();
                    message.obj = "handler1 " + mItems.get(position);
                    handler1.sendMessage(message);

                    Message message2 = Message.obtain();
                    message2.obj = "handler2 " + mItems.get(position);
                    handler2.sendMessage(message2);

                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }



        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView text;

            public ViewHolder(View itemview) {
                super(itemview);
                text = (TextView) itemview.findViewById(R.id.textview);
            }
        }
    }


    private void initLocalBroadReceiver() {

        mLocalReceiver = new LocalReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.cn.loacalbroadcastreceiver");
        mLocalBroadcastManager.registerReceiver(mLocalReceiver, intentFilter);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        Intent intent = new Intent("com.cn.loacalbroadcastreceiver");
        mLocalBroadcastManager.sendBroadcast(intent);

    }


    @Override
    protected void onDestroy() {
        mLocalBroadcastManager.unregisterReceiver(mLocalReceiver);
        super.onDestroy();
    }

    private void testHandler() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler1 = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        Log.e("handler", (String) msg.obj);
                        super.handleMessage(msg);
                    }
                };
                //
                handler2 = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        Log.e("handler", (String) msg.obj);
                        super.handleMessage(msg);
                    }
                };
                Looper.loop();
            }
        });
        thread.start();
    }

}
