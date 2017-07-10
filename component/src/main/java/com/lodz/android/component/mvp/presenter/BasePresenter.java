package com.lodz.android.component.mvp.presenter;

import com.lodz.android.component.mvp.contract.base.BasePresenterContract;
import com.lodz.android.component.mvp.contract.base.BaseViewContract;

/**
 * 带基础状态控件控制的基类Presenter
 * Created by zhouL on 2017/7/10.
 */

public class BasePresenter<VC extends BaseViewContract> extends AbsPresenter<VC> implements BasePresenterContract<VC> {

    @Override
    public void clickBackBtn() {

    }

    @Override
    public void clickReload() {

    }
}
