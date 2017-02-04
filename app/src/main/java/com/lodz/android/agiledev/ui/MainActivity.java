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
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.drawee_view);
        ImageLoader.create()
                .load(UriUtils.parseUrl("http://ww2.sinaimg.cn/large/610dc034jw1f978bh1cerj20u00u0767.jpg"))
                .setImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setImageSize(DensityUtils.dp2px(getContext(), 200), DensityUtils.dp2px(getContext(), 200))
                .into(draweeView);
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }
}
