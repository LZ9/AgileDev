package com.lodz.android.imageloader;

import android.content.Context;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.lodz.android.imageloader.glide.config.GlideApp;
import com.lodz.android.imageloader.utils.CompileUtils;

import java.io.File;


/**
 * 图片库管理类
 * Created by zhouL on 2016/11/18.
 */

public class ImageloaderManager {

    private static ImageloaderManager mInstance = new ImageloaderManager();

    public static ImageloaderManager get() {
        return mInstance;
    }

    private ImageloaderManager() {
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

    /** 清除内存缓存（必须在主线程） */
    public void clearMemoryCaches(Context context){
        GlideApp.get(context).clearMemory();
    }

    /** 清除内存缓存（包括手动GC内存，必须在主线程） */
    public void clearMemoryCachesWithGC(Context context){
        GlideApp.get(context).clearMemory();
        System.gc();
    }

    /** 清除磁盘缓存（必须异步线程） */
    public void clearDiskCaches(Context context){
        GlideApp.get(context).clearDiskCache();
    }

    /** 暂停加载 */
    public void pauseLoad(Context context){
        GlideApp.with(context).pauseRequests();
    }

    /** 恢复加载 */
    public void resumeLoad(Context context){
        GlideApp.with(context).resumeRequests();
    }

    /** 是否暂停加载 */
    public boolean isPaused(Context context){
        return GlideApp.with(context).isPaused();
    }

    public class Builder {

        /** 占位符图片资源id */
        @DrawableRes
        private int placeholderResId = 0;
        /** 加载失败图片资源id */
        @DrawableRes
        private int errorResId = 0;
        /** 图片缓存目录 */
        private File directoryFile;
        /** 缓存图片文件夹名称 */
        private String directoryName;

        private Builder() {
            if (!CompileUtils.isClassExists("com.bumptech.glide.Glide")) {
                throw new RuntimeException("请在你的build.gradle文件中配置Glide依赖");
            }
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

        /** 完成构建并初始化 */
        public void build(){}

        /** 获取默认占位符图片 */
        @DrawableRes
        public int getPlaceholderResId() {
            return placeholderResId;
        }

        /** 获取默认加载失败图片 */
        @DrawableRes
        public int getErrorResId() {
            return errorResId;
        }

        /** 获取图片缓存目录文件 */
        public File getDirectoryFile() {
            return directoryFile;
        }

        /** 获取缓存图片文件夹名称 */
        public String getDirectoryName() {
            return directoryName;
        }
    }

}
