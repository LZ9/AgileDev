package com.lodz.android.component.rx.subscribe.observer;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * 默认的RX订阅者，对数据异常进行基础校验
 * Created by zhouL on 2017/2/4.
 */
public abstract class RxObserver<T> implements Observer<T> {

    @Override
    public void onComplete() {
        onRxComplete();
    }

    @Override
    public void onSubscribe(Disposable d) {
        onRxSubscribe(d);
    }

    @Override
    public void onError(Throwable e) {
//        try {
//            RxException exception = RxExceptionFactory.create(t);
//            exception.printStackTrace();
//            networkErrorToast(exception);
//            onError(exception, mIndex);
//            mIndex++;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            onErrorfinally();
//        }
    }

    @Override
    public void onNext(T t) {

    }

    public abstract void onRxSubscribe(Disposable d);

    public abstract void onRxNext(T t);

    public abstract void onRxError(Throwable e);

    public abstract void onRxComplete();

    protected void onErrorfinally() {
    }
}
