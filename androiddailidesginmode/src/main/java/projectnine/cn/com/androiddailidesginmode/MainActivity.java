package projectnine.cn.com.androiddailidesginmode;

import android.app.Activity;
import android.os.Bundle;

import com.cn.sample1.BankWorker;
import com.cn.sample1.Man;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        BankWorker bankWorker=new BankWorker(new Man("张"));
        bankWorker.applyBank();


    }
}
