package com.lodz.android.agiledev.ui.dialog;

import android.content.Context;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.dialog.BaseTopDialog;

import androidx.annotation.NonNull;

/**
 * 测试底部弹框
 * Created by zhouL on 2017/6/14.
 */

public class TestTopDialog extends BaseTopDialog{

    public TestTopDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_test_top_layout;
    }

    @Override
    protected void findViews() {

    }

//    @Override
//    protected boolean isMatchWidth() {
//        return false;
//    }
}
