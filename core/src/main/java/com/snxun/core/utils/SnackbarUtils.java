package com.snxun.core.utils;

import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snxun.core.R;

/**
 * Snackbar帮助类
 * Created by zhouL on 2017/1/4.
 */
public class SnackbarUtils {

    private Snackbar mSnackbar;

    /**
     * 创建一个短时Snackbar
     * @param view 视图
     * @param text 文字
     */
    public static SnackbarUtils createShort(View view, String text) {
        SnackbarUtils utils = new SnackbarUtils();
        utils.mSnackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        return utils;
    }

    /**
     * 创建一个长时Snackbar
     * @param view 视图
     * @param text 文字
     */
    public static SnackbarUtils createLong(View view, String text) {
        SnackbarUtils utils = new SnackbarUtils();
        utils.mSnackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        return utils;
    }

    /**
     * 创建一个自定义时长Snackbar
     * @param view 视图
     * @param text 文字
     * @param duration 时长以毫秒为单位
     */
    public static SnackbarUtils createCustom(View view, String text, int duration) {
        SnackbarUtils utils = new SnackbarUtils();
        utils.mSnackbar = Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE).setDuration(duration);
        return utils;
    }

    /**
     * 设置文字颜色
     * @param textColor 文字颜色
     */
    public SnackbarUtils setTextColor(@ColorRes int textColor) {
        if (textColor != -1) {
            View view = mSnackbar.getView();
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(ContextCompat.getColor(view.getContext(), textColor));
        }
        return this;
    }

    /**
     * 设置文字大小
     * @param sizeSp 文字大小（单位SP）
     */
    public SnackbarUtils setTextSize(int sizeSp) {
        if (sizeSp > 11) {
            View view = mSnackbar.getView();
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp);
        }
        return this;
    }

    /**
     * 设置背景色
     * @param backgroundColor 背景色
     */
    public SnackbarUtils setBackgroundColor(@ColorRes int backgroundColor) {
        if (backgroundColor != -1) {
            View view = mSnackbar.getView();
            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), backgroundColor));
        }
        return this;
    }

    /**
     * 添加左侧布局
     * @param layoutId 布局id
     */
    public SnackbarUtils addLeftView(@LayoutRes int layoutId) {
        View snackbarview = mSnackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarview;
        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId, null);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_VERTICAL;
        snackbarLayout.addView(add_view, 0, p);
        return this;
    }

    /** 得到Snackbar */
    public Snackbar getSnackbar() {
        return mSnackbar;
    }

    /** 显示Snackbar */
    public void show() {
        mSnackbar.show();
    }
}
