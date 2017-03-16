package com.soubu.androidlib.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private static List<Activity> activityStack = new ArrayList<>();

    //当前上下文,用以显式当前dialog
    private Activity currentActivity;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity.getParent() != null) {
            currentActivity = activity.getParent();
        } else {
            currentActivity = activity;
        }
        activityStack.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activity.getParent() != null) {
            currentActivity = activity.getParent();
        } else {
            currentActivity = activity;
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity.getParent() != null) {
            currentActivity = activity.getParent();
        } else {
            currentActivity = activity;
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (currentActivity != null) {
            currentActivity = null;
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activityStack.remove(activity);
    }

    /**
     * 获取当前的Activity
     * @return
     */
    public Activity getCurrentActivity() {
        return currentActivity;
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

}
