package com.lodz.android.agiledev.utils.admin;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.lodz.android.core.log.PrintLog;

/**
 * 设备管理接收器
 * Created by zhouL on 2017/4/19.
 */
public class AdminReceiver extends DeviceAdminReceiver {

    private static final String TAG = "AdminReceiver";

    @Override
    public void onEnabled(Context context, Intent intent) {
        PrintLog.e(TAG, "onEnabled , 设备被激活");
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        PrintLog.e(TAG, "onDisableRequested , 用户请求取消激活");
        return super.onDisableRequested(context, intent);
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        PrintLog.e(TAG, "onDisabled , 取消激活");
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        PrintLog.e(TAG, "onPasswordChanged , 用户改变设备密码");
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        PrintLog.e(TAG, "onPasswordFailed , 用户改变密码失败");
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        PrintLog.e(TAG, "onPasswordSucceeded , 用户改变密码成功");
    }

    @Override
    public void onPasswordExpiring(Context context, Intent intent) {
        PrintLog.e(TAG, "onPasswordExpiring , 密码过期");
    }

    @Override
    public void onProfileProvisioningComplete(Context context, Intent intent) {
        PrintLog.e(TAG, "onProfileProvisioningComplete , 配置完成");
    }

    @Override
    public void onLockTaskModeEntering(Context context, Intent intent, String pkg) {
        PrintLog.e(TAG, "onLockTaskModeEntering , 进入锁定模式");
    }

    @Override
    public void onLockTaskModeExiting(Context context, Intent intent) {
        PrintLog.e(TAG, "onLockTaskModeExiting , 退出锁定模式");
    }

    @Override
    public String onChoosePrivateKeyAlias(Context context, Intent intent, int uid, Uri uri, String alias) {
        PrintLog.e(TAG, "onChoosePrivateKeyAlias , 选择私钥别名");
        return super.onChoosePrivateKeyAlias(context, intent, uid, uri, alias);
    }
}