package com.cn.builder;

/**
 * Created by wang on 2018/4/19.
 */

public class AppleBuilder extends Builders {

    private Computer computer = new AppleComputer();

    @Override
    public Builders buildCPU(int core) {
        computer.setmCpuCore(core);
        return this;
    }

    @Override
    public Builders buildRAM(int gb) {
        computer.setmRamSize(gb);
        return this;
    }

    @Override
    public Builders buildOS(String os) {
        computer.setmOs(os);
        return this;
    }

    @Override
    public Computer create() {
        return computer;
    }
}
