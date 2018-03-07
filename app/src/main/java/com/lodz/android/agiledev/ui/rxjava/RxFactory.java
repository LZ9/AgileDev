package com.lodz.android.agiledev.ui.rxjava;

import android.support.annotation.IntDef;

import com.lodz.android.agiledev.ui.rxjava.operate.RxGetCache;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Rxjava操作符工程类
 * Created by zhouL on 2018/3/7.
 */

public class RxFactory {

    /** 从缓存读取数据 */
    public static final int RX_GET_CACHE = 1;


    @IntDef({RX_GET_CACHE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RxType {}

    public static RxContract create(@RxType int type){
        switch (type){
            case RX_GET_CACHE:
                return new RxGetCache();
            default:
                return new RxContract() {
                    @Override
                    public void doCase() {

                    }
                };
        }
    }


}
