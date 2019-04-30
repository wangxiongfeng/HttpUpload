package projectnine.cn.com.bannerviewpager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2018/4/12.
 */
public class BannerViewPager extends ViewPager {

    //自定义  的adapter
    private BannerInterface mAdapter;
    private final int SCROLL_MSG = 0X0011;
    private final int mCutDownTime = 3500;
    private BannerScroller mScroller;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 每个5秒切换到下一页
            setCurrentItem(getCurrentItem() + 1);
            // 不断循环执行
            startRole();
        }
    };

    private Activity myActivity;
    //界面复用 内存优化  复用的界面
    private List<View> mConvertViews;

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        myActivity = (Activity) context;
        try {
            Class c=ViewPager.class; // ViewPager的类类型
            Field field = c.getDeclaredField("mScroller");
            //设置参数
            mScroller = new BannerScroller(context);
            //设置为强制改变私有
            field.setAccessible(true);
            field.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mConvertViews = new ArrayList<>();

    }

    public void setScrollerDuration(int mScrollerDuration) {
        mScroller.setScrollerDuration(mScrollerDuration);
    }


    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void setAdapter(BannerInterface mAdapter) {
        //设置父类的adapter
        this.mAdapter = mAdapter;
        setAdapter(new BannerPageAdapter());
        //管理activity生命周期
        myActivity.getApplication().registerActivityLifecycleCallbacks(ac);
    }


    /**
     * 实现自动轮播
     */
    public void startRole() {
        mHandler.removeMessages(SCROLL_MSG);
        mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mCutDownTime);
        Log.e("satrtRoll", "startRoll");
    }

    /**
     * 销毁handler 内存泄漏
     */
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(SCROLL_MSG);
        mHandler = null;
        //解除绑定
        myActivity.getApplication().unregisterActivityLifecycleCallbacks(ac);
    }


    private View getConvertView() {
        for (int i = 0; i < mConvertViews.size(); i++) {
            //获取没有添加在viewpager里
            if (mConvertViews.get(i).getParent() == null) {
                return mConvertViews.get(i);
            }
        }
        return null;
    }

    //管理activity的生命周期
    private Application.ActivityLifecycleCallbacks ac = new DefaultActivityLifecycle() {
        @Override
        public void onActivityResumed(Activity activity) {
            // 是不是监听当前activity的生命周期
            // 开启轮播
            if (activity == getContext()) {
                mHandler.sendEmptyMessageDelayed(mCutDownTime, SCROLL_MSG);
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            // 停止轮播
            if (activity == getContext()) {
                mHandler.removeMessages(SCROLL_MSG);
            }
        }
    };


    private class BannerPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 创建viewpager条目的方法
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //采用adapter设计模式   mAdapter用来得到设置的imageview
            View view = mAdapter.getView(position % mAdapter.getCount(), getConvertView());
            //將页面添加viewpager中
            container.addView(view);
            return view;
        }

        /**
         * 销毁viewpager条目的方法
         *
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            mConvertViews.add((View) object);
        }

    }


}
