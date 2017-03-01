package com.lodz.android.component.rx.subscribe.subscriber;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.lodz.android.component.base.BaseApplication;
import com.lodz.android.core.log.PrintLog;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * 基类订阅者（带背压）
 * Created by zhouL on 2017/3/1.
 */
public abstract class BaseSubscriber<T> implements Subscriber<T> {

    private String ERROR_TAG = "error_tag";

    private Subscription mSubscription;

    @Override
    public void onSubscribe(Subscription s) {
        mSubscription = s;
        onBaseSubscribe(s);
    }

    @Override
    public void onNext(T t) {
        onBaseNext(t);
    }

    @Override
    public void onError(Throwable t) {
        try {
            ApplicationInfo appInfo = BaseApplication.get().getPackageManager()
                    .getApplicationInfo(BaseApplication.get().getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null && !TextUtils.isEmpty(appInfo.metaData.getString(ERROR_TAG))) {
                PrintLog.e(appInfo.metaData.getString(ERROR_TAG), t.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        onBaseError(t);
    }

    @Override
    public void onComplete() {
        onBaseComplete();
    }

    public Subscription getSubscription() {
        return mSubscription;
    }

    public void clearSubscription(){
        mSubscription = null;
    }

    public void cancel(){
        if (mSubscription != null){
            mSubscription.cancel();
            onCancel();
        }
    }

    public void request(long n){
        if (mSubscription != null){
            mSubscription.request(n);
        }
    }

    public abstract void onBaseSubscribe(Subscription s);

    public abstract void onBaseNext(T t);

    public abstract void onBaseError(Throwable e);

    public abstract void onBaseComplete();
    /** 取消订阅时回调 */
    protected void onCancel(){}
}
