package com.lodz.android.agiledev.ui.config;

import android.os.Bundle;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.BaseActivity;

/**
 * 配置测试类
 * Created by zhouL on 2017/7/4.
 */

public class ConfigLayoutActivity extends BaseActivity{

    @Override
    protected void findViews(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_config_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusNoData();
    }
}

