package com.cn.factory;

/**
 * Created by wang on 2018/4/28.
 */

public class Client {

    public  static void main(String[] args){


        IOHandler ioHandler= IOHandlerFactory.createIOHandler(IOHandlerFactory.Type.DISK);
        ioHandler.create("33"+"\n");
        ioHandler.getproduct("33");

    }



}
