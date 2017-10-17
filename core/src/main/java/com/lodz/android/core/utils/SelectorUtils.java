package com.lodz.android.core.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

/**
 * Selector状态帮助类
 * Created by zhouL on 2017/10/17.
 */

public class SelectorUtils {

    public static StateListDrawable createPressedDrawable(Context context, @DrawableRes int normal, @DrawableRes int pressed){
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, ContextCompat.getDrawable(context, pressed));
        drawable.addState(new int[]{}, ContextCompat.getDrawable(context, normal));
        return drawable;
    }

    public static StateListDrawable createPressedDrawable(Drawable normal, Drawable pressed){
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
        drawable.addState(new int[]{}, normal);
        return drawable;
    }

    public static StateListDrawable createPressedColor(Context context, @ColorRes int normal, @ColorRes int pressed){
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, DrawableUtils.createColorDrawable(context, pressed));
        drawable.addState(new int[]{}, DrawableUtils.createColorDrawable(context, normal));
        return drawable;
    }


    public static StateListDrawable createSelectorColor(Context context, @ColorRes int normal, @ColorRes int pressed,
            @ColorRes int enabled, @ColorRes int selected, @ColorRes int focused){
        StateListDrawable drawable = new StateListDrawable();
        if (pressed > 0){
            drawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, DrawableUtils.createColorDrawable(context, pressed));
        }
        if (normal > 0){
            drawable.addState(new int[]{android.R.attr.state_enabled}, DrawableUtils.createColorDrawable(context, normal));
        }
        if (selected > 0){
            drawable.addState(new int[]{android.R.attr.state_selected}, DrawableUtils.createColorDrawable(context, selected));
        }
        if (focused > 0){
            drawable.addState(new int[]{android.R.attr.state_focused, android.R.attr.state_enabled}, DrawableUtils.createColorDrawable(context, focused));
        }
        if (enabled > 0){
            drawable.addState(new int[]{}, DrawableUtils.createColorDrawable(context, enabled));
        }
        return drawable;

//        StateListDrawable drawable = new StateListDrawable();
//        Drawable normalDrawable = normal <= 0 ? null : DrawableUtils.createColorDrawable(context, normal);
//        Drawable pressedDrawable = pressed <= 0 ? null : DrawableUtils.createColorDrawable(context, pressed);
//        Drawable unenabledDrawable = unenabled <= 0 ? null : DrawableUtils.createColorDrawable(context, unenabled);
//        Drawable selectedDrawable = selected <= 0 ? null : DrawableUtils.createColorDrawable(context, selected);
//        Drawable focusedDrawable = focused <= 0 ? null : DrawableUtils.createColorDrawable(context, focused);
//
//        // View.PRESSED_ENABLED_STATE_SET
//        drawable.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled}, pressedDrawable);
//        // View.ENABLED_FOCUSED_STATE_SET
//        drawable.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused}, focusedDrawable);
//        // View.ENABLED_STATE_SET
//        drawable.addState(new int[] { android.R.attr.state_enabled }, normalDrawable);
//        // View.FOCUSED_STATE_SET
//        drawable.addState(new int[] { android.R.attr.state_focused }, focusedDrawable);
//        // View.WINDOW_FOCUSED_STATE_SET
//        drawable.addState(new int[] { android.R.attr.state_window_focused }, unenabledDrawable);
//        // View.EMPTY_STATE_SET
//        drawable.addState(new int[] {}, normalDrawable);
//        return drawable;
    }

    public static StateListDrawable newSelector(Context context, int idNormal, int idPressed, int idFocused,  int idUnable) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);
        Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
        Drawable focused = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);
        Drawable unable = idUnable == -1 ? null : context.getResources().getDrawable(idUnable);
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focused);
        // View.ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled }, normal);
        // View.FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_focused }, focused);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_window_focused }, unable);
        // View.EMPTY_STATE_SET
        bg.addState(new int[] {}, normal);
        return bg;
    }
}
