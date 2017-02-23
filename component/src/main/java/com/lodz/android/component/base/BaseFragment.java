package com.lodz.android.component.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lodz.android.component.R;
import com.lodz.android.component.widget.base.ErrorLayout;
import com.lodz.android.component.widget.base.LoadingLayout;
import com.lodz.android.component.widget.base.NoDataLayout;
import com.trello.rxlifecycle2.components.support.RxFragment;


/**
 * 基类Fragment
 * Created by zhouL on 2016/11/18.
 */

public abstract class BaseFragment extends RxFragment implements IFragmentBackPressed{

    /** 内容布局 */
    private LinearLayout mContentLayout;
    /** 加载布局 */
    private LoadingLayout mLoadingLayout;
    /** 无数据布局 */
    private NoDataLayout mNoDataLayout;
    /** 错误布局 */
    private ErrorLayout mErrorLayout;

    /** 父控件布局 */
    private View mParentView;
    /** 是否使用懒加载 */
    private boolean isLazyLoad = true;
    /** 是否完成加载 */
    private boolean isLoadComplete = false;
    /** 是否首次启动 */
    private boolean isFirstCreate = true;
    /** 是否首次启动Resume */
    private boolean isFirstResume = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.component_fragment_base_layout, container, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isLazyLoad = configIsLazyLoad();
        if (isVisibleToUser && isLazyLoad && !isFirstCreate && !isLoadComplete){// fragment可见 && 启用懒加载 && 不是第一次启动 && 未加载完成
            init(mParentView, null);
            isLoadComplete = true;
        }
        if (isVisibleToUser && isLoadComplete){
            onFragmentResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLoadComplete = false;// fragment被回收时重置加载状态
        mParentView = null;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mParentView = view;
        initViews(view);
        if (!isLazyLoad || getUserVisibleHint()){// 不使用懒加载 || fragment可见
            init(view, savedInstanceState);
            isLoadComplete = true;
            onFragmentResume();
        }
        isFirstCreate = false;
    }

    private void init(View view, @Nullable Bundle savedInstanceState){
        startCreate();
        setContainerView();
        findViews(view, savedInstanceState);
        setListeners(view);
        initData(view);
        endCreate();
    }

    /**
     * 初始化基类的view
     * @param view
     */
    private void initViews(View view) {
        mContentLayout = (LinearLayout) view.findViewById(R.id.content_layout);
        mLoadingLayout = (LoadingLayout) view.findViewById(R.id.loading_layout);
        mNoDataLayout = (NoDataLayout) view.findViewById(R.id.no_data_layout);
        mErrorLayout = (ErrorLayout) view.findViewById(R.id.error_layout);
        showStatusLoading();
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

    protected void startCreate() {}

    protected abstract int getLayoutId();

    protected abstract void findViews(View view, Bundle savedInstanceState);

    protected void setListeners(View view){
        mErrorLayout.setReloadListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickReload();
            }
        });
    }

    protected void clickReload() {}

    protected void initData(View view) {}

    protected void endCreate() {}

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

    /** 配置是否实用懒加载（默认使用，可重写） */
    protected boolean configIsLazyLoad(){
        return true;
    }

    @Override
    public boolean onPressBack() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume){
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint() && isLoadComplete){
            onFragmentResume();
        }
    }

    /** FragmentResume时调用，与activity生命周期保持一致 */
    protected void onFragmentResume(){}
}
