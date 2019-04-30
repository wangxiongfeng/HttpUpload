package com.cn.factory3;


/**
 * Created by wang on 2018/5/2.
 */

public class IOHandlerFactory {

    private static volatile IOHandlerFactory instance;
    private IOHandler diskIOHandler;
    private IOHandler memoryIOHandler;

    private IOHandlerFactory() {

    }

    public static IOHandlerFactory getinstance() {
        if (instance == null) {
            synchronized (IOHandlerFactory.class) {
                if (instance == null) {
                    instance = new IOHandlerFactory();
                }
            }
        }
        return instance;
    }

    public IOHandler createIOHandler(Class<? extends IOHandler> ioHandlerClass) {
        try {
            return ioHandlerClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new MemoryIOHandler();
    }


    public IOHandler getDiskIOHandler() {
        if (diskIOHandler == null) {
            diskIOHandler = createIOHandler(DiskIOHandler.class);
        }
        return diskIOHandler;
    }


    public IOHandler getMemoryIOHandler() {
        if (memoryIOHandler == null) {
            memoryIOHandler = createIOHandler(MemoryIOHandler.class);
        }
        return memoryIOHandler;
    }


}
