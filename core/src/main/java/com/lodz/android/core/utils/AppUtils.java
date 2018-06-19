package com.lodz.android.core.utils;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        if (am == null){
            return "";
        }
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return "";
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return "";
    }

    /**
     * 安装akp文件
     * @param context 上下文
     * @param apkPath apk路径
     */
    public static void installApk(Context context, @NonNull String apkPath, String authority) throws Exception {
        File file = FileUtils.createFile(apkPath);
        if (!FileUtils.isFileExists(file)){
            throw new IllegalArgumentException("file no exists");
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(context, authority, file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    /**
     * 卸载应用
     * @param context 上下文
     * @param packageName 包名
     */
    public static void uninstallApp(Context context, String packageName){
        Intent intent = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName));
        context.startActivity(intent);
    }

    /** 获取随机的UUID */
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    /**
     * 通过LaunchIntent打开APP
     * @param context 上下文
     * @param packageName 目标APP的包名
     */
    public static void openAppByLaunch(Context context, String packageName) throws IllegalArgumentException, ActivityNotFoundException{
        if (context == null || TextUtils.isEmpty(packageName)) {
            throw new IllegalArgumentException("参数不能为空");
        }
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null){
            throw new ActivityNotFoundException();
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 通过android.intent.action.MAIN来打开APP
     * @param context 上下文
     * @param packageName 目标APP的包名
     */
    public static void openAppByActionMain(Context context, String packageName) throws IllegalArgumentException, ActivityNotFoundException{
        if (context == null || TextUtils.isEmpty(packageName)) {
            throw new IllegalArgumentException("参数不能为空");
        }

        String mainActivityName = "";// 启动页的路径

        Intent intent = new Intent(Intent.ACTION_MAIN);
        PackageManager packageManager = context.getPackageManager();
        for (ResolveInfo resolve : packageManager.queryIntentActivities(intent, 0)) {
            ActivityInfo info = resolve.activityInfo;
            if (info == null){
                continue;
            }
            if (packageName.equals(info.packageName)){
                mainActivityName = info.name;
                break;
            }
        }

        if (TextUtils.isEmpty(mainActivityName)) {
            throw new ActivityNotFoundException("没有找到该应用");
        }

        intent.setComponent(new ComponentName(packageName, mainActivityName));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 判断对应包名的app是否安装
     * @param context 上下文
     * @param pkgName 包名
     */
    public static boolean isPkgInstalled(Context context, String pkgName) {
        if (context == null || TextUtils.isEmpty(pkgName)) {
            return false;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(pkgName, PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
            return packageInfo != null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取已安装的PackageInfo列表
     * @param context 上下文
     */
    public static List<PackageInfo> getInstalledPackages(Context context){
        if (context == null) {
            return new ArrayList<>();
        }
        List<PackageInfo> packageInfos = context.getPackageManager().getInstalledPackages(PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        return packageInfos == null ? new ArrayList<PackageInfo>() : packageInfos;
    }

    /**
     * 获取对应包名的PackageInfo
     * @param context 上下文
     * @param pkgName 包名
     */
    public static PackageInfo getPackageInfo(Context context, String pkgName) {
        if (context == null || TextUtils.isEmpty(pkgName)) {
            return null;
        }
        try {
            return context.getPackageManager().getPackageInfo(pkgName, PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
    public static boolean isGpsOpen(Context context) {
        String str = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        return !TextUtils.isEmpty(str) && (str.contains("gps") || str.contains("GPS"));
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
