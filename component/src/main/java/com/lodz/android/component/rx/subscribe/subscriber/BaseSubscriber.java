package com.lodz.android.component.rx.subscribe.subscriber;

import android.text.TextUtils;

import com.lodz.android.component.base.application.BaseApplication;
import com.lodz.android.core.log.PrintLog;
import com.lodz.android.core.utils.AppUtils;

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
