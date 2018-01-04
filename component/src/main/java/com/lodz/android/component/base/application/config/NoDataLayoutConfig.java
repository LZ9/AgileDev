package com.lodz.android.component.base.application.config;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.widget.LinearLayout;

/**
 * 无数据页面配置
 * Created by zhouL on 2017/7/4.
 */

public class NoDataLayoutConfig {

    /** 需要提示图片 */
    private boolean isNeedImg = true;
    /** 需要提示文字 */
    private boolean isNeedTips = false;
    /** 无数据图片 */
    @DrawableRes
    private int drawableResId = 0;
    /** 提示文字 */
    private String tips = "";
    /** 文字颜色 */
    @ColorRes
    private int textColor = 0;
    /** 文字大小 */
    private int textSize = 0;
    /** 背景色 */
    @ColorRes
    private int backgroundColor = 0;
    /** 页面方向布局 */
    private int orientation = LinearLayout.VERTICAL;

    /**
     * 需要提示图片
     * @param isNeed 是否需要
     */
    public void setNeedImg(boolean isNeed){
        this.isNeedImg = isNeed;
    }

    /**
     * 需要提示文字
     * @param isNeed 是否需要
     */
    public void setNeedTips(boolean isNeed){
        this.isNeedTips = isNeed;
    }

    /**
     * 设置无数据图片
     * @param drawableResId 图片资源id
     */
    public void setImg(@DrawableRes int drawableResId){
        this.drawableResId = drawableResId;
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
    public void setTipsTextSize(int size){
        this.textSize = size;
    }

    /**
     * 设置背景色
     * @param colorRes 颜色资源id
     */
    public void setBackgroundColor(@ColorRes int colorRes) {
        this.backgroundColor = colorRes;
    }

    /**
     * 设置无数据页面的布局方向
     * @param orientation LinearLayout.HORIZONTAL或LinearLayout.VERTICAL
     */
    public void setOrientation(@BaseLayoutConfig.OrientationType int orientation) {
        if (orientation == LinearLayout.HORIZONTAL || orientation == LinearLayout.VERTICAL){
            this.orientation = orientation;
        }
    }


    /** 获取是否需要提示图片 */
    public boolean getIsNeedImg() {
        return isNeedImg;
    }

    /** 获取是否需要提示文字 */
    public boolean getIsNeedTips() {
        return isNeedTips;
    }

    /** 获取无数据图片 */
    @DrawableRes
    public int getImg() {
        return drawableResId;
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
    public int getTextSize() {
        return textSize;
    }

    /** 获取背景色 */
    @ColorRes
    public int getBackgroundColor() {
        return backgroundColor;
    }

    /** 获取无数据页面的布局方向 */
    public int getOrientation() {
        return orientation;
    }
}
