package com.lodz.android.component.mvp.base.activity;

import android.support.annotation.NonNull;

import com.lodz.android.component.base.activity.BaseRefreshActivity;
import com.lodz.android.component.mvp.contract.refresh.BaseRefreshPresenterContract;
import com.lodz.android.component.mvp.contract.refresh.BaseRefreshViewContract;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * MVP基类Activity（带基础状态控件和下来刷新控件）
 * Created by zhouL on 2017/7/17.
 */

public abstract class MvpBaseRefreshActivity<PC extends BaseRefreshPresenterContract<VC>, VC extends BaseRefreshViewContract> extends BaseRefreshActivity implements BaseRefreshViewContract{

    /** Presenter接口 */
    private PC mPresenterContract;

    @Override
    @SuppressWarnings("unchecked")
    protected void startCreate() {
        super.startCreate();
        mPresenterContract = createMainPresenter();
        if (mPresenterContract != null){
            mPresenterContract.attach(this, (VC) this);
        }
    }

    protected abstract PC createMainPresenter();

    @NonNull
    protected final PC getPresenterContract(){
        return mPresenterContract;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenterContract != null){
            mPresenterContract.detach();
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (mPresenterContract != null){
            mPresenterContract.detach();
        }
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        if (mPresenterContract != null){
            mPresenterContract.clickBackBtn();
        }
    }

    @Override
    protected void clickReload() {
        super.clickReload();
        if (mPresenterContract != null){
            mPresenterContract.clickReload();
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
    public final <T> LifecycleTransformer<T> bindUntilActivityEvent(@NonNull ActivityEvent event) {
        return bindUntilEvent(event);
    }

    @Override
    public final <T> LifecycleTransformer<T> bindUntilFragmentEvent(@NonNull FragmentEvent event) {
        throw new IllegalArgumentException("you bind activity but call fragment event");
    }
}
