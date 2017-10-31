package com.lodz.android.component.base.fragment;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;

import com.lodz.android.component.R;
import com.lodz.android.component.widget.base.ErrorLayout;
import com.lodz.android.component.widget.base.LoadingLayout;
import com.lodz.android.component.widget.base.NoDataLayout;
import com.lodz.android.component.widget.base.TitleBarLayout;

/**
 * 基类Fragment（带基础状态控件）
 * Created by zhouL on 2016/11/18.
 */
public abstract class BaseFragment extends LazyFragment{

    private ViewStub mTitleBarViewStub;
    private ViewStub mLoadingViewStub;
    private ViewStub mNoDataViewStub;
    private ViewStub mErrorViewStub;

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
        return R.layout.component_fragment_base_layout;
    }

    @Override
    protected void beforeFindViews(View view) {
        super.beforeFindViews(view);
        initViews(view);
        showStatusLoading();
        setContainerView();
    }

    /** 初始化基础控件 */
    private void initViews(View view) {
        mContentLayout = view.findViewById(R.id.content_layout);
        mTitleBarViewStub = view.findViewById(R.id.view_stub_title_bar_layout);
        mLoadingViewStub = view.findViewById(R.id.view_stub_loading_layout);
        mNoDataViewStub = view.findViewById(R.id.view_stub_no_data_layout);
        mErrorViewStub = view.findViewById(R.id.view_stub_error_layout);
    }

    /** 把内容布局设置进来 */
    private void setContainerView() {
        if (getLayoutId() <= 0 || getContext() == null){
            showStatusNoData();
            return;
        }
        View view = LayoutInflater.from(getContext()).inflate(getLayoutId(), null);
        ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentLayout.addView(view, layoutParams);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    /** 点击标题栏的返回按钮 */
    protected void clickBackBtn() {}

    /** 点击重载 */
    protected void clickReload() {}

    /** 显示无数据页面 */
    protected void showStatusNoData() {
        mContentLayout.setVisibility(View.GONE);
        if (mLoadingLayout != null){
            mLoadingLayout.setVisibility(View.GONE);
        }
        if (mErrorLayout != null){
            mErrorLayout.setVisibility(View.GONE);
        }
        getNoDataLayout().setVisibility(View.VISIBLE);
    }

    /** 显示错误页面 */
    protected void showStatusError() {
        mContentLayout.setVisibility(View.GONE);
        if (mLoadingLayout != null){
            mLoadingLayout.setVisibility(View.GONE);
        }
        getErrorLayout().setVisibility(View.VISIBLE);
        if (mNoDataLayout != null){
            mNoDataLayout.setVisibility(View.GONE);
        }
    }

    /** 显示加载页面 */
    protected void showStatusLoading() {
        mContentLayout.setVisibility(View.GONE);
        getLoadingLayout().setVisibility(View.VISIBLE);
        if (mErrorLayout != null){
            mErrorLayout.setVisibility(View.GONE);
        }
        if (mNoDataLayout != null){
            mNoDataLayout.setVisibility(View.GONE);
        }
    }

    /** 显示内容页面 */
    protected void showStatusCompleted() {
        mContentLayout.setVisibility(View.VISIBLE);
        if (mLoadingLayout != null){
            mLoadingLayout.setVisibility(View.GONE);
        }
        if (mErrorLayout != null){
            mErrorLayout.setVisibility(View.GONE);
        }
        if (mNoDataLayout != null){
            mNoDataLayout.setVisibility(View.GONE);
        }
    }

    /** 隐藏TitleBar */
    protected void goneTitleBar(){
        getTitleBarLayout().setVisibility(View.GONE);
    }

    /** 显示TitleBar */
    protected void showTitleBar(){
        getTitleBarLayout().setVisibility(View.VISIBLE);
    }

    /** 获取顶部标题栏控件 */
    protected TitleBarLayout getTitleBarLayout(){
        if (mTitleBarLayout == null){
            mTitleBarLayout = (TitleBarLayout) mTitleBarViewStub.inflate();
            mTitleBarLayout.setVisibility(View.GONE);
            mTitleBarLayout.setOnBackBtnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickBackBtn();
                }
            });
        }
        return mTitleBarLayout;
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
}
