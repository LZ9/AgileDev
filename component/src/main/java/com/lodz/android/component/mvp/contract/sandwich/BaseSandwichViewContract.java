package com.lodz.android.component.mvp.contract.sandwich;

import com.lodz.android.component.mvp.contract.abs.ViewContract;

/**
 * 带基础状态控件、下拉刷新控件和顶部底部扩展的View接口
 * Created by zhouL on 2018/4/17.
 */
public interface BaseSandwichViewContract extends ViewContract {

    void showStatusNoData();

    void showStatusError();

    void showStatusLoading();

    void showStatusCompleted();

    /** 设置刷新结束（隐藏刷新进度条） */
    void setSwipeRefreshFinish();

    /**
     * 设置刷新控件是否启用
     * @param enabled 是否启用
     */
    void setSwipeRefreshEnabled(boolean enabled);

}

