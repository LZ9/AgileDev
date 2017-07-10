package com.lodz.android.agiledev.ui.mvp.abs;

import com.lodz.android.component.mvp.contract.abs.ViewContract;

/**
 *  测试接口
 * Created by zhouL on 2017/7/7.
 */

public interface MvpTestAbsViewContract extends ViewContract{

    void showResult();

    void setResult(String result);

}
