package projectnine.cn.com.androidmvp.base;

import android.os.Looper;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by wang on 2018/6/11.
 */

public class BasePresenter<V extends BaseView> {

    //两个公用方法 传递的时候会有不同的view（activity）

    //解绑  代码写起来越来越多
    //问题  很多代码是公用反复的  attach  detch 每个Presenter都要有

    // view  的attach  detch 每个view都要有
    private WeakReference<V> mViewReference;

    private V mProxyView;

    // view 一般都是Activity涉及到内存泄漏  但是已经解绑了不会 如果没解绑 就会泄漏
    // 最好还是用一下弱引用


    // view有一个特点  都是接口
    // GC 算法回收机制
    public void attach(final V view) {    // BaseView   不在通过构造方法   初始化view  手动连接 view 与 presenter
        this.mViewReference = new WeakReference<V>(view);
        // 用代理对象
        mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //每次都会执行这个方法  调用的是被代理的对象
                if (mViewReference == null || mViewReference.get() == null) {
                    return null;
                }
                //没解绑执行的是原始被代理view的方法
                return method.invoke(mViewReference.get(), args);
            }
        });

    }

    public void detch() {
        this.mProxyView = null;
        this.mViewReference.clear();
        this.mViewReference = null;
    }

    public V getmView() {
        return mProxyView;
    }


}
