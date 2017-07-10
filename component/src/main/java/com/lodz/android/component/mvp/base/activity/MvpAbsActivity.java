package com.lodz.android.component.mvp.base.activity;

import android.support.annotation.NonNull;

import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.component.mvp.contract.abs.PresenterContract;
import com.lodz.android.component.mvp.contract.abs.ViewContract;

/**
 * MVP基础Activity
 * Created by zhouL on 2017/7/7.
 */

public abstract class MvpAbsActivity<PC extends PresenterContract<VC>, VC extends ViewContract> extends AbsActivity implements ViewContract {

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
}
