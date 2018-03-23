package com.lodz.android.agiledev.ui.mvp.refresh.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.mvp.refresh.MvpTestRefreshPresenter;
import com.lodz.android.agiledev.ui.mvp.refresh.MvpTestRefreshViewContract;
import com.lodz.android.component.mvp.base.activity.MvpBaseRefreshActivity;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 刷新测试类
 * Created by zhouL on 2017/7/17.
 */

public class MvpTestRefreshActivity extends MvpBaseRefreshActivity<MvpTestRefreshPresenter, MvpTestRefreshViewContract> implements MvpTestRefreshViewContract{

    public static void start(Context context) {
        Intent starter = new Intent(context, MvpTestRefreshActivity.class);
        context.startActivity(starter);
    }

    /** 结果 */
    @BindView(R.id.result)
    TextView mResult;
    /** 获取成功数据按钮 */
    @BindView(R.id.get_success_reuslt_btn)
    Button mGetSuccessResultBtn;
    /** 获取失败数据按钮 */
    @BindView(R.id.get_fail_reuslt_btn)
    Button mGetFailResultBtn;

    @Override
    protected MvpTestRefreshPresenter createMainPresenter() {
        return new MvpTestRefreshPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvp_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBarLayout(getTitleBarLayout());
    }

    private void initTitleBarLayout(TitleBarLayout titleBarLayout) {
        titleBarLayout.setTitleName(R.string.mvp_demo_refresh_title);
    }

    @Override
    protected void onDataRefresh() {
        getPresenterContract().getRefreshData(true);
    }

    @Override
    protected void clickReload() {
        super.clickReload();
        showStatusLoading();
        getPresenterContract().getResult(true);
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        mGetSuccessResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStatusLoading();
                getPresenterContract().getResult(true);
            }
        });

        mGetFailResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStatusLoading();
                getPresenterContract().getResult(false);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusLoading();
        getPresenterContract().getResult(true);
    }

    @Override
    public void setResult(String result) {
        mResult.setText(result);
    }

    @Override
    public void refreshFail(String tips) {
        ToastUtils.showShort(getContext(), tips);
    }

    @Override
    public void showResult() {
        mResult.setVisibility(View.VISIBLE);
    }

}
