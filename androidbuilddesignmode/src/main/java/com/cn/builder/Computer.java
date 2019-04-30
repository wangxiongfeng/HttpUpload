package com.cn.builder;

/**
 * Created by wang on 2018/4/19.
 */

public abstract class Computer {

    protected int mCpuCore = 1;
    protected int mRamSize = 0;
    protected String mOs = "Windows";

    protected Computer() {

    }

    public abstract void setmCpuCore(int mCpuCore);

    public abstract void setmRamSize(int mRamSize);

    public abstract void setmOs(String mOs);

    @Override
    public String toString() {
        return mCpuCore + "  " + mRamSize + "  " + mOs;
    }
}
