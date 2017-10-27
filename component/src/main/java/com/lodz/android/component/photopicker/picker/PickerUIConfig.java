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
    private int statusBarColor = android.R.color.black;
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
    /** 顶部背景颜色 */
    @ColorRes
    private int topLayoutColor = android.R.color.black;
    /** 底部背景颜色 */
    @ColorRes
    private int bottomLayoutColor = android.R.color.black;
    /** 预览按钮普通颜色 */
    @ColorRes
    private int previewBtnNormal = android.R.color.white;
    /** 预览按钮不可用颜色 */
    @ColorRes
    private int previewBtnUnable = android.R.color.darker_gray;
    /** 确认按钮普通颜色 */
    @ColorRes
    private int confirmBtnNormal = android.R.color.holo_green_light;
    /** 确认按钮按压颜色 */
    @ColorRes
    private int confirmBtnPressed = android.R.color.white;
    /** 确认按钮不可用颜色 */
    @ColorRes
    private int confirmBtnUnable = android.R.color.holo_green_dark;
    /** 确认文字普通颜色 */
    @ColorRes
    private int confirmTextNormal = android.R.color.white;
    /** 确认文字按压颜色 */
    @ColorRes
    private int confirmTextPressed = android.R.color.holo_green_dark;
    /** 确认文字不可用颜色 */
    @ColorRes
    private int confirmTextUnable = android.R.color.darker_gray;
    /** 文件夹选择颜色 */
    @ColorRes
    private int folderSelectColor = android.R.color.holo_green_dark;

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
     * @param color 颜色
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

    /**
     * 设置顶部背景颜色
     * @param color 颜色
     */
    public PickerUIConfig setTopLayoutColor(@ColorRes int color) {
        if (color > 0){
            this.topLayoutColor = color;
        }
        return this;
    }

    /**
     * 设置底部背景颜色
     * @param color 颜色
     */
    public PickerUIConfig setBottomLayoutColor(@ColorRes int color) {
        if (color > 0){
            this.bottomLayoutColor = color;
        }
        return this;
    }

    /**
     * 设置预览按钮普通颜色
     * @param color 颜色
     */
    public PickerUIConfig setPreviewBtnNormal(@ColorRes int color) {
        if (color > 0){
            this.previewBtnNormal = color;
        }
        return this;
    }

    /**
     * 设置预览按钮不可用颜色
     * @param color 颜色
     */
    public PickerUIConfig setPreviewBtnUnable(@ColorRes int color) {
        if (color > 0){
            this.previewBtnUnable = color;
        }
        return this;
    }

    /**
     * 设置确认按钮普通颜色
     * @param color 颜色
     */
    public PickerUIConfig setConfirmBtnNormal(@ColorRes int color) {
        if (color > 0){
            this.confirmBtnNormal = color;
        }
        return this;
    }

    /**
     * 设置确认按钮按压颜色
     * @param color 颜色
     */
    public PickerUIConfig setConfirmBtnPressed(@ColorRes int color) {
        if (color > 0){
            this.confirmBtnPressed = color;
        }
        return this;
    }

    /**
     * 设置确认按钮不可用颜色
     * @param color 颜色
     */
    public PickerUIConfig setConfirmBtnUnable(@ColorRes int color) {
        if (color > 0){
            this.confirmBtnUnable = color;
        }
        return this;
    }

    /**
     * 设置确认文字普通颜色
     * @param color 颜色
     */
    public PickerUIConfig setConfirmTextNormal(@ColorRes int color) {
        if (color > 0){
            this.confirmTextNormal = color;
        }
        return this;
    }

    /**
     * 设置确认文字按压颜色
     * @param color 颜色
     */
    public PickerUIConfig setConfirmTextPressed(@ColorRes int color) {
        if (color > 0){
            this.confirmTextPressed = color;
        }
        return this;
    }

    /**
     * 设置确认文字不可用颜色
     * @param color 颜色
     */
    public PickerUIConfig setConfirmTextUnable(@ColorRes int color) {
        if (color > 0){
            this.confirmTextUnable = color;
        }
        return this;
    }

    /**
     * 设置文件夹选择颜色
     * @param color 颜色
     */
    public PickerUIConfig setFolderSelectColor(@ColorRes int color) {
        if (color > 0){
            this.folderSelectColor = color;
        }
        return this;
    }

    public int getFolderSelectColor() {
        return folderSelectColor;
    }

    int getConfirmTextNormal() {
        return confirmTextNormal;
    }

    int getConfirmTextPressed() {
        return confirmTextPressed;
    }

    int getConfirmTextUnable() {
        return confirmTextUnable;
    }

    int getConfirmBtnNormal() {
        return confirmBtnNormal;
    }

    int getConfirmBtnPressed() {
        return confirmBtnPressed;
    }

    int getConfirmBtnUnable() {
        return confirmBtnUnable;
    }

    int getPreviewBtnNormal() {
        return previewBtnNormal;
    }

    int getPreviewBtnUnable() {
        return previewBtnUnable;
    }

    int getTopLayoutColor() {
        return topLayoutColor;
    }

    int getBottomLayoutColor() {
        return bottomLayoutColor;
    }

    int getMoreFolderImg() {
        return moreFolderImg;
    }

    int getMainTextColor() {
        return mainTextColor;
    }

    int getBackBtnColor() {
        return backBtnColor;
    }

    int getItemBgColor() {
        return itemBgColor;
    }

    int getCameraBgColor() {
        return cameraBgColor;
    }

    int getMaskColor() {
        return maskColor;
    }

    int getSelectedBtnUnselect() {
        return selectedBtnUnselect;
    }

    int getSelectedBtnSelected() {
        return selectedBtnSelected;
    }

    int getCameraImg() {
        return cameraImg;
    }

    int getStatusBarColor() {
        return statusBarColor;
    }

    int getNavigationBarColor() {
        return navigationBarColor;
    }
}
