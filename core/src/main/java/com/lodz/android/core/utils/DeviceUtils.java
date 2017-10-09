package com.lodz.android.core.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.Locale;

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

    /** 获取语言 */
    public static String getLanguage() {
        Locale locale = Locale.getDefault();
        return TextUtils.isEmpty(locale.getLanguage()) ? "" : locale.getLanguage();
    }

    /** 获取国家 */
    public static String getCountry() {
        Locale locale = Locale.getDefault();
        return TextUtils.isEmpty(locale.getCountry()) ? "" : locale.getCountry();
    }

    /**
     * 获取APN
     * @param context 上下文
     */
    public static String getAPN(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null){
            return "";
        }
        if (ConnectivityManager.TYPE_WIFI == info.getType()) {
            return TextUtils.isEmpty(info.getTypeName()) ? "wifi" : info.getTypeName();
        }
        return TextUtils.isEmpty(info.getExtraInfo()) ? "mobile" : info.getExtraInfo();
    }
}
