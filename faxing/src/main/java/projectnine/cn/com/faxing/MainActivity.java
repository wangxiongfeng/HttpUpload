package projectnine.cn.com.faxing;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.cn.fanxinginterface.B;
import com.cn.fanxinginterface.FruitGenerator;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Generic<Integer> generic = new Generic<>(11);
        Log.e("泛型类  ", generic.getKey() + "");
        Generic<String> generic2 = new Generic<>("111");
        Log.e("泛型类  ", generic2.getKey() + "");



        FruitGenerator<String> fruitGenerator=new FruitGenerator<>();
        fruitGenerator.setT("ss");

    }
}
