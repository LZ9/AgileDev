package com.lodz.android.agiledev.ui.mvc.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.mvp.ApiModule;
import com.lodz.android.component.base.fragment.BaseFragment;
import com.lodz.android.component.rx.subscribe.observer.RxObserver;
import com.lodz.android.component.rx.utils.RxUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 带基础控件的MVC测试类
 * Created by zhouL on 2018/4/17.
 */
public class MvcTestBaseFragment extends BaseFragment{

    public static MvcTestBaseFragment newInstance() {
        return new MvcTestBaseFragment();
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
        return R.layout.activity_mvp_test_layout;
    }

    @Override
    protected void findViews(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
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
