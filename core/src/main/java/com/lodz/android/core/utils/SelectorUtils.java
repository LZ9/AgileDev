package com.lodz.android.core.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

/**
 * Selector状态帮助类
 * Created by zhouL on 2017/10/17.
 */

public class SelectorUtils {

    /**
     * 创建按压背景
     * @param context 上下文
     * @param normal 正常背景
     * @param pressed 按压背景
     */
    public static StateListDrawable createBgPressedDrawable(Context context, @DrawableRes int normal, @DrawableRes int pressed){
        return createBgSelectorDrawable(context, normal, pressed, 0, 0, 0);
    }

    /**
     * 创建按压和不可用背景
     * @param context 上下文
     * @param normal 正常背景
     * @param pressed 按压背景
     * @param unable 不可用背景
     */
    public static StateListDrawable createBgPressedUnableDrawable(Context context, @DrawableRes int normal, @DrawableRes int pressed, @DrawableRes int unable){
        return createBgSelectorDrawable(context, normal, pressed, unable, 0, 0);
    }

    /**
     * 创建选中背景
     * @param context 上下文
     * @param normal 正常背景
     * @param selected 选中背景
     */
    public static StateListDrawable createBgSelectedDrawable(Context context, @DrawableRes int normal, @DrawableRes int selected){
        return createBgSelectorDrawable(context, normal, 0, 0, selected, 0);
    }

    /**
     * 创建按压背景
     * @param normal 正常背景
     * @param pressed 按压背景
     */
    public static StateListDrawable createBgPressedDrawable(Drawable normal, Drawable pressed){
        return createBgSelectorDrawable(normal, pressed, null, null, null);
    }

    /**
     * 创建按压和不可用背景
     * @param normal 正常背景
     * @param pressed 按压背景
     * @param unable 不可用背景
     */
    public static StateListDrawable createBgPressedUnableDrawable(Drawable normal, Drawable pressed, Drawable unable){
        return createBgSelectorDrawable(normal, pressed, unable, null, null);
    }

    /**
     * 创建选中背景
     * @param normal 正常背景
     * @param selected 选中背景
     */
    public static StateListDrawable createBgSelectedDrawable(Drawable normal, Drawable selected){
        return createBgSelectorDrawable(normal, null, null, selected, null);
    }

    /**
     * 创建按压背景
     * @param context 上下文
     * @param normal 正常背景
     * @param pressed 按压背景
     */
    public static StateListDrawable createBgPressedColor(Context context, @ColorRes int normal, @ColorRes int pressed){
        return createBgSelectorColor(context, normal, pressed, 0, 0, 0);
    }

    /**
     * 创建按压和不可用背景
     * @param context 上下文
     * @param normal 正常背景
     * @param pressed 按压背景
     * @param unable 不可用背景
     */
    public static StateListDrawable createBgPressedUnableColor(Context context, @ColorRes int normal, @ColorRes int pressed, @ColorRes int unable){
        return createBgSelectorColor(context, normal, pressed, unable, 0, 0);
    }

    /**
     * 创建选中背景
     * @param context 上下文
     * @param normal 正常背景
     * @param selected 选中背景
     */
    public static StateListDrawable createBgSelectedColor(Context context, @ColorRes int normal, @ColorRes int selected){
        return createBgSelectorColor(context, normal, 0, 0, selected, 0);
    }

    /**
     * 创建背景选择器
     * @param context 上下文
     * @param normal 正常背景（不需要可传0）
     * @param pressed 按压背景（不需要可传0）
     * @param unable 不可用背景（不需要可传0）
     * @param selected 选中背景（不需要可传0）
     * @param focused 获取焦点背景（不需要可传0）
     */
    @SuppressLint("ResourceType")
    public static StateListDrawable createBgSelectorDrawable(Context context, @DrawableRes int normal, @DrawableRes int pressed,
            @DrawableRes int unable, @DrawableRes int selected, @DrawableRes int focused){
        return createBgSelectorDrawable(normal != 0 ? ContextCompat.getDrawable(context, normal) : null,
                pressed != 0 ? ContextCompat.getDrawable(context, pressed) : null,
                unable != 0 ? ContextCompat.getDrawable(context, unable) : null,
                selected != 0 ? ContextCompat.getDrawable(context, selected) : null,
                focused != 0 ? ContextCompat.getDrawable(context, focused) : null);
    }

    /**
     * 创建背景选择器
     * @param context 上下文
     * @param normal 正常背景（不需要可传0）
     * @param pressed 按压背景（不需要可传0）
     * @param unable 不可用背景（不需要可传0）
     * @param selected 选中背景（不需要可传0）
     * @param focused 获取焦点背景（不需要可传0）
     */
    @SuppressLint("ResourceType")
    public static StateListDrawable createBgSelectorColor(Context context, @ColorRes int normal, @ColorRes int pressed,
            @ColorRes int unable, @ColorRes int selected, @ColorRes int focused){
        return createBgSelectorDrawable(normal != 0 ? DrawableUtils.createColorDrawable(context, normal) : null,
                pressed != 0 ? DrawableUtils.createColorDrawable(context, pressed) : null,
                unable != 0 ? DrawableUtils.createColorDrawable(context, unable) : null,
                selected != 0 ? DrawableUtils.createColorDrawable(context, selected) : null,
                focused != 0 ? DrawableUtils.createColorDrawable(context, focused) : null);
    }

