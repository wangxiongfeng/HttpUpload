package com.cn.architect;

/**
 * Created by wang on 2018/5/3.
 */

public class TeacherEat implements Eat {


    private Eat eat;

    public TeacherEat(PersonEat eat) {
        this.eat = eat;
    }

    @Override
    public void eat() {
        eat.eat();
        System.out.print("老师吃饭");
    }



}
