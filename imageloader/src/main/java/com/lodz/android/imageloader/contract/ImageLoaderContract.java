package com.lodz.android.imageloader.contract;

import android.graphics.Bitmap;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lodz.android.imageloader.glide.transformations.RoundedCornersTransformation;


/**
 * 图片加载库接口
 * Created by zhouL on 2016/11/14.
 */
public interface ImageLoaderContract {

    /**
     * 设置加载路径
     * @param o 加载路径（Fresco采用Uri，Glide包括String/Uri/File/Integer/byte[]）
     */
    ImageLoaderContract load(Object o);

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

    /** 跳过图片缓存入内存 */
    ImageLoaderContract skipMemoryCache();

    /** 设置磁盘缓存方式 */
    ImageLoaderContract diskCacheStrategy(int diskCacheStrategy);


    /** 设置居中裁切 */
    ImageLoaderContract setCenterCrop();

    /** 设置居中自适应 */
    ImageLoaderContract setFitCenter();

    /** 设置使用动画 */
    ImageLoaderContract useAnimate();

    /** 使用默认渐变效果 */
    ImageLoaderContract userCrossFade();

    /**
     * 设置动画资源id
     * @param animResId 动画资源id
     */
    ImageLoaderContract setAnimResId(@AnimRes int animResId);

    /**
     * 设置动画
     * @param animator 动画编辑器
     */
    ImageLoaderContract setAnim(ViewPropertyAnimation.Animator animator);

    /** 使用覆盖颜色 */
    ImageLoaderContract useFilterColor();

    /**
     * 设置覆盖颜色
     * @param color 颜色
     */
    ImageLoaderContract setFilterColor(@ColorInt int color);

    /**
     * 设置圆角图片的Margin
     * @param margin 间距
     */
    ImageLoaderContract setRoundedCornersMargin(int margin);

    /**
     * 设置圆角图片的位置参数
     * @param type 位置参数
     */
    ImageLoaderContract setRoundedCornerType(RoundedCornersTransformation.CornerType type);

    /** 使用灰度化 */
    ImageLoaderContract useGrayscale();

    /** 切正方形图 */
    ImageLoaderContract useCropSquare();

    /** 使用蒙板图片 */
    ImageLoaderContract useMask();

    /**
     * 设置蒙板图片资源id
     * @param maskResId 蒙板图片资源id
     */
    ImageLoaderContract setMaskResId(@DrawableRes int maskResId);

    /** 显示视频第一帧 */
    ImageLoaderContract setVideo();

    /** 添加图片请求监听器 */
    ImageLoaderContract setRequestListener(RequestListener<Object, GlideDrawable> listener);

    /** 装载图片 */
    void into(final ImageView imageView);

    /** 将图片转为bitmap并装载在SimpleTarget里面 */
    void asBitmapInto(SimpleTarget<Bitmap> target);

    /** 将图片转为gif并装载 */
    void asGifInto(ImageView imageView);

}