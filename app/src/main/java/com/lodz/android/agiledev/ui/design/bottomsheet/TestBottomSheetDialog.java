package com.lodz.android.agiledev.ui.design.bottomsheet;

import android.content.Context;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.component.widget.bottomsheets.dialog.BaseBottomSheetDialog;
import com.lodz.android.core.utils.DensityUtils;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * BottomSheetDialog测试类
 * Created by zhouL on 2018/4/23.
 */
public class TestBottomSheetDialog extends BaseBottomSheetDialog{

    /** 标题栏 */
    @BindView(R.id.title_bar_layout)
    TitleBarLayout mTitleBarLayout;

    private BottomSheetBehavior mBehavior;
    /** 是否用户关闭 */
    private boolean isUserDismiss = false;

    public TestBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_bottom_sheet_layout;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mTitleBarLayout.setOnBackBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBehavior != null){
                    isUserDismiss = true;
                    mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });
    }

    @Override
    protected void onBehaviorInit(BottomSheetBehavior behavior) {
        mBehavior = behavior;

        behavior.setPeekHeight(DensityUtils.dp2px(getContext(), 150));
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (!isUserDismiss){
                    setDim(newState == BottomSheetBehavior.STATE_EXPANDED ? 0f : 0.6f);
                }
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    cancel();
                }
                mTitleBarLayout.needBackButton(newState == BottomSheetBehavior.STATE_EXPANDED);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    @Override
    protected int configTopOffsetPx() {
//        return DensityUtils.dp2px(getContext(), 200);
        return 0;
    }
}
