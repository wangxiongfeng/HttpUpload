package projectnine.cn.com.timezoom;

import android.util.Log;

/**
 * Created by wang on 2018/3/19.
 */

public class Translation2 {
    private int status;
    private content content;

    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    public void show() {
        Log.d("RxJava", content.out);
    }


}
