package com.lodz.android.imageloader.contract;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.AnimRes;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.lodz.android.imageloader.glide.transformations.RoundedCornersTransformation;

import java.io.File;


/**
 * 图片加载库接口
 * Created by zhouL on 2016/11/14.
 */
public interface ImageLoaderContract {

    /**
     * 设置加载路径
     * @param o 加载路径（Glide包括String/Uri/File/Integer/byte[]）
     * @deprecated 请使用细类的load方法进行加载，比如：loadUrl、loadUri、loadFile等等
     */
    @Deprecated
    ImageLoaderContract load(Object o);

    /**
     * 加载网页图片
     * @param url 资源网址
     */
    ImageLoaderContract loadUrl(@NonNull String url);

    /**
     * 加载URI
     * @param uri URI路径
     */
    ImageLoaderContract loadUri(@NonNull Uri uri);

    /**
     * 加载本地文件
     * @param file 文件
     */
    ImageLoaderContract loadFile(@NonNull File file);

    /**
     * 加载本地文件路径
     * @param path 文件路径
     */
    ImageLoaderContract loadFilePath(@NonNull String path);

    /**
     * 加载资源图片
     * @param resId 资源id
     */
    ImageLoaderContract loadResId(@DrawableRes int resId);

    /**
     * 加载Base64图片
     * @param base64 字符串
     */
    ImageLoaderContract loadBase64(@NonNull String base64);

    /**
     * 在家比特数组
     * @param bytes 比特数组
     */
    ImageLoaderContract loadBytes(@NonNull byte[] bytes);

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
    ImageLoaderContract setRoundCorner(int radius);

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

    /** 设置内部居中 */
    ImageLoaderContract setCenterInside();

    /** 设置使用动画 */
    ImageLoaderContract dontAnimate();

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
    ImageLoaderContract setAnim(ViewPropertyTransition.Animator animator);

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
    ImageLoaderContract setRequestListener(RequestListener listener);

    /** 装载图片 */
    void into(ImageView imageView);

    /** 装载图片 */
    void into(Target<Drawable> target);

    /** 将图片转为bitmap并装载 */
    void asBitmapInto(ImageView imageView);

    /** 将图片转为bitmap并装载在SimpleTarget里面 */
    void asBitmapInto(Target<Bitmap> target);

    /** 将图片转为gif并装载 */
    void asGifInto(ImageView imageView);

    /** 将图片转为gif并装载 */
    void asGifInto(Target<GifDrawable> target);

}