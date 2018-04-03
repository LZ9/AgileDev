package com.lodz.android.core.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import java.io.File;
import java.util.List;

/**
 * APP帮助类
 * Created by zhouL on 2016/11/17.
 */
public class AppUtils {

    /**
     * 获取应用程序名称
     * @param context 上下文
     */
    public static String getAppName(Context context) {
        try  {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取客户端版本名称
     * @param context 上下文
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取客户端版本号
     * @param context 上下文
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /** 当前是否在主线程（UI线程） */
    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 判断传入的context是不是在主进程
     * @param context 上下文
     */
    public static boolean isMainProcess(Context context){
        String processName = getProcessName(context);
        return !TextUtils.isEmpty(processName) && !processName.contains(":");
    }

    /**
     * 获取进程名称
     * @param context 上下文
     */
    public static String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     * 安装akp文件
     * @param context 上下文
     * @param apkPath apk路径
     */
    public static void installApk(Context context, @NonNull String apkPath) {
        try {
            File file = FileUtils.createFile(apkPath);
            if (!FileUtils.isFileExists(file)){
                return;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断对应包名的app是否安装
     * @param context 上下文
     * @param pkgName 包名
     */
    public static boolean isPkgInstalled(Context context, String pkgName) {
        if (TextUtils.isEmpty(pkgName)) {
            return false;
        }

        List<PackageInfo> pinfo = context.getPackageManager().getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo == null) {
            return false;
        }

        for (int i = 0; i < pinfo.size(); i++) {
            String pn = pinfo.get(i).packageName;
            if (pn.equals(pkgName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取对应包名的PackageInfo
     * @param context 上下文
     * @param pkgName 包名
     */
    public static PackageInfo getPackageInfo(Context context, String pkgName) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(pkgName, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 权限是否被授予
     * @param context 上下文
     * @param permission 权限
     */
    public static boolean isPermissionGranted(Context context, String permission){
        try {
            return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据包名打开对应的设置界面
     * @param context 上下文
     */
    public static void jumpAppDetailSetting(Context context){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    /**
     * 跳转到日期设置页面
     * @param context 上下文
     */
    public static void jumpDateSetting(Context context){
        Intent intent = new Intent(Settings.ACTION_DATE_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 获取MetaData
     * @param context 上下文
     * @param key 标签名
     */
    public static Object getMetaData(Context context, String key) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 当前APP是否在后台
     * @param context 上下文
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null){
            return false;
        }
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
            }
        }
        return false;
    }

    /**
     * 跳转到定位设置页
     * @param context 上下文
     */
    public static void jumpLocationSetting(Context context){
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * GPS是否打开
     * @param context 上下文
     */
    public static boolean isGpsClose(Context context) {
        String str = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        return TextUtils.isEmpty(str) || (!TextUtils.isEmpty(str) && str.equals("0"));
    }

    /**
     * 跳转到WIFI设置页
     * @param context 上下文
     */
    public static void jumpWifiSetting(Context context){
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 跳转到数据流量设置页
     * @param context 上下文
     */
    public static void jumpDataRoamingSetting(Context context){
        Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
        context.startActivity(intent);
    }

}
