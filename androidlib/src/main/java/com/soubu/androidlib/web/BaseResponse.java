package com.soubu.androidlib.web;

import lombok.Data;

/**
 * 响应值的基类
 * <p>
 * 作者：余天然 on 2017/2/16 下午5:13
 * <p>
 */
@Data
public class BaseResponse<T> {

    private Integer status;
    private Integer code = -2;
    private String msg = "请求参数错误";
    private Entity<T> result;

    @Data
    public static class Entity<T> {
        private T data;
    }

}
