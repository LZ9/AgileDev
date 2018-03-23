package com.lodz.android.component.mvp.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lodz.android.component.mvp.contract.abs.PresenterContract;
import com.lodz.android.component.mvp.contract.abs.ViewContract;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * 基类Presenter
 * Created by zhouL on 2017/7/7.
 */

public class AbsPresenter<VC extends ViewContract> implements PresenterContract<VC>{

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
