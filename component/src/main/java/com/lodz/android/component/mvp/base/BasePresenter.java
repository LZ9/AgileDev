package com.lodz.android.component.mvp.base;

import com.lodz.android.component.mvp.contract.PresenterContract;
import com.lodz.android.component.mvp.contract.ViewContract;

/**
 * 基类Presenter
 * Created by zhouL on 2017/7/7.
 */

public class BasePresenter<VC extends ViewContract> implements PresenterContract<VC>{

    private VC mViewContract;

    @Override
    public void attachView(VC view) {
        if (mViewContract != null){
            mViewContract = null;
        }
        mViewContract = view;
    }

    @Override
    public void detachView() {
        if (mViewContract != null){
            mViewContract = null;
        }
    }
}
