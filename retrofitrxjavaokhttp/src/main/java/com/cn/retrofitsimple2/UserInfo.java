package com.cn.retrofitsimple2;

/**
 * Created by wang on 2018/5/24.
 * <p>
 *    要获取的数据
 */

public class UserInfo {

    public String userName;
    public String userSex;

    @Override
    public String toString() {
        return userName + "   " + userSex;
    }
}
