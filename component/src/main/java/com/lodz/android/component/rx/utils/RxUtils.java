package com.lodz.android.component.rx.utils;

import android.text.TextUtils;

import com.lodz.android.component.rx.exception.DataException;
import com.lodz.android.component.rx.exception.RxException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Rx帮助类
 * Created by zhouL on 2017/3/1.
 */
public class RxUtils {

    /** 在异步线程发起，在主线程订阅 */
    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 获取异常的提示语（配合订阅者使用）
     * @param e 异常
     * @param isNetwork 是否网络异常
     * @param defaultTips 默认提示语
     */
    public static String getExceptionTips(Throwable e, boolean isNetwork, String defaultTips){
        if (isNetwork && e instanceof RxException) {
            RxException exception = (RxException) e;
            return exception.getErrorMsg();
        }
        if (e instanceof DataException){
            DataException dataException = (DataException) e;
            if (dataException.getData() != null && !TextUtils.isEmpty(dataException.getData().getMsg())){
                return dataException.getData().getMsg();
            }
        }
        return defaultTips;
    }

    /**
     * 获取网络异常的提示语（配合订阅者使用）
     * @param e 异常
     * @param isNetwork 是否网络异常
     * @param defaultTips 默认提示语
     */
    public static String getNetworkExceptionTips(Throwable e, boolean isNetwork, String defaultTips){
        if (isNetwork && e instanceof RxException) {
            RxException exception = (RxException) e;
            return exception.getErrorMsg();
        }
        return defaultTips;
    }

}
