package com.lodz.android.core.utils;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.RequiresPermission;
import android.view.Window;
import android.view.WindowManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 系统设置帮助类
 * Created by zhouL on 2017/9/29.
 */

public class SettingUtils {

    @IntDef({Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ScreenBrightnessMode{}

    /**
     * 获取系统屏幕亮度模式的状态
     * （手动：Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL ； 自动：Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC）
     * @param context 上下文
     */
    public static int getScreenBrightnessModeState(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    /**
     * 系统屏幕亮度是否为自动模式
     * @param context 上下文
     */
    public static boolean isScreenBrightnessModeAutomatic(Context context){
        return getScreenBrightnessModeState(context) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
    }

    /**
     * 设置系统屏幕亮度模式
     * @param context 上下文
     * @param mode 模式（手动：Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL ； 自动：Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC）
     */
    public static void setScreenBrightnessMode(Context context, @ScreenBrightnessMode int mode) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, mode);
    }

    /**
     * 获取系统亮度值，范围0-255
     * @param context 上下文
     */
    public static int getScreenBrightness(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 255);
    }

    /**
     * 设置系统亮度
     * （此方法只是更改了系统的亮度属性，并不能看到效果。要想看到效果可以使用setWindowBrightness()方法设置窗口的亮度）
     * @param context 上下文
     * @param brightness 亮度值（0-255）
     */
    public static void setScreenBrightness(Context context, @IntRange(from = 1, to = 255) int brightness) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
    }

    /**
     * 设置给定Activity的窗口的亮度（可以看到效果，但系统的亮度属性不会改变）
     * @param activity Activity
     * @param brightness 亮度值（0-255）
     */
    public static void setWindowBrightness(Activity activity, @FloatRange(from = 1, to = 255) float brightness) {
        if (activity == null){
            return;
        }
        Window window = activity.getWindow();
        if (window == null){
            return;
        }
        WindowManager.LayoutParams localLayoutParams = window.getAttributes();
        localLayoutParams.screenBrightness = brightness / 255;
        window.setAttributes(localLayoutParams);
    }

    /**
     * 同时设置系统和窗口亮度
     * @param activity Activity
     * @param brightness 亮度值（0-255）
     */
    public static void setScreenAndWindowBrightness(Activity activity, @IntRange(from = 1, to = 255) int brightness) {
        setScreenBrightness(activity, brightness);
        setWindowBrightness(activity, brightness);
    }

    /**
     * 获取屏幕休眠时间（单位毫秒）
     * @param context 上下文
     */
    public static int getScreenDormantTime(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 30000);
    }

    /**
     * 设置屏幕休眠时间
     * @param context 上下文
     * @param millis 毫秒
     */
    public static void setScreenDormantTime(Context context, int millis) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, millis);
    }


    /**
     * 获取飞行模式的状态 1：打开；0：关闭
     * @param context 上下文
     */
    @SuppressWarnings("deprecation")
    public static int getAirplaneModeState(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
        }
        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0);
    }

    /**
     * 判断飞行模式是否打开
     * @param context 上下文
     */
    public static boolean isAirplaneModeOpen(Context context) {
        return getAirplaneModeState(context) == 1;
    }

    /**
     * 设置飞行模式的状态
     * @param context 上下文
     * @param enable 启动飞行模式
     */
    @SuppressWarnings("deprecation")
    public static void setAirplaneMode(Context context, boolean enable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, enable ? 1 : 0);
        }else {
            Settings.Global.putInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, enable ? 1 : 0);
        }
        // 发送飞行模式已经改变广播
        context.sendBroadcast(new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED));
    }

    /**
     * 获取蓝牙的状态
     * 关闭：BluetoothAdapter.STATE_OFF
     * 关闭中：BluetoothAdapter.STATE_TURNING_OFF
     * 打开：BluetoothAdapter.STATE_ON
     * 打开中：BluetoothAdapter.STATE_TURNING_ON
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public static int getBluetoothState() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null){
            return bluetoothAdapter.getState();
        }
        return BluetoothAdapter.STATE_OFF;
    }

    /**
     * 设置蓝牙状态
     * @param enable 是否启用
     */
    @RequiresPermission(allOf = {Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN})
    public static void setBluetoothState(boolean enable)  {
        if (getBluetoothState() == BluetoothAdapter.STATE_ON && !enable){
            BluetoothAdapter.getDefaultAdapter().disable();
        }
        if (getBluetoothState() == BluetoothAdapter.STATE_OFF && enable){
            BluetoothAdapter.getDefaultAdapter().enable();
        }
    }

    /**
     * 获取铃声音量（取值范围为0-7）
     * @param context 上下文
     */
    public static int getRingVolume(Context context) {
        return ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE)).getStreamVolume(AudioManager.STREAM_RING);
    }

    /**
     * 设置铃声音量
     * @param context 上下文
     * @param vloume 音量
     */
    public static void setRingVolume(Context context, @IntRange(from = 0, to = 7) int vloume) {
        ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE))
                .setStreamVolume(AudioManager.STREAM_RING, vloume, AudioManager.FLAG_PLAY_SOUND);
    }
}
