package com.lodz.android.agiledev.ui.mvp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.mvp.base.activity.MvpAbsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MVP测试Activity
 * Created by zhouL on 2017/7/7.
 */

public class MvpTestActivity extends MvpAbsActivity<MvpTestPresenter, MvpTestViewContract> implements MvpTestViewContract {

    @BindView(R.id.result)
    TextView mResult;

    @BindView(R.id.show_btn)
    Button mShowBtn;

    @BindView(R.id.get_reuslt_btn)
    Button mGetResultBtn;

    @Override
    protected MvpTestPresenter createMainPresenter() {
        return new MvpTestPresenter();
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
        mShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResult.setVisibility(mResult.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });

        mGetResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenterContract().getResult();
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
