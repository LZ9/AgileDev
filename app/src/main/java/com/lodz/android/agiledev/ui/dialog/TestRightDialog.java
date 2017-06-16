package com.lodz.android.agiledev.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.dialog.BaseDialog;

/**
 * 测试右侧弹框
 * Created by zhouL on 2017/6/14.
 */

public class TestRightDialog extends BaseDialog{

    public TestRightDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_test_right_layout;
    }

    @Override
    protected void findViews() {

    }
}
