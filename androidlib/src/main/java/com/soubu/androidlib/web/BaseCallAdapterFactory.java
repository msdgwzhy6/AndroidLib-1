package com.soubu.androidlib.web;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * 作者：余天然 on 2016/12/9 下午5:13
 */
public class BaseCallAdapterFactory extends CallAdapter.Factory {

    public static BaseCallAdapterFactory create() {
        return new BaseCallAdapterFactory();
    }

    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        // 获取原始类型
        Class<?> rawType = getRawType(returnType);
        // 返回值必须是ObservableWrapper并且带有泛型
        if (rawType == ObservableWrapper.class && returnType instanceof ParameterizedType) {
            Type callReturnType = getParameterUpperBound(0, (ParameterizedType) returnType);
            return new BaseCallAdapter(callReturnType);
        }
        return null;
    }
}
