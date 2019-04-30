package com.cn.architect3;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by wang on 2018/5/3.
 * 装饰设计模式 对recycler.adapter进行扩展
 */

public class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //原来的recycler.adapter 不支持头部与底部的添加
    private RecyclerView.Adapter mRealAdapter;
    private ArrayList<View> mHeaderView;
    private ArrayList<View> mFooterView;

    public WrapRecyclerAdapter(RecyclerView.Adapter mRealAdapter2) {
        this.mRealAdapter = mRealAdapter2;
        //观察者设计模式   刷新
        mRealAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                notifyDataSetChanged();
            }
        });
        mHeaderView = new ArrayList<>();
        mFooterView = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        //header  头部返回 头部的viewHolder
        int headersNum = getHeadsCount();
        if (position < headersNum) {
            return createHeaderFooterViewHoldder(mHeaderView.get(position));
        }


        //adapter
        int adjPosition = position - headersNum;
        int adapterCount = 0;
        if (mRealAdapter != null) {
            adapterCount = mRealAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mRealAdapter.onCreateViewHolder(parent, mRealAdapter.getItemViewType(adjPosition));
            }
        }


        //footer 尾部返回 尾部的viewHolder
        return createHeaderFooterViewHoldder(mFooterView.get(adjPosition - adapterCount));


    }

    public int getHeadsCount() {
        return mHeaderView.size();
    }


    private RecyclerView.ViewHolder createHeaderFooterViewHoldder(View view) {
        return new RecyclerView.ViewHolder(view) {

        };
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int numHeaders = getHeadsCount();
        if (position < numHeaders) {
            return;
        }
        // Adapter
        final int adjPosition = position - numHeaders;
        if (mRealAdapter != null) {
            int adapterCount = mRealAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                mRealAdapter.onBindViewHolder(holder, adjPosition);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mRealAdapter.getItemCount() + mHeaderView.size() + mFooterView.size();
    }


    @Override
    public int getItemViewType(int position) {
        //把位置作为ViewType
        return position;
    }

    public void addHeadView(View view) {
        if (!mHeaderView.contains(view)) {
            mHeaderView.add(view);
            notifyDataSetChanged();
        }

    }

    public void addFootView(View view) {
        if (!mFooterView.contains(view)) {
            mFooterView.add(view);
            notifyDataSetChanged();
        }
    }

    public void removeHeadView(View view) {
        if (mHeaderView.contains(view)) {
            mHeaderView.remove(view);
            notifyDataSetChanged();
        }
    }

    public void removeFootView(View view) {
        if (mFooterView.contains(view)) {
            mFooterView.remove(view);
            notifyDataSetChanged();
        }
    }

}
