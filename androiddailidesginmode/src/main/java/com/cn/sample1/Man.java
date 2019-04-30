package com.cn.sample1;

/**
 * Created by wang on 2018/4/28.
 * 被代理的对象
 */

public class Man implements IBank {

    private String name;
    public Man(String name){
        this.name=name;
    }

    @Override
    public void applyBank() {
        System.out.print(name+"申请办卡\n");
    }

    @Override
    public void lostBank() {

    }
}
