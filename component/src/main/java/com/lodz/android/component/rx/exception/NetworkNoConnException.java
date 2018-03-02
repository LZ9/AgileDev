package com.lodz.android.component.rx.exception;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * 网络未连接异常
 * Created by zhouL on 2016/11/22.
 */
public class NetworkNoConnException extends NetworkException {
    public NetworkNoConnException(String errorMsg) {
        super(errorMsg);
    }

    public NetworkNoConnException(String message, String errorMsg) {
        super(message, errorMsg);
    }

    public NetworkNoConnException(String message, String errorMsg, Throwable cause) {
        super(message, errorMsg, cause);
    }

    public NetworkNoConnException(Throwable cause, String errorMsg) {
        super(cause, errorMsg);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public NetworkNoConnException(String message, String errorMsg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, errorMsg, cause, enableSuppression, writableStackTrace);
    }
}
