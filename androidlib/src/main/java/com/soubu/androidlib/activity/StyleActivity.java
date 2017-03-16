package com.soubu.androidlib.activity;

import com.soubu.androidlib.navigation.Navigation;

/**
 * 作者：余天然 on 2017/3/15 上午9:52
 */
public abstract class StyleActivity<N extends Navigation> extends LifeActivity implements StyleHelper.Callback<N> {

    private StyleHelper<N> styleHelper;

    @Override
    protected void initLifeCallbacks() {
        styleHelper = new StyleHelper<>(this);
        styleHelper.setStyleCallback(this);
        addLifeCallback(styleHelper);
    }

    public StyleHelper<N> getStyleHelper() {
        return styleHelper;
    }
}
