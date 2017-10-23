package com.lodz.android.component.rx.exception;

import com.lodz.android.component.R;
import com.lodz.android.component.base.application.BaseApplication;
import com.lodz.android.core.network.NetworkManager;

import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * 异常工厂
 * Created by zhouL on 2016/11/22.
 */
public class RxExceptionFactory {

    public static RxException create(Throwable t) {
        String tips = "";
        if (t == null){
            tips = BaseApplication.get() == null ? "数据接口异常，请稍后再试" : BaseApplication.get().getString(R.string.component_exception_api_error_tips);
            return new RxException(tips);
        }

        if (!NetworkManager.get().isNetworkAvailable()) {
            tips = BaseApplication.get() == null ? "网络尚未连接，请检查您的网络状态" : BaseApplication.get().getString(R.string.component_exception_network_no_connect_tips);
            return new NetworkNoConnException(t, tips);
        } else if (t instanceof SocketTimeoutException || t instanceof SocketException) {
            tips = BaseApplication.get() == null ? "网络连接超时，请稍后再试" : BaseApplication.get().getString(R.string.component_exception_network_time_out_tips);
            return new NetworkTimeOutException(t, tips);
        } else if(t instanceof RxException) {
            return (RxException) t;
        }
        tips = BaseApplication.get() == null ? "数据接口异常，请稍后再试" : BaseApplication.get().getString(R.string.component_exception_api_error_tips);
        return new RxException(t, tips);
    }

}
