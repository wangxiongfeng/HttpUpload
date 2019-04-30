package com.cn.sample1;

/**
 * Created by wang on 2018/4/28.
 */

public class Client {

    public  static void main(String[] args){

        BankWorker bankWorker=new BankWorker(new Man("张"));
        bankWorker.applyBank();

    }

}
