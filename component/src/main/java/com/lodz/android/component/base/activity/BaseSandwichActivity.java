package com.lodz.android.component.base.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lodz.android.component.R;
import com.lodz.android.component.widget.base.ErrorLayout;
import com.lodz.android.component.widget.base.LoadingLayout;
import com.lodz.android.component.widget.base.NoDataLayout;

import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * 基类Activity（带基础状态控件、中部刷新控件和顶部/底部扩展）
 * Created by zhouL on 2018/4/16.
 */
public abstract class BaseSandwichActivity extends AbsActivity{

    private ViewStub mLoadingViewStub;
    private ViewStub mNoDataViewStub;
    private ViewStub mErrorViewStub;

    /** 顶部布局 */
    private FrameLayout mTopLayout;
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
    /** 底部布局 */
    private FrameLayout mBottomLayout;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.component_activity_base_sandwich_layout;
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        initViews();
        showStatusLoading();
        setTopView();
        setBottomView();
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
        setSwipeRefreshEnabled(false);
    }

    /** 初始化基类的view */
    private void initViews() {
        mTopLayout = findViewById(R.id.top_layout);
        mContentLayout = findViewById(R.id.content_layout);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mLoadingViewStub = findViewById(R.id.view_stub_loading_layout);
        mNoDataViewStub = findViewById(R.id.view_stub_no_data_layout);
        mErrorViewStub = findViewById(R.id.view_stub_error_layout);
        mBottomLayout = findViewById(R.id.bottom_layout);
    }

    /** 把顶部布局设置进来 */
    private void setTopView() {
        if (getTopLayoutId() == 0){
            mTopLayout.setVisibility(View.GONE);
            return;
        }
        View view = LayoutInflater.from(this).inflate(getTopLayoutId(), null);
        ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTopLayout.addView(view, layoutParams);
        mTopLayout.setVisibility(View.VISIBLE);
    }

    @LayoutRes
    protected int getTopLayoutId(){
        return 0;
    }

    /** 把底部布局设置进来 */
    private void setBottomView() {
        if (getBottomLayoutId() == 0){
            mBottomLayout.setVisibility(View.GONE);
            return;
        }
        View view = LayoutInflater.from(this).inflate(getBottomLayoutId(), null);
        ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mBottomLayout.addView(view, layoutParams);
        mBottomLayout.setVisibility(View.VISIBLE);
    }

    @LayoutRes
    protected int getBottomLayoutId(){
        return 0;
    }

    /** 把内容布局设置进来 */
    private void setContainerView() {
        if (getLayoutId() == 0){
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

        // 下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onDataRefresh();
            }
        });
    }

    /** 下拉刷新 */
    protected void onDataRefresh(){}

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

    /** 点击错误页面的重试按钮 */
    protected void clickReload() {}

    /** 显示无数据页面 */
    protected void showStatusNoData() {
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        mContentLayout.setVisibility(View.GONE);
        getNoDataLayout().setVisibility(View.VISIBLE);
        if (mLoadingLayout != null){
            mLoadingLayout.setVisibility(View.GONE);
        }
        if (mErrorLayout != null){
            mErrorLayout.setVisibility(View.GONE);
        }
    }

    /** 显示错误页面 */
    protected void showStatusError() {
        mSwipeRefreshLayout.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.GONE);
        if (mNoDataLayout != null){
            mNoDataLayout.setVisibility(View.GONE);
        }
        if (mLoadingLayout != null){
            mLoadingLayout.setVisibility(View.GONE);
        }
        getErrorLayout().setVisibility(View.VISIBLE);
    }

    /** 显示加载页面 */
    protected void showStatusLoading() {
        mSwipeRefreshLayout.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.GONE);
        if (mNoDataLayout != null){
            mNoDataLayout.setVisibility(View.GONE);
        }
        getLoadingLayout().setVisibility(View.VISIBLE);
        if (mErrorLayout != null){
            mErrorLayout.setVisibility(View.GONE);
        }
    }

    /** 显示内容页面 */
    protected void showStatusCompleted() {
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        mContentLayout.setVisibility(View.VISIBLE);
        if (mNoDataLayout != null){
            mNoDataLayout.setVisibility(View.GONE);
        }
        if (mLoadingLayout != null){
            mLoadingLayout.setVisibility(View.GONE);
        }
        if (mErrorLayout != null){
            mErrorLayout.setVisibility(View.GONE);
        }
    }

    /** 获取加载控件 */
    protected LoadingLayout getLoadingLayout(){
        if (mLoadingLayout == null){
            mLoadingLayout = (LoadingLayout) mLoadingViewStub.inflate();
            mLoadingLayout.setVisibility(View.GONE);
        }
        return mLoadingLayout;
    }

    /** 获取无数据控件 */
    protected NoDataLayout getNoDataLayout(){
        if (mNoDataLayout == null){
            mNoDataLayout = (NoDataLayout) mNoDataViewStub.inflate();
            mNoDataLayout.setVisibility(View.GONE);
        }
        return mNoDataLayout;
    }

    /** 获取加载失败界面 */
    protected ErrorLayout getErrorLayout(){
        if (mErrorLayout == null){
            mErrorLayout = (ErrorLayout) mErrorViewStub.inflate();
            mErrorLayout.setVisibility(View.GONE);
            mErrorLayout.setReloadListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickReload();
                }
            });
        }
        return mErrorLayout;
    }

    /** 获取顶部布局 */
    protected View getTopView(){
        return mTopLayout;
    }

    /** 获取底部布局 */
    protected View getBottomView(){
        return mBottomLayout;
    }
}
