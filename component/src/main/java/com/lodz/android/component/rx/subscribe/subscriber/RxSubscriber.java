package com.lodz.android.component.rx.subscribe.subscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * 默认的RX订阅者（带背压）
 * Created by zhouL on 2017/2/6.
 */

public abstract class RxSubscriber<T> implements Subscriber<T> {

    private Subscription mSubscription;

    @Override
    public void onSubscribe(Subscription s) {
        mSubscription = s;
        onRxSubscribe(s);
    }

    @Override
    public void onComplete() {
        onRxComplete();
    }

    @Override
    public void onNext(T t) {
        onRxNext(t);
    }

    @Override
    public void onError(Throwable t) {
        onRxError(t);
    }

    public abstract void onRxSubscribe(Subscription s);

    public abstract void onRxNext(T t);

    public abstract void onRxError(Throwable t);

    public abstract void onRxComplete();

    public Subscription getSubscription(){
        return mSubscription;
    }
}
