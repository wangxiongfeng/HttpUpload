package projectnine.cn.com.servicebaohuo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, MessageService.class));
        startService(new Intent(this, GuardService.class));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //大于5.0才启动
            startService(new Intent(this, JobWakeupService.class));
        }

    }
}
