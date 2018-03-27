package com.lodz.android.agiledev.ui.dialogfragment;

import android.os.Bundle;
import android.view.View;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.dialogfragment.BaseDialogFragment;

/**
 * 测试中间DialogFragment
 * Created by zhouL on 2018/3/26.
 */

public class TestCenterDialogFragment extends BaseDialogFragment{

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_test_center_layout;
    }

    @Override
    protected void findViews(View view, Bundle savedInstanceState) {

    }
}
