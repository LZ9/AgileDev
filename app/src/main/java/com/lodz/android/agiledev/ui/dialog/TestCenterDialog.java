package com.lodz.android.agiledev.ui.dialog;

import android.content.Context;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.dialog.BaseDialog;

import androidx.annotation.NonNull;

/**
 * 测试中间弹框
 * Created by zhouL on 2017/6/14.
 */

public class TestCenterDialog extends BaseDialog{

    public TestCenterDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_test_center_layout;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void onStartInit(Context context) {
        super.onStartInit(context);
    }
}
