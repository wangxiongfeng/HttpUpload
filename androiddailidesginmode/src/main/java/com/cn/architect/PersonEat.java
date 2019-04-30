package com.cn.architect;

/**
 * Created by wang on 2018/5/3.
 */

public class PersonEat implements Eat {

    @Override
    public void eat() {
        System.out.print("人吃饭\n");
    }
}
