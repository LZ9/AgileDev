package com.lodz.android.core.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 图片帮助类
 * Created by zhouL on 2017/5/22.
 */

public class ImageUtils {

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
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * base64转为byte[]
     * @param base64Data base64字符串
     */
    public static byte[] base64ToByte(String base64Data) {
        return Base64.decode(base64Data, Base64.DEFAULT);
    }
}
