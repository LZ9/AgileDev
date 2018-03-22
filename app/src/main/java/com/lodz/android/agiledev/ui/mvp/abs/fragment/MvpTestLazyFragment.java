package com.lodz.android.agiledev.ui.mvp.abs.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.mvp.abs.MvpTestAbsPresenter;
import com.lodz.android.agiledev.ui.mvp.abs.MvpTestAbsViewContract;
import com.lodz.android.component.mvp.base.fragment.MvpLazyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MVP测试Fragment
 * Created by zhouL on 2017/8/2.
 */

public class MvpTestLazyFragment extends MvpLazyFragment<MvpTestAbsPresenter, MvpTestAbsViewContract> implements MvpTestAbsViewContract {

    public static MvpTestLazyFragment newInstance() {
        return new MvpTestLazyFragment();
    }

    @BindView(R.id.result)
    TextView mResult;

    @BindView(R.id.get_reuslt_btn)
    Button mGetResultBtn;

    @Override
    protected MvpTestAbsPresenter createMainPresenter() {
        return new MvpTestAbsPresenter();
    }

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_mvp_test_layout;
    }

    @Override
    protected void findViews(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void setListeners(View view) {
        super.setListeners(view);

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
