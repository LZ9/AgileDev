package com.lodz.android.agiledev.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.dialog.BaseRightDialog;

/**
 * 测试右侧弹框
 * Created by zhouL on 2017/6/14.
 */

public class TestRightDialog extends BaseRightDialog{

    public TestRightDialog(@NonNull Context context) {
        super(context, R.style.NoDimDialog);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_test_right_layout;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initData() {
        super.initData();
        setCanceledOnTouchOutside(false);
    }

    //    @Override
//    protected boolean isMatchHeight() {
//        return false;
//    }
}
