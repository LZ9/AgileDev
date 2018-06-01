package com.lodz.android.agiledev.ui.customview;

import android.os.Bundle;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;

import butterknife.ButterKnife;

/**
 * 自定义控件测试类
 * Created by zhouL on 2018/6/1.
 */
public class CustomViewTestActivity extends BaseActivity{

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_view_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        getTitleBarLayout().setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }
}
