package projectnine.cn.com.androidandfix;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class TestActivity extends AppCompatActivity {
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.test);
        btn = (Button) findViewById(R.id.btn);

        fixDexBug();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this, 2 / 0 + "bug修复测试", Toast.LENGTH_LONG).show();
            }
        });

    }


    /**
     * 自定义热修复
     */
    private void fixDexBug() {
        File fileFix = new File(Environment.getExternalStorageDirectory(), "fix.dex");
        if (fileFix.exists()) {
            FixDexManager fixdexmanager = new FixDexManager(this);
            try {
                fixdexmanager.fixDex(fileFix.getAbsolutePath());
                Toast.makeText(this, "修复成功", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, "修复失败", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }
}
