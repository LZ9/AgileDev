package com.lodz.android.agiledev.ui.mvp.base;

import com.lodz.android.component.mvp.contract.base.BaseViewContract;

/**
 * 测试接口
 * Created by zhouL on 2017/7/7.
 */

public interface MvpTestBaseViewContract extends BaseViewContract{

    void showResult();

    void setResult(String result);

}
