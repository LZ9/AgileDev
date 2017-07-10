package com.lodz.android.component.mvp.base.activity;

import android.support.annotation.NonNull;

import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.mvp.contract.base.BasePresenterContract;
import com.lodz.android.component.mvp.contract.base.BaseViewContract;

/**
 * MVP基类Activity（带基础状态控件）
 * Created by zhouL on 2017/7/10.
 */

public abstract class MvpBaseActivity<PC extends BasePresenterContract<VC>, VC extends BaseViewContract> extends BaseActivity implements BaseViewContract{

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
}
