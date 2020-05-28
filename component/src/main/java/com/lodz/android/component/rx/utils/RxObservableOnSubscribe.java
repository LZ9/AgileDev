package com.lodz.android.component.rx.utils;


import io.reactivex.rxjava3.core.ObservableOnSubscribe;

/**
 * 带入参的ObservableOnSubscribe
 * Created by zhouL on 2018/7/3.
 */
public abstract class RxObservableOnSubscribe<T> implements ObservableOnSubscribe<T> {

    private Object[] mArgs;

    public RxObservableOnSubscribe(Object... args) {
        this.mArgs = args;
    }

    /** 获取入参的map */
    public Object[] getArgs() {
        return mArgs;
    }
}
