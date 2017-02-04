package com.lodz.android.component.rx.exception;


import com.lodz.android.component.rx.status.ResponseStatus;

/**
 * 服务端数据异常
 * Created by zhouL on 2016/11/22.
 */

public class DataException extends RxException {

    /** 数据信息 */
    private ResponseStatus mData;

    public DataException(String errorMsg) {
        super(errorMsg);
    }

    public DataException(String message, String errorMsg) {
        super(message, errorMsg);
    }

    public DataException(String message, Throwable cause, String errorMsg) {
        super(message, cause, errorMsg);
    }

    public DataException(Throwable cause, String errorMsg) {
        super(cause, errorMsg);
    }

    public ResponseStatus getData() {
        return mData;
    }

    public void setData(ResponseStatus data) {
        this.mData = data;
    }
}
