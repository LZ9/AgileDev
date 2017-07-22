package com.lodz.android.core.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Bitmap帮助类
 * Created by zhouL on 2017/6/1.
 */

public class BitmapUtils {

    /**
     * bitmap转为base64
     * @param bitmap 图片
     */
    public static String bitmapToBase64Default(Bitmap bitmap) {
        return bitmapToBase64(bitmap, 70);
    }

    /**
     * bitmap转为base64
     * @param bitmap 图片
     * @param quality 质量
     */
    public static String bitmapToBase64(Bitmap bitmap, int quality) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     * @param base64Data base64字符串
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = base64ToByte(base64Data);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * base64转为byte[]
     * @param base64Data base64字符串
     */
    public static byte[] base64ToByte(String base64Data) {
        return Base64.decode(base64Data, Base64.DEFAULT);
    }

    /**
     * 缩放bitmap
     * @param path 图片路径
     * @param zoneWidthPx 展示区域宽度（单位PX）
     * @param zoneHeightPx 展示区域高度（单位PX）
     */
    public static Bitmap decodeBitmap(String path, int zoneWidthPx, int zoneHeightPx){
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
