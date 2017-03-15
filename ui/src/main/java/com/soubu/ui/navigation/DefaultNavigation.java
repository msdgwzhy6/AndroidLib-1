package com.soubu.ui.navigation;

import android.view.View;

import com.soubu.ui.R;


/**
 * 作者：余天然 on 2017/3/14 下午11:42
 */
public class DefaultNavigation extends Navigation {

    @Override
    public int getLayoutId() {
        return R.layout.navigation_default;
    }

    @Override
    public int getTitleId() {
        return R.id.tv_title;
    }

    @Override
    public int getBackId() {
        return R.id.fr_back;
    }

    @Override
    public void init(View navigationView) {

    }
}
