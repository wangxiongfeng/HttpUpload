package projectnine.cn.com.androidaop;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by wang on 2018/6/12.
 * <p>
 * 操作类
 */
@Aspect
public class SectionAspect {

    /**
     * 找到处理的切点  注解路径  **(..)  处理所有的方法
     */
    @Pointcut("execution(@projectnine.cn.com.androidaop.CheckNet * *(..))")
    public void checkNetBehavior() {

    }

    /**
     * 处理切面
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Around("checkNetBehavior()")   //  围绕切点
    public Object checkNet(ProceedingJoinPoint joinPoint) throws Throwable {
        // 做埋点 日志上传  权限检测（RxPermission）
        // 埋点，是对网站、App或者后台等应用程序进行数据采集的一种方法。
        // 网络检测
        //1 获取 checknet注解  ndk 图片压缩  c++调用java方法
        Log.e("TAG", "面向切面编程");  //方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckNet checkNet = signature.getMethod().getAnnotation(CheckNet.class);
        if (checkNet != null) {
            Object object = joinPoint.getThis();  //view activity fragment  getthis 当前切点所在的类
            Context context = getContext(object);
            // 判断有没有网络
            if (context != null) {
                if (!isNetworkAvailable(context)) {
                    // 没有网络 不执行
                    Toast.makeText(context, "没有网络", Toast.LENGTH_LONG).show();
                    return null;
                }
            }
        }
        return joinPoint.proceed();
    }

    private boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Context) object;
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return null;
    }

}
