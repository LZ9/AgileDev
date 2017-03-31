package com.lodz.android.core.utils;

import android.content.Context;
import android.os.Build;
import android.os.StatFs;
import android.os.storage.StorageManager;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

/**
 * 存储帮助类
 * Created by zhouL on 2016/12/26.
 */
public class StorageUtils {

    /**
     * 获得存储大小，单位byte
     * @param path
     */
    public static long getStorageSize(String path) {
        try {
            StatFs fs = new StatFs(path);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
                return fs.getAvailableBlocksLong() * fs.getBlockSizeLong();
            }
            return fs.getAvailableBlocksLong() * fs.getBlockSizeLong();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 获取外置存储路径
     * @param context 上下文
     */
    public static String getExternalStoragePath(Context context) {
        try {
            StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            Class<?> storageVolumeClass = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = storageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClass.getMethod("getPath");
            Method isRemovable = storageVolumeClass.getMethod("isRemovable");
            Object result = getVolumeList.invoke(storageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (removable) {
                    return path;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取内置存储路径
     * @param context 上下文
     */
    public static String getInternalStoragePath(Context context) {
        try {
            StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            Class<?> storageVolumeClass = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = storageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClass.getMethod("getPath");
            Method isRemovable = storageVolumeClass.getMethod("isRemovable");
            Object result = getVolumeList.invoke(storageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (!removable) {
                    return path;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
