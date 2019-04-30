package com.cn.factory;

/**
 * Created by wang on 2018/5/2.
 */

public class IOHandlerFactory {

    public enum Type{
        DISK,MEMORY
    }

    public static IOHandler createIOHandler(Type type){
        switch (type){
            case DISK:
                return  new DiskIOHandler();
            case MEMORY:
                return new MemoryIOHandler();
            default:
                return null;
        }
    }

}
