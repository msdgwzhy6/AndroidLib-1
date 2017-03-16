package com.soubu.androidlib.web;

import com.trello.rxlifecycle.LifecycleTransformer;

/**
 * 方便Rx绑定生命周期
 * <p>
 * 作者：余天然 on 2016/12/9 下午3:18
 */
public interface RxView<T> {

    LifecycleTransformer<T> bindLife();
}
