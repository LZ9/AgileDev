package com.lodz.android.component.rx.exception;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * 网络超时异常
 * Created by zhouL on 2016/11/22.
 */
public class NetworkTimeOutException extends NetworkException {
    public NetworkTimeOutException(String errorMsg) {
        super(errorMsg);
    }

    public NetworkTimeOutException(String message, String errorMsg) {
        super(message, errorMsg);
    }

    public NetworkTimeOutException(String message, String errorMsg, Throwable cause) {
        super(message, errorMsg, cause);
    }

    public NetworkTimeOutException(Throwable cause, String errorMsg) {
        super(cause, errorMsg);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public NetworkTimeOutException(String message, String errorMsg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, errorMsg, cause, enableSuppression, writableStackTrace);
    }
}
