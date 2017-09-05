package com.lodz.android.agiledev.ui.dialog;

import android.content.Context;
import android.os.Build;
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

    @BindView(R.id.tips)
    TextView mTips;


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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSureBtn.setElevation(16f);//添加阴影
            mTips.setElevation(16f);
        }
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
