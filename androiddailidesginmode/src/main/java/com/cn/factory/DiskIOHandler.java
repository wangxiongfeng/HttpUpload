package com.cn.factory;

/**
 * Created by wang on 2018/5/2.
 */

public class DiskIOHandler implements IOHandler {

    @Override
    public void create(String key) {
        System.out.print("生成" + key);
    }

    @Override
    public void getproduct(String key) {
        System.out.print("得到" + key);
    }

}
