package com.soubu.androidlib.application;

import android.app.Application;
import android.content.Context;

import com.soubu.androidlib.web.TestConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * 作者：余天然 on 2017/3/16 上午9:51
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    private Scheduler scheduler;
    private ActivityLifecycle activityLifecycle;

    @Override
    public void onCreate() {
        super.onCreate();
        TestConfig.IS_TEST = true;
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    /**
     * 生命周期管理
     */
    private void initActivityCycle() {
        activityLifecycle = new ActivityLifecycle();
        this.registerActivityLifecycleCallbacks(activityLifecycle);//注册
    }


    public Context getCurrentActivity() {
        return activityLifecycle.getCurrentActivity();
    }

    public void finishAllActivity() {
        activityLifecycle.finishAllActivity();
    }

    /**
     * 线程池管理
     */
    private void initScheduler() {
        //核心有2个线程，最大线程数量为20，存活时间60s
        ExecutorService customThreadExecutor = new ThreadPoolExecutor(2, 20, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        scheduler = Schedulers.from(customThreadExecutor);
    }

    public static Scheduler getScheduler() {
        return getInstance().scheduler;
    }
}
