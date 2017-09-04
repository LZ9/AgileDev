package com.lodz.android.agiledev.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.dialog.BaseBottomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 测试底部弹框
 * Created by zhouL on 2017/6/14.
 */

public class TestBottomDialog extends BaseBottomDialog{

    @BindView(R.id.sure_btn)
    TextView mSureBtn;

    public TestBottomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_test_bottom_layout;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mSureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
