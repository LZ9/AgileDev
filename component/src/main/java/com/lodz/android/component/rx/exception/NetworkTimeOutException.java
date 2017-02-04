package com.lodz.android.component.rx.exception;

/**
 * 网络超时异常
 * Created by zhouL on 2016/11/22.
 */
public class NetworkTimeOutException extends RxException {

    public NetworkTimeOutException(String errorMsg) {
        super(errorMsg);
    }

    public NetworkTimeOutException(String message, String errorMsg) {
        super(message, errorMsg);
    }

    public NetworkTimeOutException(String message, Throwable cause, String errorMsg) {
        super(message, cause, errorMsg);
    }

    public NetworkTimeOutException(Throwable cause, String errorMsg) {
        super(cause, errorMsg);
    }
}
