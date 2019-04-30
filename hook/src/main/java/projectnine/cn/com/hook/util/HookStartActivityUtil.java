package projectnine.cn.com.hook.util;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by wang on 2018/5/17.
 */

public class HookStartActivityUtil {

    private final String EXTRA_ORIGIN_INTENT = "EXTRA_ORIGIN_INTENT";
    private String TAG = "HookStartActivityUtil";
    private Context mContent;
    private Class<?> mProxyClass;


    public HookStartActivityUtil(Context context, Class<?> proxyClass) {

        this.mContent = context.getApplicationContext();
        this.mProxyClass = proxyClass;

    }

    /**
     * 换回原来的intent
     *
     * @throws Exception
     */
    public void hookLaunchActivity() throws Exception {
        //获取ActivityThread实例
        Class<?> atClass = Class.forName("android.app.ActivityThread");
        Field atField = atClass.getDeclaredField("sCurrentActivityThread");
        atField.setAccessible(true);
        Object sCurrentActivityThread = atField.get(null);   //sCurrentActivityThread  就是ActivityThread
        //获取ActivityThread中的mH
        Field mHField = atClass.getDeclaredField("mH");
        mHField.setAccessible(true);
        Object mHandler = mHField.get(sCurrentActivityThread);
        //给Handler设置CallBack回调 反射
        Class<?> handlerClass = Class.forName("android.os.Handler");
        Field mCallbackField = handlerClass.getDeclaredField("mCallback");
        mCallbackField.setAccessible(true);
        mCallbackField.set(mHandler, new HandlerCallBack()); // 重新设置mCallback的值
    }

    private class HandlerCallBack implements Handler.Callback {
        @Override
        public boolean handleMessage(Message msg) {
            //没发一个消息  都会走callback方法
            if (msg.what == 100) {
                Log.e(TAG, "handmessage");
                handLaunchActivity(msg);
            }
            return false;
        }

        /**
         * 启动创建activity拦截
         *
         * @param msg
         */
        private void handLaunchActivity(Message msg) {
            try {
                Object record = msg.obj;//ActivityThread中的ActivityClientRecord
                //1 从record获取过安检的Intent
                Field intentField = record.getClass().getDeclaredField("intent");
                intentField.setAccessible(true);
                Intent safeIntent = (Intent) intentField.get(record);
                // 2 从safeIntent中获取原来的intent
                  Intent originIntent = safeIntent.getParcelableExtra(EXTRA_ORIGIN_INTENT);
                // 3 重新设置回去
                if (originIntent != null) {
                    intentField.set(record, originIntent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void hookStartActivity() throws Exception {

        // 获取ActivityManagerNative里面的gDefault
        Class<?> amnClass = Class.forName("android.app.ActivityManagerNative");
        //获取属性
        Field gDefaultField = amnClass.getDeclaredField("gDefault");
        gDefaultField.setAccessible(true);
        Object gDefault = gDefaultField.get(null); //gDefault 的值

        // 获取gDefault 中的instance属性  gDefault是Singleton类型
        Class<?> singletonClass = Class.forName("android.util.Singleton");
        Field mInstanceField = singletonClass.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);
        Object iamInstance = mInstanceField.get(gDefault); // mInstance 的值   IActivityManager的一个实例

        Class<?> iamclass = Class.forName("android.app.IActivityManager");
        iamInstance = Proxy.newProxyInstance(HookStartActivityUtil.class.getClassLoader(),
                new Class[]{iamclass},
                new StartInvocationHandler(iamInstance)  //iamInstance是执行者
        );
        //重新指定
        mInstanceField.set(gDefault, iamInstance);

    }

    private class StartInvocationHandler implements InvocationHandler {

        private Object object2;

        public StartInvocationHandler(Object object) {
            this.object2 = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.e(TAG, method.getName()); //IActivityManager  里所有方法
            if (method.getName().equals("startActivity")) {   //重新写startActivity方法
                //首先获取原來的intent
                Intent originItent = (Intent) args[2];
                //创建一个安全
                Intent safeIntent = new Intent(mContent, mProxyClass);  //  mProxyClass  -->ProxyActivity.class
                args[2] = safeIntent;
                //绑定原来的intent  originItent==Start2Activity
                safeIntent.putExtra(EXTRA_ORIGIN_INTENT, originItent);
            }
            return method.invoke(object2, args);
        }
    }

}
