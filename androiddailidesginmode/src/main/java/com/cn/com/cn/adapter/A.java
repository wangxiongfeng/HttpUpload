package com.cn.com.cn.adapter;


/**
 * 此类缺少special方法    需要与B类一起工作用I接口实现
 */
public class A extends B implements I {
    @Override
    public void eat() {
        super.special();


    }
}
