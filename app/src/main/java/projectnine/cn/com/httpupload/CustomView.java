package projectnine.cn.com.httpupload;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wang on 2018/3/19.
 */

public class CustomView extends View {

    public CustomView(Context context) {
        this(context,null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("--------000","onTouchEvent");
        return super.onTouchEvent(event);
    }
}
