package projectnine.cn.com.bannerviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by wang on 2018/4/12.
 */
public class BannerView extends RelativeLayout {

    private BannerViewPager mBannerVp;
    private TextView mBannerDescTv;
    private LinearLayout mDotContainer; //点的容器


    private BannerInterface mAdapter;
    private Context mContext;

    private int mCurrentPosition = 0;

    //attrs
    private int mDotGravity = -1; //点的显示位置
    private Drawable mIndicatorFocusDrawable;
    private Drawable mIndicatorNormalFocusDrawable;
    private int mDotSize = 8;//点的大小  默认8个dp
    private int mDotDistance = 8; //点的距离

    private RelativeLayout mBannerBv;
    private int mBottomColor = Color.TRANSPARENT;

    private float mWidthProportion, mHeightProportion;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        //把布局加载这个view中
        inflate(context, R.layout.ui_banner_layout, this);
        initAttribute(attrs);
        initView();
    }


    private void initAttribute(AttributeSet attrs) {

        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);

        mDotGravity = array.getInt(R.styleable.BannerView_dotGravity, mDotGravity);

        mIndicatorFocusDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorFoucus);
        if (mIndicatorFocusDrawable == null) {
            mIndicatorFocusDrawable = new ColorDrawable(Color.RED);
        }

        mIndicatorNormalFocusDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorNormal);
        if (mIndicatorNormalFocusDrawable == null) {
            mIndicatorNormalFocusDrawable = new ColorDrawable(Color.WHITE);
        }

        mDotSize = (int) array.getDimension(R.styleable.BannerView_dotSize, dip2px(mDotSize));
        mDotDistance = (int) array.getDimension(R.styleable.BannerView_dotDistance, dip2px(mDotDistance));
        mBottomColor = array.getColor(R.styleable.BannerView_bottomColor, mBottomColor);
        mWidthProportion = array.getFloat(R.styleable.BannerView_widthProperation, mWidthProportion);
        mHeightProportion = array.getFloat(R.styleable.BannerView_heightProperation, mHeightProportion);

        array.recycle();

    }

    private void initView() {

        mBannerVp = (BannerViewPager) findViewById(R.id.banner_vp);
        mBannerDescTv = (TextView) findViewById(R.id.banner_desc_tv);
        mDotContainer = (LinearLayout) findViewById(R.id.dot_container);
        mBannerBv = (RelativeLayout) findViewById(R.id.bottom_view);
        mBannerBv.setBackgroundColor(mBottomColor);

    }


    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void setAdapter(BannerInterface adapter) {
        this.mAdapter = adapter;
        mBannerVp.setAdapter(adapter);
        initDotIndicator(); //初始化点的集合
        mBannerVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //监听当前选中的位置
                pageSelect(position);
            }
        });
        String firstDesc = mAdapter.getBannerDesc(0);
        mBannerDescTv.setText(firstDesc);

//        Log.e("mHeightProportion", mHeightProportion + "   " + mWidthProportion);
//        if (mHeightProportion == 0 || mWidthProportion == 0) {
//            return;
//        }
//        //动态指定宽高
//        int width = getMeasuredWidth();  //0
//        int height = (int) (width * mHeightProportion / mWidthProportion);
//        Log.e("mHeightProportion", height + "   " + width);
//        getLayoutParams().height = height;

    }



    private void pageSelect(int position) {

        DotIndicatorView oldIndicatorView = (DotIndicatorView) mDotContainer.getChildAt(mCurrentPosition);
        oldIndicatorView.setDrawable(mIndicatorNormalFocusDrawable);

        //把当前位置 点亮
        mCurrentPosition = position % mAdapter.getCount();
        DotIndicatorView currentIndicatorView = (DotIndicatorView) mDotContainer.getChildAt(mCurrentPosition);
        currentIndicatorView.setDrawable(mIndicatorFocusDrawable);

        String bannerDesc = mAdapter.getBannerDesc(mCurrentPosition);
        mBannerDescTv.setText(bannerDesc);

    }

    public void startRoll() {
        mBannerVp.startRole();
    }


    /**
     * 获取点的位置
     *
     * @return
     */
    public int getDotGravity() {
        switch (mDotGravity) {
            case 0:
                return Gravity.CENTER;
            case -1:
                return Gravity.LEFT;
            case 1:
                return Gravity.RIGHT;
        }
        return Gravity.RIGHT;
    }


    /**
     * 设置点的集合
     */
    private void initDotIndicator() {
        int count = mAdapter.getCount();
        mDotContainer.setGravity(getDotGravity());
        for (int i = 0; i < count; i++) {
            //不断添加圆点
            DotIndicatorView indicatorView = new DotIndicatorView(mContext);
            //设置大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mDotSize, mDotSize);
            params.leftMargin = mDotDistance;
            indicatorView.setLayoutParams(params);
            if (i == 0) {
                indicatorView.setDrawable(mIndicatorFocusDrawable);
            } else {
                indicatorView.setDrawable(mIndicatorNormalFocusDrawable);
            }
            mDotContainer.addView(indicatorView);
        }
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    public void setScrollerDuration(int duration) {
        mBannerVp.setScrollerDuration(duration);
    }

}
