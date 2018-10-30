package com.lodz.android.imageloader.glide.impl;

import android.graphics.Color;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.lodz.android.imageloader.R;
import com.lodz.android.imageloader.glide.transformations.RoundedCornersTransformation;

import androidx.annotation.AnimRes;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;


/**
 * Glide构建类
 * Created by zhouL on 2017/4/7.
 */

public class GlideBuilderBean {

    /** 磁盘缓存策略 */
    public interface DiskCacheStrategy{
        /** 使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据 */
        int ALL = 1;
        /** 啥也不缓存 */
        int NONE = 2;
        /** 只缓存全尺寸图 */
        int DATA = 3;
        /** 只缓存最终降低分辨后用到的图片 */
        int RESOURCE = 4;
        /** 自动选择磁盘缓存策略 */
        int AUTOMATIC = 5;
    }

    /** 加载路径 */
    public Object path = null;
    /** 加载图的资源id */
    @DrawableRes
    public int placeholderResId = R.drawable.imageloader_ic_launcher;
    /** 失败图的资源id */
    @DrawableRes
    public int errorResId = R.drawable.imageloader_ic_launcher;
    /** 控件宽度（单位：px） */
    public int width = 0;
    /** 控件高度（单位：px） */
    public int height = 0;
    /** 使用高斯模糊 */
    public boolean useBlur = false;
    /** 高斯模糊率（0-25） */
    public int blurRadius = 5;
    /** 是否使用圆形图片 */
    public boolean useCircle = false;
    /** 是否使用圆角 */
    public boolean useRoundCorner = false;
    /** 圆角半径 */
    public int roundCornerRadius = 10;
    /** 保存到内存 */
    public boolean saveToMemoryCache = true;
    /** 磁盘缓存策略 */
    public int diskCacheStrategy = DiskCacheStrategy.AUTOMATIC;
    /** 居中裁切 */
    public boolean centerCrop = false;
    /** 居中自适应 */
    public boolean fitCenter = false;
    /** 设置内部居中 */
    public boolean centerInside = false;
    /** 直接显示图片不使用动画 */
    public boolean dontAnimate = false;
    /** 渐变显示 */
    public boolean crossFade = false;
    /** 动画资源id */
    @AnimRes
    public int animResId = -1;
    /** 动画编辑器 */
    public ViewPropertyTransition.Animator animator;
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
    public int maskResId = R.drawable.imageloader_mask_starfish;
    /** 是否显示视频第一帧 */
    public boolean isVideo = false;
    /** 图片请求监听器 */
    public RequestListener requestListener = null;

}
