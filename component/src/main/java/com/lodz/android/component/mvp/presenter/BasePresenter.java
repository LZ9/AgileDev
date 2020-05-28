package com.lodz.android.component.mvp.presenter;

import android.content.Context;

import com.lodz.android.component.mvp.contract.abs.PresenterContract;
import com.lodz.android.component.mvp.contract.abs.ViewContract;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.android.ActivityEvent;
import com.trello.rxlifecycle4.android.FragmentEvent;

import androidx.annotation.NonNull;

/**
 * 基类Presenter
 * Created by zhouL on 2017/7/7.
 *
 * 在Activity里只操作UI、显示toast、显示dialog
 * 在ViewContract里定义UI的交互方法
 * 在Presenter里进行数据获取、数据组装、页面跳转、业务逻辑判断
 */

public class BasePresenter<VC extends ViewContract> implements PresenterContract<VC>{

    /** View接口 */
    private VC mViewContract;
    /** 上下文 */
    private Context mContext;

    public final Context getContext(){
        return mContext;
    }

    @Override
    public final void attach(Context context, VC view) {
        if (mViewContract != null){
            mViewContract = null;
        }
        mContext = context;
        mViewContract = view;
    }

    @Override
    public final void detach() {
        mContext = null;
        mViewContract = null;
    }

    @Override
    public final boolean isDetach(){
        return mContext == null || mViewContract == null;
    }

    @Override
    public final <T> LifecycleTransformer<T> bindUntilActivityEvent(@NonNull ActivityEvent event) {
        return getViewContract().bindUntilActivityEvent(event);
    }

    @Override
    public final <T> LifecycleTransformer<T> bindUntilFragmentEvent(@NonNull FragmentEvent event) {
        return getViewContract().bindUntilFragmentEvent(event);
    }

    @Override
    public final <T> LifecycleTransformer<T> bindUntilDetachEvent() {
        return getViewContract().bindUntilDetachEvent();
    }

    /** 获取View接口对象 */
    public final VC getViewContract(){
        if (mViewContract == null){
            throw new NullPointerException("the view already detach , your code maybe have memory leak");
        }
        return mViewContract;
    }
}
