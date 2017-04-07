package com.lodz.android.imageloader.fresco.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.lodz.android.imageloader.contract.ImageLoaderContract;
import com.lodz.android.imageloader.ImageloaderManager;
import com.lodz.android.imageloader.utils.blur.FastBlur;

/**
 * Fresco图片加载库
 * Created by zhouL on 2016/11/21.
 */
public class FrescoImageLoader implements ImageLoaderContract, ImageLoaderContract.FrescoContract {

    /** Fresco构建类 */
    private FrescoBuilderBean mFrescoBuilderBean;

    /** 创建FrescoImageLoader */
    public static FrescoImageLoader create(){
        FrescoImageLoader fresco = new FrescoImageLoader();
        fresco.mFrescoBuilderBean = getFrescoBuilderBean(ImageloaderManager.get().getBuilder());
        return fresco;
    }

    /**
     * 根据用户配置的参数获取构造bean
     * @param builder 配置参数
     */
    private static FrescoBuilderBean getFrescoBuilderBean(ImageloaderManager.Builder builder) {
        FrescoBuilderBean bean = new FrescoBuilderBean();
        if (builder.getPlaceholderResId() != 0){// 获取配置参数
            bean.placeholderResId = builder.getPlaceholderResId();
        }
        if (builder.getErrorResId() != 0){
            bean.errorResId = builder.getErrorResId();
        }
        if (builder.getRetryResId() != 0){
            bean.retryResId = builder.getRetryResId();
        }
        return bean;
    }

    @Override
    public ImageLoaderContract load(Uri uri) {
        mFrescoBuilderBean.uri = uri;
        return this;
    }

    @Override
    public ImageLoaderContract setPlaceholder(@DrawableRes int placeholderResId) {
        mFrescoBuilderBean.placeholderResId = placeholderResId;
        return this;
    }

    @Override
    public ImageLoaderContract setError(@DrawableRes int errorResId) {
        mFrescoBuilderBean.errorResId = errorResId;
        return this;
    }

    @Override
    public ImageLoaderContract setImageSize(int width, int height) {
        mFrescoBuilderBean.width = width;
        mFrescoBuilderBean.height = height;
        if (width > 0 && height > 0){
            setResizeOptions(width, height);
        }
        return this;
    }

    @Override
    public ImageLoaderContract useCircle() {
        mFrescoBuilderBean.useCircle = true;
        return this;
    }

    @Override
    public ImageLoaderContract useRoundCorner() {
        mFrescoBuilderBean.useRoundCorner = true;
        return this;
    }

    @Override
    public ImageLoaderContract setRoundCorner(float radius) {
        mFrescoBuilderBean.roundCornerRadius = radius;
        return this;
    }

    @Override
    public ImageLoaderContract useBlur() {
        mFrescoBuilderBean.useBlur = true;
        return this;
    }

    @Override
    public ImageLoaderContract setBlurRadius(int radius) {
        mFrescoBuilderBean.blurRadius = radius;
        return this;
    }

    @Override
    public FrescoContract joinFresco() {
        return this;
    }

    @Override
    public GlideContract joinGlide() {
        return null;
    }

    @Override
    public FrescoContract setPlaceholderScaleType(ScalingUtils.ScaleType scaleType) {
        mFrescoBuilderBean.placeholderScaleType = scaleType;
        return this;
    }

    @Override
    public FrescoContract setErrorScaleType(ScalingUtils.ScaleType scaleType) {
        mFrescoBuilderBean.errorScaleType = scaleType;
        return this;
    }

    @Override
    public FrescoContract serRetry(@DrawableRes int retryResId) {
        mFrescoBuilderBean.retryResId = retryResId;
        return this;
    }

    @Override
    public FrescoContract serRetryScaleType(ScalingUtils.ScaleType scaleType) {
        mFrescoBuilderBean.retryScaleType = scaleType;
        return this;
    }

