package com.lodz.android.core.network;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 运营商信息
 * Created by zhouL on 2018/4/23.
 */
public class OperatorInfo {
    @IntDef({OPERATOR_UNKNOWN, OPERATOR_CMCC, OPERATOR_CUCC, OPERATOR_CTCC})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OperatorType {}

    /** 未知 */
    public static final int OPERATOR_UNKNOWN = 0;
    /** 移动 */
    public static final int OPERATOR_CMCC = 1;
    /** 联通 */
    public static final int OPERATOR_CUCC = 2;
    /** 电信 */
    public static final int OPERATOR_CTCC = 3;

    /** 网络类型 */
    @OperatorType
    public int type = OPERATOR_UNKNOWN;

    // --------------------- 基站信息 ---------------------------
    /** 中国460 */
    public String mcc;
    /** 00移动、01联通、11电信4G */
    public String mnc;
    /** 1~65535 */
    public String lac;
    /** 2G（1~65535） 3G/4G（1~268435455） */
    public String cid;

    public boolean isSuccess(){
        return type != OPERATOR_UNKNOWN;
    }
}
