package com.lodz.android.agiledev.ui.dialog;

import android.content.Context;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.dialog.BaseLeftDialog;

/**
 * 测试左侧弹框
 * Created by zhouL on 2017/8/31.
 */

public class TestLeftDialog extends BaseLeftDialog{

    public TestLeftDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_test_left_layout;
    }

    @Override
    protected void findViews() {

    }

//    @Override
//    protected boolean isMatchHeight() {
//        return false;
//    }
}