    @Override
    public FrescoContract setImageScaleType(ScalingUtils.ScaleType scaleType) {
        mFrescoBuilderBean.imageScaleType = scaleType;
        return this;
    }

    @Override
    public FrescoContract setResizeOptions(int resizeWidth, int resizeHeight) {
        mFrescoBuilderBean.resizeWidth = resizeWidth;
        mFrescoBuilderBean.resizeHeight = resizeHeight;
        return this;
    }

    @Override
    public FrescoContract setAspectRatio(float aspectRatio) {
        mFrescoBuilderBean.aspectRatio = aspectRatio;
        return this;
    }

    @Override
    public FrescoContract setProgressBarImage(@Nullable Drawable drawable) {
        mFrescoBuilderBean.progressBarDrawable = drawable;
        return this;
    }

    @Override
    public FrescoContract setBackgroundImage(@Nullable Drawable drawable) {
        mFrescoBuilderBean.backgroundDrawable = drawable;
        return this;
    }

    @Override
    public FrescoContract setOverlayImage(@Nullable Drawable drawable) {
        mFrescoBuilderBean.overlayDrawable = drawable;
        return this;
    }

    @Override
    public FrescoContract setFadeDuration(int durationMs) {
        mFrescoBuilderBean.fadeDuration = durationMs;
        return this;
    }

    @Override
    public FrescoContract setBorder(@ColorInt int borderColor, float borderWidth) {
        mFrescoBuilderBean.roundCornerBorderColor = borderColor;
        mFrescoBuilderBean.roundCornerBorderWidth = borderWidth;
        return this;
    }

    @Override
    public FrescoContract setLocalThumbnailPreviews(boolean isEnable) {
        mFrescoBuilderBean.localThumbnailPreviews = isEnable;
        return this;
    }

    @Override
    public FrescoContract setControllerListener(ControllerListener controllerListener) {
        mFrescoBuilderBean.controllerListener = controllerListener;
        return this;
    }

    @Override
    public FrescoContract wrapImageHeight(int width) {
        mFrescoBuilderBean.useWrapImage = true;
        mFrescoBuilderBean.width = width;
        return this;
    }

    @Override
    public FrescoContract wrapImageWidth(int height) {
        mFrescoBuilderBean.useWrapImage = true;
        mFrescoBuilderBean.height = height;
        return this;
    }

    @Override
    public FrescoContract wrapImage() {
        mFrescoBuilderBean.useWrapImage = true;
        return this;
    }

    @Override
    public void into(SimpleDraweeView simpleDraweeView) {
        if (mFrescoBuilderBean == null || mFrescoBuilderBean.uri == null || simpleDraweeView == null){
            return;
        }
        simpleDraweeView = getConfig(simpleDraweeView, mFrescoBuilderBean);
        simpleDraweeView.setController(getDraweeController(simpleDraweeView, mFrescoBuilderBean));
    }

    /**
     * 获取控件配置
     * @param simpleDraweeView 图片控件
     * @param bean 构建类
     */
    private SimpleDraweeView getConfig(SimpleDraweeView simpleDraweeView, FrescoBuilderBean bean) {
        if (bean.aspectRatio > 0){
            simpleDraweeView.setAspectRatio(bean.aspectRatio);//设置固定宽高比
        }

        if (!bean.useWrapImage && (bean.width > 0 || bean.height > 0)){//设置图片宽高
            ViewGroup.LayoutParams layoutParams = simpleDraweeView.getLayoutParams();
            if (layoutParams == null){
                layoutParams = new ViewGroup.LayoutParams(bean.width, bean.height);
            }else {
                if (bean.width > 0){
                    layoutParams.width = bean.width;
                }
                if (bean.height > 0){
                    layoutParams.height = bean.height;
                }
            }
            simpleDraweeView.setLayoutParams(layoutParams);
        }

        simpleDraweeView.setHierarchy(getGenericDraweeHierarchy(simpleDraweeView.getHierarchy(), bean));
        return simpleDraweeView;
    }

