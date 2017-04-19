package com.lodz.android.core.cache;

import android.content.Context;

import com.lodz.android.core.utils.FileUtils;

import java.io.File;


/**
 * ACache的帮助类
 * Created by zhouL on 2017/4/19.
 */
public class ACacheUtils {

    private static ACacheUtils mInstance;

    public static ACacheUtils get() {
        if (mInstance == null) {
            synchronized (ACacheUtils.class) {
                if (mInstance == null) {
                    mInstance = new ACacheUtils();
                }
            }
        }
        return mInstance;
    }

    private ACacheUtils() {
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
    private Builder getBuilder(){
        if (mBuilder == null){
            mBuilder = newBuilder();
        }
        return mBuilder;
    }

    /** 创建ACache对象 */
    public ACache create(){
        Builder builder = getBuilder();
        if (builder.getCacheDir() == null){
            throw new NullPointerException("please set cache dir first");
        }
        return ACache.get(builder.getCacheDir(), builder.getMaxSize(), builder.getMaxCount());
    }

    public class Builder{
        /** 缓存目录 */
        private File mCacheDir = null;
        /** 缓存大小 */
        private long mMaxSize = 0;
        /** 缓存数量 */
        private int mMaxCount = 0;

        /**
         * 设置缓存目录
         * @param dirPath 缓存目录
         */
        public Builder setCacheDir(String dirPath){
            mCacheDir = FileUtils.createFile(dirPath);
            return this;
        }

        /**
         * 设置缓存大小
         * @param maxSize 缓存大小
         */
        public Builder setMaxSize(long maxSize){
            mMaxSize = maxSize;
            return this;
        }

        /**
         * 设置缓存数量
         * @param maxCount 缓存数量
         */
        public Builder setMaxCount(int maxCount){
            mMaxCount = maxCount;
            return this;
        }

        /**
         * 完成ACache构建
         * @param context 上下文
         */
        public void build(Context context){
            if (mCacheDir == null){
                mCacheDir = new File(context.getApplicationContext().getCacheDir(), "ACache");
            }
        }

        /** 获取缓存目录 */
        private File getCacheDir() {
            return mCacheDir;
        }

        /** 获取缓存大小 */
        private long getMaxSize() {
            return mMaxSize == 0 ? ACache.MAX_SIZE : mMaxSize;
        }

        /** 获取缓存数量 */
        private int getMaxCount() {
            return mMaxCount == 0 ? ACache.MAX_COUNT : mMaxCount;
        }
    }
}
