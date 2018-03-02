package com.lodz.android.component.rx.exception;

import com.lodz.android.core.network.NetworkManager;

import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * RX异常工厂
 * Created by zhouL on 2016/11/22.
 */
public class RxExceptionFactory {

    public static RxException create(Throwable t) {
        if (t == null){
            return new RxException("数据接口异常，请稍后再试");
        }

        if (t instanceof RxException){
            return (RxException) t;
        }

        if (!NetworkManager.get().isNetworkAvailable()) {
            return new NetworkNoConnException(t.getMessage(), "网络尚未连接，请检查您的网络状态", t.getCause());
        }

        if (t instanceof SocketTimeoutException || t instanceof SocketException) {
            return new NetworkTimeOutException(t.getMessage(), "网络连接超时，请稍后再试", t.getCause());
        }

        return new RxException(t.getMessage(), "数据接口异常，请稍后再试", t.getCause());
    }

}
