package projectnine.cn.com.androidndkdemo1;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by wang on 2018/9/6.
 */

public class Java2C {

    private Context mContext;
    public Java2C(Context mContext){
        this.mContext=mContext;
    }


    //c调用java 的成员变量
    private String codeError="验证码错误";
    private String userNameError="用户名错误";
    private static String loginSuccess="登录成功";


    static {
        System.loadLibrary("mysoku");
    }


    //java 调用c
    public native String getFromC();

    public native String login(String username, String psw, int code);

    public native int[] modifyValue(int[] array);

    //c调用java的方法
    public void  showMessage(String message){
        Toast.makeText(mContext,message+"",Toast.LENGTH_LONG).show();
    }

}
