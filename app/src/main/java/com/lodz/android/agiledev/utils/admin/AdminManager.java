package com.lodz.android.agiledev.utils.admin;

import android.annotation.SuppressLint;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.IntDef;
import android.text.TextUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 设备管理者
 * Created by zhouL on 2017/4/19.
 */
public class AdminManager {

    @SuppressLint("InlinedApi")
    @IntDef({
            DevicePolicyManager.PASSWORD_QUALITY_UNSPECIFIED, // 对密码没有要求
            DevicePolicyManager.PASSWORD_QUALITY_ALPHABETIC, // 用户输入的密码必须要有字母（或者其他字符）
            DevicePolicyManager.PASSWORD_QUALITY_ALPHANUMERIC, // 用户输入的密码必须要有字母和数字
            DevicePolicyManager.PASSWORD_QUALITY_NUMERIC, // 用户输入的密码必须要有数字
            DevicePolicyManager.PASSWORD_QUALITY_NUMERIC_COMPLEX,// 用户输入的密码必须要有数字且不能是重复的或简单排序的（例如：1111、1234）
            DevicePolicyManager.PASSWORD_QUALITY_SOMETHING,// 由设计人员决定的
            DevicePolicyManager.PASSWORD_QUALITY_COMPLEX,// 用户输入的密码必须要有字母、数字和特殊符号
    })
    @Retention(RetentionPolicy.SOURCE)
    private @interface PasswordQuality {}

    /**
     * 获取设备管理
     * @param context 上下文
     */
    public static DevicePolicyManager get(Context context) {
        return (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
    }

    /**
     * 激活设备管理
     * @param context 上下文
     */
    public static void active(Context context){
        active(context, "");
    }

    /**
     * 激活设备管理
     * @param context 上下文
     * @param tips 提示语
     */
    public static void active(Context context, String tips){
        if (context == null || isAdminActive(context)) {
            return;
        }
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, new ComponentName(context, AdminReceiver.class));
        if (!TextUtils.isEmpty(tips)) {
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, tips);
        }
        context.startActivity(intent);
    }

    /**
     * 设备管理是否被激活
     * @param context 上下文
     */
    public static boolean isAdminActive(Context context) {
        if (context == null) {
            return false;
        }
        DevicePolicyManager manager = get(context);
        ComponentName name = new ComponentName(context, AdminReceiver.class);
        return manager != null && manager.isAdminActive(name);
    }

    /**
     * 移除激活
     * @param context 上下文
     */
    public static void removeActiveAdmin(Context context){
        if (context == null || !isAdminActive(context)) {
            return;
        }
        DevicePolicyManager manager = get(context);
        if (manager == null){
            return;
        }
        manager.removeActiveAdmin(new ComponentName(context, AdminReceiver.class));
    }

    /**
     * 跳转密码类型
     * @param context 上下文
     */
    public static void setPasswordQuality(Context context, @PasswordQuality int quality){
        if (context == null || !isAdminActive(context)) {
            return;
        }
        DevicePolicyManager manager = get(context);
        if (manager == null){
            return;
        }
        if (quality == DevicePolicyManager.PASSWORD_QUALITY_NUMERIC_COMPLEX){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                manager.setPasswordQuality(new ComponentName(context, AdminReceiver.class), quality);
            }
            return;
        }
        manager.setPasswordQuality(new ComponentName(context, AdminReceiver.class), quality);
    }

    /**
     * 重置密码
     * @param context 上下文
     * @param password 密码
     */
    public static void resetPassword(Context context, String password){
        if (context == null || !isAdminActive(context) || TextUtils.isEmpty(password)) {
            return;
        }
        DevicePolicyManager manager = get(context);
        if (manager == null){
            return;
        }
        manager.resetPassword(password, DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
    }

    /**
     * 锁屏
     * @param context 上下文
     */
    public static void lockNow(Context context){
        if (context == null || !isAdminActive(context)) {
            return;
        }
        DevicePolicyManager manager = get(context);
        if (manager == null){
            return;
        }
        manager.lockNow();
    }

    /**
     * 恢复出厂设置
     * @param context 上下文
     */
    public static void wipeData(Context context){
        if (context == null || !isAdminActive(context)) {
            return;
        }
        DevicePolicyManager manager = get(context);
        if (manager == null){
            return;
        }
        manager.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
    }

    /**
     * 设置相机是否禁用
     * @param context 上下文
     * @param disabled 是否禁用
     */
    public static void setCameraDisabled(Context context, boolean disabled){
        if (context == null || !isAdminActive(context)) {
            return;
        }
        DevicePolicyManager manager = get(context);
        if (manager == null){
            return;
        }
        manager.setCameraDisabled(new ComponentName(context, AdminReceiver.class), disabled);
    }

}