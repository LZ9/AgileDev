package com.lodz.android.agiledev.ui.mvp.refresh;

import com.lodz.android.component.mvp.contract.refresh.BaseRefreshViewContract;

/**
 * 测试接口
 * Created by zhouL on 2017/7/29.
 */

public interface MvpTestRefreshViewContract extends BaseRefreshViewContract{

    void showResult();

    void setResult(String result);

    void refreshFail(String tips);
}
