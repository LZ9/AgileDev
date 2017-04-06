package com.lodz.android.agiledev.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.premissions.PremissionsActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.ToastUtils;

/**
 * 标题测试activity
 * Created by zhouL on 2017/2/23.
 */
public class TitleBarTestActivity extends BaseActivity{

    @Override
    protected int getLayoutId() {
        return R.layout.activity_premissions_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        initTitleBar(getTitleBarLayout());
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        findViewById(R.id.btn_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PremissionsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }

    private void initTitleBar(TitleBarLayout titleBarLayout) {
        titleBarLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_ff4081));

        titleBarLayout.setTitleName("决定是你啦杰尼龟");
        titleBarLayout.setTitleTextColor(android.R.color.holo_blue_light);
        titleBarLayout.setTitleTextSize(20);

        titleBarLayout.replaceBackBtn(getBackBtnView());
        titleBarLayout.setBackBtnName("返回");
        titleBarLayout.setBackBtnTextSize(15);
        titleBarLayout.setBackBtnTextColor(android.R.color.holo_blue_light);


        titleBarLayout.addExpandView(getExpandView());

        titleBarLayout.setDivideLineColor(R.color.color_d28928);
        titleBarLayout.setDivideLineHeight(10);
        titleBarLayout.goneDivideLine();
    }



    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        ToastUtils.showShort(getContext(), "点击返回按钮");
    }

    private View getExpandView() {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(layoutParams);
        imageView.setPadding(10, 5, 10, 5);
        imageView.setImageResource(R.drawable.ic_launcher);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return imageView;
    }

    private View getBackBtnView() {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(layoutParams);
        imageView.setPadding(10, 5, 10, 5);
        imageView.setImageResource(R.drawable.ic_progress_loading_64_8);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return imageView;
    }
}
