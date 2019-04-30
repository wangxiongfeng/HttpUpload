package projectnine.cn.com.redpackage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by wang on 2018/4/24.
 */

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int baseLineX = 0;
        int baseLineY = 200;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);
        paint.setTextSize(120);
        paint.setColor(Color.BLUE);


//        paint.setTextAlign(Paint.Align.CENTER);
        //drawText是中的参数y是基线的位置。
//        canvas.drawText("hgggdsr", baseLineX, baseLineY, paint);

        String dd = "gfdgyadgsdfg";
        Rect rect = new Rect();
        paint.getTextBounds(dd, 0, dd.length(), rect);
        int dx = getWidth() / 2 - rect.width() / 2;
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;   //  以基线为标准
        Log.e("dydyd", dy + "   " + fontMetricsInt.bottom + "    " + fontMetricsInt.top + "   " + getHeight() / 2);  // 47   33    -127
        // dy代表控件中心线与基线之间的距离
        int baseline = getHeight() / 2 + dy;  //控件中间线位置+（rectf中心线距离基线的距离）
        canvas.drawLine(dx, baseline, 3000, baseline, paint);
        canvas.drawText(dd, dx, baseline, paint);

        paint.setStrokeCap(Paint.Cap.ROUND);//
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN)); //取交集

    }
}
