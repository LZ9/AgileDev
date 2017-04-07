package com.lodz.android.imageloader.fresco.impl;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;

import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.imagepipeline.image.ImageInfo;
import com.lodz.android.imageloader.BuilderBean;
import com.lodz.android.imageloader.R;

/**
 * Fresco构建类
 * Created by zhouL on 2016/11/21.
 */
public class FrescoBuilderBean extends BuilderBean{

    /** 加载图的缩放类型 */
    public ScalingUtils.ScaleType placeholderScaleType = ScalingUtils.ScaleType.FIT_XY;
    /** 失败图的缩放类型 */
    public ScalingUtils.ScaleType errorScaleType = ScalingUtils.ScaleType.FIT_XY;
    /** 重试图的资源id */
    @DrawableRes
    public int retryResId = R.drawable.ic_retry;
    /** 重试图的缩放类型 */
    public ScalingUtils.ScaleType retryScaleType = ScalingUtils.ScaleType.CENTER_INSIDE;
    /** 图片的缩放类型 */
    public ScalingUtils.ScaleType imageScaleType = null;
    /** 分辨率宽度 */
    public int resizeWidth = 0;
    /** 分辨率高度 */
    public int resizeHeight = 0;
    /** 宽高比 */
    public float aspectRatio = 0f;
    /** 加载进度图 */
    public Drawable progressBarDrawable = null;
    /** 背景图 */
    public Drawable backgroundDrawable = null;
    /** 叠加图 */
    public Drawable overlayDrawable = null;
    /** 淡入时间（单位毫秒） */
    public int fadeDuration = 0;
    /** 描边颜色 */
    @ColorInt
    public int roundCornerBorderColor = Color.WHITE;
    /** 描边的宽度 */
    public float roundCornerBorderWidth = 0f;
    /** 缩略图预览 */
    public boolean localThumbnailPreviews = false;
    /** 控制监听器 */
    public ControllerListener<? super ImageInfo> controllerListener;
    /** 使用图片自适应 */
    public boolean useWrapImage = false;
}
