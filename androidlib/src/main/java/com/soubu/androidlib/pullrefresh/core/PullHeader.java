package com.soubu.androidlib.pullrefresh.core;

/**
 * 作者：余天然 on 2016/10/18 下午1:59
 */
public interface PullHeader {

    //下拉刷新
    void onDownBefore(int scrollY);

    //松开刷新
    void onDownAfter(int scrollY);

    //准备刷新(从松手后的位置滚动到刷新的位置)
    void onRefreshScrolling(int scrollY);

    //正在刷新……
    void onRefreshDoing(int scrollY);

    //刷新完成后，回到默认状态中
    void onRefreshCompleteScrolling(int scrollY, boolean isRefreshSuccess);

    //刷新取消后，回到默认状态中（没有超过有效的下拉距离）
    void onRefreshCancelScrolling(int scrollY);
}
