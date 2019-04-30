package projectnine.cn.com.androidandfix;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.LoginFilter;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });

//        aliFixBug();

//        TranslateAnimation

//        ObjectAnimator
//        ValueAnimator
//        AnimatorSet  继承  Animator

        ValueAnimator s = ObjectAnimator.ofFloat();
        s.setRepeatMode(ValueAnimator.REVERSE); //逆向重复
//        s.setDuration()


    }


    private void aliFixBug() {
        // 每次启动去后台获取差分包  fix.apatch  修复本地bug
        // apkpatch.bat -f new.apk -t old.apk -o output -k joke.jks -p 123456 -a 123456 -e android
        //测试  获取本地内存 fix.apatch
        File fileFix = new File(Environment.getExternalStorageDirectory(), "fix.apatch");
        if (fileFix.exists()) {
            try {
                BaseApplication.mPatchManager.addPatch(fileFix.getAbsolutePath());
                Toast.makeText(MainActivity.this, "修复成功", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "修复失败", Toast.LENGTH_LONG).show();
            }
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, 2 / 1 + "dd", Toast.LENGTH_LONG).show();
            }
        });
    }


}
