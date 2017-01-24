package com.snxun.component.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.snxun.component.R;
import com.snxun.component.widget.base.ErrorLayout;
import com.snxun.component.widget.base.LoadingLayout;
import com.snxun.component.widget.base.NoDataLayout;
import com.snxun.component.widget.base.TitleBarLayout;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

/**
 * 基类Activity
 * Created by zhouL on 2016/11/17.
 */
public abstract class BaseActivity extends RxAppCompatActivity {

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(BaseApplication.get() != null){
            BaseApplication.get().registerActivity(this);
        }
        startCreate();
        setContentView(R.layout.activity_base_layout);
        initViews();
        setContainerView();
        findViews(savedInstanceState);
        setListeners();
        initData();
        endCreate();
    }

    /** 初始化基类的view */
    private void initViews() {
        mTitleBarLayout = (TitleBarLayout) findViewById(R.id.title_bar_layout);
        mContentLayout = (LinearLayout) findViewById(R.id.content_layout);
        mLoadingLayout = (LoadingLayout) findViewById(R.id.loading_layout);
        mNoDataLayout = (NoDataLayout) findViewById(R.id.no_data_layout);
        mErrorLayout = (ErrorLayout) findViewById(R.id.error_layout);
        showStatusLoading();
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

    protected void startCreate() {}

    protected abstract int getLayoutId();

    protected abstract void findViews(Bundle savedInstanceState);

    protected void setListeners(){
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

    protected void clickBackBtn() {}

    protected void clickReload() {}

    protected void initData() {}

    protected void endCreate() {}

    protected Context getContext(){
        return this;
    }

    @Override
    public void finish() {
        if(BaseApplication.get() != null){
            BaseApplication.get().removeActivity(this);
        }
        super.finish();
    }

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

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager() != null){
            List<Fragment> list = getSupportFragmentManager().getFragments();// 获取activity下的fragment
            if (list != null && list.size() > 0){
                for (Fragment fragment : list) {
                    if (isFragmentConsumeBackPressed(fragment)){
                        return;
                    }
                }
            }
        }
        if (!onPressBack()){
            super.onBackPressed();
        }
    }

    /**
     * 该fragment是否消耗了返回按钮事件
     * @param fragment fragment
     */
    private boolean isFragmentConsumeBackPressed(Fragment fragment){
        if (fragment == null){
            return false;
        }

        // fragment底下还有子fragment
        if (fragment.getChildFragmentManager() != null && fragment.getChildFragmentManager().getFragments() != null
                && fragment.getChildFragmentManager().getFragments().size() > 0){
            List<Fragment> list = fragment.getChildFragmentManager().getFragments();
            for (Fragment f : list) {
                if (isFragmentConsumeBackPressed(f)){
                    return true;
                }
            }
        }

        // fragment底下没有子fragment或子fragment没有消费事件 则判断自己
        if (fragment.getUserVisibleHint() && fragment.isVisible() && fragment instanceof IFragmentBackPressed){
            if (fragment.getParentFragment() != null){
                // 如果子fragment的父fragment没有显示，则不询问该fragment的返回事件（避免受预先初始化却没有展示到前端的fragment的影响）
                if (!fragment.getParentFragment().getUserVisibleHint()){
                    return false;
                }
            }
            IFragmentBackPressed itf = (IFragmentBackPressed) fragment;
            return itf.onPressBack();// fragment是否消耗返回按钮事件
        }
        return false;
    }

    /** 用户点击返回按钮 */
    protected boolean onPressBack(){
        return false;
    }
}
