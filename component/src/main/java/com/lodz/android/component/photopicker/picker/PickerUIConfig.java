package com.lodz.android.component.photopicker.picker;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

/**
 * 选择器UI配置
 * Created by zhouL on 2017/10/25.
 */

public class PickerUIConfig {

    public static PickerUIConfig createDefault(){
        return new PickerUIConfig();
    }

    private PickerUIConfig() {
    }

    /** 顶部状态栏颜色 */
    @ColorRes
    private int statusBarColor = 0;
    /** 底部导航栏颜色 */
    @ColorRes
    private int navigationBarColor = 0;
    /** 拍照按钮图片 */
    @DrawableRes
    private int cameraImg = 0;
    /** 拍照按钮背景颜色 */
    @ColorRes
    private int cameraBgColor = android.R.color.black;
    /** 选择按钮未选中颜色 */
    @ColorRes
    private int selectedBtnUnselect = android.R.color.holo_green_dark;
    /** 选择按钮选中颜色 */
    @ColorRes
    private int selectedBtnSelected = android.R.color.holo_green_dark;
    /** 选中后的遮罩层颜色 */
    @ColorRes
    private int maskColor = 0;



    /**
     * 设置顶部状态栏颜色
     * @param color 颜色
     */
    public PickerUIConfig setStatusBarColor(@ColorRes int color) {
        if (color > 0){
            this.statusBarColor = color;
        }
        return this;
    }

    /**
     * 设置底部导航栏颜色
     * @param color 颜色
     */
    public PickerUIConfig setNavigationBarColor(@ColorRes int color) {
        if (color > 0){
            this.navigationBarColor = color;
        }
        return this;
    }

    /**
     * 设置相机图标
     * @param cameraRes 相机图标
     */
    public PickerUIConfig setCameraImg(@DrawableRes int cameraRes) {
        if (cameraRes > 0){
            this.cameraImg = cameraRes;
        }
        return this;
    }

    /**
     * 设置拍照按钮背景颜色
     * @param color
     */
    public PickerUIConfig setCameraBgColor(@ColorRes int color) {
        if (color > 0){
            this.cameraBgColor = color;
        }
        return this;
    }

    /**
     * 设置选择按钮未选中颜色
     * @param color 颜色
     */
    public PickerUIConfig setSelectedBtnUnselect(@ColorRes int color) {
        if (color > 0){
            this.selectedBtnUnselect = color;
        }
        return this;
    }

    /**
     * 设置选择按钮选中颜色
     * @param color 颜色
     */
    public PickerUIConfig setSelectedBtnSelected(@ColorRes int color) {
        if (color > 0){
            this.selectedBtnSelected = color;
        }
        return this;
    }

    /**
     * 设置选中后的遮罩层颜色
     * @param color 颜色
     */
    public PickerUIConfig setMaskColor(@ColorRes int color) {
        if (color > 0){
            this.maskColor = color;
        }
        return this;
    }

    public int getCameraBgColor() {
        return cameraBgColor;
    }

    public int getMaskColor() {
        return maskColor;
    }

    public int getSelectedBtnUnselect() {
        return selectedBtnUnselect;
    }

    public int getSelectedBtnSelected() {
        return selectedBtnSelected;
    }

    public int getCameraImg() {
        return cameraImg;
    }

    public int getStatusBarColor() {
        return statusBarColor;
    }

    public int getNavigationBarColor() {
        return navigationBarColor;
    }
}
