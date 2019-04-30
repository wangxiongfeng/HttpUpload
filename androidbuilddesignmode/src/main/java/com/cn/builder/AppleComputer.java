package com.cn.builder;

/**
 * Created by wang on 2018/4/19.
 */

public class AppleComputer extends Computer {

    protected int mCpuCore = 1;
    protected int mRamSize = 0;
    protected String mOs = "Windows";

    @Override
    public void setmCpuCore(int mCpuCore) {
        this.mCpuCore = mCpuCore;
    }

    @Override
    public void setmRamSize(int mRamSize) {
        this.mRamSize = mRamSize;
    }

    @Override
    public void setmOs(String mOs) {
        this.mOs = mOs;
    }
}
