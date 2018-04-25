package com.lodz.android.component.mvp.base.fragment;

import android.support.annotation.NonNull;

import com.lodz.android.component.base.fragment.BaseSandwichFragment;
import com.lodz.android.component.mvp.contract.abs.PresenterContract;
import com.lodz.android.component.mvp.contract.sandwich.BaseSandwichViewContract;
import com.lodz.android.core.utils.ToastUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * MVP基类Fragment（带基础状态控件、中部刷新控件和顶部/底部扩展）
 * Created by zhouL on 2018/4/17.
 */
public abstract class MvpBaseSandwichFragment<PC extends PresenterContract<VC>, VC extends BaseSandwichViewContract> extends BaseSandwichFragment implements BaseSandwichViewContract {

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
    public void setSwipeRefreshFinish() {
        super.setSwipeRefreshFinish();
    }

    @Override
    public void setSwipeRefreshEnabled(boolean enabled) {
        super.setSwipeRefreshEnabled(enabled);
    }

    @Override
    public final <T> LifecycleTransformer<T> bindUntilActivityEvent(@NonNull ActivityEvent event) {
        throw new IllegalArgumentException("you bind fragment but call activity event");
    }

    @Override
    public final <T> LifecycleTransformer<T> bindUntilFragmentEvent(@NonNull FragmentEvent event) {
        return bindUntilEvent(event);
    }

    @Override
    public final <T> LifecycleTransformer<T> bindUntilDetachEvent() {
        return bindUntilEvent(FragmentEvent.DESTROY_VIEW);
    }

    @Override
    public final void finish() {
    }

    @Override
    public final void showShortToast(int resId) {
        showShortToast(getString(resId));
    }

    @Override
    public final void showShortToast(String tips) {
        ToastUtils.showShort(getContext(), tips);
    }

    @Override
    public final void showLongToast(int resId) {
        showLongToast(getString(resId));
    }

    @Override
    public final void showLongToast(String tips) {
        ToastUtils.showLong(getContext(), tips);
    }
}

