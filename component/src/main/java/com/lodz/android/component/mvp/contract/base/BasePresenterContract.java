package com.lodz.android.component.mvp.contract.base;

import com.lodz.android.component.mvp.contract.abs.PresenterContract;

/**
 * 带基础状态控件的Presenter接口
 * Created by zhouL on 2017/7/10.
 */

public interface BasePresenterContract<VC extends BaseViewContract> extends PresenterContract<VC>{

    void clickBackBtn();

    void clickReload();

}
