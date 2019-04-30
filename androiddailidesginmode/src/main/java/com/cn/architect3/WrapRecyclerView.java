package com.cn.architect3;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wang on 2018/5/3.
 */

public class WrapRecyclerView extends RecyclerView {


    private WrapRecyclerAdapter wrapRecyclerAdapter;

    public void setAdapter2(MainActivity.RecyclerAdapter recyclerAdapter) {
        wrapRecyclerAdapter = new WrapRecyclerAdapter(recyclerAdapter);
        setAdapter(wrapRecyclerAdapter);
    }

    public WrapRecyclerView(Context context) {
        this(context, null);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addHeadView(View view) {
        if (wrapRecyclerAdapter != null) {
            wrapRecyclerAdapter.addHeadView(view);
        }
    }

    public void addFootView(View view) {
        if (wrapRecyclerAdapter != null) {
            wrapRecyclerAdapter.addFootView(view);
        }
    }

    public void removeHeadView(View view) {
        if (wrapRecyclerAdapter != null) {
            wrapRecyclerAdapter.removeHeadView(view);
        }
    }

    public void removeFootView(View view) {
        if (wrapRecyclerAdapter != null) {
            wrapRecyclerAdapter.removeFootView(view);
        }
    }


}
