package com.lodz.android.agiledev.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.lodz.android.component.base.BaseFragment;

/**
 * Created by zhouL on 2017/2/22.
 */

public class TestFragment extends BaseFragment{

    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void findViews(View view, Bundle savedInstanceState) {

    }
}
