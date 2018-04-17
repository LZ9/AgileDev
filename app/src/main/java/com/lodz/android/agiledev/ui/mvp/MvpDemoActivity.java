package com.lodz.android.agiledev.ui.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.agiledev.ui.mvp.abs.activity.MvpTestAbsActivity;
import com.lodz.android.agiledev.ui.mvp.base.activity.MvpTestBaseActivity;
import com.lodz.android.agiledev.ui.mvp.refresh.activity.MvpTestRefreshActivity;
import com.lodz.android.agiledev.ui.mvp.sandwich.activity.MvpTestSandwichActivity;
import com.lodz.android.component.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MVP模式测试类
 * Created by zhouL on 2018/3/22.
 */

public class MvpDemoActivity extends BaseActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, MvpDemoActivity.class);
        context.startActivity(starter);
    }

    /** 基础Activity */
    @BindView(R.id.abs_btn)
    Button mAbsBtn;
    /** 带基础状态控件Activity */
    @BindView(R.id.base_btn)
    Button mBaseBtn;
    /** 带基础状态控件和下来刷新控件Activity */
    @BindView(R.id.refresh_btn)
    Button mRefreshBtn;
    /** 带基础状态控件、中部刷新控件和顶部/底部扩展Activity */
    @BindView(R.id.sandwich_btn)
    Button mSandwichBtn;
    /** Fragment用例 */
    @BindView(R.id.fragment_btn)
    Button mFragmentBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvp_demo_layout;
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
    protected void setListeners() {
        super.setListeners();
        // 基础Activity
        mAbsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MvpTestAbsActivity.start(getContext());
            }
        });

        // 带基础状态控件Activity
        mBaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MvpTestBaseActivity.start(getContext());
            }
        });

        // 带基础状态控件和下来刷新控件Activity
        mRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MvpTestRefreshActivity.start(getContext());
            }
        });

        // 带基础状态控件、中部刷新控件和顶部/底部扩展Activity
        mSandwichBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MvpTestSandwichActivity.start(getContext());
            }
        });

        // Fragment用例
        mFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MvpFragmentActivity.start(getContext());
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }
}
