package projectnine.cn.com.androidaop;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class Myservice extends IntentService {

    public Myservice(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

}
