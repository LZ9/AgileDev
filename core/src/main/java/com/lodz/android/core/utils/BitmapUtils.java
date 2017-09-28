package com.lodz.android.core.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
        return bitmapToBase64(bitmap, 70, Base64.NO_WRAP);
    }

    /**
     * bitmap转为base64
     * @param bitmap 图片
     * @param quality 质量
     * @param flags 转码类型 例如Base64.DEFAULT、Base64.NO_WRAP等
     */
    public static String bitmapToBase64(Bitmap bitmap, int quality, int flags) {
        String result = "";
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, flags);
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
        return TextUtils.isEmpty(result) ? "" : result;
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
     * Drawable转化为Bitmap
     * @param drawable drawable
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    /**
     * base64转为byte[]
     * @param base64Data base64字符串
     */
    public static byte[] base64ToByte(String base64Data) {
        return Base64.decode(base64Data, Base64.DEFAULT);
    }

    /**
     * 压缩bitmap
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
        }
        return null;
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

    /**
     * 从View获取Bitmap
     * @param view 控件
     */
    public static Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(canvas);
        return bitmap;
    }

    /**
     * 合并Bitmap
     * @param bgd 背景Bitmap
     * @param fg 前景Bitmap
     */
    public static Bitmap combineBitmap(Bitmap bgd, Bitmap fg) {
        Bitmap bmp;
        int width = bgd.getWidth() > fg.getWidth() ? bgd.getWidth() : fg.getWidth();
        int height = bgd.getHeight() > fg.getHeight() ? bgd.getHeight() : fg.getHeight();

        bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(bgd, 0, 0, null);
        canvas.drawBitmap(fg, 0, 0, paint);

        return bmp;
    }

    /** 左上 */
    public static final int LEFT_TOP = 1;
    /** 左下 */
    public static final int LEFT_BOTTOM = 2;
    /** 右上 */
    public static final int RIGHT_TOP = 3;
    /** 右下 */
    public static final int RIGHT_BOTTOM = 4;

    @IntDef({LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WatermarkLocationType {}

    /**
     * 生成水印图片
     * @param src 原图片
     * @param watermark 水印图片
     * @param location 方位 {@link #LEFT_TOP}、{@link #LEFT_BOTTOM}、{@link #RIGHT_TOP}、{@link #RIGHT_BOTTOM}
     * @param margin 边距（像素）
     */
    public static Bitmap createWatermarkBitmap(Bitmap src, Bitmap watermark, @WatermarkLocationType int location, int margin) {
        if (src == null || watermark == null) {
            return null;
        }

        int width = src.getWidth();
        int height = src.getHeight();
        int watermarkWidth = watermark.getWidth();
        int watermarkHeight = watermark.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src

        if (location == LEFT_TOP){
            canvas.drawBitmap(watermark, margin,  margin, null);// 在src的左上角画入水印
        }
        if (location == LEFT_BOTTOM){
            canvas.drawBitmap(watermark, margin, height - watermarkHeight - margin, null);// 在src的左下角画入水印
        }
        if (location == RIGHT_TOP){
            canvas.drawBitmap(watermark, width - watermarkWidth - margin, margin, null);// 在src的右上角画入水印
        }

        if (location == RIGHT_BOTTOM){
            canvas.drawBitmap(watermark, width - watermarkWidth - margin, height - watermarkHeight - margin, null);// 在src的右下角画入水印
        }

        canvas.save(Canvas.ALL_SAVE_FLAG);// 保存
        canvas.restore();// 存储
        return bitmap;
    }


    /**
     * 将原图片转为灰度图
     * @param bitmap 原图片
     */
    public static Bitmap createGreyBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth(); // 获取位图的宽
        int height = bitmap.getHeight(); // 获取位图的高

        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        return result;
    }

    /**
     * 将原图片转为圆角图
     * @param bitmap 原图片
     * @param round 圆角（像素）
     */
    public static Bitmap createRoundedCornerBitmap(Bitmap bitmap, float round) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, round, round, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 将原图片增加倒影
     * @param bitmap 原图片
     */
    public static Bitmap createReflectionBitmap(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2, width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

        return bitmapWithReflection;
    }

    /**
     * 将原图片转为居中圆形图
     * @param bitmap 原图片
     */
    public static Bitmap createRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            left = 0;
            right = width;
            float clip = (height - width) / 2;
            top = clip;
            bottom = height - clip;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

}
