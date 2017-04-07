package com.lodz.android.agiledev.ui;

import android.os.Bundle;
import android.widget.ImageView;

import com.lodz.android.agiledev.AgileDevApplication;
import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.imageloader.ImageLoader;
import com.lodz.android.imageloader.utils.UriUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        showFresco();
        showGlide();
    }

    private void showGlide() {
        ImageView imageView = (ImageView) findViewById(R.id.img_view);
        ImageLoader.create(this)
                .load(UriUtils.parseUrl("http://ww2.sinaimg.cn/large/610dc034jw1f91ypzqaivj20u00k0jui.jpg"))
                .joinGlide()
                .into(imageView);

    }

    private void showFresco() {
//        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.drawee_view);
//        SimpleDraweeView draweeView2 = (SimpleDraweeView) findViewById(R.id.drawee_view_2);
////        String url = "http://ww1.sinaimg.cn/large/610dc034jw1f8kmud15q1j20u011hdjg.jpg";// 纵向长方形
////        String url = "http://ww2.sinaimg.cn/large/610dc034jw1f978bh1cerj20u00u0767.jpg";// 矩形
//        String url = "http://ww2.sinaimg.cn/large/610dc034jw1f91ypzqaivj20u00k0jui.jpg";// 横向长方形
//        ImageLoader.create(this)
//                .load(UriUtils.parseUrl(url))
//                .useRoundCorner()
//                .setRoundCorner(20)
//                .joinFresco()
//                .setImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)
//                .wrapImageWidth(DensityUtils.dp2px(getContext(), 200))
////                .wrapImage()
//                .into(draweeView);
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
        showStatusCompleted();
    }

    @Override
    protected boolean onPressBack() {
        AgileDevApplication.get().exit();
        return true;
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        AgileDevApplication.get().exit();
    }
}
