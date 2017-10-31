package com.lodz.android.component.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.lodz.android.component.base.application.BaseApplication;
import com.lodz.android.component.base.fragment.IFragmentBackPressed;
import com.lodz.android.component.base.fragment.LazyFragment;
import com.lodz.android.component.event.ActivityFinishEvent;
import com.lodz.android.core.utils.ReflectUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 底层抽象Activity（已经订阅了EventBus）
 * Created by zhouL on 2017/2/23.
 */
public abstract class AbsActivity extends RxAppCompatActivity {

    private static final String SAVE_INSTANCE_STATE_BUNDLE = "application_data_save";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        startCreate();
        setContentView(getAbsLayoutId());
        afterSetContentView();
        findViews(savedInstanceState);
        setListeners();
        initData();
        endCreate();
    }

    protected void startCreate() {}

    @LayoutRes
    protected abstract int getAbsLayoutId();

    protected void afterSetContentView() {}

    protected abstract void findViews(Bundle savedInstanceState);

    protected void setListeners(){}

    protected void initData() {}

    protected void endCreate() {}

    protected Context getContext(){
        return this;
    }

    @Override
    public void finish() {
        EventBus.getDefault().unregister(this);
        super.finish();
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
            if (fragment instanceof LazyFragment){
                LazyFragment lazyFragment = (LazyFragment) fragment;
                Class<?> c = ReflectUtils.getClassForName("com.lodz.android.component.base.fragment.LazyFragment");
                if (c != null){
                    Object o = ReflectUtils.getFieldValue(c, lazyFragment, "isAlreadyPause");// 通过反射取到该fragment是否已经进入暂停状态
                    if (o != null && o instanceof Boolean){
                        Boolean isAlreadyPause = (Boolean) o;
                        if (isAlreadyPause){
                            return false;
                        }
                    }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ActivityFinishEvent event) {
        if (!isFinishing()){
            finish();
        }
    }

    /** 当APP处于后台被系统回收时回调 */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (BaseApplication.get() == null) {
            return;
        }
        Bundle bundle = BaseApplication.get().getSaveInstanceState();
        if (outState != null && bundle != null) {
            outState.putBundle(SAVE_INSTANCE_STATE_BUNDLE, bundle);
        }
        super.onSaveInstanceState(outState);
    }

    /** 被回收后从后台回到前台调用 */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (BaseApplication.get() == null) {
            return;
        }
        if (savedInstanceState != null) {
            BaseApplication.get().getRestoreInstanceState(savedInstanceState.getBundle(SAVE_INSTANCE_STATE_BUNDLE));
        }
    }

    /**
     * 添加fragment
     * @param containerViewId 父控件id
     * @param fragment fragment
     * @param tag fragment的标签
     */
    protected void addFragment(@IdRes int containerViewId, Fragment fragment, @Nullable String tag){
        getSupportFragmentManager().beginTransaction().add(containerViewId, fragment, tag).commit();
    }

    /**
     * 替换fragment
     * @param containerViewId 父控件id
     * @param fragment fragment
     * @param tag fragment的标签
     */
    protected void replaceFragment(@IdRes int containerViewId, Fragment fragment, @Nullable String tag){
        getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment, tag).commit();
    }

    /**
     * 显示fragment
     * @param fragment fragment
     */
    protected void showFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().show(fragment).commit();
    }

    /**
     * 隐藏fragment
     * @param fragment fragment
     */
    protected void hideFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().hide(fragment).commit();
    }

    /** 获取所有的fragment */
    protected List<Fragment> getFragments(){
        return getSupportFragmentManager().getFragments();
    }

    /**
     * 根据标签找到对应的fragment
     * @param tag fragment的标签
     */
    protected Fragment findFragmentByTag(String tag){
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    /**
     * 根据id找到对应的fragment
     * @param id id
     */
    protected Fragment findFragmentById(@IdRes int id){
        return getSupportFragmentManager().findFragmentById(id);
    }

    /**
     * 添加到回退堆栈
     * @param name 回退堆栈的名称，可为null
     */
    protected void addToBackStack(String name){
        getSupportFragmentManager().beginTransaction().addToBackStack(name).commit();
    }

}
