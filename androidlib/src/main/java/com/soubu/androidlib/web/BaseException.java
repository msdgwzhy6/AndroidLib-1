package com.soubu.androidlib.web;

/**
 * 网络异常
 * <p>
 * 作者：余天然 on 16/9/16 下午6:18
 */
public class BaseException extends RuntimeException {

    private int httpCode;
    private int errorCode;//-1=系统错误，-2=未知网络错误,-3=未知业务错误,-4=网络解析错误,-5=网络连接错误

    public BaseException(int httpCode, int errorCode, String message) {
        super(message);
        this.httpCode = httpCode;
        this.errorCode = errorCode;
    }

    public BaseException(int httpCode, int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
