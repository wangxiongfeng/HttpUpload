package projectnine.cn.com.redpackage;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by wang on 2018/4/9.
 */
public class RedPackageView extends View {

    private Bitmap mRedPackageBitmap;
    private Bitmap mProgressBgBitmap;

    private float mCurrentProgress;
    private float mTotalProgress;

    private Paint mPaint;

    public RedPackageView(Context context) {
        this(context, null);
    }

    public RedPackageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RedPackageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRedPackageBitmap = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_btn_speak_now);
        mProgressBgBitmap = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_btn_speak_now);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = (int) (Math.max(mRedPackageBitmap.getWidth(), mRedPackageBitmap.getHeight() * 1.1f));
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        canvas.drawBitmap(mRedPackageBitmap, 0, 0, null);
        int progressBgwidth = mProgressBgBitmap.getWidth();
        int left = (int) (progressBgwidth * 0.09f);
        int top = height - mProgressBgBitmap.getHeight() - left;
        canvas.drawBitmap(mProgressBgBitmap, left, top, null); // 进度条背景
        if (mTotalProgress == 0 || mCurrentProgress == 0) {
            return;
        }
        if (mCurrentProgress != 0) {
            //进度条
            int progressHeight = (int) (mProgressBgBitmap.getHeight() * 0.25f);
            float progressWidth = width * 0.64f;
            int currentProgressWidth = (int) (progressWidth * mCurrentProgress / mTotalProgress);
            int round = progressHeight / 2;

//        Shader shader = new LinearGradient(0, 0, progressWidth, 0, new int[]{mProgressStarColor,mProgressEndColor},
//                new float[]{0, 1.0f}, Shader.TileMode.CLAMP);
//        mProgressPaint.setShader(shader);

            top = (int) (top * 1.19f);
            left = (int) (left * 2.5f);
            RectF rectF = new RectF(left, top, left + currentProgressWidth, top + progressHeight);
            canvas.drawRoundRect(rectF, round, round, mPaint);

        }

        // 爆炸动画
//        if (mBombProgress > 0 && mBombProgress < 1) {
//            float preAngle = (float) (2 * Math.PI / mBombIcon.length);
//            for (int i = 0; i < 8; i++) {
//                double angle = i * preAngle;
//                mPaint.setAlpha((int) (320 - mBombProgress * 255));
//                float cx = (float) (bombCenterX + mProgressBombRadius * mBombProgress * Math.cos(angle));
//                float cy = (float) (bombCenterY + mProgressBombRadius * mBombProgress * Math.sin(angle));
//                canvas.drawBitmap(mBombIcon[i % 2], cx, cy, mBombPaint);
//            }
//        }

    }


    private synchronized void setmCurrentProgress(float mCurrentProgress) {
        this.mCurrentProgress = mCurrentProgress;
        invalidate();
    }

    public void setmTotalProgress(float mTotalProgress) {
        this.mTotalProgress = mTotalProgress;
    }


    private ValueAnimator valueAnimator;

    /**
     * 进度条
     *
     * @param from
     * @param to
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void startAnimation(int from, int to) {

        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofFloat(from, to);
            valueAnimator.setDuration(600);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setmCurrentProgress((Float) animation.getAnimatedValue());
                }
            });
            //进度条涨满过后爆炸
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    executeBombAnimation();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        valueAnimator.start();

    }


    private float mBombProgress; //当前爆炸进度

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void executeBombAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBombProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //缩小放大
                if (mCurrentProgress == mTotalProgress) {
                    executeShrinkAnimator();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }


    private AnimatorSet mSmalllAnimators;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void executeShrinkAnimator() {
        if (mSmalllAnimators != null) {
            mSmalllAnimators = new AnimatorSet();

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1f, 0);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1f, 0);

            mSmalllAnimators.setDuration(600);
            mSmalllAnimators.setInterpolator(new DecelerateInterpolator());
            mSmalllAnimators.play(scaleX).with(scaleY);
            mSmalllAnimators.start();


            mSmalllAnimators.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    //放大动画
                    executeBigAnimator();
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }

            });


        }


    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void executeBigAnimator() {

        if (mSmalllAnimators != null) {
            mSmalllAnimators = new AnimatorSet();
            ObjectAnimator scalex = ObjectAnimator.ofFloat(this, "scaleX", 0, 1f);
            ObjectAnimator scaley = ObjectAnimator.ofFloat(this, "scaleY", 0, 1f);
            mSmalllAnimators.setDuration(600);
            mSmalllAnimators.setInterpolator(new DecelerateInterpolator());
            mSmalllAnimators.play(scalex).with(scaley);
            mSmalllAnimators.start();
        }


    }


}
