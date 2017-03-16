package com.soubu.androidlib.web;

import com.soubu.androidlib.util.SPUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Header的OkHttp拦截器
 * <p>
 * 作者：余天然 on 2016/12/7 下午3:44
 */
public class HeaderInterceptor implements Interceptor {

    public static String TAG_UID = "UID";


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        String uid = SPUtil.getValue(TAG_UID, "");
        String sign = EncryptHelper.createSign(oldRequest);
        Request newRequest = oldRequest
                .newBuilder()
                .header("uid", uid)
                .header("sign", sign)
                .build();
        return chain.proceed(newRequest);
    }
}
