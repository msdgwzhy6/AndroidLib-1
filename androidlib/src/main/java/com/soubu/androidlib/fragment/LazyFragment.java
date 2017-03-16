package com.soubu.androidlib.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * 自带延迟加载的Fragment基类
 * <p>
 * 一般和ViewPager配合使用
 * <p>
 * 作者：余天然 on 2016/12/12 上午11:27
 */
public abstract class LazyFragment extends StyleFragment {

    public boolean isPrepared = false;//控件是否准备好了

    public boolean isFirstLoad = false;//是否是第一次加载

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        if (getUserVisibleHint()) {
            onFragmentVisibleChange(true);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //防止空指针异常
        if (!isPrepared) {
            return;
        }
        onFragmentVisibleChange(isVisibleToUser);
    }

    public void onFragmentVisibleChange(boolean isVisible) {
        //可见且未加载过数据，则加载数据
        if (isVisible && !isFirstLoad) {
            onFirstLoad();
            isFirstLoad = true;
        }
    }

    /**
     * 第一次加载
     */
    protected void onFirstLoad() {
    }

}
