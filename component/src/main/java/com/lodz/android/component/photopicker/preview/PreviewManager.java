package com.lodz.android.component.photopicker.preview;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.IntRange;

import com.lodz.android.component.R;
import com.lodz.android.core.utils.ArrayUtils;
import com.lodz.android.core.utils.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片预览管理类
 * Created by zhouL on 2017/10/13.
 */

public class PreviewManager {

    /** 预览数据 */
    static PreviewBean previewBean;

    /** 创建构造对象 */
    public static <T> Builder<T> create(){
        return new Builder<>();
    }

    private <T> PreviewManager(final Builder<T> builder) {
        previewBean = null;
        previewBean = builder.previewBean;
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
         * @param previewLoader 图片加载器
         */
        public Builder<T> setImgLoader(PreviewLoader<T> previewLoader) {
            previewBean.previewLoader = previewLoader;
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
        if (previewBean.previewLoader == null){// 校验图片加载器
            ToastUtils.showShort(context, R.string.preview_loader_unset);
            return;
        }
        if (ArrayUtils.isEmpty(previewBean.sourceList)){// 校验数据列表
            ToastUtils.showShort(context, R.string.preview_source_list_empty);
            return;
        }
        if ((previewBean.showPosition + 1) > previewBean.sourceList.size()){// 校验默认位置参数
            previewBean.showPosition = 0;
        }
        PicturePreviewActivity.start(context);
    }
}
