package com.lodz.android.core.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

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
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getIMSI(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (tm == null){
                return "";
            }
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
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getIMEI(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (tm == null){
                return "";
            }
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
    @SuppressLint("MissingPermission")
    public static String getAPN(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null){
            return "";
        }
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null){
            return "";
        }
        if (ConnectivityManager.TYPE_WIFI == info.getType()) {
            return TextUtils.isEmpty(info.getTypeName()) ? "wifi" : info.getTypeName();
        }
        return TextUtils.isEmpty(info.getExtraInfo()) ? "mobile" : info.getExtraInfo();
    }

    /**
     * 设置状态栏颜色
     * @param context 上下文
     * @param window 窗口
     * @param statusBarColor 状态栏颜色
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarColor(Context context, Window window, @ColorRes int statusBarColor) {
        if (context == null || statusBarColor == 0 || window == null){
            return;
        }
        try {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(context, statusBarColor));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置导航栏颜色
     * @param context 上下文
     * @param window 窗口
     * @param navigationBarColor 导航栏颜色
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setNavigationBarColor(Context context, Window window, @ColorRes int navigationBarColor) {
        if (context == null || window == null || navigationBarColor == 0){
            return;
        }
        try {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(ContextCompat.getColor(context, navigationBarColor));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
