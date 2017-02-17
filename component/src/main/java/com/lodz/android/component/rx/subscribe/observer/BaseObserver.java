package com.lodz.android.component.rx.subscribe.observer;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 基类订阅者
 * Created by zhouL on 2017/2/17.
 */
public abstract class BaseObserver<T> implements Observer<T> {

    private Disposable mDisposable;

    @Override
    public void onSubscribe(Disposable d) {
        this.mDisposable = d;
        onBaseSubscribe(d);
    }

    @Override
    public void onNext(T t) {
        onBaseNext(t);
    }

    @Override
    public void onError(Throwable e) {
        onBaseError(e);
    }

    @Override
    public void onComplete() {
        onBaseComplete();
    }

    public Disposable getDisposable(){
        return mDisposable;
    }

    public void clearDisposable(){
        mDisposable = null;
    }

    public void dispose(){
        if (mDisposable != null){
            mDisposable.dispose();
            onDispose();
        }
    }

    public abstract void onBaseSubscribe(Disposable d);

    public abstract void onBaseNext(T t);

    public abstract void onBaseError(Throwable e);

    public abstract void onBaseComplete();
    /** 取消订阅时回调 */
    protected void onDispose(){}
}
