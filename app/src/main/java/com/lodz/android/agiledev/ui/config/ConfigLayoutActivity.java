package com.lodz.android.agiledev.ui.config;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.DensityUtils;

/**
 * 基础控件配置测试类
 * Created by zhouL on 2017/7/4.
 */

public class ConfigLayoutActivity extends BaseActivity{

    @Override
    protected int getLayoutId() {
        return R.layout.activity_config_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        initTitleBar(getTitleBarLayout());
    }


    /** 初始化标题栏 */
    private void initTitleBar(TitleBarLayout titleBarLayout) {
        titleBarLayout.setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
        titleBarLayout.needExpandView(true);
        titleBarLayout.addExpandView(getExpandView());
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void clickReload() {
        super.clickReload();
        showStatusLoading();
    }

    @Override
    protected void initData() {
        super.initData();
        // 可以在App的configBaseLayout方法对基础控件进行统一订制
        showStatusLoading();
    }

    /** 获取扩展view */
    private View getExpandView() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        final TextView loadingTv = getTextView(R.string.config_base_loading);//加载
        loadingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStatusLoading();
            }
        });
        linearLayout.addView(loadingTv, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final TextView noDataTv = getTextView(R.string.config_base_no_data);//无数据
        noDataTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStatusNoData();
            }
        });
        linearLayout.addView(noDataTv, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final TextView failTv = getTextView(R.string.config_base_fail);//失败
        failTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStatusError();
            }
        });
        linearLayout.addView(failTv, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        return linearLayout;
    }

    /** 获取TextView */
    private TextView getTextView(@StringRes int resId) {
        final TextView textView = new TextView(getContext());
        textView.setText(resId);
        textView.setPadding(DensityUtils.dp2px(getContext(), 2), 0 , DensityUtils.dp2px(getContext(), 2), 0);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        return textView;
    }
}

