package com.cn.sample2;

import com.cn.sample1.IBank;

/**
 * Created by wang on 2018/4/28.
 * 被代理的对象
 */

public class Man implements IBank {

    private String name;

    public Man(String name) {
        this.name = name;
    }

    @Override
    public void applyBank() {
        System.out.print(name + "申请办卡\n");
    }

    @Override
    public void lostBank() {
        System.out.print(name + "挂失办卡\n");
    }





}
