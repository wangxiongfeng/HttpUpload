package projectnine.cn.com.bannerviewpager;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private BannerView bannerView;

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bannerView = (BannerView) findViewById(R.id.bannerview);


        //适配器模式
        BannerAdapterImpl bannerAdapter1=new BannerAdapterImpl(this);

        bannerView.setAdapter(bannerAdapter1);

        bannerView.startRoll();

        bannerView.setScrollerDuration(1000);
    }

}
