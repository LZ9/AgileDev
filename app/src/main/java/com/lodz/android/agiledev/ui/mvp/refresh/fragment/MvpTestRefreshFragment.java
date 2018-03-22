package com.lodz.android.agiledev.ui.mvp.refresh.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.mvp.refresh.MvpTestRefreshPresenter;
import com.lodz.android.agiledev.ui.mvp.refresh.MvpTestRefreshViewContract;
import com.lodz.android.component.mvp.base.fragment.MvpBaseRefreshFragment;
import com.lodz.android.core.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 刷新测试类
 * Created by zhouL on 2017/8/2.
 */

public class MvpTestRefreshFragment extends MvpBaseRefreshFragment<MvpTestRefreshPresenter, MvpTestRefreshViewContract> implements MvpTestRefreshViewContract {

    public static MvpTestRefreshFragment newInstance() {
        return new MvpTestRefreshFragment();
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
    protected void findViews(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void onDataRefresh() {
        getPresenterContract().getRefreshData(false);
    }

    @Override
    protected void setListeners(View view) {
        super.setListeners(view);
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
    protected void initData(View view) {
        super.initData(view);
        showStatusLoading();
        getPresenterContract().getResult(true);
    }

    @Override
    public void showResult() {
        mResult.setVisibility(View.VISIBLE);
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
    protected void clickReload() {
        super.clickReload();
        showStatusLoading();
        getPresenterContract().getResult(true);
    }
}