    /**
     * 获取控件配置
     * @param hierarchy 控件的GenericDraweeHierarchy
     * @param bean 构建类
     */
    private GenericDraweeHierarchy getGenericDraweeHierarchy(GenericDraweeHierarchy hierarchy, FrescoBuilderBean bean) {
        hierarchy.setPlaceholderImage(bean.placeholderResId, bean.placeholderScaleType);// 默认占位图
        hierarchy.setFailureImage(bean.errorResId, bean.errorScaleType);// 失败图
        hierarchy.setRetryImage(bean.retryResId, bean.retryScaleType);// 重试图
        if (bean.imageScaleType != null){
            hierarchy.setActualImageScaleType(bean.imageScaleType);// 设置图片缩放类型
        }

        if (bean.progressBarDrawable != null){
            hierarchy.setProgressBarImage(bean.progressBarDrawable);// 设置动态加载进度
        }

        if (bean.backgroundDrawable != null){
            hierarchy.setBackgroundImage(bean.backgroundDrawable);//设置背景图
        }

        if (bean.overlayDrawable != null){
            hierarchy.setOverlayImage(bean.overlayDrawable);//设置叠加图
        }

        if (bean.useCircle || bean.useRoundCorner){
            hierarchy.setRoundingParams(getRoundingParams(bean));// 设置圆形图片或圆角
        }

        if (bean.fadeDuration > 0){
            hierarchy.setFadeDuration(bean.fadeDuration);// 设置淡入展示的时间（3s）
        }
        return hierarchy;
    }

    /**
     * 获取圆形/圆角/描边参数配置
     * @param bean 构建类
     */
    private RoundingParams getRoundingParams(FrescoBuilderBean bean){
        RoundingParams roundingParams = new RoundingParams();
        if (bean.useCircle){
            roundingParams.setRoundAsCircle(true);// 设置显示圆形
        }

        if (bean.useRoundCorner){
            roundingParams.setCornersRadius(bean.roundCornerRadius);// 设置圆角的半径
        }

        if (bean.roundCornerBorderWidth > 0){
            roundingParams.setBorder(bean.roundCornerBorderColor, bean.roundCornerBorderWidth);// 设置描边的颜色和宽度
        }
        return roundingParams;
    }

    /**
     * 获取DraweeController
     * @param draweeView 控件
     * @param bean 构建类
     */
    private DraweeController getDraweeController(SimpleDraweeView draweeView, FrescoBuilderBean bean) {
        return Fresco.newDraweeControllerBuilder()
                .setOldController(draweeView.getController())
                .setImageRequest(getImageRequest(bean))
                .setTapToRetryEnabled(ImageloaderManager.get().getBuilder().isTapToRetryEnabled()) // 开启重试功能
                .setAutoPlayAnimations(ImageloaderManager.get().getBuilder().isAutoPlayAnimations()) // 自动播放gif动画
                .setControllerListener(configControllerListener(draweeView, bean))
                .build();
    }

    /**
     * 获取ImageRequest
     * @param bean 构建类
     */
    private ImageRequest getImageRequest(FrescoBuilderBean bean) {
        ResizeOptions resizeOptions = null;
        if (bean.resizeWidth > 0 && bean.resizeHeight > 0){// 配置内存中图片分辨率
            resizeOptions = new ResizeOptions(bean.resizeWidth, bean.resizeHeight);
        }

        BlurPostprocessor blurPostprocessor = null;
        if (bean.useBlur){// 配置高斯模糊
            blurPostprocessor = new BlurPostprocessor(bean.blurRadius);
        }

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(bean.uri)
                .setLocalThumbnailPreviewsEnabled(bean.localThumbnailPreviews)
                .setResizeOptions(resizeOptions)//设置图片在内存的大小，类似分辨率
                .setRotationOptions(RotationOptions.autoRotate())//设置自动旋转
                .setProgressiveRenderingEnabled(true)//开启渐进式加载
                .setPostprocessor(blurPostprocessor)//进行高斯模糊，配合setResizeOptions使用可以减少运算时间
                .build();
        return request;
    }

