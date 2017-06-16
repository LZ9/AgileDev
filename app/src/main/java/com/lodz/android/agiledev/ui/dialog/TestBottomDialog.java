package com.lodz.android.agiledev.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.dialog.BaseDialog;

/**
 * 测试底部弹框
 * Created by zhouL on 2017/6/14.
 */

public class TestBottomDialog extends BaseDialog{

    public TestBottomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_test_bottom_layout;
    }

    @Override
    protected void findViews() {

    }
}
