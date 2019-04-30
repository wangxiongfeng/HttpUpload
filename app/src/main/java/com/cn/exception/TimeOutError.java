package com.cn.exception;

/**
 * Created by wang on 2018/3/13.
 */

public class TimeOutError extends Exception {

    public TimeOutError() {
    }

    public TimeOutError(String message) {
        super(message);
    }

    public TimeOutError(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeOutError(Throwable cause) {
        super(cause);
    }

}
