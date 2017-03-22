package com.lodz.android.core.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * 设备帮助类
 * Created by zhouL on 2017/3/22.
 */
public class DeviceUtils {

    /** 获取手机的UA信息 */
    public static String getUserAgent() {
        try {
            return System.getProperty("http.agent");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取手机的IMSI
     * @param context 上下文
     * <p>
     * <b>需要动态申请READ_PHONE_STATE<b/>
     */
    @SuppressLint("HardwareIds")
    public static String getIMSI(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            return TextUtils.isEmpty(tm.getSubscriberId()) ? "" : tm.getSubscriberId();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取手机的IMEI
     * @param context 上下文
     * <p>
     * <b>需要动态申请READ_PHONE_STATE<b/>
     */
    @SuppressLint("HardwareIds")
    public static String getIMEI(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            return TextUtils.isEmpty(tm.getDeviceId()) ? "" : tm.getDeviceId();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
