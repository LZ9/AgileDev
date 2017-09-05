package com.lodz.android.agiledev.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.component.widget.base.TitleBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 弹框测试
 * Created by zhouL on 2017/6/14.
 */

public class DialogTestActivity extends AbsActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, DialogTestActivity.class);
        context.startActivity(starter);
    }

    @BindView(R.id.title_bar_layout)
    TitleBarLayout mTitleBarLayout;

    @BindView(R.id.center_btn)
    Button mCenterBtn;

    @BindView(R.id.center_scale_btn)
    Button mCenterScaleBtn;

    @BindView(R.id.right_btn)
    Button mRightBtn;

    @BindView(R.id.left_btn)
    Button mLeftBtn;

    @BindView(R.id.bottom_btn)
    Button mBottomBtn;

    @BindView(R.id.top_btn)
    Button mTopBtn;


    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_dialog_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBarLayout(mTitleBarLayout);
    }

    private void initTitleBarLayout(TitleBarLayout titleBarLayout) {
        titleBarLayout.setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
        titleBarLayout.setOnBackBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestCenterDialog dialog = new TestCenterDialog(getContext());
                dialog.show();
            }
        });

        mCenterScaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestCenterScaleDialog dialog = new TestCenterScaleDialog(getContext());
                dialog.show();
            }
        });

        mRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestRightDialog dialog = new TestRightDialog(getContext());
                dialog.show();
            }
        });

        mLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestLeftDialog dialog = new TestLeftDialog(getContext());
                dialog.show();
            }
        });

        mBottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestBottomDialog dialog = new TestBottomDialog(getContext());
                dialog.show();
            }
        });

        mTopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestTopDialog dialog = new TestTopDialog(getContext());
                dialog.show();
            }
        });
    }
}
