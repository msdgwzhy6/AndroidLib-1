package com.soubu.androidlib.recycler.divider;

/**
 * 作者：余天然 on 2016/12/21 下午7:16
 */
public interface Pinnable {

    //是否需要显示悬停title
    boolean isPanned();

    //悬停的tag
    String getPinnedTag();
}
