package com.lodz.android.imageloader.glide.impl;

import android.graphics.Color;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;

import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.lodz.android.imageloader.BuilderBean;
import com.lodz.android.imageloader.R;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Glide构建类
 * Created by zhouL on 2017/4/7.
 */

public class GlideBuilderBean extends BuilderBean {

    /** 磁盘缓存策略 */
    public interface DiskCacheStrategy{
        /** 缓存所有类型的图片 */
        int ALL = 1;
        /** 啥也不缓存 */
        int NONE = 2;
        /** 只缓存全尺寸图 */
        int SOURCE = 3;
        /** 只缓存最终降低分辨后用到的图片 */
        int RESULT = 4;
    }

    /** 保存到内存 */
    public boolean saveToMemoryCache = true;
    /** 磁盘缓存策略 */
    public int diskCacheStrategy = DiskCacheStrategy.SOURCE;
    /** 居中裁切 */
    public boolean centerCrop = false;
    /** 居中自适应 */
    public boolean fitCenter = false;
    /** 直接显示图片不使用动画 */
    public boolean dontAnimate = true;
    /** 渐变显示 */
    public boolean crossFade = false;
    /** 动画资源id */
    @AnimRes
    public int animResId = -1;
    /** 动画编辑器 */
    public ViewPropertyAnimation.Animator animator;
    /** 使用覆盖颜色 */
    public boolean useFilterColor = false;
    /** 覆盖颜色 */
    @ColorInt
    public int filterColor = Color.TRANSPARENT;
    /** 圆角图片的Margin */
    public int roundedCornersMargin = 0;
    /** 圆角位置参数 */
    public RoundedCornersTransformation.CornerType cornerType = RoundedCornersTransformation.CornerType.ALL;
    /** 使用灰度化 */
    public boolean useGrayscale = false;
    /** 使用正方形图 */
    public boolean useCropSquare = false;
    /** 使用蒙板 */
    public boolean useMask = false;
    /** 默认蒙板图片资源id */
    @DrawableRes
    public int maskResId = R.drawable.mask_starfish;
}
