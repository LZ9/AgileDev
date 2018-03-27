package com.lodz.android.agiledev.ui.dialogfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * DialogFragment测试类
 * Created by zhouL on 2018/3/26.
 */

public class DialogFragmentTestActivity extends BaseActivity{


    public static void start(Context context) {
        Intent starter = new Intent(context, DialogFragmentTestActivity.class);
        context.startActivity(starter);
    }

    @BindView(R.id.center_btn)
    Button mCenterBtn;

    @BindView(R.id.right_btn)
    Button mRightBtn;

    @BindView(R.id.left_btn)
    Button mLeftBtn;

    @BindView(R.id.bottom_btn)
    Button mBottomBtn;

    @BindView(R.id.top_btn)
    Button mTopBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dialog_fragment_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        getTitleBarLayout().setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        mCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestCenterDialogFragment dialogFragment = new TestCenterDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "TestCenterDialogFragment");
            }
        });

        mRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestRightDialogFragment dialogFragment = new TestRightDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "TestRightDialogFragment");
            }
        });

        mLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestLeftDialogFragment dialogFragment = new TestLeftDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "TestLeftDialogFragment");
            }
        });

        mBottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestBottomDialogFragment dialogFragment = new TestBottomDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "TestBottomDialogFragment");
            }
        });

        mTopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestTopDialogFragment dialogFragment = new TestTopDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "TestTopDialogFragment");
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }
}
