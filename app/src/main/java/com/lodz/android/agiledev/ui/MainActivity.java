package com.lodz.android.agiledev.ui;

import android.os.Bundle;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.BaseActivity;
import com.lodz.android.core.utils.DensityUtils;
import com.lodz.android.imageloader.ImageLoader;
import com.lodz.android.imageloader.utils.UriUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        final SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.drawee_view);
        final SimpleDraweeView draweeView2 = (SimpleDraweeView) findViewById(R.id.drawee_view_2);
//        String url = "http://ww1.sinaimg.cn/large/610dc034jw1f8kmud15q1j20u011hdjg.jpg";// 纵向长方形
//        String url = "http://ww2.sinaimg.cn/large/610dc034jw1f978bh1cerj20u00u0767.jpg";// 矩形
        String url = "http://ww2.sinaimg.cn/large/610dc034jw1f91ypzqaivj20u00k0jui.jpg";// 横向长方形
        ImageLoader.create()
                .load(UriUtils.parseUrl(url))
                .setImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)
                .wrapImageWidth(DensityUtils.dp2px(getContext(), 200))
//                .wrapImage()
                .into(draweeView);

        ImageLoader.create()
                .load(UriUtils.parseUrl(url))
                .setImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)
                .wrapImageHeight(DensityUtils.dp2px(getContext(), 200))
                .into(draweeView2);
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }
}
