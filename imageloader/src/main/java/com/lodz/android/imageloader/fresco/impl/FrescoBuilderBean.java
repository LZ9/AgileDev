package com.lodz.android.imageloader.fresco.impl;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;

import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.imagepipeline.image.ImageInfo;
import com.lodz.android.imageloader.R;

/**
 * Fresco构建类
 * Created by zhouL on 2016/11/21.
 */
public class FrescoBuilderBean {

    /** 默认加载图 */
    private final static int DEFAULT_PLACEHOLDER = R.drawable.ic_launcher;
    /** 默认加载失败图 */
    private final static int DEFAULT_ERROR = R.drawable.ic_launcher;
    /** 默认重试图 */
    private final static int DEFAULT_RETRY = R.drawable.ic_retry;

    /** 默认不展示圆形图 */
    private final static boolean DEFAULT_USE_CIRCLE = false;
    /** 默认不使用圆角 */
    private final static boolean DEFAULT_USE_ROUND_CORNER = false;
    /** 默认圆角参数 */
    private final static float DEFAULT_ROUNDE_CORNERS = 55f;
    /** 默认描边颜色 */
    private final static int DEFAULT_BORDER_COLOR = Color.WHITE;
    /** 默认缩略图预览 */
    private final static boolean DEFAULT_LOCAL_THUMBNAIL_PREVIEWS = false;

    /** 默认不使用高斯模糊 */
    private final static boolean DEFAULT_USE_BLUR = false;
    /** 默认模糊率 */
    private final static int DEFAULT_BLUR_RADIUS = 5;


    /** 加载路径 */
    public Uri uri = null;

    /** 加载图的资源id */
    @DrawableRes
    public int placeholderResId = DEFAULT_PLACEHOLDER;

    /** 加载图的缩放类型 */
    public ScalingUtils.ScaleType placeholderScaleType = ScalingUtils.ScaleType.FIT_XY;

    /** 失败图的资源id */
    @DrawableRes
    public int errorResId = DEFAULT_ERROR;

    /** 失败图的缩放类型 */
    public ScalingUtils.ScaleType errorScaleType = ScalingUtils.ScaleType.FIT_XY;

    /** 重试图的资源id */
    @DrawableRes
    public int retryResId = DEFAULT_RETRY;

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

    /** 控件宽度 */
    public int width = 0;

    /** 控件高度 */
    public int height = 0;

    /** 加载进度图 */
    public Drawable progressBarDrawable = null;

    /** 背景图 */
    public Drawable backgroundDrawable = null;

    /** 叠加图 */
    public Drawable overlayDrawable = null;

    /** 淡入时间（单位毫秒） */
    public int fadeDuration = 0;

    /** 是否使用圆形图片 */
    public boolean useCircle = DEFAULT_USE_CIRCLE;

    /** 是否使用圆角 */
    public boolean useRoundCorner = DEFAULT_USE_ROUND_CORNER;

    /** 圆角半径 */
    public float roundCornerRadius = DEFAULT_ROUNDE_CORNERS;

    /** 描边颜色 */
    @ColorInt
    public int roundCornerBorderColor = DEFAULT_BORDER_COLOR;

    /** 描边的宽度 */
    public float roundCornerBorderWidth = 0f;

    /** 使用高斯模糊 */
    public boolean useBlur = DEFAULT_USE_BLUR;

    /** 高斯模糊率（0-25） */
    public int blurRadius = DEFAULT_BLUR_RADIUS;

    /** 缩略图预览 */
    public boolean localThumbnailPreviews = DEFAULT_LOCAL_THUMBNAIL_PREVIEWS;

    /** 控制监听器 */
    public ControllerListener<? super ImageInfo> controllerListener;

}
