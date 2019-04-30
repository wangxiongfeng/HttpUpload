package projectnine.cn.com.androidioc;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.bufferknife.LoginActivity;

import projectnine.cn.com.androidiocclibrary.CheckNet;
import projectnine.cn.com.androidiocclibrary.OnClick;
import projectnine.cn.com.androidiocclibrary.ViewById;
import projectnine.cn.com.androidiocclibrary.ViewUtils;


/**
 * ioc  自定义注解+反射   xutils
 */
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.tv1)   //实例化交给viewutils
    private TextView mTestView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        mTestView.setText("ff");
    }


    @OnClick({R.id.tv1, R.id.test_iv})
    @CheckNet()
    private void onClick2(View view) {
        Toast.makeText(this, "ddd", Toast.LENGTH_LONG).show();
//        BitmapFactory.decodeStream()
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }


}