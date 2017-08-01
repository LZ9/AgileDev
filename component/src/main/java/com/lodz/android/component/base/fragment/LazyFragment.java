package com.lodz.android.component.base.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

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
    /** 当前的fragment是否已经暂停 */
    private boolean isAlreadyPause = false;
    /** 是否从OnPause离开 */
    private boolean isOnPauseOut = false;


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
        boolean isInit = false;
        if (isVisibleToUser && isLazyLoad && !isFirstCreate && !isLoadComplete){// fragment可见 && 启用懒加载 && 不是第一次启动 && 未加载完成
            init(mParentView, null);
            isLoadComplete = true;
            isInit = true;
        }
        if (isVisibleToUser && isLoadComplete){
            onFragmentResume();
            if (!isInit){
                notifyChildResume(this);
            }
        }
        if (!isVisibleToUser && isLoadComplete){
            onFragmentPause();
            notifyChildPause(this);
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
        if (getUserVisibleHint() && isLoadComplete){//自己显示 && 已经加载
            if (getParentFragment() != null && !getParentFragment().getUserVisibleHint()){// 父类不显示
                return;
            }
            if (isOnPauseOut){//自己是从OnPause回来的
                onFragmentResume();
                isOnPauseOut = false;
            }
        }
    }

    /** FragmentResume时调用，与activity生命周期保持一致 */
    protected void onFragmentResume(){
        isAlreadyPause = false;
    }

    /** 通知内部显示着的fragment显示 */
    private void notifyChildResume(Fragment fragment) {
        if (fragment == null){
            return;
        }
        if (fragment.getChildFragmentManager() == null){
            return;
        }
        List<Fragment> fragments = fragment.getChildFragmentManager().getFragments();//获取fragment底下的其他fragment
        if (fragments == null || fragments.size() == 0){
            return;
        }
        for (Fragment f : fragments) {
            if (f.getParentFragment() != null && !f.getParentFragment().getUserVisibleHint()){//它的父类没有显示
                continue;
            }
            if (f.getUserVisibleHint() && f instanceof LazyFragment){// 父类显示自己也显示
                LazyFragment lazyFragment = (LazyFragment) f;
                lazyFragment.onFragmentResume();
            }
            notifyChildResume(f);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint() && isLoadComplete && !isAlreadyPause){// 自己显示 && 已加载完成 && 未调用过pause方法
            if (getParentFragment() != null && !getParentFragment().getUserVisibleHint()){//父类没有显示
                return;
            }
            if (getParentFragment() != null && getParentFragment() instanceof LazyFragment){
                LazyFragment lazyFragment = (LazyFragment) getParentFragment();
                if (lazyFragment.isAlreadyPause){// 父类已经暂停了
                    return;
                }
            }
            isOnPauseOut = true;
            onFragmentPause();
        }
    }

    /** FragmentPause时调用，与activity生命周期保持一致 */
    protected void onFragmentPause(){
        isAlreadyPause = true;
    }

    /** 通知内部显示着的fragment暂停 */
    private void notifyChildPause(Fragment fragment) {
        if (fragment == null){
            return;
        }
        if (fragment.getChildFragmentManager() == null){
            return;
        }
        List<Fragment> fragments = fragment.getChildFragmentManager().getFragments();//获取fragment底下的其他fragment
        if (fragments == null || fragments.size() == 0){
            return;
        }
        for (Fragment f : fragments) {
            if (f.getUserVisibleHint() && f instanceof LazyFragment){
                LazyFragment lazyFragment = (LazyFragment) f;
                if (!lazyFragment.isAlreadyPause){//子类的fragment没有暂停过
                    lazyFragment.onFragmentPause();
                }
            }
            notifyChildPause(f);
        }
    }

    @Override
    public boolean onPressBack() {
        return false;
    }
}
