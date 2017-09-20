package com.lodz.android.component.mvp.base.fragment;

import android.support.annotation.NonNull;

import com.lodz.android.component.base.fragment.LazyFragment;
import com.lodz.android.component.mvp.contract.abs.PresenterContract;
import com.lodz.android.component.mvp.contract.abs.ViewContract;

/**
 * MVP懒加载的fragment
 * Created by zhouL on 2017/7/29.
 */

public abstract class MvpLazyFragment<PC extends PresenterContract<VC>, VC extends ViewContract> extends LazyFragment implements ViewContract {

    /** Presenter接口 */
    private PC mPresenterContract;

    @Override
    @SuppressWarnings("unchecked")
    protected void startCreate() {
        super.startCreate();
        mPresenterContract = createMainPresenter();
        if (mPresenterContract != null){
            mPresenterContract.onCreate(getContext(), (VC) this);
        }
    }

    protected abstract PC createMainPresenter();

    @NonNull
    protected PC getPresenterContract(){
        return mPresenterContract;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenterContract != null){
            mPresenterContract.onDestroy();
        }
    }

    @Override
    protected void onFragmentResume() {
        super.onFragmentResume();
        if (mPresenterContract != null){
            mPresenterContract.onResume();
        }
    }

    @Override
    protected void onFragmentPause() {
        super.onFragmentPause();
        if (mPresenterContract != null){
            mPresenterContract.onPause();
        }
    }

}
