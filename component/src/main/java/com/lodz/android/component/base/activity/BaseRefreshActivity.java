package com.lodz.android.component.base.activity;

import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
 * 基类Activity（带基础状态控件和下来刷新控件）
 * Created by zhouL on 2017/2/28.
 */
public abstract class BaseRefreshActivity extends AbsActivity{

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
    /** 下拉刷新 */
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.component_activity_base_refresh_layout;
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        initViews();
        setContainerView();
        initSwipeRefreshLayout();
    }

    /** 初始化下拉刷新配置 */
    private void initSwipeRefreshLayout() {
        // 设置下拉进度的切换颜色
        setSwipeRefreshColorScheme(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // 设置下拉进度的背景颜色
        setSwipeRefreshBackgroundColor(android.R.color.white);
    }

    /** 初始化基类的view */
    private void initViews() {
        mTitleBarLayout = (TitleBarLayout) findViewById(R.id.title_bar_layout);
        mContentLayout = (LinearLayout) findViewById(R.id.content_layout);
        mLoadingLayout = (LoadingLayout) findViewById(R.id.loading_layout);
        mNoDataLayout = (NoDataLayout) findViewById(R.id.no_data_layout);
        mErrorLayout = (ErrorLayout) findViewById(R.id.error_layout);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
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

        // 下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onDataRefresh();
            }
        });
    }

    /** 下拉刷新 */
    protected abstract void onDataRefresh();

    @Override
    protected void initData() {
        super.initData();
        showStatusLoading();
    }

    /**
     * 设置下拉进度的切换颜色
     * @param colorResIds 颜色资源id
     */
    protected void setSwipeRefreshColorScheme(@ColorRes int... colorResIds){
        mSwipeRefreshLayout.setColorSchemeResources(colorResIds);
    }

    /**
     * 设置下拉进度的背景颜色
     * @param colorResId 颜色资源id
     */
    protected void setSwipeRefreshBackgroundColor(@ColorRes int colorResId){
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), colorResId));
    }

    /** 设置刷新结束（隐藏刷新进度条） */
    protected void setSwipeRefreshFinish(){
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 设置刷新控件是否启用
     * @param enabled 是否启用
     */
    protected void setSwipeRefreshEnabled(boolean enabled){
        mSwipeRefreshLayout.setEnabled(enabled);
    }

    /** 点击标题栏的返回按钮 */
    protected void clickBackBtn() {}

    /** 点击错误页面的重试按钮 */
    protected void clickReload() {}

    /** 显示无数据页面 */
    protected void showStatusNoData() {
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        mContentLayout.setVisibility(View.GONE);
        mNoDataLayout.setVisibility(View.VISIBLE);
        mLoadingLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.GONE);
    }

    /** 显示错误页面 */
    protected void showStatusError() {
        mSwipeRefreshLayout.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.GONE);
        mNoDataLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    /** 显示加载页面 */
    protected void showStatusLoading() {
        mSwipeRefreshLayout.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.GONE);
        mNoDataLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.GONE);
    }

    /** 显示内容页面 */
    protected void showStatusCompleted() {
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        mContentLayout.setVisibility(View.VISIBLE);
        mNoDataLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.GONE);
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