    /**
     * 创建背景选择器
     * @param normal 正常背景
     * @param pressed 按压背景
     * @param unable 不可用背景
     * @param selected 选中背景
     * @param focused 获取焦点背景
     */
    public static StateListDrawable createBgSelectorDrawable(Drawable normal, Drawable pressed, Drawable unable, Drawable selected, Drawable focused) {
        StateListDrawable drawable = new StateListDrawable();
        if (pressed != null) {
            drawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        }
        if (selected != null) {
            drawable.addState(new int[]{android.R.attr.state_selected, android.R.attr.state_enabled}, selected);
        }
        if (focused != null) {
            drawable.addState(new int[]{android.R.attr.state_focused, android.R.attr.state_enabled}, focused);
        }
        if (normal != null) {
            drawable.addState(new int[]{android.R.attr.state_enabled}, normal);
        }
        if (unable != null) {
            drawable.addState(new int[]{}, unable);
        }
        return drawable;
    }

    /**
     * 创建按压文字颜色
     * @param normal 正常颜色
     * @param pressed 按压颜色
     */
    public static ColorStateList createTxPressedColor(@ColorInt int normal, @ColorInt int pressed){
        return createTxSelectorColor(normal, pressed, 0, 0, 0);
    }

    /**
     * 创建按压和不可用文字颜色
     * @param normal 正常颜色
     * @param pressed 按压颜色
     * @param unable 不可用颜色
     */
    public static ColorStateList createTxPressedUnableColor(@ColorInt int normal, @ColorInt int pressed, @ColorInt int unable){
        return createTxSelectorColor(normal, pressed, unable, 0, 0);
    }

    /**
     * 创建选中文字颜色
     * @param normal 正常颜色
     * @param selected 选中颜色
     */
    public static ColorStateList createTxSelectedColor(@ColorInt int normal, @ColorInt int selected){
        return createTxSelectorColor(normal, 0, 0, selected, 0);
    }

    /**
     * 创建按压文字颜色
     * @param context 上下文
     * @param normal 正常颜色
     * @param pressed 按压颜色
     */
    public static ColorStateList createTxPressedColor(Context context, @ColorRes int normal, @ColorRes int pressed){
        return createTxSelectorColor(context, normal, pressed, 0, 0, 0);
    }

    /**
     * 创建按压和不可用文字颜色
     * @param context 上下文
     * @param normal 正常颜色
     * @param pressed 按压颜色
     * @param unable 不可用颜色
     */
    public static ColorStateList createTxPressedUnableColor(Context context, @ColorRes int normal, @ColorRes int pressed, @ColorRes int unable){
        return createTxSelectorColor(context, normal, pressed, unable, 0, 0);
    }

    /**
     * 创建选中文字颜色
     * @param context 上下文
     * @param normal 正常颜色
     * @param selected 选中颜色
     */
    public static ColorStateList createTxSelectedColor(Context context, @ColorRes int normal, @ColorRes int selected){
        return createTxSelectorColor(context, normal, 0, 0, selected, 0);
    }

    /**
     * 创建文字颜色选择器
     * @param normal 正常颜色（不需要可传0）
     * @param pressed 按压颜色（不需要可传0）
     * @param unable 不可用颜色（不需要可传0）
     * @param selected 选中颜色（不需要可传0）
     * @param focused 获取焦点颜色（不需要可传0）
     */
    @SuppressLint("ResourceType")
    public static ColorStateList createTxSelectorColor(Context context, @ColorRes int normal, @ColorRes int pressed,
                                                       @ColorRes int unable, @ColorRes int selected, @ColorRes int focused){
        return createTxSelectorColor(normal != 0 ? ContextCompat.getColor(context, normal) : 0,
                pressed != 0 ? ContextCompat.getColor(context, pressed) : 0,
                unable != 0 ? ContextCompat.getColor(context, unable) : 0,
                selected != 0 ? ContextCompat.getColor(context, selected) : 0,
                focused != 0 ? ContextCompat.getColor(context, focused) : 0);
    }

    /**
     * 创建文字颜色选择器
     * @param normal 正常颜色（不需要可传0）
     * @param pressed 按压颜色（不需要可传0）
     * @param unable 不可用颜色（不需要可传0）
     * @param selected 选中颜色（不需要可传0）
     * @param focused 获取焦点颜色（不需要可传0）
     */
    public static ColorStateList createTxSelectorColor(@ColorInt int normal, @ColorInt int pressed,
            @ColorInt int unable, @ColorInt int selected, @ColorInt int focused){
        int[] colors = new int[]{pressed, selected, focused, normal, unable};
        int[][] states = new int[colors.length][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_selected, android.R.attr.state_enabled};
        states[2] = new int[]{android.R.attr.state_focused, android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_enabled};
        states[4] = new int[] {};
        return new ColorStateList(states, colors);
    }

}
