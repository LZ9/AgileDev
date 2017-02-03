package com.snxun.imageloader.fresco.impl;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.snxun.imageloader.contract.ImageLoaderContract;
import com.snxun.imageloader.fresco.config.ImageloaderManager;
import com.snxun.imageloader.utils.blur.FastBlur;

/**
 * Fresco图片加载库
 * Created by zhouL on 2016/11/21.
 */
public class FrescoImageLoader implements ImageLoaderContract {

    /** Fresco构建类 */
    private FrescoBuilderBean mFrescoBuilderBean;

    /** 创建FrescoImageLoader */
    public static FrescoImageLoader create(){
        FrescoImageLoader fresco = new FrescoImageLoader();
        fresco.mFrescoBuilderBean = new FrescoBuilderBean();
        if (ImageloaderManager.get().getBuilder().getPlaceholderResId() != 0){// 获取配置参数
            fresco.mFrescoBuilderBean.placeholderResId = ImageloaderManager.get().getBuilder().getPlaceholderResId();
        }
        if (ImageloaderManager.get().getBuilder().getErrorResId() != 0){
            fresco.mFrescoBuilderBean.errorResId = ImageloaderManager.get().getBuilder().getErrorResId();
        }
        if (ImageloaderManager.get().getBuilder().getRetryResId() != 0){
            fresco.mFrescoBuilderBean.retryResId = ImageloaderManager.get().getBuilder().getRetryResId();
        }

        return fresco;
    }

    @Override
    public ImageLoaderContract load(Uri uri) {
        mFrescoBuilderBean.uri = uri;
        return this;
    }

    @Override
    public ImageLoaderContract setPlaceholder(@DrawableRes int placeholderResId, ScalingUtils.ScaleType scaleType) {
        mFrescoBuilderBean.placeholderResId = placeholderResId;
        mFrescoBuilderBean.placeholderScaleType = scaleType;
        return this;
    }

    @Override
    public ImageLoaderContract setError(@DrawableRes int errorResId, ScalingUtils.ScaleType scaleType) {
        mFrescoBuilderBean.errorResId = errorResId;
        mFrescoBuilderBean.errorScaleType = scaleType;
        return this;
    }

    @Override
    public ImageLoaderContract serRetry(@DrawableRes int retryResId, ScalingUtils.ScaleType scaleType) {
        mFrescoBuilderBean.retryResId = retryResId;
        mFrescoBuilderBean.retryScaleType = scaleType;
        return this;
    }

    @Override
    public ImageLoaderContract setImageScaleType(ScalingUtils.ScaleType scaleType) {
        mFrescoBuilderBean.imageScaleType = scaleType;
        return this;
    }

    @Override
    public ImageLoaderContract setResizeOptions(int resizeWidth, int resizeHeight) {
        mFrescoBuilderBean.resizeWidth = resizeWidth;
        mFrescoBuilderBean.resizeHeight = resizeHeight;
        return this;
    }

    @Override
    public ImageLoaderContract setAspectRatio(float aspectRatio) {
        mFrescoBuilderBean.aspectRatio = aspectRatio;
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
    public ImageLoaderContract setProgressBarImage(@Nullable Drawable drawable) {
        mFrescoBuilderBean.progressBarDrawable = drawable;
        return this;
    }

    @Override
    public ImageLoaderContract setBackgroundImage(@Nullable Drawable drawable) {
        mFrescoBuilderBean.backgroundDrawable = drawable;
        return this;
    }

    @Override
    public ImageLoaderContract setOverlayImage(@Nullable Drawable drawable) {
        mFrescoBuilderBean.overlayDrawable = drawable;
        return this;
    }

    @Override
    public ImageLoaderContract setFadeDuration(int durationMs) {
        mFrescoBuilderBean.fadeDuration = durationMs;
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
    public ImageLoaderContract setBorder(@ColorInt int borderColor, float borderWidth) {
        mFrescoBuilderBean.roundCornerBorderColor = borderColor;
        mFrescoBuilderBean.roundCornerBorderWidth = borderWidth;
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
    public ImageLoaderContract setLocalThumbnailPreviews(boolean isEnable) {
        mFrescoBuilderBean.localThumbnailPreviews = isEnable;
        return this;
    }

    @Override
    public ImageLoaderContract setControllerListener(ControllerListener controllerListener) {
        mFrescoBuilderBean.controllerListener = controllerListener;
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

        if (bean.width > 0 || bean.height > 0){//设置图片宽高
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
     * @param simpleDraweeView 控件
     * @param bean 构建类
     */
    private DraweeController getDraweeController(SimpleDraweeView simpleDraweeView, FrescoBuilderBean bean) {
        return Fresco.newDraweeControllerBuilder()
                .setOldController(simpleDraweeView.getController())
                .setImageRequest(getImageRequest(bean))
                .setTapToRetryEnabled(ImageloaderManager.get().getBuilder().isTapToRetryEnabled()) // 开启重试功能
                .setAutoPlayAnimations(ImageloaderManager.get().getBuilder().isAutoPlayAnimations()) // 自动播放gif动画
                .setControllerListener(bean.controllerListener)
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
}
