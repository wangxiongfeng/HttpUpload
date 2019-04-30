package com.cn.sample2;


import com.cn.sample1.IBank;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * Created by wang on 2018/4/28.
 */

public class Client {

    //动态代理  返回接口的代理对象
    public static void main(String[] args) {

        Man man = new Man("张");
//        BankWorker bankWorker = new BankWorker(man);
//        bankWorker.applyBank();
        //返回目标接口IBank的实例对象    代理了所有方法而且都会走BankInvocationHandler里面的 invoke方法
        IBank bank = (IBank) Proxy.newProxyInstance(IBank.class.getClassLoader(),//ClassLoader
//                new Class<?>[]{IBank.class},  //目标接口数组
                man.getClass().getInterfaces(),
                new BankInvocationHandler(man)
        );

        // 调这个方法时   来到BankInvocationHandler的invoke方法
        bank.applyBank();
        bank.lostBank();

    }
}
