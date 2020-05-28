package com.lodz.android.component.rx.subscribe.observer;

import com.lodz.android.component.rx.exception.DataException;
import com.lodz.android.component.rx.exception.NetworkException;
import com.lodz.android.component.rx.exception.RxException;
import com.lodz.android.component.rx.exception.RxExceptionFactory;
import com.lodz.android.component.rx.status.ResponseStatus;

import io.reactivex.rxjava3.disposables.Disposable;


/**
 * 网络接口使用的订阅者（无背压），主要对接口进行判断处理
 * Created by zhouL on 2017/2/4.
 */
public abstract class RxObserver<T> extends BaseObserver<T> {

    @Override
    public final void onBaseComplete() {
        onRxComplete();
    }

    @Override
    public final void onBaseSubscribe(Disposable d) {
        onRxSubscribe(d);
    }

    @Override
    public final void onBaseError(Throwable t) {
        RxException exception = RxExceptionFactory.create(t);
        onRxError(exception, exception instanceof NetworkException);
        onErrorEnd();
    }

    @Override
    public final void onBaseNext(T t) {
        try {
            checkError(t);
            onRxNext(t);
        } catch (Exception e) {
            onError(e);
        }
    }

    /** 核对数据 */
    private void checkError(T t) throws NullPointerException, RxException {
        if (t == null) {
            throw new NullPointerException("数据是空的");
        }

        if (t instanceof ResponseStatus) {
            ResponseStatus status = (ResponseStatus) t;
            if (!status.isSuccess()) {//服务端返回接口失败
                DataException exception = new DataException("response fail");
                exception.setData(status);
                throw exception;
            }
        }
    }

    public void onRxSubscribe(Disposable d){}

    public abstract void onRxNext(T t);

    public abstract void onRxError(Throwable e, boolean isNetwork);

    public void onRxComplete(){}

    /** onError执行完后会调用该方法 */
    public void onErrorEnd() {}

}
