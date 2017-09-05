package com.lodz.android.component.base.application.config;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;

/**
 * 标题栏配置
 * Created by zhouL on 2017/7/4.
 */

public class TitleBarLayoutConfig {

    /** 是否需要显示返回按钮 */
    private boolean isNeedBackBtn = true;
    /** 替换默认的返回按钮资源图片 */
    @DrawableRes
    private int backBtnResId = 0;
    /** 返回按钮文字 */
    private String backBtnText = "";
    /** 返回按钮文字颜色 */
    @ColorRes
    private int backBtnTextColor = 0;
    /** 返回按钮文字大小 */
    private float backBtnTextSize = 0f;
    /** 标题文字颜色 */
    @ColorRes
    private int titleTextColor = 0;
    /** 标题文字大小 */
    private float titleTextSize = 0f;
    /** 是否显示分割线 */
    private boolean isShowDivideLine = true;
    /** 分割线颜色 */
    @ColorRes
    private int divideLineColor = 0;
    /** 分割线高度 */
    private int divideLineHeight = 0;
    /** 背景颜色 */
    @ColorRes
    private int backgroundColor = 0;
    /** 背景资源图片 */
    @DrawableRes
    private int backgroundResId = 0;
    /** 是否需要阴影 */
    private boolean isNeedElevation = false;
    /** 阴影的值 */
    private float elevationVale = 12f;


    /**
     * 需要显示返回按钮
     * @param isNeed 是否需要
     */
    public void setNeedBackButton(boolean isNeed){
        this.isNeedBackBtn = isNeed;
    }

    /**
     * 替换默认的返回按钮
     * @param resId 返回按钮的资源id
     */
    public void setReplaceBackBtnResId(@DrawableRes int resId){
        this.backBtnResId = resId;
    }

    /**
     * 设置返回按钮文字
     * @param str 文字描述
     */
    public void setBackBtnName(String str){
        this.backBtnText = str;
    }

    /**
     * 设置返回按钮文字颜色
     * @param colorRes 颜色资源id
     */
    public void setBackBtnTextColor(@ColorRes int colorRes){
        this.backBtnTextColor = colorRes;
    }

    /**
     * 设置返回按钮文字大小
     * @param size 文字大小（单位sp）
     */
    public void setBackBtnTextSize(float size){
        this.backBtnTextSize = size;
    }

    /**
     * 设置标题文字颜色
     * @param colorRes 颜色资源id
     */
    public void setTitleTextColor(@ColorRes int colorRes){
        this.titleTextColor = colorRes;
    }

    /**
     * 设置标题文字大小
     * @param size 文字大小（单位sp）
     */
    public void setTitleTextSize(float size){
        this.titleTextSize = size;
    }

    /**
     * 是否显示分割线
     * @param isShow 是否显示
     */
    public void setIsShowDivideLine(boolean isShow){
        this.isShowDivideLine = isShow;
    }

    /**
     * 设置分割线颜色
     * @param colorRes 色值
     */
    public void setDivideLineColor(@ColorRes int colorRes){
        this.divideLineColor = colorRes;
    }

    /**
     * 设置分割线高度
     * @param height 高度（单位dp）
     */
    public void setDivideLineHeight(int height){
        this.divideLineHeight = height;
    }

    /**
     * 设置背景颜色
     * @param colorRes 色值
     */
    public void setBackgroundColor(@ColorRes int colorRes){
        this.backgroundColor = colorRes;
    }

    /**
     * 设置背景资源图片
     * @param resId 资源图片id
     */
    public void setBackgroundResId(@DrawableRes int resId){
        this.backgroundResId = resId;
    }

    /**
     * 是否需要阴影
     * @param isNeed 是否需要
     */
    public void setIsNeedElevation(boolean isNeed){
        this.isNeedElevation = isNeed;
    }

    /**
     * 设置阴影的值
     * @param elevationVale 阴影值
     */
    public void setElevationVale(@FloatRange(from=0.0) float elevationVale) {
        this.elevationVale = elevationVale;
    }

    /** 获取是否需要显示返回按钮 */
    public boolean getIsNeedBackBtn() {
        return isNeedBackBtn;
    }

    /** 获取替换默认的返回按钮 */
    @DrawableRes
    public int getBackBtnResId() {
        return backBtnResId;
    }

    /** 获取返回按钮文字 */
    public String getBackBtnText() {
        return backBtnText;
    }

    /** 获取返回按钮文字颜色 */
    @ColorRes
    public int getBackBtnTextColor() {
        return backBtnTextColor;
    }

    /** 获取返回按钮文字大小 */
    public float getBackBtnTextSize() {
        return backBtnTextSize;
    }

    /** 获取标题文字颜色 */
    @ColorRes
    public int getTitleTextColor() {
        return titleTextColor;
    }

    /** 获取标题文字大小 */
    public float getTitleTextSize() {
        return titleTextSize;
    }

    /** 获取是否显示分割线 */
    public boolean getIsShowDivideLine() {
        return isShowDivideLine;
    }

    /** 获取分割线颜色 */
    @ColorRes
    public int getDivideLineColor() {
        return divideLineColor;
    }

    /** 获取分割线高度 */
    public int getDivideLineHeight() {
        return divideLineHeight;
    }

    /** 获取背景颜色 */
    @ColorRes
    public int getBackgroundColor() {
        return backgroundColor;
    }

    /** 获取背景资源图片 */
    @DrawableRes
    public int getBackgroundResId() {
        return backgroundResId;
    }

    /** 获取是否需要阴影 */
    public boolean getIsNeedElevation() {
        return isNeedElevation;
    }

    /** 获取阴影的值 */
    public float getElevationVale() {
        return elevationVale;
    }
}
