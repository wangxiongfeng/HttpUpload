package com.cn.eventbean;

/**
 * Created by wang on 2018/3/21.
 */

public class MyEvent {
  private  String message;

    public MyEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
