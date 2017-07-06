package com.lodz.android.imageloader.glide.impl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lodz.android.imageloader.ImageloaderManager;
import com.lodz.android.imageloader.contract.ImageLoaderContract;
import com.lodz.android.imageloader.glide.transformations.BlurTransformation;
import com.lodz.android.imageloader.glide.transformations.ColorFilterTransformation;
import com.lodz.android.imageloader.glide.transformations.CropCircleTransformation;
import com.lodz.android.imageloader.glide.transformations.CropSquareTransformation;
import com.lodz.android.imageloader.glide.transformations.GrayscaleTransformation;
import com.lodz.android.imageloader.glide.transformations.MaskTransformation;
import com.lodz.android.imageloader.glide.transformations.RoundedCornersTransformation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Glide图片加载库
 * Created by zhouL on 2017/4/7.
 */
public class GlideImageLoader implements ImageLoaderContract, ImageLoaderContract.GlideContract{

    private Context mContext;
    /** 构造对象实体 */
    private GlideBuilderBean mGlideBuilderBean;
    /** 请求管理对象 */
    private RequestManager mRequestManager;

    /** 创建Glide加载库 */
    public static GlideImageLoader with(Context context) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        imageLoader.mGlideBuilderBean = getGlideBuilderBean(ImageloaderManager.get().getBuilder());
        imageLoader.mRequestManager = Glide.with(context);
        imageLoader.mContext = context;
        return imageLoader;
    }

    /** 创建Glide加载库 */
    public static GlideImageLoader with(Activity activity) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        imageLoader.mGlideBuilderBean = getGlideBuilderBean(ImageloaderManager.get().getBuilder());
        imageLoader.mRequestManager = Glide.with(activity);
        imageLoader.mContext = activity;
        return imageLoader;
    }

    /** 创建Glide加载库 */
    public static GlideImageLoader with(FragmentActivity fragmentActivity) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        imageLoader.mGlideBuilderBean = getGlideBuilderBean(ImageloaderManager.get().getBuilder());
        imageLoader.mRequestManager = Glide.with(fragmentActivity);
        imageLoader.mContext = fragmentActivity;
        return imageLoader;
    }

    /** 创建Glide加载库 */
    public static GlideImageLoader with(Fragment fragment) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        imageLoader.mGlideBuilderBean = getGlideBuilderBean(ImageloaderManager.get().getBuilder());
        imageLoader.mRequestManager = Glide.with(fragment);
        imageLoader.mContext = fragment.getContext();
        return imageLoader;
    }

    private static GlideBuilderBean getGlideBuilderBean(ImageloaderManager.Builder builder) {
        GlideBuilderBean bean = new GlideBuilderBean();
        if (builder.getPlaceholderResId() != 0){// 获取配置参数
            bean.placeholderResId = builder.getPlaceholderResId();
        }
        if (builder.getErrorResId() != 0){
            bean.errorResId = builder.getErrorResId();
        }
        return bean;
    }

    @Override
    public ImageLoaderContract load(Object o) {
        if (o instanceof String || o instanceof Uri || o instanceof File || o instanceof Integer || o instanceof byte[]){
            mGlideBuilderBean.path = o;
            return this;
        }
        throw new RuntimeException("Glide不支持String/Uri/File/Integer/byte[]以外的图片路径参数");
    }

    @Override
    public ImageLoaderContract setPlaceholder(@DrawableRes int placeholderResId) {
        mGlideBuilderBean.placeholderResId = placeholderResId;
        return this;
    }

    @Override
    public ImageLoaderContract setError(@DrawableRes int errorResId) {
        mGlideBuilderBean.errorResId = errorResId;
        return this;
    }

    @Override
    public ImageLoaderContract setImageSize(int width, int height) {
        mGlideBuilderBean.width = width;
        mGlideBuilderBean.height = height;
        return this;
    }

    @Override
    public ImageLoaderContract useBlur() {
        mGlideBuilderBean.useBlur = true;
        return this;
    }

    @Override
    public ImageLoaderContract setBlurRadius(int radius) {
        useBlur();
        mGlideBuilderBean.blurRadius = radius;
        return this;
    }

    @Override
    public ImageLoaderContract useRoundCorner() {
        mGlideBuilderBean.useRoundCorner = true;
        return this;
    }

    @Override
    public ImageLoaderContract setRoundCorner(float radius) {
        useRoundCorner();
        mGlideBuilderBean.roundCornerRadius = radius;
        return this;
    }

    @Override
    public ImageLoaderContract useCircle() {
        mGlideBuilderBean.useCircle = true;
        return this;
    }

    @Override
    public FrescoContract joinFresco() {
        throw new RuntimeException("您已选择依赖Glide，请勿调用joinFresco()");
    }

    @Override
    public GlideContract joinGlide() {
        return this;
    }

    @Override
    public GlideContract skipMemoryCache() {
        mGlideBuilderBean.saveToMemoryCache = false;
        return this;
    }

    @Override
    public GlideContract diskCacheStrategy(int diskCacheStrategy) {
        mGlideBuilderBean.diskCacheStrategy = diskCacheStrategy;
        return this;
    }

    @Override
    public GlideContract setCenterCrop() {
        mGlideBuilderBean.centerCrop = true;
        return this;
    }

    @Override
    public GlideContract setFitCenter() {
        mGlideBuilderBean.fitCenter = true;
        return this;
    }

    @Override
    public GlideContract useAnimate() {
        mGlideBuilderBean.dontAnimate = false;
        return this;
    }

    @Override
    public GlideContract userCrossFade() {
        mGlideBuilderBean.crossFade = true;
        useAnimate();
        return this;
    }

    @Override
    public GlideContract setAnimResId(@AnimRes int animResId) {
        mGlideBuilderBean.animResId = animResId;
        useAnimate();
        return this;
    }

    @Override
    public GlideContract setAnim(ViewPropertyAnimation.Animator animator) {
        mGlideBuilderBean.animator = animator;
        useAnimate();
        return this;
    }

    @Override
    public GlideContract useFilterColor() {
        mGlideBuilderBean.useFilterColor = true;
        return this;
    }

    @Override
    public GlideContract setFilterColor(@ColorInt int color) {
        useFilterColor();
        mGlideBuilderBean.filterColor = color;
        return this;
    }

    @Override
    public GlideContract setRoundedCornersMargin(int margin) {
        mGlideBuilderBean.roundedCornersMargin = margin;
        return this;
    }

    @Override
    public GlideContract setRoundedCornerType(RoundedCornersTransformation.CornerType type) {
        mGlideBuilderBean.cornerType = type;
        return this;
    }

    @Override
    public GlideContract useGrayscale() {
        mGlideBuilderBean.useGrayscale = true;
        return this;
    }

    @Override
    public GlideContract useCropSquare() {
        mGlideBuilderBean.useCropSquare = true;
        return this;
    }

    @Override
    public GlideContract useMask() {
        mGlideBuilderBean.useMask = true;
        return this;
    }

    @Override
    public GlideContract setMaskResId(@DrawableRes int maskResId) {
        useMask();
        mGlideBuilderBean.maskResId = maskResId;
        return this;
    }

    @Override
    public GlideContract setVideo() {
        mGlideBuilderBean.isVideo = true;
        return this;
    }

    @Override
    public GlideContract setRequestListener(RequestListener listener) {
        mGlideBuilderBean.requestListener = listener;
        return this;
    }

    @Override
    public void into(ImageView imageView) {
        if (mRequestManager == null){
            return;
        }
        final DrawableTypeRequest request = getDrawableTypeRequest(mContext, mRequestManager, mGlideBuilderBean);
        request.into(imageView);
    }

    @Override
    public void asBitmapInto(SimpleTarget<Bitmap> target) {
        if (mRequestManager == null){
            return;
        }
        DrawableTypeRequest request = getDrawableTypeRequest(mContext, mRequestManager, mGlideBuilderBean);
        request.asBitmap().into(target);
    }

    @Override
    public void asGifInto(ImageView imageView) {
        if ( mRequestManager == null){
            return;
        }
        DrawableTypeRequest request = getDrawableTypeRequest(mContext, mRequestManager, mGlideBuilderBean);
        request.asGif().into(imageView);
    }

    /**
     * 组装DrawableTypeRequest
     * @param context 上下文
     * @param manager 请求管理类
     * @param bean 构建实体
     */
    @SuppressWarnings("unchecked")
    private DrawableTypeRequest getDrawableTypeRequest(Context context, RequestManager manager, GlideBuilderBean bean){
        DrawableTypeRequest request = manager.load(bean.path);
        request.placeholder(bean.placeholderResId);// 设置加载图
        request.error(bean.errorResId);// 设置加载失败图
        request.skipMemoryCache(!bean.saveToMemoryCache);// 设置跳过内存缓存
        request = configDiskCacheStrategy(request, bean);// 配置磁盘缓存策略
        if (bean.width > 0 && bean.height > 0) {
            request.override(bean.width, bean.height);// 设置图片宽高
        }
        if (bean.centerCrop){
            request.centerCrop();
        }
        if (bean.fitCenter){
            request.fitCenter();
        }
        if (bean.dontAnimate){
            request.dontAnimate();
        }
        if (bean.crossFade){
            request.crossFade();
        }
        if (bean.animResId != -1){
            request.animate(bean.animResId);
        }
        if (bean.animator != null){
            request.animate(bean.animator);
        }
        if (bean.requestListener != null){// 设置请求监听器
            request.listener(bean.requestListener);
        }

        setTransformation(context, request, bean);
        return request;
    }

    /**
     * 配置磁盘缓存策略
     * @param request 请求
     * @param bean 构建实体
     */
    private DrawableTypeRequest configDiskCacheStrategy(DrawableTypeRequest request, GlideBuilderBean bean) {
        if (bean.isVideo){//显示视频的第一帧需要设置为NONE
            request.diskCacheStrategy(DiskCacheStrategy.NONE);
        } else if (bean.path instanceof File || bean.path instanceof Integer){// 资源文件和本地手机存储的文件不需要进行磁盘缓存
            request.diskCacheStrategy(DiskCacheStrategy.NONE);
        }else {// 其他情况根据自定义缓存策略设置
            request.diskCacheStrategy(getGlideDiskCacheStrategy(bean.diskCacheStrategy));
        }
        return request;
    }

    /**
     * 获取Glide的缓存策略
     * @param diskCacheStrategy 自定义缓存策略枚举值
     */
    private DiskCacheStrategy getGlideDiskCacheStrategy(int diskCacheStrategy) {
        switch (diskCacheStrategy){
            case GlideBuilderBean.DiskCacheStrategy.ALL:
                return DiskCacheStrategy.ALL;
            case GlideBuilderBean.DiskCacheStrategy.NONE:
                return DiskCacheStrategy.NONE;
            case GlideBuilderBean.DiskCacheStrategy.SOURCE:
                return DiskCacheStrategy.SOURCE;
            case GlideBuilderBean.DiskCacheStrategy.RESULT:
                return DiskCacheStrategy.RESULT;
            default:
                return DiskCacheStrategy.SOURCE;
        }
    }

    /** 对图片进行变换 */
    @SuppressWarnings("unchecked")
    private void setTransformation(Context context, DrawableTypeRequest request, GlideBuilderBean bean) {
        List<Transformation<Bitmap>> list = new ArrayList<>();
        if (bean.useBlur){
            list.add(new BlurTransformation(context, bean.blurRadius));
        }
        if (bean.useFilterColor){
            list.add(new ColorFilterTransformation(context, bean.filterColor));
        }
        if (bean.useRoundCorner){
            list.add(new RoundedCornersTransformation(context, (int) bean.roundCornerRadius, bean.roundedCornersMargin, bean.cornerType));
        }
        if (bean.useGrayscale){
            list.add(new GrayscaleTransformation(context));
        }
        if (bean.useCircle){
            list.add(new CropCircleTransformation(context));
        }
        if (bean.useCropSquare){
            list.add(new CropSquareTransformation(context));
        }
        if (bean.useMask){
            list.add(new MaskTransformation(context, bean.maskResId));
        }
        if (list.size() > 0){
            Transformation[] transformations = new Transformation[list.size()];
            for (int i = 0; i < list.size(); i++) {
                transformations[i] = list.get(i);
            }
            request.bitmapTransform(transformations);
        }
    }
}
