package com.cn.factory2;

/**
 * Created by wang on 2018/5/2.
 */

public class MemoryIOFactory implements IOFactory {
    @Override
    public IOHandler createIOHandler() {
        return new MemoryIOHandler();
    }
}
