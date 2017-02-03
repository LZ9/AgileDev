package com.lodz.android.imageloader.fresco.config;

import android.content.Context;
import android.support.annotation.DrawableRes;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Fresco管理类
 * Created by zhouL on 2016/11/18.
 */

public class ImageloaderManager {

    private static ImageloaderManager mInstance;

    public static ImageloaderManager get() {
        if (mInstance == null) {
            synchronized (ImageloaderManager.class) {
                if (mInstance == null) {
                    mInstance = new ImageloaderManager();
                }
            }
        }
        return mInstance;
    }

    /** 构建对象 */
    private Builder mBuilder;

    /** 设置构建对象 */
    public Builder newBuilder(){
        mBuilder = null;
        mBuilder = new Builder();
        return mBuilder;
    }

    /** 获取构建对象 */
    public Builder getBuilder(){
        if (mBuilder == null){
            mBuilder = newBuilder();
        }
        return mBuilder;
    }

    /** 清除内存缓存 */
    public void clearMemoryCaches(){
        Fresco.getImagePipeline().clearMemoryCaches();
    }

    /** 清除内存缓存（包括手动GC内存） */
    public void clearMemoryCachesWithGC(){
        Fresco.getImagePipeline().clearMemoryCaches();
        System.gc();
    }

    /** 清楚磁盘缓存 */
    public void clearDiskCaches(){
        Fresco.getImagePipeline().clearDiskCaches();
    }

    /** 清除所有缓存（内存+磁盘） */
    public void clearCaches(){
        Fresco.getImagePipeline().clearCaches();
        System.gc();
    }

    /** 暂停加载 */
    public void pauseLoad(){
        Fresco.getImagePipeline().pause();
    }

    /** 恢复加载 */
    public void resumeLoad(){
        Fresco.getImagePipeline().resume();
    }

    /** 是否暂停加载 */
    public boolean isPaused(){
        return Fresco.getImagePipeline().isPaused();
    }

    public class Builder {

        /** 占位符图片资源id */
        @DrawableRes
        private int placeholderResId = 0;
        /** 加载失败图片资源id */
        @DrawableRes
        private int errorResId = 0;
        /** 默认重载图片资源id */
        @DrawableRes
        private int retryResId = 0;
        /** 开启重试功能 */
        private boolean tapToRetryEnabled = false;
        /** 自动播放gif动画 */
        private boolean autoPlayAnimations = true;

        /**
         * 设置占位符图片
         * @param placeholderResId 占位符图片Id
         */
        public Builder setPlaceholderResId(int placeholderResId) {
            this.placeholderResId = placeholderResId;
            return this;
        }

        /**
         * 设置加载失败图片
         * @param errorResId 加载失败图片Id
         */
        public Builder setErrorResId(int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        /**
         * 设置默认重载图片
         * @param retryResId 重载图片Id
         */
        public Builder setRetryResId(int retryResId) {
            this.retryResId = retryResId;
            return this;
        }

        /**
         * 开启重试功能
         * @param tapToRetryEnabled 开启重试
         */
        public Builder setTapToRetryEnabled(boolean tapToRetryEnabled) {
            this.tapToRetryEnabled = tapToRetryEnabled;
            return this;
        }

        /**
         * 自动播放gif动画
         * @param autoPlayAnimations 自动播放
         */
        public Builder setAutoPlayAnimations(boolean autoPlayAnimations) {
            this.autoPlayAnimations = autoPlayAnimations;
            return this;
        }

        /**
         * 完成构建并初始化
         * @param context 上下文
         */
        public void build(Context context){
            Fresco.initialize(context, FrescoConfig.create().getImagePipelineConfig(context));
        }

        /** 获取默认占位符图片 */
        public int getPlaceholderResId() {
            return placeholderResId;
        }

        /** 获取默认加载失败图片 */
        public int getErrorResId() {
            return errorResId;
        }

        /** 获取默认重载图片 */
        public int getRetryResId() {
            return retryResId;
        }

        /** 开启重试功能 */
        public boolean isTapToRetryEnabled() {
            return tapToRetryEnabled;
        }

        /** 自动播放gif动画 */
        public boolean isAutoPlayAnimations() {
            return autoPlayAnimations;
        }

    }



}
