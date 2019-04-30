package com.cn.factory2;

/**
 * Created by wang on 2018/5/2.
 */

public class DiskIOFactory implements IOFactory {
    @Override
    public IOHandler createIOHandler() {
        return new DiskIOHandler();
    }
}
