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
    /** 照片背景颜色 */
    @ColorRes
    private int itemBgColor = android.R.color.black;
    /** 选择按钮未选中颜色 */
    @ColorRes
    private int selectedBtnUnselect = android.R.color.holo_green_dark;
    /** 选择按钮选中颜色 */
    @ColorRes
    private int selectedBtnSelected = android.R.color.holo_green_dark;
    /** 选中后的遮罩层颜色 */
    @ColorRes
    private int maskColor = 0;
    /** 返回按钮颜色 */
    @ColorRes
    private int backBtnColor = android.R.color.white;
    /** 主文字颜色 */
    @ColorRes
    private int mainTextColor = android.R.color.white;
    /** 更多文件夹图片 */
    @DrawableRes
    private int moreFolderImg = 0;



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

    /**
     * 设置照片背景颜色
     * @param color 颜色
     */
    public PickerUIConfig setItemBgColor(@ColorRes int color) {
        if (color > 0){
            this.itemBgColor = color;
        }
        return this;
    }

    /**
     * 设置返回按钮颜色
     * @param color 颜色
     */
    public PickerUIConfig setBackBtnColor(@ColorRes int color) {
        if (color > 0){
            this.backBtnColor = color;
        }
        return this;
    }

    /**
     * 设置主文字颜色
     * @param color 颜色
     */
    public PickerUIConfig setMainTextColor(@ColorRes int color) {
        if (color > 0){
            this.mainTextColor = color;
        }
        return this;
    }

    /**
     * 设置更多文件夹图片
     * @param moreFolderRes 资源图片
     */
    public PickerUIConfig setMoreFolderImg(@DrawableRes int moreFolderRes) {
        if (moreFolderRes > 0){
            this.moreFolderImg = moreFolderRes;
        }
        return this;
    }

    public int getMoreFolderImg() {
        return moreFolderImg;
    }

    public int getMainTextColor() {
        return mainTextColor;
    }

    public int getBackBtnColor() {
        return backBtnColor;
    }

    public int getItemBgColor() {
        return itemBgColor;
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
