package com.soubu.androidlib.activity;


import com.soubu.androidlib.navigation.DefaultNavigation;

/**
 * 作者：余天然 on 2017/3/15 下午12:14
 */
public abstract class TabActivity extends StyleActivity<DefaultNavigation> implements TabHelper.Callback {

    private TabHelper tabHelper;

    @Override
    protected void initLifeCallbacks() {
        super.initLifeCallbacks();
        tabHelper = new TabHelper(this);
        tabHelper.setTabCallback(this);
        addLifeCallback(tabHelper);
    }

    @Override
    public DefaultNavigation createNavigation() {
        return new DefaultNavigation();
    }

    public void switchTab(int pos) {
        tabHelper.switchTab(pos);
    }

}
