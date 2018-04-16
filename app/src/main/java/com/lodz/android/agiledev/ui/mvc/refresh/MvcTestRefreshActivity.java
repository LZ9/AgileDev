package com.lodz.android.agiledev.ui.mvc.refresh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.mvp.ApiModule;
import com.lodz.android.component.base.activity.BaseRefreshActivity;
import com.lodz.android.component.rx.subscribe.observer.BaseObserver;
import com.lodz.android.component.rx.utils.RxUtils;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.ToastUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 带基础状态控件和下来刷新控件Activity
 * Created by zhouL on 2018/4/16.
 */
public class MvcTestRefreshActivity extends BaseRefreshActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, MvcTestRefreshActivity.class);
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
        titleBarLayout.setTitleName(R.string.mvc_demo_refresh_title);
    }

    @Override
    protected void onDataRefresh() {
        getRefreshData(new Random().nextInt(9) % 2 == 0);
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
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onBaseNext(String s) {
                        mResult.setText(s);
                        showStatusCompleted();
                    }

                    @Override
                    public void onBaseError(Throwable e) {
                        showStatusError();
                    }
                });
    }

    private void getRefreshData(boolean isSuccess){
        ApiModule.requestResult(isSuccess)
                .compose(RxUtils.<String>ioToMainObservable())
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onBaseNext(String s) {
                        setSwipeRefreshFinish();
                        mResult.setText(s);
                    }

                    @Override
                    public void onBaseError(Throwable e) {
                        setSwipeRefreshFinish();
                        ToastUtils.showShort(getContext(), "刷新数据失败");
                    }
                });
    }
}
