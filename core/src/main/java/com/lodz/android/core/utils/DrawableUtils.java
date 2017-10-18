package com.lodz.android.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

/**
 * Drawable帮助类
 * Created by zhouL on 2017/10/17.
 */

public class DrawableUtils {

    /**
     * 用颜色创建Drawable
     * @param context 上下文
     * @param color 颜色
     */
    public static ColorDrawable createColorDrawable(Context context, @ColorRes int color){
        return new ColorDrawable(ContextCompat.getColor(context, color));
    }

    /**
     * 用Bitmap创建Drawable
     * @param context 上下文
     * @param bitmap Bitmap
     */
    public static BitmapDrawable createBitmapDrawable(Context context, Bitmap bitmap){
        return new BitmapDrawable(context.getResources(), bitmap);
    }
}
