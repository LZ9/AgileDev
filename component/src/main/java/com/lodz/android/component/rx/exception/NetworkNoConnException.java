package com.lodz.android.component.rx.exception;

/**
 * 网络未连接异常
 * Created by zhouL on 2016/11/22.
 */
public class NetworkNoConnException extends RxException {

    public NetworkNoConnException(String errorMsg) {
        super(errorMsg);
    }

    public NetworkNoConnException(String message, String errorMsg) {
        super(message, errorMsg);
    }

    public NetworkNoConnException(String message, Throwable cause, String errorMsg) {
        super(message, cause, errorMsg);
    }

    public NetworkNoConnException(Throwable cause, String errorMsg) {
        super(cause, errorMsg);
    }
}
