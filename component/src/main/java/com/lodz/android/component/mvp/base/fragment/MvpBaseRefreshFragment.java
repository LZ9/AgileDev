package com.lodz.android.component.mvp.base.fragment;

import android.support.annotation.NonNull;

import com.lodz.android.component.base.fragment.BaseRefreshFragment;
import com.lodz.android.component.mvp.contract.abs.PresenterContract;
import com.lodz.android.component.mvp.contract.refresh.BaseRefreshViewContract;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * MVP基类Fragment（带基础状态控件和下来刷新控件）
 * Created by zhouL on 2017/8/2.
 */

public abstract class MvpBaseRefreshFragment<PC extends PresenterContract<VC>, VC extends BaseRefreshViewContract> extends BaseRefreshFragment implements BaseRefreshViewContract {

    /** Presenter接口 */
    private PC mPresenterContract;

    @Override
    @SuppressWarnings("unchecked")
    protected void startCreate() {
        super.startCreate();
        mPresenterContract = createMainPresenter();
        if (mPresenterContract != null){
            mPresenterContract.attach(getContext(), (VC) this);
        }
    }

    protected abstract PC createMainPresenter();

    @NonNull
    protected final PC getPresenterContract(){
        return mPresenterContract;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenterContract != null){
            mPresenterContract.detach();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenterContract != null){
            mPresenterContract.detach();
        }
    }

    @Override
    public void showStatusNoData() {
        super.showStatusNoData();
    }

    @Override
    public void showStatusError() {
        super.showStatusError();
    }

    @Override
    public void showStatusLoading() {
        super.showStatusLoading();
    }

    @Override
    public void showStatusCompleted() {
        super.showStatusCompleted();
    }

    @Override
    public void goneTitleBar() {
        super.goneTitleBar();
    }

    @Override
    public void showTitleBar() {
        super.showTitleBar();
    }

    @Override
    public void setSwipeRefreshFinish() {
        super.setSwipeRefreshFinish();
    }

    @Override
    public void setSwipeRefreshEnabled(boolean enabled) {
        super.setSwipeRefreshEnabled(enabled);
    }

    @Override
    public <T> LifecycleTransformer<T> bindUntilActivityEvent(@NonNull ActivityEvent event) {
        throw new IllegalArgumentException("you bind fragment but call activity event");
    }

    @Override
    public <T> LifecycleTransformer<T> bindUntilFragmentEvent(@NonNull FragmentEvent event) {
        return bindUntilEvent(event);
    }

    @Override
    public final <T> LifecycleTransformer<T> bindUntilDetachEvent() {
        return bindUntilEvent(FragmentEvent.DESTROY_VIEW);
    }
}