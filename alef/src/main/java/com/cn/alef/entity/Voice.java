package com.cn.alef.entity;

import java.util.ArrayList;

/**
 * Created by wang on 2017/8/14.
 */

public class Voice {
    public ArrayList<WSBean> ws;

    public class WSBean {
        public ArrayList<CWBean> cw;
    }

    public class CWBean {
        public String w;
    }
}
