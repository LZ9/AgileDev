package com.lodz.android.component.rx.exception;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * 网络异常
 * Created by zhouL on 2018/3/2.
 */

public class NetworkException extends RxException{

    public NetworkException(String errorMsg) {
        super(errorMsg);
    }

    public NetworkException(String message, String errorMsg) {
        super(message, errorMsg);
    }

    public NetworkException(String message, String errorMsg, Throwable cause) {
        super(message, errorMsg, cause);
    }

    public NetworkException(Throwable cause, String errorMsg) {
        super(cause, errorMsg);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public NetworkException(String message, String errorMsg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, errorMsg, cause, enableSuppression, writableStackTrace);
    }
}
