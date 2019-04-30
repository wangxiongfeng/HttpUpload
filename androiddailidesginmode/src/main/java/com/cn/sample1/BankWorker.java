package com.cn.sample1;

/**
 * Created by wang on 2018/4/28.
 * 代理对象
 */

public class BankWorker implements IBank {

    public Man man;

    /**
     * 持有被代理的对象
     */
    public BankWorker(Man man) {
        this.man = man;
    }

    @Override
    public void applyBank() {
        System.out.print("开始受理\n");
        man.applyBank();
        System.out.print("受理完毕\n");
    }

    @Override
    public void lostBank() {

    }

}
