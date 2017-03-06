package com.lodz.android.core.utils;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * 手机震动帮助类
 * Created by zhouL on 2017/3/6.
 */
public class VibratorUtil {

    /**
     * 震动手机
     * @param context 上下文
     * @param milliseconds 震动时间（毫秒）
     */
    public static Vibrator vibrate(Context context, long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        if (vibrator != null){
            vibrator.vibrate(milliseconds);
        }
        return vibrator;
    }

    /**
     * 震动手机
     * @param context 上下文
     * @param pattern 自定义震动模式 数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长...] 时长的单位是毫秒
     * @param isRepeat 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    public static Vibrator vibrate(Context context, long[] pattern, boolean isRepeat) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        if (vibrator != null){
            vibrator.vibrate(pattern, isRepeat ? 1 : -1);
        }
        return vibrator;
    }

    /**
     * 取消震动
     * @param vibrator 震动器
     */
    public static void cancel(Vibrator vibrator){
        if (vibrator != null){
            vibrator.cancel();
        }
    }

    /** 手机硬件是否有震动器 */
    public static boolean hasVibrator(Context context){
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        return vibrator != null && vibrator.hasVibrator();
    }
}
