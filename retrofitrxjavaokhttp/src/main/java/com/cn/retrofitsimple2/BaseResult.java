package com.cn.retrofitsimple2;

/**
 * Created by wang on 2018/5/24.
 *
 * 返回数据基本参数
 *
 *  "RetCode":0,
     "ErrorMessage":null,
     "DetailMessage":null,
     "RetData":[
     {
     "CourseID":5094,
     "Title":"Music2",
     "Description":null,
     "Status":0,
 *
 *
 */

public class BaseResult {

    int  retCode;
    String msg;

    public int getRetCode() {
        return retCode;
    }


    public String getMsg() {
        return msg;
    }


    public boolean isOk(){
        return retCode==0;
    }

}
