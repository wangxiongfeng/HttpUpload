package com.cn.architect;

/**
 * Created by wang on 2018/5/3.
 * <p>
 * 装饰设计模式   代替继承
 * 在不使用继承的方式下  扩张一个对象的功能  可是一个对象变得越来越强大
 * <p>
 * recyclerview 添加head foot
 */

public class Client {


    public static void main(String[] args) {

        PersonEat personEat = new PersonEat();
        TeacherEat teacherEat = new TeacherEat(personEat); //对象
        teacherEat.eat();

    }


}
