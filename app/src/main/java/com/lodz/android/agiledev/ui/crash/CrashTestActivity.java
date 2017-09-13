package com.lodz.android.agiledev.ui.crash;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.UiHandler;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 崩溃测试
 * Created by zhouL on 2017/9/12.
 */

public class CrashTestActivity extends BaseActivity{

    @BindView(R.id.crash_btn)
    TextView mCrashBtn;

    @BindView(R.id.crash_tips)
    TextView mCrashTips;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_crash_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBar(getTitleBarLayout());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mCrashBtn.setElevation(12f);
        }
    }

    /** 初始化标题栏 */
    private void initTitleBar(TitleBarLayout titleBarLayout) {
        titleBarLayout.setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mCrashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCrashTips.setVisibility(View.VISIBLE);
                UiHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String a = null;//可以在SplashActivity的initCrashHandler方法中进行测试配置
                        a.toString();
                    }
                }, 100);
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }
}
