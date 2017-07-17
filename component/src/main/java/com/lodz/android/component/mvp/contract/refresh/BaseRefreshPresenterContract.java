package com.lodz.android.component.mvp.contract.refresh;

import com.lodz.android.component.mvp.contract.base.BasePresenterContract;

/**
 * 带基础状态控件和下拉刷新控件的Presenter接口
 * Created by zhouL on 2017/7/17.
 */

public interface BaseRefreshPresenterContract <VC extends BaseRefreshViewContract> extends BasePresenterContract<VC> {

    /** 下拉刷新 */
    void onDataRefresh();

}
