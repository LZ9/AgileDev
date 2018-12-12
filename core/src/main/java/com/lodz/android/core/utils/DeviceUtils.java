package com.lodz.android.core.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.annotation.StringDef;
import androidx.core.content.ContextCompat;

/**
 * 设备帮助类
 * Created by zhouL on 2017/3/22.
 */
public class DeviceUtils {

    /** 品牌 */
    public static final String BRAND = "BRAND";
    /** 型号 */
    public static final String MODEL = "MODEL";
    /** 模板 */
    public static final String BOARD = "BOARD";
    /** CPU1 */
    public static final String CPU_ABI = "CPU_ABI";
    /** CPU2 */
    public static final String CPU_ABI2 = "CPU_ABI2";
    /** 制造商 */
    public static final String MANUFACTURER = "MANUFACTURER";
    /** 产品 */
    public static final String PRODUCT = "PRODUCT";
    /** 设备 */
    public static final String DEVICE = "DEVICE";

    @StringDef({BRAND, MODEL, BOARD, CPU_ABI, CPU_ABI2, MANUFACTURER, PRODUCT, DEVICE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DeviceKey {}

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
    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
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
    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
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
     * 获取APN名称
     * @param context 上下文
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    public static String getApnName(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null){
            return "";
        }
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null){
            return "";
        }
        if (ConnectivityManager.TYPE_MOBILE == info.getType() && !TextUtils.isEmpty(info.getExtraInfo())) {//移动网络下返回APN名称
            return info.getExtraInfo();
        }
        return "";
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
    public static void setNavigationBarColorRes(Context context, Window window, @ColorRes int navigationBarColor) {
        if (navigationBarColor == 0){
            return;
        }
        setNavigationBarColorInt(context, window, ContextCompat.getColor(context, navigationBarColor));
    }

    /**
     * 设置导航栏颜色
     * @param context 上下文
     * @param window 窗口
     * @param navigationBarColor 导航栏颜色
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setNavigationBarColorInt(Context context, Window window, @ColorInt int navigationBarColor) {
        if (context == null || window == null){
            return;
        }
        try {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(navigationBarColor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 获取设备信息 */
    public static Map<String, String> getDeviceInfo() {
        Map<String, String> infos = new HashMap<>();
        try {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return infos;
    }

    /**
     * 获取设备key对应的值
     * @param key 键，例如：{@link #BRAND}、{@link #MODEL}等等
     */
    public static String getDeviceValue(String key){
        if (TextUtils.isEmpty(key)) {
            return "";
        }
        try {
            Map<String, String> map = getDeviceInfo();
            return map.get(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /** 校验默认位置的root文件，不存在返回null */
    public static File checkRootFile() {
        return checkRootFile(null);
    }

    /**
     * 校验root文件，不存在返回null
     * @param paths su文件的路径数组，传null使用默认路径
     */
    public static File checkRootFile(String[] paths) {
        if (ArrayUtils.getSize(paths) == 0) {
            paths = new String[]{"/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                    "/system/bin/failsafe/su", "/data/local/su"};
        }
        for (String path : paths) {
            File file = new File(path);
            if (file.exists()) {//存在返回文件
                return file;
            }
        }
        return null;
    }

    /** 手机是否root，通过校验默认位置的su文件，存在一定误差 */
    public static boolean isRoot(){
        return checkRootFile() != null;
    }
}
