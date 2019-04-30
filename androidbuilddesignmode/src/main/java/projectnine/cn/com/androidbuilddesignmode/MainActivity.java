package projectnine.cn.com.androidbuilddesignmode;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cn.builder.AppleBuilder;
import com.cn.builder.Builders;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tt();
    }

    private void tt() {

        //通过Builder来构建产品对象, 而Director封装了构建复杂产品对象对象的过程，不对外隐藏构建细节。
        Builders builders = new AppleBuilder();

        builders.buildCPU(1).buildOS("dd").buildRAM(3);  // 组装参数

        Log.e("d", builders.create().toString());

        /**
         * 良好的封装性， 使用建造者模式你不必知道内部的细节，只要知道我想要什么效果就行了；
         建造者独立，容易扩展，不过我们不需要扩展了，这么多年碰到的效果都在里面了；
         在对象创建过程中会使用到系统中的一些其它对象，这些对象在创建过程中不易得到；
         大大节省了代码量，按照我们之前的那种写法没个几行写不出这效果，这里就一行而且效果完全自定义。
         */

    }


}
