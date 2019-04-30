package projectnine.cn.com.myhashmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        int number = 10;
        //原始数二进制
        printInfo(number);
        number = number << 16;     // 10* 2的16次方
        //左移一位
        printInfo(number);   //  655360
        number = number >> 1;
        //右移一位
        printInfo(number);  // 327680


        int count = 100;
        HashMap<Integer, Student> map = new HashMap<>();
        for (int i = 0; i < count; i++) {
            map.put(i, new Student("wwww" + i));
        }
        for (int i = 0; i < count; i++) {
            Log.e("MainActivity", (map.get(i).getName()) + "   " + map.getSize());
        }

    }

    private static void printInfo(int num) {  //输出一个int的二进制数
//        Log.e("MainActivity",Integer.toBinaryString(num)+"   "+num);
    }
}
