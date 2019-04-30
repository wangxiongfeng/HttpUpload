package com.cn.genericity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by wang on 2018/5/25.
 */

public class Test {

    static class Test1 extends T<Person, Animal> {

    }

    static class Test2 implements I<Person, Animal>, I2<Fruit> {

    }



    public static void main(String[] args) {

        Test1 test1 = new Test1();
        Type types = test1.getClass().getGenericSuperclass();
        Type[] generictype = ((ParameterizedType) types).getActualTypeArguments();
        for (Type type : generictype) {
            System.out.print("     -----------    "+type);
        }



        Test2 test2 = new Test2();
        Type[] types2 = test2.getClass().getGenericInterfaces();
        for (Type type : types2) {
            System.out.print("     -----------    "+type);
        }



    }


}
