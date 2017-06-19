package com.lodz.android.agiledev.ui.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.AbsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhouL on 2017/6/14.
 */

public class DialogTestActivity extends AbsActivity{

    @BindView(R.id.center_btn)
    Button mCenterBtn;

    @BindView(R.id.right_btn)
    Button mRightBtn;

    @BindView(R.id.bottom_btn)
    Button mBottomBtn;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_dialog_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestCenterDialog dialog = new TestCenterDialog(getContext());
                dialog.show();
            }
        });

        mRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestRightDialog dialog = new TestRightDialog(getContext());
                dialog.show();
            }
        });

        mBottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestBottomDialog dialog = new TestBottomDialog(getContext());
                dialog.show();
            }
        });
    }
}
