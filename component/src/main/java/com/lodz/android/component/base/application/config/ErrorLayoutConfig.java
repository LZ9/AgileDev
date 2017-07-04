package com.lodz.android.component.base.application.config;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

/**
 * 错误页面配置
 * Created by zhouL on 2017/7/3.
 */

public class ErrorLayoutConfig{

    /** 需要提示图片 */
    private boolean isNeedImg = true;
    /** 需要提示文字 */
    private boolean isNeedTips = false;
    /** 错误图片 */
    @DrawableRes
    private int drawableResId = 0;
    /** 提示文字 */
    private String tips = "";
    /** 文字颜色 */
    @ColorRes
    private int textColor = 0;
    /** 文字大小 */
    private float textSize = 0f;
    /** 背景色 */
    @ColorRes
    private int backgroundColor = 0;

    /**
     * 需要提示图片
     * @param isNeed 是否需要
     */
    public void setNeedImg(boolean isNeed) {
        isNeedImg = isNeed;
    }

    /**
     * 需要提示文字
     * @param isNeed 是否需要
     */
    public void setNeedTips(boolean isNeed) {
        isNeedTips = isNeed;
    }

    /**
     * 设置界面错误图片
     * @param drawableResId 图片资源id
     */
    public void setImg(@DrawableRes int drawableResId) {
        this.drawableResId = drawableResId;
    }

    /**
     * 设置提示文字
     * @param str 文字描述
     */
    public void setTips(String str) {
        this.tips = str;
    }

    /**
     * 设置文字颜色
     * @param colorRes 颜色资源id
     */
    public void setTipsTextColor(@ColorRes int colorRes) {
        this.textColor = colorRes;
    }

    /**
     * 设置文字大小
     * @param size 文字大小（单位sp）
     */
    public void setTipsTextSize(float size) {
        this.textSize = size;
    }

    /**
     * 设置背景色
     * @param colorRes 颜色资源id
     */
    public void setBackgroundColor(@ColorRes int colorRes) {
        this.backgroundColor = colorRes;
    }


    /** 获取是否需要图片 */
    public boolean getIsNeedImg() {
        return isNeedImg;
    }

    /** 获取是否需要提示语 */
    public boolean getIsNeedTips() {
        return isNeedTips;
    }

    /** 获取图片 */
    @DrawableRes
    public int getImg() {
        return drawableResId;
    }

    /** 获取提示语 */
    public String getTips() {
        return tips;
    }

    /** 获取提示语颜色 */
    @ColorRes
    public int getTextColor() {
        return textColor;
    }

    /** 获取提示语大小 */
    public float getTextSize() {
        return textSize;
    }

    /** 获取背景色 */
    @ColorRes
    public int getBackgroundColor() {
        return backgroundColor;
    }
}
