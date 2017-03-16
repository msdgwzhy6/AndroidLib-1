package com.soubu.androidlib.web;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * 网络客户端
 * <p>
 * 作者：余天然 on 16/7/11 下午2:45
 */
public class WebClient {

    private final OkHttpClient client;
    private final Retrofit retrofit;

    /**
     * 创建网络辅助类
     */
    public WebClient(String host) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(logging)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .client(client)
                .addCallAdapterFactory(BaseCallAdapterFactory.create())
                .addConverterFactory(BaseConverter.create())
                .build();
    }

    public OkHttpClient getClient() {
        return client;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
