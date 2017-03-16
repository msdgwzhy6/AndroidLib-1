package com.soubu.androidlib.web;


import com.soubu.androidlib.util.LogUtil;
import com.soubu.androidlib.util.ShowWidgetUtil;

import java.net.ConnectException;

import rx.Subscriber;

/**
 * Rx事件接收器基类
 * <p>
 * 作者：余天然 on 2016/12/7 下午4:31
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private RxView view;
    private ILoading loading;

    public BaseSubscriber(RxView view, ILoading loading) {
        this.view = view;
    }

    public RxView getBaseView() {
        return view;
    }

    @Override
    public void onCompleted() {
        this.view = null;
        this.loading.dismiss();
    }

    @Override
    public void onError(Throwable e) {
        this.view = null;
        this.loading.dismiss();
        //先调用网络错误
        BaseException exception = castToException(e);
        //再调用请求失败
        onFailure(exception);
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    public BaseException castToException(Throwable e) {
        if (e instanceof ConnectException) {
            return new BaseException(0, -5, "请检查网络", e);
        }
        if (e instanceof BaseException) {
            return (BaseException) e;
        }
        //系统故障
        return new BaseException(0, -1, "系统故障", e);
    }

    /**
     * 请求成功
     */
    public abstract void onSuccess(T response);

    /**
     * 请求失败
     * <p>
     * 这里做一些公共的错误处理，个别页面需要自己处理错误的话，重写即可
     */
    public void onFailure(BaseException exception) {
        LogUtil.printException(exception);
        ShowWidgetUtil.showShort(exception.getMessage());
    }

}
