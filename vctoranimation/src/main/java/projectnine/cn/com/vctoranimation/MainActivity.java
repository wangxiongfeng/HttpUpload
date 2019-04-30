package projectnine.cn.com.vctoranimation;

import android.app.Activity;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView= (ImageView) findViewById(R.id.imageview);

        AnimatedVectorDrawable animatedVectorDrawable =
                (AnimatedVectorDrawable) imageView.getDrawable();
        if(animatedVectorDrawable.isRunning()) {
            animatedVectorDrawable.stop();
        } else {
            animatedVectorDrawable.start();
        }


    }
}
