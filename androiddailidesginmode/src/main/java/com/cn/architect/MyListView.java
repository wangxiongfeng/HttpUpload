package com.cn.architect;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by wang on 2018/5/8.
 */

public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int newheightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, newheightMeasureSpec);
//        View中onMeasure方法已经默认为我们的控件测量了宽高
//        onMeasure方法最后需要调用setMeasuredDimension方法来保存测量的宽高值
//        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }




}
