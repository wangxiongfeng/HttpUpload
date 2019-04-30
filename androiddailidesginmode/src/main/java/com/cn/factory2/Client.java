package com.cn.factory2;

/**
 * Created by wang on 2018/4/28.
 */

public class Client {

    public static void main(String[] args) {

        // 工厂方法模式   创建多个不同类型工厂
        IOFactory ioFactory = new DiskIOFactory();
        IOHandler ioHandler = ioFactory.createIOHandler();
        ioHandler.create("33" + "\n");
        ioHandler.getproduct("33");



        IOFactory ioFactory1=new MemoryIOFactory();
        IOHandler ioHandler1=ioFactory1.createIOHandler();
        ioHandler1.create("");
        ioHandler1.getproduct("");


    }


}
