package com.lodz.android.imageloader.glide.config;

import android.content.Context;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.lodz.android.imageloader.ImageloaderManager;
import com.lodz.android.imageloader.utils.CompileUtils;

import java.io.File;
import java.io.InputStream;

import androidx.annotation.NonNull;

/**
 * Created by zhouL on 2018/4/9.
 */
@GlideModule
public class CacheAppGlideModule extends AppGlideModule {

    /** 缓存文件夹名称 */
    private static final String IMAGE_PIPELINE_CACHE_DIR = "image_cache";


    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);

        // 获取配置缓存文件夹名称
        String directoryName = ImageloaderManager.get().getBuilder().getDirectoryName();
        if (TextUtils.isEmpty(directoryName)){
            directoryName = IMAGE_PIPELINE_CACHE_DIR;
        }

        // 获取配置缓存文件路径文件
        File fileCacheDir = ImageloaderManager.get().getBuilder().getDirectoryFile();

        if (fileCacheDir == null){
            fileCacheDir = context.getApplicationContext().getCacheDir();//使用应用路径作为缓存文件夹
            ImageloaderManager.get().getBuilder().setDirectoryFile(fileCacheDir);
        }else {
            if (!fileCacheDir.exists()){// 传入的文件路径未创建，则创建该文件
                try {
                    //noinspection ResultOfMethodCallIgnored
                    fileCacheDir.mkdirs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!fileCacheDir.exists()){// 创建失败则使用默认路径
                fileCacheDir = context.getApplicationContext().getCacheDir();
                ImageloaderManager.get().getBuilder().setDirectoryFile(fileCacheDir);
            }
        }

        int diskCacheSize = 1024 * 1024 * 30;//最多可以缓存多少字节的数据
        //设置磁盘缓存大小
        builder.setDiskCache(new DiskLruCacheFactory(fileCacheDir.getAbsolutePath(), directoryName, diskCacheSize));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        if (CompileUtils.isClassExists("com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader")){
            registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
        }
    }
}
