package com.lodz.android.agiledev.ui.mvc.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.mvp.ApiModule;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.rx.subscribe.observer.RxObserver;
import com.lodz.android.component.rx.utils.RxUtils;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MVC带基础状态控件Activity
 * Created by zhouL on 2018/4/16.
 */
public class MvcTestBaseActivity extends BaseActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, MvcTestBaseActivity.class);
        context.startActivity(starter);
    }

    /** 结果 */
    @BindView(R.id.result)
    TextView mResult;
    /** 获取成功数据按钮 */
    @BindView(R.id.get_success_reuslt_btn)
    Button mGetSuccessResultBtn;
    /** 获取失败数据按钮 */
    @BindView(R.id.get_fail_reuslt_btn)
    Button mGetFailResultBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvc_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBarLayout(getTitleBarLayout());
    }

    private void initTitleBarLayout(TitleBarLayout titleBarLayout) {
        titleBarLayout.setTitleName(R.string.mvc_demo_base_title);
    }

    @Override
    protected void clickReload() {
        super.clickReload();
        showStatusLoading();
        getResult(true);
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mGetSuccessResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStatusLoading();
                getResult(true);
            }
        });

        mGetFailResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStatusLoading();
                getResult(false);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusLoading();
        getResult(true);
    }

    private void getResult(boolean isSuccess){
        ApiModule.requestResult(isSuccess)
                .compose(RxUtils.<String>ioToMainObservable())
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new RxObserver<String>() {
                    @Override
                    public void onRxNext(String s) {
                        mResult.setText(s);
                        showStatusCompleted();
                    }

                    @Override
                    public void onRxError(Throwable e, boolean isNetwork) {
                        showStatusError();
                    }
                });
    }
}
