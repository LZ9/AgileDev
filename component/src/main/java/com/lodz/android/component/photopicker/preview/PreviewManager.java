package com.lodz.android.component.photopicker.preview;

import android.content.Context;

import com.lodz.android.component.R;
import com.lodz.android.component.photopicker.contract.OnClickListener;
import com.lodz.android.component.photopicker.contract.OnLongClickListener;
import com.lodz.android.component.photopicker.contract.PhotoLoader;
import com.lodz.android.core.utils.ArrayUtils;
import com.lodz.android.core.utils.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.ColorRes;
import androidx.annotation.IntRange;

/**
 * 图片预览管理类
 * Created by zhouL on 2017/10/13.
 */

public class PreviewManager {

    /** 预览数据 */
    static PreviewBean sPreviewBean;

    /** 创建构造对象 */
    public static <T> Builder<T> create(){
        return new Builder<>();
    }

    private <T> PreviewManager(Builder<T> builder) {
        sPreviewBean = null;
        sPreviewBean = builder.previewBean;
    }

    /** 预览数据构建类 */
    public static class Builder<T> implements Serializable {

        /** 预览数据 */
        private PreviewBean<T> previewBean;

        private Builder() {
            this.previewBean = new PreviewBean<>();
        }

        /**
         * 设置图片加载器
         * @param photoLoader 图片加载器
         */
        public Builder<T> setImgLoader(PhotoLoader<T> photoLoader) {
            previewBean.photoLoader = photoLoader;
            return this;
        }

        /**
         * 设置是否可缩放
         * @param isScale 是否可缩放
         */
        public Builder<T> setScale(boolean isScale){
            previewBean.isScale = isScale;
            return this;
        }

        /**
         * 设置默认展示图片的位置
         * @param position 位置
         */
        public Builder<T> setPosition(@IntRange(from = 0) int position){
            previewBean.showPosition = position;
            return this;
        }

        /**
         * 设置背景色
         * @param backgroundColor 背景色
         */
        public Builder<T> setBackgroundColor(@ColorRes int backgroundColor){
            previewBean.backgroundColor = backgroundColor;
            return this;
        }

        /**
         * 设置顶部状态栏颜色
         * @param statusBarColor 状态栏颜色
         */
        public Builder<T> setStatusBarColor(@ColorRes int statusBarColor){
            previewBean.statusBarColor = statusBarColor;
            return this;
        }

        /**
         * 设置底部导航栏颜色
         * @param navigationBarColor 导航栏颜色
         */
        public Builder<T> setNavigationBarColor(@ColorRes int navigationBarColor){
            previewBean.navigationBarColor = navigationBarColor;
            return this;
        }

        /**
         * 设置页码文字颜色
         * @param pagerTextColor 页码文字颜色
         */
        public Builder<T> setPagerTextColor(@ColorRes int pagerTextColor){
            previewBean.pagerTextColor = pagerTextColor;
            return this;
        }

        /**
         * 设置页码文字大小
         * @param textSize 文字大小（单位SP）
         */
        public Builder<T> setPagerTextSize(int textSize){
            previewBean.pagerTextSize = textSize;
            return this;
        }

        /**
         * 设置是否显示页码文字
         * @param isShow 是否显示
         */
        public Builder<T> setShowPagerText(boolean isShow){
            previewBean.isShowPagerText = isShow;
            return this;
        }


        /**
         * 设置点击监听
         * @param listener 监听器
         */
        public Builder<T> setOnClickListener(OnClickListener<T> listener){
            previewBean.clickListener = listener;
            return this;
        }

        /**
         * 设置长按监听
         * @param listener 监听器
         */
        public Builder<T> setOnLongClickListener(OnLongClickListener<T> listener){
            previewBean.longClickListener = listener;
            return this;
        }

        /**
         * 完成构建
         * @param source 单张图片
         */
        public PreviewManager build(T source) {
            previewBean.sourceList = new ArrayList<>();
            previewBean.sourceList.add(source);
            return new <T>PreviewManager(this);
        }

        /**
         * 完成构建
         * @param sourceList 图片列表
         */
        public PreviewManager build(List<T> sourceList) {
            previewBean.sourceList = sourceList;
            return new <T>PreviewManager(this);
        }

        /**
         * 完成构建
         * @param sourceArray 图片数组
         */
        public PreviewManager build(T[] sourceArray) {
            previewBean.sourceList = ArrayUtils.arrayToList(sourceArray);
            return new <T>PreviewManager(this);
        }
    }

    /**
     * 打开预览器
     * @param context 上下文
     */
    public void open(Context context){
        if (sPreviewBean.photoLoader == null){// 校验图片加载器
            ToastUtils.showShort(context, R.string.component_photo_loader_unset);
            return;
        }
        if (ArrayUtils.isEmpty(sPreviewBean.sourceList)){// 校验数据列表
            ToastUtils.showShort(context, R.string.component_preview_source_list_empty);
            return;
        }
        sPreviewBean.isShowPagerText = ArrayUtils.getSize(sPreviewBean.sourceList) > 1;// 只有一张图片不显示页码
        if ((sPreviewBean.showPosition + 1) > sPreviewBean.sourceList.size()){// 校验默认位置参数
            sPreviewBean.showPosition = 0;
        }
        PicturePreviewActivity.start(context);
    }
}
