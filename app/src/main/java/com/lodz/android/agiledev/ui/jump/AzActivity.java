package com.lodz.android.agiledev.ui.jump;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.AbsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhouL on 2017/8/30.
 */

public class AzActivity extends AbsActivity{

    /** 跳转B页面 */
    @BindView(R.id.jump_b_btn)
    Button mJumpBBtn;

    /** 跳转C页面 */
    @BindView(R.id.jump_c_btn)
    Button mJumpCBtn;

    /** 跳转D页面 */
    @BindView(R.id.jump_d_btn)
    Button mJumpDBtn;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_a_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        mJumpBBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BzActivity.start(getContext());
            }
        });

        mJumpCBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CzActivity.start(getContext());
            }
        });

        mJumpDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DzActivity.start(getContext());
            }
        });
    }
}
