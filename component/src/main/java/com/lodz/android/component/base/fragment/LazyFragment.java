package com.lodz.android.component.base.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * 懒加载的fragment
 * Created by zhouL on 2017/3/1.
 */

public abstract class LazyFragment extends RxFragment implements IFragmentBackPressed{

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
        return inflater.inflate(getAbsLayoutId(), container, false);
    }

    @LayoutRes
    protected abstract int getAbsLayoutId();

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
        if (!isVisibleToUser && isLoadComplete){
            onFragmentPause();
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
        if (!isLazyLoad || getUserVisibleHint()){// 不使用懒加载 || fragment可见
            init(view, savedInstanceState);
            isLoadComplete = true;
            onFragmentResume();
        }
        isFirstCreate = false;
    }

    private void init(View view, @Nullable Bundle savedInstanceState){
        startCreate();
        beforeFindViews(view);
        findViews(view, savedInstanceState);
        setListeners(view);
        initData(view);
        endCreate();
    }

    protected void startCreate() {}

    protected void beforeFindViews(View view) {}

    protected abstract void findViews(View view, Bundle savedInstanceState);

    protected void initData(View view) {}

    protected void endCreate() {}

    protected void setListeners(View view){}

    /** 配置是否使用懒加载（默认使用，可重写） */
    protected boolean configIsLazyLoad(){
        return true;
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

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint() && isLoadComplete){
            onFragmentPause();
        }
    }

    /** FragmentPause时调用，与activity生命周期保持一致 */
    protected void onFragmentPause(){}

    @Override
    public boolean onPressBack() {
        return false;
    }
}
