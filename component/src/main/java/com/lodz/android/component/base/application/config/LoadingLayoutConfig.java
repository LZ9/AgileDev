package com.lodz.android.component.base.application.config;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;


/**
 * 加载页面配置
 * Created by zhouL on 2017/7/4.
 */

public class LoadingLayoutConfig {

    /** 是否需要提示文字 */
    private boolean isNeedTips = true;
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
    /** 不确定模式 */
    private boolean isIndeterminate = true;
    /** 不确定模式下的资源 */
    @DrawableRes
    private int indeterminateDrawable = 0;

    /**
     * 需要提示文字
     * @param isNeed 是否需要
     */
    public void setNeedTips(boolean isNeed){
        this.isNeedTips = isNeed;
    }

    /**
     * 设置提示文字
     * @param tips 文字描述
     */
    public void setTips(String tips){
        this.tips = tips;
    }

    /**
     * 设置文字颜色
     * @param colorRes 颜色资源id
     */
    public void setTipsTextColor(@ColorRes int colorRes){
        this.textColor = colorRes;
    }

    /**
     * 设置文字大小
     * @param size 文字大小（单位sp）
     */
    public void setTipsTextSize(float size){
        this.textSize = size;
    }

    /**
     * 设置不确定模式
     * @param isIndeterminate 是否是不确定模式
     */
    public void setIsIndeterminate(boolean isIndeterminate){
        this.isIndeterminate = isIndeterminate;
    }

    /**
     * 设置不确定模式下的资源
     * @param resId 资源id
     */
    public void setIndeterminateDrawable(@DrawableRes int resId){
        this.indeterminateDrawable = resId;
    }

    /**
     * 设置背景色
     * @param colorRes 颜色资源id
     */
    public void setBackgroundColor(@ColorRes int colorRes) {
        this.backgroundColor = colorRes;
    }


    /** 获取是否需要提示文字 */
    public boolean getIsNeedTips() {
        return isNeedTips;
    }

    /** 获取提示文字 */
    public String getTips() {
        return tips;
    }

    /** 获取文字颜色 */
    @ColorRes
    public int getTextColor() {
        return textColor;
    }

    /** 获取文字大小 */
    public float getTextSize() {
        return textSize;
    }

    /** 获取背景色 */
    @ColorRes
    public int getBackgroundColor() {
        return backgroundColor;
    }

    /** 获取是否是不确定模式 */
    public boolean getIsIndeterminate() {
        return isIndeterminate;
    }

    /** 获取不确定模式下的资源 */
    public int getIndeterminateDrawable() {
        return indeterminateDrawable;
    }
}
