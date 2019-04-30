package com.cn.styleinterface;

import projectnine.cn.com.faxing.Generic;

public class Test {
    //泛型边界的上下限  类型实参只准传入某种类型的父类或某种类型的子类。
    public static void showKeyValues(Generic<? extends Object> obj) {

        System.out.print(obj.getKey());

    }


    public static void main(String[] args) {


        Generic<String> s1 = new Generic<>("1111  ");
        Generic<Integer> s2 = new Generic<>(990);

        showKeyValues(s1);
        showKeyValues(s2);


    }


}
