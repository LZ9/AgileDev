package com.lodz.android.agiledev.ui.mvp;

import com.lodz.android.component.mvp.contract.ViewContract;

/**
 *
 * Created by zhouL on 2017/7/7.
 */

public interface MvpTestViewContract extends ViewContract{

    void showResult();

    void setResult(String result);

}
