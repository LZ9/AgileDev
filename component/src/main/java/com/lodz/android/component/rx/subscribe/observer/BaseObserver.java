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

    private static final String ERROR_TAG = "error_tag";

    private Disposable mDisposable;

    @Override
    public final void onSubscribe(Disposable d) {
        this.mDisposable = d;
        onBaseSubscribe(d);
    }

    @Override
    public final void onNext(T t) {
        onBaseNext(t);
    }

    @Override
    public final void onError(Throwable t) {
        if (t != null){
            t.printStackTrace();
        }
        printTagLog(t);
        onBaseError(t);
    }

    /** 打印标签日志 */
    private void printTagLog(Throwable t) {
        if (BaseApplication.get() == null || t == null){
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
    public final void onComplete() {
        onBaseComplete();
    }

    public Disposable getDisposable(){
        return mDisposable;
    }

    public void clearDisposable(){
        mDisposable = null;
    }

    /** 停止订阅 */
    public void dispose(){
        if (mDisposable != null){
            mDisposable.dispose();
            onDispose();
        }
    }

    public void onBaseSubscribe(Disposable d){}

    public abstract void onBaseNext(T t);

    public abstract void onBaseError(Throwable e);

    public void onBaseComplete(){}
    /** 取消订阅时回调 */
    public void onDispose(){}

    /** 创建空调用 */
    public static <T> Observer<T> empty() {
        return new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(T t) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}
