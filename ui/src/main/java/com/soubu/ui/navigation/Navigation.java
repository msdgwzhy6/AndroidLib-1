package com.soubu.ui.navigation;

import android.view.View;

/**
 * 抽象的导航栏
 * <p>
 * 作者：余天然 on 2017/3/14 下午10:13
 */
public abstract class Navigation {

    public abstract int getLayoutId();

    public int getTitleId() {
        return 0;
    }

    public int getBackId() {
        return 0;
    }

    public void init(View navigationView) {

    }
}
