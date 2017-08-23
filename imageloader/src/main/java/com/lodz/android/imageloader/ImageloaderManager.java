package com.lodz.android.imageloader;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lodz.android.imageloader.fresco.config.FrescoConfig;
import com.lodz.android.imageloader.utils.CompileUtils;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 图片库管理类
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

    private ImageloaderManager() {
    }

    /** 未选择加载框 */
    protected static final int TYPE_NONE = 0;
    /** Glide */
    protected static final int TYPE_GLIDE = 1;
    /** Fresco */
    protected static final int TYPE_FRESCO = 2;

    @IntDef({TYPE_NONE, TYPE_GLIDE, TYPE_FRESCO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoaderType {}

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
    public void clearMemoryCaches(Context context){
        if (getBuilder().loaderType == ImageloaderManager.TYPE_FRESCO){
            Fresco.getImagePipeline().clearMemoryCaches();
        }
        if (getBuilder().loaderType == ImageloaderManager.TYPE_GLIDE){
            Glide.get(context).clearMemory();
        }
    }

    /** 清除内存缓存（包括手动GC内存） */
    public void clearMemoryCachesWithGC(Context context){
        if (getBuilder().loaderType == ImageloaderManager.TYPE_FRESCO){
            Fresco.getImagePipeline().clearMemoryCaches();
        }
        if (getBuilder().loaderType == ImageloaderManager.TYPE_GLIDE){
            Glide.get(context).clearMemory();
        }
        System.gc();
    }

    /** 清除磁盘缓存 */
    public void clearDiskCaches(Context context){
        if (getBuilder().loaderType == ImageloaderManager.TYPE_FRESCO){
            Fresco.getImagePipeline().clearDiskCaches();
        }
        if (getBuilder().loaderType == ImageloaderManager.TYPE_GLIDE){
            Glide.get(context).clearDiskCache();
        }
    }

    /** 清除所有缓存（内存+磁盘） */
    public void clearCaches(Context context){
        if (getBuilder().loaderType == ImageloaderManager.TYPE_FRESCO){
            Fresco.getImagePipeline().clearCaches();
        }
        if (getBuilder().loaderType == ImageloaderManager.TYPE_GLIDE){
            Glide.get(context).clearMemory();
            Glide.get(context).clearDiskCache();
        }
        System.gc();
    }

    /** 暂停加载 */
    public void pauseLoad(Context context){
        if (getBuilder().loaderType == ImageloaderManager.TYPE_FRESCO){
            Fresco.getImagePipeline().pause();
        }
        if (getBuilder().loaderType == ImageloaderManager.TYPE_GLIDE){
            Glide.with(context).pauseRequests();
        }
    }

    /** 恢复加载 */
    public void resumeLoad(Context context){
        if (getBuilder().loaderType == ImageloaderManager.TYPE_FRESCO){
            Fresco.getImagePipeline().resume();
        }
        if (getBuilder().loaderType == ImageloaderManager.TYPE_GLIDE){
            Glide.with(context).resumeRequests();
        }
    }

    /** 是否暂停加载 */
    public boolean isPaused(Context context){
        if (getBuilder().loaderType == ImageloaderManager.TYPE_FRESCO){
            return Fresco.getImagePipeline().isPaused();
        }
        if (getBuilder().loaderType == ImageloaderManager.TYPE_GLIDE){
            return Glide.with(context).isPaused();
        }
        return false;
    }

    public class Builder {

        /** 图片加载库类型 */
        @LoaderType
        private int loaderType = ImageloaderManager.TYPE_NONE;
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
        /** 图片缓存目录 */
        private File directoryFile;
        /** 缓存图片文件夹名称 */
        private String directoryName;

        private Builder() {
            if (CompileUtils.isClassExists("com.facebook.drawee.backends.pipeline.Fresco")) {
                this.loaderType = ImageloaderManager.TYPE_FRESCO;
                return;
            }
            if (CompileUtils.isClassExists("com.bumptech.glide.Glide")) {
                this.loaderType = ImageloaderManager.TYPE_GLIDE;
                return;
            }
            throw new RuntimeException("请在你的build.gradle文件中配置Glide或Fresco的依赖");
        }

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
         * 设置默认重载图片（Glide无效）
         * @param retryResId 重载图片Id
         */
        public Builder setFrescoRetryResId(int retryResId) {
            this.retryResId = retryResId;
            return this;
        }

        /**
         * 开启重试功能（Glide无效）
         * @param tapToRetryEnabled 开启重试
         */
        public Builder setFrescoTapToRetryEnabled(boolean tapToRetryEnabled) {
            this.tapToRetryEnabled = tapToRetryEnabled;
            return this;
        }

        /**
         * 自动播放gif动画（Glide无效）
         * @param autoPlayAnimations 自动播放
         */
        public Builder setFrescoAutoPlayAnimations(boolean autoPlayAnimations) {
            this.autoPlayAnimations = autoPlayAnimations;
            return this;
        }

        /**
         * 设置图片缓存目录文件
         * @param file 图片缓存目录文件
         */
        public Builder setDirectoryFile(@NonNull File file) {
            this.directoryFile = file;
            return this;
        }

        /**
         * 设置缓存图片文件夹名称
         * @param name 缓存图片文件夹名称
         */
        public Builder setDirectoryName(@NonNull String name) {
            this.directoryName = name;
            return this;
        }

        /**
         * 完成构建并初始化
         * @param context 上下文
         */
        public void build(Context context){
            if (getBuilder().loaderType == ImageloaderManager.TYPE_FRESCO){
                Fresco.initialize(context, FrescoConfig.create().getImagePipelineConfig(context));
            }
            if (getBuilder().loaderType == ImageloaderManager.TYPE_GLIDE){
                return;
            }
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

        /** 获取图片缓存目录文件 */
        public File getDirectoryFile() {
            return directoryFile;
        }

        /** 获取缓存图片文件夹名称 */
        public String getDirectoryName() {
            return directoryName;
        }

        /** 获取加载库类型 */
        @LoaderType
        public int getLoaderType() {
            return loaderType;
        }
    }

}
