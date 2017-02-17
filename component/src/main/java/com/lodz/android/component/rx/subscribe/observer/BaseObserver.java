package com.lodz.android.component.rx.subscribe.observer;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.lodz.android.component.base.BaseApplication;
import com.lodz.android.core.log.PrintLog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 基类订阅者
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
    public void onError(Throwable e) {
        try {
            ApplicationInfo appInfo = BaseApplication.get().getPackageManager()
                    .getApplicationInfo(BaseApplication.get().getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null && !TextUtils.isEmpty(appInfo.metaData.getString(ERROR_TAG))){
                PrintLog.e(appInfo.metaData.getString(ERROR_TAG), e.toString());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
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
