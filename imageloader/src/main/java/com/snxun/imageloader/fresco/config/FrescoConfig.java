package com.snxun.imageloader.fresco.config;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Fresco配置类
 * Created by zhouL on 2016/11/18.
 */

public class FrescoConfig {

    /** 日志标签 */
    private static final String TAG = "Fresco";
    /** 缓存文件夹名称 */
    private static final String IMAGE_PIPELINE_CACHE_DIR = "image_cache";

    public static FrescoConfig create(){
        return new FrescoConfig();
    }

    public ImagePipelineConfig getImagePipelineConfig(Context context){
        return OkHttpImagePipelineConfigFactory
                .newBuilder(context, getOkHttpClient())
                .setMainDiskCacheConfig(getDiskCacheConfig(context))
                .setRequestListeners(getRequestLogConfig())
                .setMemoryTrimmableRegistry(getMemoryTrimmableRegistryConfig())
                .setBitmapsConfig(Bitmap.Config.RGB_565) // 若不是要求忒高清显示应用，就用使用RGB_565吧（默认是ARGB_8888)
                .setDownsampleEnabled(true) // 在解码时改变图片的大小，支持PNG、JPG以及WEBP格式的图片，与ResizeOptions配合使用
                .setBitmapMemoryCacheParamsSupplier(getBitmapMemoryCacheParamsSupplier(context))// 设置内存配置
                .build();
    }

    /**
     * 获取主缓存配置
     * @param context 上下文
     */
    private DiskCacheConfig getDiskCacheConfig(Context context){
        File fileCacheDir = context.getApplicationContext().getCacheDir();//使用应用路径作为缓存文件夹
        return DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)// 文件夹名称
                .setBaseDirectoryPath(fileCacheDir)// 文件夹路径
                .build();
    }

    /** 获取OkHttpClient */
    private OkHttpClient getOkHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    /** 获取请求信息打印配置 */
    private Set<RequestListener> getRequestLogConfig(){
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);//开启Fresco日志
        Set<RequestListener> requestListeners = new HashSet<>();
        requestListeners.add(new RequestLoggingListener());
        return requestListeners;
    }

    /** 报内存警告时的监听 */
    private MemoryTrimmableRegistry getMemoryTrimmableRegistryConfig(){
        MemoryTrimmableRegistry memoryTrimmableRegistry = NoOpMemoryTrimmableRegistry.getInstance();
        memoryTrimmableRegistry.registerMemoryTrimmable(new MemoryTrimmable() {
            @Override
            public void trim(MemoryTrimType trimType) {
                final double suggestedTrimRatio = trimType.getSuggestedTrimRatio();
                Log.w(TAG, "Fresco onCreate suggestedTrimRatio : " + suggestedTrimRatio);

                if (MemoryTrimType.OnCloseToDalvikHeapLimit.getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInBackground.getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInForeground.getSuggestedTrimRatio() == suggestedTrimRatio
                        ) {
                    // 清除内存缓存
                    Fresco.getImagePipeline().clearMemoryCaches();
                }
            }
        });
        return memoryTrimmableRegistry;
    }

    /**
     * 设置内存配置
     * @param context 上下文
     */
    private Supplier<MemoryCacheParams> getBitmapMemoryCacheParamsSupplier(final Context context){
        Supplier<MemoryCacheParams> supplier = new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return new MemoryCacheParams(getMaxCacheSize(activityManager), // 内存缓存中总图片的最大大小,以字节为单位。
                            256,                                     // 内存缓存中图片的最大数量。
                            Integer.MAX_VALUE,                      // 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                            Integer.MAX_VALUE,                      // 内存缓存中准备清除的总图片的最大数量。
                            Integer.MAX_VALUE);                     // 内存缓存中单个图片的最大大小。
                } else {
                    return new MemoryCacheParams(
                            getMaxCacheSize(activityManager),
                            256,
                            Integer.MAX_VALUE,
                            Integer.MAX_VALUE,
                            Integer.MAX_VALUE);
                }
            }
        };
        return supplier;
    }

    /** 获取最大缓存大小 */
    private int getMaxCacheSize(ActivityManager activityManager) {
        final int maxMemory = Math.min(activityManager.getMemoryClass() * ByteConstants.MB, Integer.MAX_VALUE);
        Log.d(TAG, "Fresco Max memory : " + (maxMemory/ ByteConstants.MB) + " MB");
        if (maxMemory < 32 * ByteConstants.MB) {
            return 4 * ByteConstants.MB;
        } else if (maxMemory < 64 * ByteConstants.MB) {
            return 6 * ByteConstants.MB;
        } else {
            // We don't want to use more ashmem on Gingerbread for now, since it doesn't respond well to
            // native memory pressure (doesn't throw exceptions, crashes app, crashes phone)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                return 8 * ByteConstants.MB;
            } else {
                return maxMemory / 4;
            }
        }
    }
}
