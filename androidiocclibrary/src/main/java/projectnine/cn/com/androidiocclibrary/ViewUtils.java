package projectnine.cn.com.androidiocclibrary;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by wang on 2018/4/18.
 */

public class ViewUtils {


    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }


    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }


    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }


    //兼容 上面3个方法   object反射需要执行的类
    private static void inject(ViewFinder finder, Object object) {
        injectField(finder, object);
        injectEvent(finder, object);
    }


    /**
     * 注入属性
     *
     * @param finder
     * @param object Activity
     */
    private static void injectField(ViewFinder finder, Object object) {  //  Object-->  activity
        // 1 获取类里的所有属性
        Class<?> clazz = object.getClass();  // 获取Activity的类类型   Activity.class
        try {
            Activity a = (Activity) clazz.newInstance();// 通过类的类类型创建该类的实例对象
        } catch (Exception e) {
            e.printStackTrace();
        }
        Field[] fields = clazz.getDeclaredFields(); // 获取所有属性  包括私有和公有
        // 2 获取ViewById里面的value的值
        for (Field field : fields) {
            Log.e("dddddd", field.getName());  // mTestView
            ViewById viewbyid = field.getAnnotation(ViewById.class);  // 返回该元素的指定类型的注释  annotationClass -- 对应于注释类型的Class对象
            if (viewbyid != null) {
                //获取注解里id值   R.id.tv1
                int viewid = viewbyid.value();  // R.id.XXX
                // 3 findviewbyid找到view
                View view = finder.findViewById(viewid);
                if (view != null) {
                    field.setAccessible(true);
                    // 4 动态的注入找到的view
                    try {
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private static void injectEvent(ViewFinder finder, Object object) {
        //1 获取类里的所有方法
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        //2 获取OnClick里面的value的值
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] viewids = onClick.value();
                for (int viewid : viewids) {
                    // R.id.tv1, R.id.test_iv
                    //3 findviewbyid 找到 view
                    View view = finder.findViewById(viewid);
                    // 扩展
                    boolean isCheckNet = method.getAnnotation(CheckNet.class) != null;
                    if(view != null) {
                        //4 setOnclicklistener
                        view.setOnClickListener(new Decler(method, object, isCheckNet));
                    }
                }
            }
        }
    }


    private static class Decler implements View.OnClickListener {

        private Method method;
        private Object object;
        private boolean isCheckNet;

        public Decler(Method method, Object object, boolean isCheckNet) {
            this.method = method;
            this.object = object;
            this.isCheckNet = isCheckNet;
        }

        @Override
        public void onClick(View view) {
            //需不需要检测网络
            if (isCheckNet) {
                if (!isNetWork()) { //没网
                    Toast.makeText(view.getContext(), "请连接网络", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            try {
                method.setAccessible(true);
                // 5反射方法注入
                method.invoke(object, view);
            } catch (Exception e) {
                try {
                    method.invoke(object, null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

    }


    private static boolean isNetWork() {
        //......检测是否有网络
        return true;
    }

}
