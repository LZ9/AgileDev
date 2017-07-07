package com.lodz.android.agiledev.ui.mvp;

import android.os.Bundle;

import com.lodz.android.component.mvp.base.MvpAbsActivity;

/**
 * Created by zhouL on 2017/7/7.
 */

public class MvpTestActivity extends MvpAbsActivity<MvpTestPresenter, MvpTestViewContract> implements MvpTestViewContract{

    @Override
    public void showResult() {

    }

    @Override
    protected MvpTestPresenter createPresenter() {
        return new MvpTestPresenter();
    }

    @Override
    protected int getAbsLayoutId() {
        return 0;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {

    }
}