    /** 高斯模糊 */
    private class BlurPostprocessor extends BasePostprocessor {

        /** 模糊率(0-25) */
        private int radius;

        public BlurPostprocessor(int radius) {
            this.radius = radius;
        }

        @Override
        public String getName() {
            return "BlurPostprocessor";
        }

        @Override
        public void process(Bitmap bitmap) {
            FastBlur.blur(bitmap, this.radius, true);
        }
    }

    /**
     * 配置ControllerListener
     * @param draweeView 控件
     * @param bean 构建类
     */
    private ControllerListener<? super ImageInfo> configControllerListener(final SimpleDraweeView draweeView, final FrescoBuilderBean bean) {
        if (!bean.useWrapImage){
            return bean.controllerListener;
        }

        return new BaseControllerListener<ImageInfo>(){
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {

                if (bean.width > 0 && bean.height > 0){// 已经指定宽高
                    setLayoutParams(draweeView, bean.width, bean.height);
                    return;
                }

                int imageWidth = imageInfo.getWidth();// 图片宽
                int imageHeight = imageInfo.getHeight();// 图片高
                float aspectRatio = imageWidth / (imageHeight * 1.0f);// 图片宽高比

                if (bean.width > 0 && bean.height == 0){// 指定宽度自适应高度
                    setLayoutParams(draweeView, bean.width, getHeight(bean.width, aspectRatio));
                    return;
                }

                if (bean.height > 0 && bean.width == 0){// 指定高度自适应宽度
                    setLayoutParams(draweeView, getWidth(bean.height, aspectRatio), bean.height);
                    return;
                }

                int screenWidth = getScreenWidth(draweeView.getContext());// 屏幕宽
                int screenHeight = getScreenHeight(draweeView.getContext());// 屏幕高

                if (imageWidth >= imageHeight){// 横向长方形（矩形）
                    if (imageWidth > screenWidth){
                        imageWidth = screenWidth;
                        imageHeight = getHeight(imageWidth, aspectRatio);
                    }
                }else {// 纵向长方形
                    if (imageHeight > screenHeight){
                        if (getWidth(screenHeight, aspectRatio) > screenWidth){// 用高度换算后的宽度仍然大于屏幕宽度，则改用宽度换算
                            imageWidth = screenWidth;
                            imageHeight = getHeight(imageWidth, aspectRatio);
                        } else {
                            imageHeight = screenHeight;
                            imageWidth = getWidth(imageHeight, aspectRatio);
                        }
                    }
                }
                // 宽高都自适应
                setLayoutParams(draweeView, imageWidth, imageHeight);
            }
        };
    }

    /**
     * 根据宽高比换算宽度
     * @param height 高度
     * @param aspectRatio 宽高比
     */
    private int getWidth(int height, float aspectRatio){
        return (int) (height * aspectRatio);
    }

    /**
     * 根据宽高比换算高度
     * @param width 宽度
     * @param aspectRatio 宽高比
     * @return
     */
    private int getHeight(int width, float aspectRatio){
        return (int) (width / aspectRatio);
    }

    /**
     * 设置图片宽高
     * @param draweeView 控件
     * @param width 宽度
     * @param height 高度
     */
    private void setLayoutParams(SimpleDraweeView draweeView, int width, int height) {
        ViewGroup.LayoutParams layoutParams = draweeView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(width, height);
        } else {
            layoutParams.width = width;
            layoutParams.height = height;
        }
        draweeView.setLayoutParams(layoutParams);
    }

    /**
     * 获得屏幕宽度
     * @param context 上下文
     */
    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     * @param context 上下文
     */
    private int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
}
