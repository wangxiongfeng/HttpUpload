package com.cn.retrofitsimple2;

/**
 * Created by wang on 2018/5/24.
 * <p>
 * T   STRING OR  USERINFO
 */

public class Result<T> extends BaseResult {

    //成功返回正常对象 UserInfo  失败返回 string
    public Object data;

}
