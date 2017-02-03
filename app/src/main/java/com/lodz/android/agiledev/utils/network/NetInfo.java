package com.lodz.android.agiledev.utils.network;

import android.support.annotation.IntDef;

/**
 * 手机网络信息
 * Created by zhouL on 2016/11/23.
 */
public class NetInfo {

    @IntDef({NETWORK_TYPE_NONE, NETWORK_TYPE_UNKNOWN, NETWORK_TYPE_WIFI, NETWORK_TYPE_2G, NETWORK_TYPE_3G, NETWORK_TYPE_4G})
    public @interface NetType {}

    /** 未连接网络 */
    public static final int NETWORK_TYPE_NONE = -1;
    /** 未知网络（可能是有线） */
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    /** WIFI */
    public static final int NETWORK_TYPE_WIFI = 1;
    /** 2G */
    public static final int NETWORK_TYPE_2G = 2;
    /** 3G */
    public static final int NETWORK_TYPE_3G = 3;
    /** 4G */
    public static final int NETWORK_TYPE_4G = 4;


    /** 网络类型 */
    @NetType
    public int type = NETWORK_TYPE_NONE;

    /** 网络制式 */
    public int standard = NETWORK_TYPE_NONE;

    /** 扩展信息 */
    public String extraInfo = "";

}