package com.cn.fanxinginterface;

public class B  implements Generator<String> {

    @Override
    public String next() {
        return "bbb";
    }

}
