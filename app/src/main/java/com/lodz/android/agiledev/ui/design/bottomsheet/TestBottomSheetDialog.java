package com.lodz.android.agiledev.ui.design.bottomsheet;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.bottomsheets.dialog.BaseBottomSheetDialog;
import com.lodz.android.core.utils.DensityUtils;

/**
 * BottomSheetDialog测试类
 * Created by zhouL on 2018/4/23.
 */
public class TestBottomSheetDialog extends BaseBottomSheetDialog{

    public TestBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_bottom_sheet_layout;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initData() {
        super.initData();
        getBehavior().setPeekHeight(DensityUtils.dp2px(getContext(), 150));
    }
}
