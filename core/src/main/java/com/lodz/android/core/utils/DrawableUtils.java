package com.lodz.android.core.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

/**
 * Drawable帮助类
 * Created by zhouL on 2017/10/17.
 */

public class DrawableUtils {

    public static ColorDrawable createColorDrawable(Context context, @ColorRes int color){
        return new ColorDrawable(ContextCompat.getColor(context, color));
    }
}
