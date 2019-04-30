package com.cn.sample2;


import com.cn.sample1.IBank;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by wang on 2018/4/28.
 */

public class BankInvocationHandler implements InvocationHandler {

    private IBank object;

    public BankInvocationHandler(IBank object) {
        this.object = object;
    }

    /**
     * @param proxy  代理对象
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //目标接口调用的方法都会来到这里面
        System.out.print("开始受理\n");
        //调用被代理对象的方法  Man里面applyBank方法
        Object voidObject = method.invoke(object, args);
        System.out.print("受理完毕\n");
        return voidObject;
    }







}
