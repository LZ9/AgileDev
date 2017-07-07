package com.lodz.android.component.mvp.base;

import android.support.annotation.NonNull;

import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.component.mvp.contract.PresenterContract;
import com.lodz.android.component.mvp.contract.ViewContract;

/**
 * MVP基础Activity
 * Created by zhouL on 2017/7/7.
 */

public abstract class MvpAbsActivity<PC extends PresenterContract<VC>, VC extends ViewContract> extends AbsActivity implements ViewContract {

    /**  */
    private PC mMvpPresenter;

    @Override
    protected void startCreate() {
        super.startCreate();
        mMvpPresenter = createPresenter();
        mMvpPresenter.attachView(getViewContract());
    }

    protected abstract PC createPresenter();

    @NonNull
    protected PC getPresenterContract(){
        return mMvpPresenter;
    }

    @SuppressWarnings("unchecked")
    protected VC getViewContract(){
        return (VC) this;
    }

    @Override
    protected void onDestroy() {
        mMvpPresenter.detachView();
        super.onDestroy();
    }
}
