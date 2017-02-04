package com.lodz.android.component.rx.exception;

import android.text.TextUtils;

/**
 * RX异常基类
 * Created by zhouL on 2016/11/22.
 */
public class RxException extends Exception{

    /** 自定义异常信息 */
    private String mErrorMsg = "";

    public RxException(String errorMsg) {
        super();
        this.mErrorMsg = errorMsg;
    }

    public RxException(String message, String errorMsg) {
        super(message);
        this.mErrorMsg = errorMsg;
    }

    public RxException(String message, Throwable cause, String errorMsg) {
        super(message, cause);
        this.mErrorMsg = errorMsg;
    }

    public RxException(Throwable cause, String errorMsg) {
        super(cause);
        this.mErrorMsg = errorMsg;
    }

    /** 获取自定义异常信息 */
    public String getErrorMsg() {
        return TextUtils.isEmpty(mErrorMsg) ? getMessage() : mErrorMsg;
    }
}
