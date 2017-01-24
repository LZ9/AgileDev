package com.snxun.core.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Toast帮助类
 * Created by zhouL on 2016/11/17.
 */

public class ToastUtils {

    /**
     * 显示短时间的Toast
     * @param context 上下文
     * @param str 文字
     */
    public static void showShort(final Context context, final CharSequence str) {
        show(context, str, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时间的Toast
     * @param context 上下文
     * @param strResId 文字资源id
     */
    public static void showShort(final Context context, @StringRes final int strResId) {
        if (context == null){
            return;
        }
        show(context, context.getString(strResId), Toast.LENGTH_SHORT);
    }

    /**
     * 显示长时间的Toast
     * @param context 上下文
     * @param str 文字
     */
    public static void showLong(final Context context, final CharSequence str) {
        show(context, str, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时间的Toast
     * @param context 上下文
     * @param strResId 文字资源id
     */
    public static void showLong(final Context context, @StringRes final int strResId) {
        if (context == null){
            return;
        }
        show(context, context.getString(strResId), Toast.LENGTH_LONG);
    }

    /**
     * 自定义显示Toast时间
     * @param context 上下文
     * @param message 文字
     * @param duration 时间长度
     */
    public static void showCustom(final Context context, final CharSequence message, final int duration) {
        show(context, message, duration);
    }

    /**
     * 自定义显示Toast时间
     * @param context 上下文
     * @param strResId 文字资源id
     * @param duration 时间长度
     */
    public static void showCustom(final Context context, @StringRes final int strResId, final int duration) {
        if (context == null){
            return;
        }
        show(context, context.getString(strResId), duration);
    }

    /**
     * 显示Toast
     * @param context 上下文
     * @param message 文字
     * @param duration 时间长度
     */
    private static void show(final Context context, final CharSequence message, final int duration) {
        if (context == null || TextUtils.isEmpty(message)){
            return;
        }

        if (AppUtils.isMainThread()){//主线程直接显示
            Toast.makeText(context, message, duration).show();
            return;
        }

        UiHandler.post(new Runnable() {//非主线程post到主线程显示
            @Override
            public void run() {
                Toast.makeText(context, message, duration).show();
            }
        });
    }

}
