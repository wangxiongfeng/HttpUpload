package projectnine.cn.com.androidaop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //AspectJ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Looper.prepare();


    }


    @CheckNet("")
    public void click(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}