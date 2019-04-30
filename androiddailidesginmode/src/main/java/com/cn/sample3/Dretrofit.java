package com.cn.sample3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by wang on 2018/4/28.
 */

public class Dretrofit {

    public <T> T create(Class<T> clazz) {

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //接口中每个方法（被调用）都走一遍

                //1  解析方法的所有注解

                //2 解析参数的所有注解

                //3 封装成call对象

                //4 返回call对象

                return "daili";
            }
        });


    }

}
