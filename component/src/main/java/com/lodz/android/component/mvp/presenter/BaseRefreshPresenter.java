package com.lodz.android.component.mvp.presenter;

import com.lodz.android.component.mvp.contract.refresh.BaseRefreshPresenterContract;
import com.lodz.android.component.mvp.contract.refresh.BaseRefreshViewContract;

/**
 * 带基础状态控件和下拉刷新控件的基类Presenter
 * Created by zhouL on 2017/7/17.
 */

public class BaseRefreshPresenter<VC extends BaseRefreshViewContract> extends BasePresenter<VC> implements BaseRefreshPresenterContract<VC> {

    @Override
    public void onDataRefresh() {

    }
}
