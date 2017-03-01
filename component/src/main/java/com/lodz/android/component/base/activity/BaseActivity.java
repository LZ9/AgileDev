package com.lodz.android.component.base.activity;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lodz.android.component.R;
import com.lodz.android.component.widget.base.ErrorLayout;
import com.lodz.android.component.widget.base.LoadingLayout;
import com.lodz.android.component.widget.base.NoDataLayout;
import com.lodz.android.component.widget.base.TitleBarLayout;

/**
 * 基类Activity（带基础状态控件）
 * Created by zhouL on 2016/11/17.
 */
public abstract class BaseActivity extends AbsActivity {

    /** 顶部标题布局 */
    private TitleBarLayout mTitleBarLayout;
    /** 内容布局 */
    private LinearLayout mContentLayout;
    /** 加载布局 */
    private LoadingLayout mLoadingLayout;
    /** 无数据布局 */
    private NoDataLayout mNoDataLayout;
    /** 错误布局 */
    private ErrorLayout mErrorLayout;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.component_activity_base_layout;
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        initViews();
        showStatusLoading();
        setContainerView();
    }

    /** 初始化基类的view */
    private void initViews() {
        mTitleBarLayout = (TitleBarLayout) findViewById(R.id.title_bar_layout);
        mContentLayout = (LinearLayout) findViewById(R.id.content_layout);
        mLoadingLayout = (LoadingLayout) findViewById(R.id.loading_layout);
        mNoDataLayout = (NoDataLayout) findViewById(R.id.no_data_layout);
        mErrorLayout = (ErrorLayout) findViewById(R.id.error_layout);
    }

    /** 把内容布局设置进来 */
    private void setContainerView() {
        if (getLayoutId() <= 0){
            showStatusNoData();
            return;
        }
        View view = LayoutInflater.from(this).inflate(getLayoutId(), null);
        ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentLayout.addView(view, layoutParams);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    protected void setListeners() {
        super.setListeners();
        mTitleBarLayout.setOnBackBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBackBtn();
            }
        });

        mErrorLayout.setReloadListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickReload();
            }
        });
    }

    /** 点击标题栏的返回按钮 */
    protected void clickBackBtn() {}

    /** 点击错误页面的重试按钮 */
    protected void clickReload() {}

    /** 显示无数据页面 */
    protected void showStatusNoData() {
        mContentLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.GONE);
        mNoDataLayout.setVisibility(View.VISIBLE);
    }

    /** 显示错误页面 */
    protected void showStatusError() {
        mContentLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);
        mNoDataLayout.setVisibility(View.GONE);
    }

    /** 显示加载页面 */
    protected void showStatusLoading() {
        mContentLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.GONE);
        mNoDataLayout.setVisibility(View.GONE);
    }

    /** 显示内容页面 */
    protected void showStatusCompleted() {
        mContentLayout.setVisibility(View.VISIBLE);
        mLoadingLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.GONE);
        mNoDataLayout.setVisibility(View.GONE);
    }

    /** 隐藏TitleBar */
    protected void goneTitleBar(){
        mTitleBarLayout.setVisibility(View.GONE);
    }

    /** 获取顶部标题栏控件 */
    protected TitleBarLayout getTitleBarLayout(){
        return mTitleBarLayout;
    }

    /** 获取加载控件 */
    protected LoadingLayout getLoadingLayout(){
        return mLoadingLayout;
    }

    /** 获取无数据控件 */
    protected NoDataLayout getNoDataLayout(){
        return mNoDataLayout;
    }

    /** 获取加载失败界面 */
    protected ErrorLayout getErrorLayout(){
        return mErrorLayout;
    }
}
