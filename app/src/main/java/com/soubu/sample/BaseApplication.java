package com.soubu.sample;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * 作者：余天然 on 2017/3/15 上午12:24
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...

        instance = this;
    }

    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }
}
