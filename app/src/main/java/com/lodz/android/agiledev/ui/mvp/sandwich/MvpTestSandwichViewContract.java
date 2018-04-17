package com.lodz.android.agiledev.ui.mvp.sandwich;

import com.lodz.android.component.mvp.contract.sandwich.BaseSandwichViewContract;

/**
 * 测试接口
 * Created by zhouL on 2018/4/17.
 */
public interface MvpTestSandwichViewContract extends BaseSandwichViewContract {

    void showResult();

    void setResult(String result);

    void refreshFail(String tips);
}
