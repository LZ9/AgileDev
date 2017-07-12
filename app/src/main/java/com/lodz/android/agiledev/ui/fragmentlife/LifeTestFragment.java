package com.lodz.android.agiledev.ui.fragmentlife;

import android.os.Bundle;
import android.view.View;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.fragment.LazyFragment;
import com.lodz.android.core.log.PrintLog;

/**
 * 生命周期测试fragment
 * Created by zhouL on 2017/7/12.
 */

public class LifeTestFragment extends LazyFragment{

    public static LifeTestFragment newInstance(String fragmentName) {
        LifeTestFragment fragment = new LifeTestFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fragmentName", fragmentName);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mFragmentName = "";

    @Override
    protected void startCreate() {
        super.startCreate();
        Bundle bundle = getArguments();
        mFragmentName = bundle.getString("fragmentName");
    }

    @Override
    protected int getAbsLayoutId() {
        return R.layout.fragment_login_main_layout;
    }

    @Override
    protected void findViews(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void onFragmentResume() {
        super.onFragmentResume();
        PrintLog.d("testtag", mFragmentName + " : onFragmentResume");
    }

    @Override
    protected void onFragmentPause() {
        super.onFragmentPause();
        PrintLog.e("testtag", mFragmentName + " : onFragmentPause");
    }


}
