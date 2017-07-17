package com.lodz.android.component.mvp.contract.refresh;

import com.lodz.android.component.mvp.contract.base.BaseViewContract;

/**
 * 带基础状态控件和下拉刷新控件的View接口
 * Created by zhouL on 2017/7/17.
 */

public interface BaseRefreshViewContract extends BaseViewContract {

    /** 设置刷新结束（隐藏刷新进度条） */
    void setSwipeRefreshFinish();

    /**
     * 设置刷新控件是否启用
     * @param enabled 是否启用
     */
    void setSwipeRefreshEnabled(boolean enabled);

}
