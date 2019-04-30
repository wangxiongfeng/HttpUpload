package com.cn.factory3;

/**
 * Created by wang on 2018/4/28.
 *
 * 使用场景：很多对象有共性  后期可能存在多样性 不想让别人知道我们 的创造细节 而且创建的过程比较复杂
 * 工厂是用来生成对象的 把对象的实例化和初始化封装起来
 *
 *
 */
public class Client {

    public  static void main(String[] args){

        //抽象工厂模式   通过特定的方法返回单一的对象
        IOHandler ioHandler=IOHandlerFactory.getinstance().getDiskIOHandler();
//      IOHandler ioHandler=IOHandlerFactory.getinstance().getMemoryIOHandler();
        ioHandler.create("33"+"\n");
        ioHandler.getproduct("33");

    }


}
