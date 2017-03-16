package com.soubu.androidlib.exception;

/**
 * 作者：余天然 on 2017/3/15 上午10:01
 */
public class NullStyleException extends RuntimeException {

    public NullStyleException() {
        super("Style 不能为空");
    }
}
