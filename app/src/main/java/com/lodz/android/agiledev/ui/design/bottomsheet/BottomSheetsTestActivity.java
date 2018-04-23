package com.lodz.android.agiledev.ui.design.bottomsheet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.log.PrintLog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * BottomSheets测试类
 * Created by zhouL on 2018/4/23.
 */
public class BottomSheetsTestActivity extends AbsActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, BottomSheetsTestActivity.class);
        context.startActivity(starter);
    }

    /** 标题栏 */
    @BindView(R.id.title_bar_layout)
    TitleBarLayout mTitleBarLayout;
    /** BottomSheets */
    @BindView(R.id.sheets_btn)
    TextView mSheetsBtn;
    /** BottomSheetDialog */
    @BindView(R.id.sheet_dialog_btn)
    TextView mSheetDialogBtn;
    /** BottomSheetDialogFragment */
    @BindView(R.id.sheet_dialog_fragment_btn)
    TextView mSheetDialogFragmentBtn;
    /** 底部BottomSheet */
    @BindView(R.id.bottom_sheet)
    NestedScrollView mBottomSheet;

    BottomSheetBehavior mBottomSheetBehavior;


    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_bottom_sheets_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mTitleBarLayout.setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
    }


    @Override
    protected void setListeners() {
        super.setListeners();
        mTitleBarLayout.setOnBackBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSheetsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED
                        ? BottomSheetBehavior.STATE_COLLAPSED : BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        mSheetDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestBottomSheetDialog dialog = new TestBottomSheetDialog(getContext());
                dialog.show();
            }
        });

        mSheetDialogFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestBottomSheetDialogFragment dialogFragment = new TestBottomSheetDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "TestBottomSheetDialogFragment");
            }
        });

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //这里是bottomSheet 状态的改变
                PrintLog.d("testtag", "newState : " + newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //这里是拖拽中的回调，根据slideOffset可以做一些动画
                PrintLog.i("testtag", "slideOffset : " + slideOffset);
            }
        });
    }
}
