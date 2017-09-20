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
        if (mPresenterContract != null){
            mPresenterContract.onCreate(this, (VC) this);
        }
    }

    protected abstract PC createMainPresenter();

    @NonNull
    protected PC getPresenterContract(){
        return mPresenterContract;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenterContract != null){
            mPresenterContract.onDestroy();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenterContract != null){
            mPresenterContract.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenterContract != null){
            mPresenterContract.onResume();
        }
    }
}
