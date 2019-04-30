package com.cn.alef.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cn.alef.R;
import com.cn.alef.entity.HistoryInfo;

import java.util.List;

/**
 * Created by wang on 2017/8/13.
 */

public class HistoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<HistoryInfo> listItems;
    private LayoutInflater inflater;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;

    public HistoryAdapter(Context context, List<HistoryInfo> listItems) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * 根据数据列表的position返回需要展示的layout的对应的type
     * type的值必须从0开始
     */
    @Override
    public int getItemViewType(int position) {
        int p = position;
        if (p % 2 == 0)
            return TYPE_1;
        else
            return TYPE_2;
    }

    /**
     * 该方法返回多少个不同的布局
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            inflater = LayoutInflater.from(mContext);
            switch (type) {
                case TYPE_1:
                    convertView = inflater.inflate(R.layout.history_itemleft,
                            parent, false);
                    holder1 = new ViewHolder1();
                    convertView.setTag(holder1);
                    break;
                case TYPE_2:
                    convertView = inflater.inflate(R.layout.history_itemright,
                            parent, false);
                    holder2 = new ViewHolder2();
                    convertView.setTag(holder2);
                    break;
                default:
                    break;
            }
        } else {
            switch (type) {
                case TYPE_1:
                    holder1 = (ViewHolder1) convertView.getTag();
                    break;
                case TYPE_2:
                    holder2 = (ViewHolder2) convertView.getTag();
                    break;
            }
        }

        return convertView;
    }

    public class ViewHolder1 {
    }

    public class ViewHolder2 {

    }


}
