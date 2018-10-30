package com.lodz.android.agiledev.ui.config;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.component.widget.MmsTabLayout;
import com.lodz.android.component.widget.base.ErrorLayout;
import com.lodz.android.component.widget.base.LoadingLayout;
import com.lodz.android.component.widget.base.NoDataLayout;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 基础控件配置测试类
 * Created by zhouL on 2017/7/4.
 */

public class ConfigLayoutActivity extends AbsActivity{

    /** 标题栏 */
    @BindView(R.id.title_bar_layout)
    TitleBarLayout mTitleBarLayout;
    /** TabLayout */
    @BindView(R.id.tab_layout)
    MmsTabLayout mTabLayout;
    /** 加载页 */
    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;
    /** 无数据页 */
    @BindView(R.id.no_data_layout)
    NoDataLayout mNoDataLayout;
    /** 失败页 */
    @BindView(R.id.error_layout)
    ErrorLayout mErrorLayout;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_config_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBar();
    }

    private void initTabLayout() {
        mTabLayout.addTab(mTabLayout.newTab().setText(getContext().getString(R.string.config_base_loading)), true);
        mTabLayout.addTab(mTabLayout.newTab().setText(getContext().getString(R.string.config_base_no_data)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getContext().getString(R.string.config_base_fail)));
    }


    /** 初始化标题栏 */
    private void initTitleBar() {
        mTitleBarLayout.setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        showLoading();
                        break;
                    case 1:
                        showNoData();
                        break;
                    case 2:
                        showError();
                        break;
                    default:
                        showLoading();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mTitleBarLayout.setOnBackBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleBarLayout.getExpandView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(getContext(), "测试");
            }
        });
    }

    private void showLoading() {
        mLoadingLayout.setVisibility(View.VISIBLE);
        mNoDataLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.GONE);
    }

    private void showNoData() {
        mLoadingLayout.setVisibility(View.GONE);
        mNoDataLayout.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.GONE);
    }

    private void showError() {
        mLoadingLayout.setVisibility(View.GONE);
        mNoDataLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        super.initData();
        initTabLayout();
    }
}

