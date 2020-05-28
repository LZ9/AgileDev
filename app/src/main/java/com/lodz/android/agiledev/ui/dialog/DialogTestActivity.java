package com.lodz.android.agiledev.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.component.rx.subscribe.observer.ProgressObserver;
import com.lodz.android.component.rx.utils.RxUtils;
import com.lodz.android.component.widget.base.TitleBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;

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

    @BindView(R.id.progress_btn)
    Button mProgressBtn;


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

        mProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.just("")
                        .map(new Function<String, String>() {
                            @Override
                            public String apply(String s) throws Exception {
                                Thread.sleep(2000);
                                return s;
                            }
                        })
                        .compose(RxUtils.<String>ioToMainObservable())
                        .subscribe(new ProgressObserver<String>() {
                            @Override
                            public void onPgNext(String s) {

                            }

                            @Override
                            public void onPgError(Throwable e, boolean isNetwork) {

                            }
                        }.create(getContext(), R.string.dialog_test_progress, false));
            }
        });
    }
}
