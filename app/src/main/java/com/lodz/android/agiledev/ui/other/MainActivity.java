package com.lodz.android.agiledev.ui.other;

import android.os.Bundle;

import com.lodz.android.agiledev.App;
import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.bean.PersonBean;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.core.cache.ACacheUtils;
import com.lodz.android.core.log.PrintLog;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        showFresco();
//        showGlide();
    }

    private void showGlide() {
//        ImageView imageView = (ImageView) findViewById(R.id.img_view);
//        ImageLoader.create(this)
//                .load("http://ww2.sinaimg.cn/large/610dc034jw1f91ypzqaivj20u00k0jui.jpg")
//                .setPlaceholder(R.drawable.ic_launcher)
//                .setError(R.drawable.ic_launcher)
//                .setImageSize(100, 100)
//                .useCircle()
//                .useBlur()
//                .setBlurRadius(10)
//                .useRoundCorner()
//                .setRoundCorner(10)
//                .joinGlide()
//                .diskCacheStrategy(GlideBuilderBean.DiskCacheStrategy.SOURCE)//设置磁盘缓存方式
//                .skipMemoryCache()// 跳过图片缓存入内存
//                .setCenterCrop()// 设置居中裁切
//                .setFitCenter()// 设置居中自适应
//                .useAnimate()// 设置使用动画
//                .userCrossFade()// 使用默认渐变效果
//                .setAnimResId(R.anim.anim_bottom_in)// 设置动画资源id
//                .setAnim(new ViewPropertyAnimation.Animator() {// 设置动画
//                    @Override
//                    public void animate(View view) {
//
//                    }
//                })
//                .useFilterColor()// 使用覆盖颜色
//                .setFilterColor(ContextCompat.getColor(getContext(), R.color.color_60ea413c))// 设置覆盖颜色
//                .setRoundedCornersMargin(5)// 设置圆角图片的Margin
//                .setRoundedCornerType(RoundedCornersTransformation.CornerType.LEFT)// 设置圆角图片的位置参数
//                .useGrayscale()// 使用灰度化
//                .useCropSquare()// 切正方形图
//                .useMask()// 使用蒙板图片
//                .setMaskResId(R.drawable.mask_starfish)// 设置蒙板图片资源id
//                .into(imageView);

    }

    private void showFresco() {
//        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) findViewById(R.id.drawee_view);
//        SimpleDraweeView draweeView2 = (SimpleDraweeView) findViewById(R.id.drawee_view_2);
////        String url = "http://ww1.sinaimg.cn/large/610dc034jw1f8kmud15q1j20u011hdjg.jpg";// 纵向长方形
////        String url = "http://ww2.sinaimg.cn/large/610dc034jw1f978bh1cerj20u00u0767.jpg";// 矩形
//        String url = "http://ww2.sinaimg.cn/large/610dc034jw1f91ypzqaivj20u00k0jui.jpg";// 横向长方形
////        ImageLoader.create(this)
////                .load(UriUtils.parseUrl("http://ww2.sinaimg.cn/large/610dc034jw1f91ypzqaivj20u00k0jui.jpg"))// 设置加载路径，请使用UriUtils将图片地址或资源id转为uri
////                .setPlaceholder(R.drawable.ic_launcher)// 设置加载图
////                .setError(R.drawable.ic_launcher)// 设置加载失败图
////                .setImageSize(100, 100)// 设置图片宽高
////                .useCircle()// 使用圆形图片
////                .useBlur()// 使用高斯模糊
////                .setBlurRadius(10)// 设置高斯模糊
////                .useRoundCorner()// 使用圆角
////                .setRoundCorner(10)// 设置圆角
////                .joinFresco()// 进入Fresco特性
////                .setPlaceholderScaleType(ScalingUtils.ScaleType.CENTER)// 设置加载图的缩放类型
////                .setErrorScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)// 设置加载失败图的缩放类型
////                .serRetry(R.drawable.ic_launcher)// 设置重试图片
////                .serRetryScaleType(ScalingUtils.ScaleType.CENTER_CROP)// 设置重试图片的缩放类型
////                .setImageScaleType(ScalingUtils.ScaleType.CENTER)// 设置图片的缩放类型
////                .setResizeOptions(100, 100)// 设置图片在内存的大小，类似分辨率，如果已经设置了setImageSize()方法则不需要再重复设置该方法
////                .setAspectRatio(1.2f)// 设置固定宽高比(w/h)
////                .setProgressBarImage(ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher))// 设置加载进度图
////                .setBackgroundImage(ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher))// 设置背景图
////                .setOverlayImage(ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher))// 设置叠加图
////                .setFadeDuration(1200)// 设置淡入展示的时间
////                .setBorder(Color.GREEN, 5)// 设置描边
////                .setLocalThumbnailPreviews(true)// 加载本地图片时使用缩略图预览（多图预览时建议开启，否则会导致卡顿）
////                .setControllerListener(new BaseControllerListener())// 设置控制监听器
////                .wrapImageHeight(200)// 指定宽度，高度自适应图片
////                .wrapImageWidth(120)// 指定高度，宽度自适应图片
////                .wrapImage()// 宽、高都自适应图片，宽 >= 高：图片宽超过屏幕时，以屏幕的宽作为宽度来换算高度，宽 < 高：图片高超过屏幕时，以屏幕的高作为高度来换算宽度
////                .into(simpleDraweeView);
//
//
//        ImageLoader.create(this)
//                .load(UriUtils.parseUrl(url))
//                .joinFresco()
//                .setImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)
//                .wrapImageHeight(DensityUtils.dp2px(getContext(), 200))
//                .into(draweeView2);
    }

    @Override
    protected void initData() {
        super.initData();
        testCache();
        showStatusCompleted();
    }

    private void testCache() {
        ACacheUtils.get().create().put("a", "8");
        PersonBean personBean = new PersonBean();
        personBean.name = "Jack";
        personBean.age = 55;
        personBean.isMan = true;
        ACacheUtils.get().create().put("personBean", personBean);
    }

    @Override
    protected boolean onPressBack() {
        App.get().exit();
        return true;
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        PrintLog.d("testtag", ACacheUtils.get().create().getAsString("a"));
        PersonBean personBean = (PersonBean) ACacheUtils.get().create().getAsObject("personBean");
        PrintLog.e("testtag", personBean.toString());
    }
}
