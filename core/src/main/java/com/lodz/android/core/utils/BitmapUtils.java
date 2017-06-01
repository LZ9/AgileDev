package com.lodz.android.core.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Bitmap帮助类
 * Created by zhouL on 2017/6/1.
 */

public class BitmapUtils {

    /**
     * 创建bitmap
     * @param path 图片路径
     * @param zoneWidthPx 展示区域宽度（单位PX）
     * @param zoneHeightPx 展示区域高度（单位PX）
     */
    public static Bitmap decodeBitMap(String path, int zoneWidthPx, int zoneHeightPx){
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, opts);//计算出图片的宽高
            opts.inSampleSize = setInSampleSize(opts, zoneWidthPx, zoneHeightPx);
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
            opts.inInputShareable = true;
            opts.inJustDecodeBounds = false;
            InputStream inputStream = new FileInputStream(path);
            return BitmapFactory.decodeStream(inputStream, null, opts);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /** 设置图片的缩放比例 */
    private static int setInSampleSize(BitmapFactory.Options opts, int zoneWidthPx, int zoneHeightPx) {
        int pictureHeight = opts.outHeight;//图片高度
        int pictureWidth = opts.outWidth;//图片宽度
        if (pictureHeight <= zoneHeightPx && pictureWidth <= zoneWidthPx) {//图片宽高均小于屏幕宽高则不设置缩放比例
            return 1;
        }
        if (pictureHeight > pictureWidth) {//如果高大于宽
            return Double.valueOf(Math.ceil((double) pictureHeight / (double) zoneHeightPx)).intValue();//向上取整
        }else {
            return Double.valueOf(Math.ceil((double) pictureWidth / (double) zoneWidthPx)).intValue();
        }
    }
}
