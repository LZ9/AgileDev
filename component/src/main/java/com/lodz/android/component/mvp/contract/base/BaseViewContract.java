package com.lodz.android.component.mvp.contract.base;

import com.lodz.android.component.mvp.contract.abs.ViewContract;

/**
 * 带基础状态控件的View接口
 * Created by zhouL on 2017/7/10.
 */

public interface BaseViewContract extends ViewContract{

    void showStatusNoData();

    void showStatusError();

    void showStatusLoading();

    void showStatusCompleted();

    void goneTitleBar();

}
