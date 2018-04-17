package com.lodz.android.agiledev.ui.mvc.sandwich;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.mvp.ApiModule;
import com.lodz.android.component.base.fragment.BaseSandwichFragment;
import com.lodz.android.component.rx.subscribe.observer.BaseObserver;
import com.lodz.android.component.rx.utils.RxUtils;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.ToastUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MVC带基础状态控件、中部刷新控件和顶部/底部扩展Fragment
 * Created by zhouL on 2018/4/17.
 */
public class MvcTestSandwichFragment extends BaseSandwichFragment{

    public static MvcTestSandwichFragment newInstance() {
        return new MvcTestSandwichFragment();
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

    /** 顶部标题栏 */
    @BindView(R.id.title_bar_layout)
    TitleBarLayout mTitleBarLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvc_test_layout;
    }

    @Override
    protected int getTopLayoutId() {
        return R.layout.view_mvc_test_top_layout;
    }

    @Override
    protected int getBottomLayoutId() {
        return R.layout.view_mvc_test_bottom_layout;
    }

    @Override
    protected void findViews(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        mTitleBarLayout.setVisibility(View.GONE);
        setSwipeRefreshEnabled(true);
    }

    @Override
    protected void onDataRefresh() {
        super.onDataRefresh();
        getRefreshData(new Random().nextInt(9) % 2 == 0);
    }

    @Override
    protected void clickReload() {
        super.clickReload();
        showStatusLoading();
        getResult(true);
    }

    @Override
    protected void setListeners(View view) {
        super.setListeners(view);

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
    protected void initData(View view) {
        super.initData(view);
        showStatusLoading();
        getResult(true);
    }

    private void getResult(boolean isSuccess){
        ApiModule.requestResult(isSuccess)
                .compose(RxUtils.<String>ioToMainObservable())
                .compose(this.<String>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
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
                .compose(this.<String>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onBaseNext(String s) {
                        setSwipeRefreshFinish();
                        mResult.setText(s);
                        showStatusCompleted();
                    }

                    @Override
                    public void onBaseError(Throwable e) {
                        setSwipeRefreshFinish();
                        ToastUtils.showShort(getContext(), "刷新数据失败");
                    }
                });
    }
}
