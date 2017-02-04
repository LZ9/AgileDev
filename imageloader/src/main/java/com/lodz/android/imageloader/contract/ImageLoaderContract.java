package com.lodz.android.imageloader.contract;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 图片加载库接口
 * Created by zhouL on 2016/11/14.
 */
public interface ImageLoaderContract {

    /**
     * 设置加载路径
     * @param uri 加载路径
     */
    ImageLoaderContract load(Uri uri);

    /**
     * 设置加载图
     * @param placeholderResId 加载图的资源文件
     * @param scaleType 缩放类型
     */
    ImageLoaderContract setPlaceholder(@DrawableRes int placeholderResId, ScalingUtils.ScaleType scaleType);

    /**
     * 设置加载失败图
     * @param errorResId 加载失败图的资源文件
     * @param scaleType 缩放类型
     */
    ImageLoaderContract setError(@DrawableRes int errorResId, ScalingUtils.ScaleType scaleType);

    /**
     * 设置重试图片
     * @param retryResId 重试图片的资源文件
     * @param scaleType 缩放类型
     */
    ImageLoaderContract serRetry(@DrawableRes int retryResId, ScalingUtils.ScaleType scaleType);

    /**
     * 设置图片的缩放类型
     * @param scaleType 缩放类型
     */
    ImageLoaderContract setImageScaleType(ScalingUtils.ScaleType scaleType);

    /**
     * 设置图片在内存的大小，类似分辨率
     * @param resizeWidth 宽（单位px）
     * @param resizeHeight 高（单位px）
     */
    ImageLoaderContract setResizeOptions(int resizeWidth, int resizeHeight);

    /**
     * 设置固定宽高比(w/h)
     * @param aspectRatio 宽高比
     */
    ImageLoaderContract setAspectRatio(float aspectRatio);

    /**
     * 设置图片宽高
     * @param width 宽（单位px）
     * @param height 高（单位px）
     */
    ImageLoaderContract setImageSize(int width, int height);

    /**
     * 设置加载进度图
     * @param drawable 图片
     */
    ImageLoaderContract setProgressBarImage(@Nullable Drawable drawable);

    /**
     * 设置背景图
     * @param drawable 图片
     */
    ImageLoaderContract setBackgroundImage(@Nullable Drawable drawable);

    /**
     * 设置叠加图
     * @param drawable 图片
     */
    ImageLoaderContract setOverlayImage(@Nullable Drawable drawable);

    /**
     * 设置淡入展示的时间
     * @param durationMs 淡入时间（单位毫秒）
     */
    ImageLoaderContract setFadeDuration(int durationMs);

    /** 使用圆形图片 */
    ImageLoaderContract useCircle();

    /** 使用圆角 */
    ImageLoaderContract useRoundCorner();

    /**
     * 设置圆角
     * @param radius 圆角半径
     */
    ImageLoaderContract setRoundCorner(float radius);

    /**
     * 设置描边
     * @param borderColor 描边颜色
     * @param borderWidth 描边的宽度
     */
    ImageLoaderContract setBorder(@ColorInt int borderColor, float borderWidth);

    /** 使用高斯模糊 */
    ImageLoaderContract useBlur();

    /**
     * 设置高斯模糊（请配合setResizeOptions方法一起使用可以减少运算时间）
     * @param radius 模糊率（0-25）
     */
    ImageLoaderContract setBlurRadius(int radius);

    /**
     * 加载本地图片时使用缩略图预览（多图预览时建议开启，否则会导致卡顿）
     * @param isEnable 是否开启
     */
    ImageLoaderContract setLocalThumbnailPreviews(boolean isEnable);

    /**
     * 设置控制监听器
     * @param controllerListener 控制监听器
     */
    ImageLoaderContract setControllerListener(ControllerListener controllerListener);

    /**
     * 指定宽度，高度自适应图片
     * @param width 宽（单位px）
     */
    ImageLoaderContract wrapImageHeight(int width);

    /**
     * 指定高度，宽度自适应图片
     * @param height 高（单位px）
     */
    ImageLoaderContract wrapImageWidth(int height);

    /**
     * 宽、高都自适应图片
     * 宽 >= 高：图片宽超过屏幕时，以屏幕的宽作为宽度来换算高度
     * 宽 < 高：图片高超过屏幕时，以屏幕的高作为高度来换算宽度
     */
    ImageLoaderContract wrapImage();

    /** 装载图片 */
    void into(final SimpleDraweeView simpleDraweeView);


}