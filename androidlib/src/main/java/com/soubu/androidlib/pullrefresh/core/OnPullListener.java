package com.soubu.androidlib.pullrefresh.core;

/**
 * 下拉上拉监听器
 * <p>
 * 作者：余天然 on 2016/10/18 下午3:17
 */
public interface OnPullListener {

    //执行刷新
    void onRefresh();

    //执行加载更多
    void onLoadMore();
}
