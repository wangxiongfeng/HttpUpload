package projectnine.cn.com.bannerviewpager;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by wang on 2018/4/12.
 */

public class BannerScroller extends Scroller {

    private  int mScrollerDuration=850;//动画持续的时间

    public void setScrollerDuration(int mScrollerDuration ){
        this.mScrollerDuration=mScrollerDuration;
    }

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollerDuration);
    }


}
