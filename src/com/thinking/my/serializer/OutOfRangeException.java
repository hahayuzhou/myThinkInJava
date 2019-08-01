package com.thinking.my.serializer;

/**
 * Created by liyong on 2019/4/22.
 */
public class OutOfRangeException extends Exception {

    private final String msg;

    public OutOfRangeException(String message) {
        msg = message;
    }

    public OutOfRangeException() {
        msg = "Out range exeception.";
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
