package com.lodz.android.component.rx.subscribe.observer;

import android.text.TextUtils;

import com.lodz.android.component.base.application.BaseApplication;
import com.lodz.android.core.log.PrintLog;
import com.lodz.android.core.utils.AppUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 基类订阅者（无背压）
 * Created by zhouL on 2017/2/17.
 */
public abstract class BaseObserver<T> implements Observer<T> {

    private String ERROR_TAG = "error_tag";

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
    public void onError(Throwable t) {
        try {
            printTagLog(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        onBaseError(t);
    }

    /** 打印标签日志 */
    private void printTagLog(Throwable t) {
        if (BaseApplication.get() == null){
            return;
        }
        Object o = AppUtils.getMetaData(BaseApplication.get(), ERROR_TAG);
        if (o != null && o instanceof String){
            String tag = (String) o;
            if (!TextUtils.isEmpty(tag)) {
                PrintLog.e(tag, t.toString(), t);
            }
        }
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
