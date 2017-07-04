package com.lodz.android.component.rx.exception;

import com.lodz.android.component.R;
import com.lodz.android.component.base.application.BaseApplication;
import com.lodz.android.core.network.NetworkManager;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * 异常工厂
 * Created by zhouL on 2016/11/22.
 */
public class RxExceptionFactory {

    public static RxException create(Throwable t) {
        if (t == null){
            return new RxException(BaseApplication.get().getString(R.string.exception_api_error_tips));
        }

        if (!NetworkManager.get().isNetworkAvailable()) {
            return new NetworkNoConnException(t, BaseApplication.get().getString(R.string.exception_network_no_connect_tips));
        } else if (t instanceof SocketTimeoutException || t instanceof SocketException || t instanceof ConnectException) {
            return new NetworkTimeOutException(t, BaseApplication.get().getString(R.string.exception_network_time_out_tips));
        } else if(t instanceof RxException) {
            return (RxException) t;
        }
        return new RxException(t, BaseApplication.get().getString(R.string.exception_api_error_tips));
    }

}
