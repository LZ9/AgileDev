package com.lodz.android.agiledev.ui.image;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.AbsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Glide展示类
 * Created by zhouL on 2017/4/7.
 */

public class GlideActivity extends AbsActivity{

    @BindView(R.id.img_view)
    ImageView mImageView;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_glide_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        // 自定义通知栏
        findViewById(R.id.custom_notification_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 原生通知栏
        findViewById(R.id.notification_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 本地图片
        findViewById(R.id.local_image_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 网络图片
        findViewById(R.id.url_image_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 网络gif
        findViewById(R.id.url_gif_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 动画显示
        findViewById(R.id.anim_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 毛玻璃
        findViewById(R.id.url_blur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 覆盖颜色
        findViewById(R.id.url_filter_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 圆角
        findViewById(R.id.url_rounded_corners).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





    }
}
