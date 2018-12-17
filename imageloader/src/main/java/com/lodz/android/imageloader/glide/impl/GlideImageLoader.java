package com.lodz.android.imageloader.glide.impl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.bumptech.glide.signature.ObjectKey;
import com.lodz.android.imageloader.ImageloaderManager;
import com.lodz.android.imageloader.contract.ImageLoaderContract;
import com.lodz.android.imageloader.glide.config.GlideApp;
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
import java.util.UUID;

import androidx.annotation.AnimRes;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


/**
 * Glide图片加载库
 * Created by zhouL on 2017/4/7.
 */
public class GlideImageLoader implements ImageLoaderContract{

    /** 构造对象实体 */
    private GlideBuilderBean mGlideBuilderBean;
    /** 请求管理对象 */
    private RequestManager mRequestManager;

    /** 创建Glide加载库 */
    public static GlideImageLoader with(Context context) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        imageLoader.mGlideBuilderBean = getGlideBuilderBean(ImageloaderManager.get().getBuilder());
        imageLoader.mRequestManager = GlideApp.with(context);
        return imageLoader;
    }

    /** 创建Glide加载库 */
    public static GlideImageLoader with(Activity activity) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        imageLoader.mGlideBuilderBean = getGlideBuilderBean(ImageloaderManager.get().getBuilder());
        imageLoader.mRequestManager = GlideApp.with(activity);
        return imageLoader;
    }

    /** 创建Glide加载库 */
    public static GlideImageLoader with(FragmentActivity fragmentActivity) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        imageLoader.mGlideBuilderBean = getGlideBuilderBean(ImageloaderManager.get().getBuilder());
        imageLoader.mRequestManager = GlideApp.with(fragmentActivity);
        return imageLoader;
    }

    /** 创建Glide加载库 */
    public static GlideImageLoader with(Fragment fragment) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        imageLoader.mGlideBuilderBean = getGlideBuilderBean(ImageloaderManager.get().getBuilder());
        imageLoader.mRequestManager = GlideApp.with(fragment);
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
        if (o instanceof String) {
            return loadUrl((String) o);
        }
        if (o instanceof Uri) {
            return loadUri((Uri) o);
        }
        if (o instanceof File) {
            return loadFile((File) o);
        }
        if (o instanceof Integer) {
            return loadResId((Integer) o);
        }
        if (o instanceof byte[]) {
            return loadBytes((byte[]) o);
        }
        throw new RuntimeException("Glide不支持String/Uri/File/Integer/byte[]以外的图片路径参数");
    }

    @Override
    public ImageLoaderContract loadUrl(@NonNull String url) {
        mGlideBuilderBean.path = url;
        return this;
    }

    @Override
    public ImageLoaderContract loadUri(@NonNull Uri uri) {
        mGlideBuilderBean.path = uri;
        return this;
    }

    @Override
    public ImageLoaderContract loadFile(@NonNull File file) {
        mGlideBuilderBean.path = file;
        return this;
    }

    @Override
    public ImageLoaderContract loadFilePath(@NonNull String path) {
        File file = new File(path);
        if (file.exists()){//文件存在
            mGlideBuilderBean.path = file;
            return this;
        }
        return loadUrl("");
    }

    @Override
    public ImageLoaderContract loadResId(int resId) {
        mGlideBuilderBean.path = resId;
        return this;
    }

    @Override
    public ImageLoaderContract loadBase64(@NonNull String base64) {
        try {
            return loadBytes(Base64.decode(base64, Base64.DEFAULT));
        }catch (Exception e){
            e.printStackTrace();
        }
        return loadUrl("");
    }

    @Override
    public ImageLoaderContract loadBytes(@NonNull byte[] bytes) {
        mGlideBuilderBean.path = bytes;
        return this;
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
    public ImageLoaderContract setRoundCorner(int radius) {
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
    public ImageLoaderContract skipMemoryCache() {
        mGlideBuilderBean.saveToMemoryCache = false;
        return this;
    }

    @Override
    public ImageLoaderContract diskCacheStrategy(int diskCacheStrategy) {
        mGlideBuilderBean.diskCacheStrategy = diskCacheStrategy;
        return this;
    }

    @Override
    public ImageLoaderContract setCenterCrop() {
        mGlideBuilderBean.centerCrop = true;
        return this;
    }

    @Override
    public ImageLoaderContract setFitCenter() {
        mGlideBuilderBean.fitCenter = true;
        return this;
    }

    @Override
    public ImageLoaderContract setCenterInside() {
        mGlideBuilderBean.centerInside = true;
        return this;
    }

    @Override
    public ImageLoaderContract dontAnimate() {
        mGlideBuilderBean.dontAnimate = true;
        return this;
    }

    @Override
    public ImageLoaderContract userCrossFade() {
        mGlideBuilderBean.crossFade = true;
        mGlideBuilderBean.dontAnimate = false;
        return this;
    }

    @Override
    public ImageLoaderContract setAnimResId(@AnimRes int animResId) {
        mGlideBuilderBean.animResId = animResId;
        mGlideBuilderBean.dontAnimate = false;
        return this;
    }

    @Override
    public ImageLoaderContract setAnim(ViewPropertyTransition.Animator animator) {
        mGlideBuilderBean.animator = animator;
        mGlideBuilderBean.dontAnimate = false;
        return this;
    }

    @Override
    public ImageLoaderContract useFilterColor() {
        mGlideBuilderBean.useFilterColor = true;
        return this;
    }

    @Override
    public ImageLoaderContract setFilterColor(@ColorInt int color) {
        useFilterColor();
        mGlideBuilderBean.filterColor = color;
        return this;
    }

    @Override
    public ImageLoaderContract setRoundedCornersMargin(int margin) {
        mGlideBuilderBean.roundedCornersMargin = margin;
        return this;
    }

    @Override
    public ImageLoaderContract setRoundedCornerType(RoundedCornersTransformation.CornerType type) {
        mGlideBuilderBean.cornerType = type;
        return this;
    }

    @Override
    public ImageLoaderContract useGrayscale() {
        mGlideBuilderBean.useGrayscale = true;
        return this;
    }

    @Override
    public ImageLoaderContract useCropSquare() {
        mGlideBuilderBean.useCropSquare = true;
        return this;
    }

    @Override
    public ImageLoaderContract useMask() {
        mGlideBuilderBean.useMask = true;
        return this;
    }

    @Override
    public ImageLoaderContract setMaskResId(@DrawableRes int maskResId) {
        useMask();
        mGlideBuilderBean.maskResId = maskResId;
        return this;
    }

    @Override
    public ImageLoaderContract setVideo() {
        mGlideBuilderBean.isVideo = true;
        return this;
    }

    @Override
    public ImageLoaderContract setRequestListener(RequestListener listener) {
        mGlideBuilderBean.requestListener = listener;
        return this;
    }

    @Override
    public void into(ImageView imageView) {
        if (mRequestManager == null) {
            return;
        }
        RequestBuilder<Drawable> requestBuilder = mRequestManager.load(mGlideBuilderBean.path);
        requestBuilder = setDrawableRequestBuilder(requestBuilder, mGlideBuilderBean);
        requestBuilder.into(imageView);
    }

    @Override
    public void into(SimpleTarget<Drawable> target) {
        if (mRequestManager == null) {
            return;
        }
        RequestBuilder<Drawable> requestBuilder = mRequestManager.load(mGlideBuilderBean.path);
        requestBuilder = setDrawableRequestBuilder(requestBuilder, mGlideBuilderBean);
        requestBuilder.into(target);
    }

    @Override
    public void asBitmapInto(ImageView imageView) {
        if (mRequestManager == null){
            return;
        }
        RequestBuilder<Bitmap> requestBuilder = mRequestManager.asBitmap().load(mGlideBuilderBean.path);
        requestBuilder = setBitmapRequestBuilder(requestBuilder, mGlideBuilderBean);
        requestBuilder.into(imageView);
    }

    @Override
    public void asBitmapInto(SimpleTarget<Bitmap> target) {
        if (mRequestManager == null){
            return;
        }
        RequestBuilder<Bitmap> requestBuilder = mRequestManager.asBitmap().load(mGlideBuilderBean.path);
        requestBuilder = setBitmapRequestBuilder(requestBuilder, mGlideBuilderBean);
        requestBuilder.into(target);
    }

    @Override
    public void asGifInto(ImageView imageView) {
        if ( mRequestManager == null){
            return;
        }
        RequestBuilder<GifDrawable> requestBuilder = mRequestManager.asGif().load(mGlideBuilderBean.path);
        requestBuilder = setGifDrawableRequestBuilder(requestBuilder, mGlideBuilderBean);
        requestBuilder.into(imageView);
    }

    @Override
    public void asGifInto(SimpleTarget<GifDrawable> target) {
        if ( mRequestManager == null){
            return;
        }
        RequestBuilder<GifDrawable> requestBuilder = mRequestManager.asGif().load(mGlideBuilderBean.path);
        requestBuilder = setGifDrawableRequestBuilder(requestBuilder, mGlideBuilderBean);
        requestBuilder.into(target);
    }

    @SuppressWarnings("unchecked")
    private RequestBuilder<Drawable> setDrawableRequestBuilder(RequestBuilder<Drawable> requestBuilder, GlideBuilderBean bean) {
        requestBuilder = requestBuilder.apply(getRequestOptions(bean));
        requestBuilder = setDrawableTransitionOptions(requestBuilder, bean);
        if (bean.requestListener != null){// 设置请求监听器
            requestBuilder = requestBuilder.listener(bean.requestListener);
        }
        return requestBuilder;
    }

    @SuppressWarnings("unchecked")
    private RequestBuilder<Bitmap> setBitmapRequestBuilder(RequestBuilder<Bitmap> requestBuilder, GlideBuilderBean bean) {
        requestBuilder = requestBuilder.apply(getRequestOptions(bean));
        requestBuilder = setBitmapTransitionOptions(requestBuilder, bean);
        if (bean.requestListener != null){// 设置请求监听器
            requestBuilder = requestBuilder.listener(bean.requestListener);
        }
        return requestBuilder;
    }

    @SuppressWarnings("unchecked")
    private RequestBuilder<GifDrawable> setGifDrawableRequestBuilder(RequestBuilder<GifDrawable> requestBuilder, GlideBuilderBean bean) {
        requestBuilder = requestBuilder.apply(getRequestOptions(bean));
        requestBuilder = setGifDrawableTransitionOptions(requestBuilder, bean);
        if (bean.requestListener != null){// 设置请求监听器
            requestBuilder = requestBuilder.listener(bean.requestListener);
        }
        return requestBuilder;
    }

    private RequestOptions getRequestOptions(GlideBuilderBean bean){
        RequestOptions options = new RequestOptions();
        if (bean.path instanceof byte[]){
            options = options.signature(new ObjectKey(UUID.randomUUID().toString()));// 修复glide加载byte数组图片无法缓存的BUG
        }
        options = options.placeholder(bean.placeholderResId);// 设置加载图
        options = options.error(bean.errorResId);// 设置加载失败图
        options = options.skipMemoryCache(!bean.saveToMemoryCache);// 设置跳过内存缓存
        options = configDiskCacheStrategy(options, bean);// 配置磁盘缓存策略
        if (bean.width > 0 && bean.height > 0) {
            options = options.override(bean.width, bean.height);// 设置图片宽高
        }
        if (bean.centerCrop){
            options = options.centerCrop();
        }
        if (bean.fitCenter){
            options = options.fitCenter();
        }
        if (bean.centerInside){
            options = options.centerInside();
        }
        if (bean.dontAnimate){
            options = options.dontAnimate();
        }
        options = setTransformation(options, bean);
        return options;
    }

    /** 设置图片转换选项 */
    private RequestBuilder<Drawable> setDrawableTransitionOptions(RequestBuilder<Drawable> requestBuilder, GlideBuilderBean bean) {
        if (bean.crossFade) {
            requestBuilder = requestBuilder.transition(new DrawableTransitionOptions().crossFade());
        }
        if (bean.animResId != -1){
            requestBuilder = requestBuilder.transition(new DrawableTransitionOptions().transition(bean.animResId));
        }
        if (bean.animator != null){
            requestBuilder = requestBuilder.transition(new DrawableTransitionOptions().transition(bean.animator));
        }
        return requestBuilder;
    }

    /** 设置图片转换选项 */
    private RequestBuilder<Bitmap> setBitmapTransitionOptions(RequestBuilder<Bitmap> requestBuilder, GlideBuilderBean bean) {
        if (bean.crossFade) {
            requestBuilder = requestBuilder.transition(new BitmapTransitionOptions().crossFade());
        }
        if (bean.animResId != -1){
            requestBuilder = requestBuilder.transition(new BitmapTransitionOptions().transition(bean.animResId));
        }
        if (bean.animator != null){
            requestBuilder = requestBuilder.transition(new BitmapTransitionOptions().transition(bean.animator));
        }
        return requestBuilder;
    }

    /** 设置图片转换选项 */
    private RequestBuilder<GifDrawable> setGifDrawableTransitionOptions(RequestBuilder<GifDrawable> requestBuilder, GlideBuilderBean bean) {
        if (bean.animResId != -1){
            requestBuilder = requestBuilder.transition(new GenericTransitionOptions<>().transition(bean.animResId));
        }
        if (bean.animator != null){
            requestBuilder = requestBuilder.transition(new GenericTransitionOptions<>().transition(bean.animator));
        }
        return requestBuilder;
    }

    /**
     * 配置磁盘缓存策略
     * @param request 请求
     * @param bean 构建实体
     */
    private RequestOptions configDiskCacheStrategy(RequestOptions request, GlideBuilderBean bean) {
        if (bean.isVideo){//显示视频的第一帧需要设置为NONE
            request = request.diskCacheStrategy(DiskCacheStrategy.NONE);
        } else if (bean.path instanceof File || bean.path instanceof Integer || bean.path instanceof byte[]){
            request = request.diskCacheStrategy(DiskCacheStrategy.NONE);// 资源文件、本地手机存储的文件和byte数组不需要进行磁盘缓存
        }else {// 其他情况根据自定义缓存策略设置
            request = request.diskCacheStrategy(getGlideDiskCacheStrategy(bean.diskCacheStrategy));
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
            case GlideBuilderBean.DiskCacheStrategy.DATA:
                return DiskCacheStrategy.DATA;
            case GlideBuilderBean.DiskCacheStrategy.RESOURCE:
                return DiskCacheStrategy.RESOURCE;
            case GlideBuilderBean.DiskCacheStrategy.AUTOMATIC:
                return DiskCacheStrategy.AUTOMATIC;
            default:
                return DiskCacheStrategy.AUTOMATIC;
        }
    }

    /** 对图片进行变换 */
    @SuppressWarnings("unchecked")
    private RequestOptions setTransformation(RequestOptions request, GlideBuilderBean bean) {
        List<Transformation<Bitmap>> list = new ArrayList<>();
        if (bean.useBlur){
            list.add(new BlurTransformation(bean.blurRadius));
        }
        if (bean.useFilterColor){
            list.add(new ColorFilterTransformation(bean.filterColor));
        }
        if (bean.useRoundCorner){
            list.add(new RoundedCornersTransformation(bean.roundCornerRadius, bean.roundedCornersMargin, bean.cornerType));
        }
        if (bean.useGrayscale){
            list.add(new GrayscaleTransformation());
        }
        if (bean.useCircle){
            list.add(new CropCircleTransformation());
        }
        if (bean.useCropSquare){
            list.add(new CropSquareTransformation());
        }
        if (bean.useMask){
            list.add(new MaskTransformation(bean.maskResId));
        }
        if (list.size() > 0){
            Transformation[] transformations = new Transformation[list.size()];
            for (int i = 0; i < list.size(); i++) {
                transformations[i] = list.get(i);
            }
            request = request.transforms(transformations);
        }
        return request;
    }
}
