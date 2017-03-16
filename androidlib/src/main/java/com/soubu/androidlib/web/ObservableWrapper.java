package com.soubu.androidlib.web;

import com.soubu.androidlib.util.LogUtil;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * 作者：余天然 on 2016/12/9 下午4:18
 */
public class ObservableWrapper<T> {

    private Observable<T> observable;

    public ObservableWrapper(Observable<T> observable) {
        this.observable = observable;
    }

    public Observable<T> getObservable() {
        return observable;
    }

    public Subscription sendTo(final BaseSubscriber<T> subscriber,RxView<T> rxView, final ILoading loading) {
        if (!TestConfig.IS_TEST) {
            return observable
                    .compose(rxView.bindLife())
                    .compose(new BaseTransformer<T>())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            if (loading != null) {
                                loading.show();
                            }
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())//在主线程显示进度条
                    .throttleFirst(1, TimeUnit.SECONDS)//取1秒之内的最后一次,防止重复提交
                    .onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {//捕获自定义异常或系统异常
                        @Override
                        public Observable<? extends T> call(final Throwable throwable) {
                            LogUtil.print(throwable.getMessage());
                            return Observable.create(new Observable.OnSubscribe<T>() {
                                @Override
                                public void call(Subscriber<? super T> subscriber) {
                                    if (throwable instanceof BaseException) {
                                        subscriber.onError(throwable);
                                    } else if (throwable instanceof ConnectException) {
                                        subscriber.onError(throwable);
                                    } else {
                                        subscriber.onError(new BaseException(0, -4, "网络解析错误", throwable));
                                    }
                                }
                            });
                        }
                    })
                    .subscribe(subscriber);

        } else {
            return observable
                    .subscribe(subscriber);
        }

    }

    public ObservableWrapper<T> interval(long initialDelay, long period, TimeUnit unit) {
        Observable<T> newObservable = Observable.interval(0, 10, TimeUnit.SECONDS)
                .flatMap(new Func1<Long, Observable<T>>() {
                    @Override
                    public Observable<T> call(Long aLong) {
                        return observable;
                    }
                });
        return new ObservableWrapper<T>(newObservable);
    }
}
