package com.lodz.android.core.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.FloatRange;
import android.support.v4.content.ContextCompat;

/**
 * 颜色帮助类
 * Created by zhouL on 2018/4/26.
 */
public class ColorUtils {

    /**
     * 设置颜色的透明度
     * @param context 上下文
     * @param color 颜色资源id
     * @param alpha 透明度
     */
    @ColorInt
    public static int getColorAlphaRes(Context context, @ColorRes int color, @FloatRange(from=0.0, to=1.0) float alpha){
        return getColorAlphaInt(ContextCompat.getColor(context, color), alpha);
    }

    /**
     * 设置颜色的透明度
     * @param color 颜色
     * @param alpha 透明度
     */
    @ColorInt
    public static int getColorAlphaInt(@ColorInt int color, @FloatRange(from=0.0, to=1.0) float alpha){
        // 透明度最大值
        final int MAX = 255;

        if (alpha < 0f){
            alpha = 0f;
        }
        if (alpha > 1f){
            alpha = 1f;
        }

        String colorHex = Integer.toHexString(color);

        int alphaInt = (int) (alpha * MAX);// 获取alpha对应的十进制数值
        String hex = Integer.toHexString(alphaInt);//把十进制转为16进制
        if (hex.length() == 1){
            hex = "0" + hex;
        }

        if (colorHex.length() == 8){
            return Color.parseColor("#" + hex + colorHex.substring(2, colorHex.length()));
        }

        if (colorHex.length() == 6){
            return Color.parseColor("#" + hex + colorHex);
        }

        return color;
    }
}
