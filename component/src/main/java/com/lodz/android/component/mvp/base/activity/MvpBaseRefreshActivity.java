package com.lodz.android.component.mvp.base.activity;

import android.support.annotation.NonNull;

import com.lodz.android.component.base.activity.BaseRefreshActivity;
import com.lodz.android.component.mvp.contract.refresh.BaseRefreshPresenterContract;
import com.lodz.android.component.mvp.contract.refresh.BaseRefreshViewContract;

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
        mPresenterContract.onCreate(this, (VC) this);
    }

    protected abstract PC createMainPresenter();

    @NonNull
    protected PC getPresenterContract(){
        return mPresenterContract;
    }

    @Override
    protected void onDestroy() {
        mPresenterContract.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mPresenterContract.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mPresenterContract.onResume();
        super.onResume();
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        mPresenterContract.clickBackBtn();
    }

    @Override
    protected void clickReload() {
        super.clickReload();
        mPresenterContract.clickReload();
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
    public void setSwipeRefreshFinish() {
        super.setSwipeRefreshFinish();
    }

    @Override
    public void setSwipeRefreshEnabled(boolean enabled) {
        super.setSwipeRefreshEnabled(enabled);
    }
}
