package com.lodz.android.core.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.text.TextUtils;

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

    /**
     * 当前是否在主线程（UI线程）
     * @return
     */
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

}
