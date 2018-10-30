package com.lodz.android.component.rx.exception;

import android.os.Build;
import androidx.annotation.RequiresApi;

/**
 * RX异常基类
 * Created by zhouL on 2016/11/22.
 */
public class RxException extends Exception{

    /** 自定义异常信息 */
    private String mErrorMsg = "";

    public RxException(String errorMsg) {
        this.mErrorMsg = errorMsg;
    }

    public RxException(String message, String errorMsg) {
        super(message);
        this.mErrorMsg = errorMsg;
    }

    public RxException(String message, String errorMsg, Throwable cause) {
        super(message, cause);
        this.mErrorMsg = errorMsg;
    }

    public RxException(Throwable cause, String errorMsg) {
        super(cause);
        this.mErrorMsg = errorMsg;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public RxException(String message, String errorMsg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.mErrorMsg = errorMsg;
    }

    /** 获取自定义异常信息 */
    public String getErrorMsg() {
        return mErrorMsg;
    }
}
