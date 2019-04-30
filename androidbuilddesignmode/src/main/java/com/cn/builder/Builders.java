package com.cn.builder;

/**
 * Created by wang on 2018/4/19.
 */

public abstract class Builders {

    public abstract Builders buildCPU(int core);

    public abstract Builders buildRAM(int gb);

    public abstract Builders buildOS(String os);

    public abstract Computer create();

}
