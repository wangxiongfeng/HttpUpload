package projectnine.cn.com.bannerviewpager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wang on 2018/4/12.
 */

public class DotIndicatorView extends View {

    private Drawable drawable;

    public DotIndicatorView(Context context) {
        this(context, null);
    }

    public DotIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置drawable
     *
     * @param drawable
     */
    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (drawable != null) {
//            drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
//            drawable.draw(canvas);
            //画圆
            Bitmap bitmap = drawableToBitmap(drawable);
            Bitmap circleBitmap = getCircleBitmap(bitmap);
            canvas.drawBitmap(circleBitmap, 0, 0, null);
        }

    }


    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        //其他类型
        //创建一个什么都没有的bitmap
        Bitmap outBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        //创建一个画布
        Canvas canvas = new Canvas(outBitmap);
        //将drawable画到bitmap上
        drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        drawable.draw(canvas); //drawable被画到outBitmap上
        return outBitmap;
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {

        //创建bitmap
        Bitmap circlrBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(circlrBitmap);  // 画布的背景
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);

        //在画布上画个圆
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));  // 取交集
        //在把原来的bitmap绘制到新的圆上
        canvas.drawBitmap(bitmap, 0, 0, paint);
        bitmap.recycle();

        return circlrBitmap;
    }


}
