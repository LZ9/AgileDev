package com.lodz.android.imageloader.contract;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lodz.android.imageloader.glide.transformations.RoundedCornersTransformation;


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
     */
    ImageLoaderContract setPlaceholder(@DrawableRes int placeholderResId);

    /**
     * 设置加载失败图
     * @param errorResId 加载失败图的资源文件
     */
    ImageLoaderContract setError(@DrawableRes int errorResId);

    /**
     * 设置图片宽高
     * @param width 宽（单位px）
     * @param height 高（单位px）
     */
    ImageLoaderContract setImageSize(int width, int height);

    /** 使用高斯模糊 */
    ImageLoaderContract useBlur();

    /**
     * 设置高斯模糊
     * @param radius 模糊率（0-25）
     */
    ImageLoaderContract setBlurRadius(int radius);

    /** 使用圆角 */
    ImageLoaderContract useRoundCorner();

    /**
     * 设置圆角
     * @param radius 圆角半径
     */
    ImageLoaderContract setRoundCorner(float radius);

    /** 使用圆形图片 */
    ImageLoaderContract useCircle();


    /** 转换Fresco特性 */
    FrescoContract joinFresco();

    interface FrescoContract{

        /**
         * 设置加载图的缩放类型
         * @param scaleType 缩放类型
         */
        FrescoContract setPlaceholderScaleType(ScalingUtils.ScaleType scaleType);

        /**
         * 设置加载失败图的缩放类型
         * @param scaleType 缩放类型
         */
        FrescoContract setErrorScaleType(ScalingUtils.ScaleType scaleType);

        /**
         * 设置重试图片
         * @param retryResId 重试图片的资源文件
         */
        FrescoContract serRetry(@DrawableRes int retryResId);

        /**
         * 设置重试图片的缩放类型
         * @param scaleType 缩放类型
         */
        FrescoContract serRetryScaleType(ScalingUtils.ScaleType scaleType);

        /**
         * 设置图片的缩放类型
         * @param scaleType 缩放类型
         */
        FrescoContract setImageScaleType(ScalingUtils.ScaleType scaleType);

        /**
         * 设置图片在内存的大小，类似分辨率
         * @param resizeWidth 宽（单位px）
         * @param resizeHeight 高（单位px）
         */
        FrescoContract setResizeOptions(int resizeWidth, int resizeHeight);

        /**
         * 设置固定宽高比(w/h)
         * @param aspectRatio 宽高比
         */
        FrescoContract setAspectRatio(float aspectRatio);

        /**
         * 设置加载进度图
         * @param drawable 图片
         */
        FrescoContract setProgressBarImage(@Nullable Drawable drawable);

        /**
         * 设置背景图
         * @param drawable 图片
         */
        FrescoContract setBackgroundImage(@Nullable Drawable drawable);

        /**
         * 设置叠加图
         * @param drawable 图片
         */
        FrescoContract setOverlayImage(@Nullable Drawable drawable);

        /**
         * 设置淡入展示的时间
         * @param durationMs 淡入时间（单位毫秒）
         */
        FrescoContract setFadeDuration(int durationMs);

        /**
         * 设置描边
         * @param borderColor 描边颜色
         * @param borderWidth 描边的宽度
         */
        FrescoContract setBorder(@ColorInt int borderColor, float borderWidth);

        /**
         * 加载本地图片时使用缩略图预览（多图预览时建议开启，否则会导致卡顿）
         * @param isEnable 是否开启
         */
        FrescoContract setLocalThumbnailPreviews(boolean isEnable);

        /**
         * 设置控制监听器
         * @param controllerListener 控制监听器
         */
        FrescoContract setControllerListener(ControllerListener controllerListener);

        /**
         * 指定宽度，高度自适应图片
         * @param width 宽（单位px）
         */
        FrescoContract wrapImageHeight(int width);

        /**
         * 指定高度，宽度自适应图片
         * @param height 高（单位px）
         */
        FrescoContract wrapImageWidth(int height);

        /**
         * 宽、高都自适应图片
         * 宽 >= 高：图片宽超过屏幕时，以屏幕的宽作为宽度来换算高度
         * 宽 < 高：图片高超过屏幕时，以屏幕的高作为高度来换算宽度
         */
        FrescoContract wrapImage();

        /** 装载图片 */
        void into(final SimpleDraweeView simpleDraweeView);
    }

    /** 转换Glide特性 */
    GlideContract joinGlide();

    public interface GlideContract{

        /** 跳过图片缓存入内存 */
        GlideContract skipMemoryCache();

        /** 跳过图片缓存入磁盘 */
        GlideContract diskCacheStrategy(int diskCacheStrategy);


        /** 设置居中裁切 */
        GlideContract setCenterCrop();

        /** 设置居中自适应 */
        GlideContract setFitCenter();

        /** 设置使用动画 */
        GlideContract useAnimate();

        /** 使用默认渐变效果（可能会拉伸图片，不建议使用） */
        @Deprecated
        GlideContract userCrossFade();

        /**
         * 设置动画资源id
         * @param animResId 动画资源id
         */
        GlideContract setAnimResId(@AnimRes int animResId);

        /**
         * 设置动画
         * @param animator 动画编辑器
         */
        GlideContract setAnim(ViewPropertyAnimation.Animator animator);

        /** 使用覆盖颜色 */
        GlideContract useFilterColor();

        /**
         * 设置覆盖颜色
         * @param color 颜色
         */
        GlideContract setFilterColor(@ColorInt int color);

        /**
         * 设置圆角图片的Margin
         * @param margin 间距
         */
        GlideContract setRoundedCornersMargin(int margin);

        /**
         * 设置圆角图片的位置参数
         * @param type 位置参数
         */
        GlideContract setRoundedCornerType(RoundedCornersTransformation.CornerType type);

        /** 使用灰度化 */
        GlideContract useGrayscale();

        /** 切正方形图 */
        GlideContract useCropSquare();

        /** 使用蒙板图片 */
        GlideContract useMask();

        /**
         * 设置蒙板图片资源id
         * @param maskResId 蒙板图片资源id
         */
        GlideContract setMaskResId(@DrawableRes int maskResId);

        /** 装载图片 */
        void into(final ImageView imageView);

        /** 将图片转为bitmap并装载在SimpleTarget里面 */
        void asBitmapInto(SimpleTarget<Bitmap> target);

        /** 将图片转为gif并装载 */
        void asGifInto(ImageView imageView);

    }
}