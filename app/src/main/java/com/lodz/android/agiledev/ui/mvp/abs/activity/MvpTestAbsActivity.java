package com.lodz.android.agiledev.ui.mvp.abs.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.mvp.abs.MvpTestAbsPresenter;
import com.lodz.android.agiledev.ui.mvp.abs.MvpTestAbsViewContract;
import com.lodz.android.component.mvp.base.activity.MvpAbsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MVP测试Activity
 * Created by zhouL on 2017/7/7.
 */
public class MvpTestAbsActivity extends MvpAbsActivity<MvpTestAbsPresenter, MvpTestAbsViewContract> implements MvpTestAbsViewContract {

    public static void start(Context context) {
        Intent starter = new Intent(context, MvpTestAbsActivity.class);
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
    protected MvpTestAbsPresenter createMainPresenter() {
        return new MvpTestAbsPresenter();
    }

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_mvp_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mGetSuccessResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenterContract().getResult(true);
            }
        });

        mGetFailResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenterContract().getResult(false);
            }
        });
    }

    @Override
    public void showResult() {
        mResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void setResult(String result) {
        mResult.setText(result);
    }

}
