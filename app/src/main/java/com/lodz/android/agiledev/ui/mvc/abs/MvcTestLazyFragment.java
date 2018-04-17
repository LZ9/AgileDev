package com.lodz.android.agiledev.ui.mvc.abs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.mvp.ApiModule;
import com.lodz.android.component.base.fragment.LazyFragment;
import com.lodz.android.component.rx.subscribe.observer.ProgressObserver;
import com.lodz.android.component.rx.utils.RxUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MVC测试Fragment
 * Created by zhouL on 2018/4/17.
 */
public class MvcTestLazyFragment extends LazyFragment{

    public static MvcTestLazyFragment newInstance() {
        return new MvcTestLazyFragment();
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
    protected int getAbsLayoutId() {
        return R.layout.activity_mvc_test_layout;
    }

    @Override
    protected void findViews(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void setListeners(View view) {
        super.setListeners(view);
        mGetSuccessResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getResult(true);
            }
        });

        mGetFailResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getResult(false);
            }
        });
    }

    private void getResult(boolean isSuccess){
        ApiModule.requestResult(isSuccess)
                .compose(RxUtils.<String>ioToMainObservable())
                .compose(this.<String>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(new ProgressObserver<String>() {
                    @Override
                    public void onPgNext(String s) {
                        mResult.setText(s);
                    }

                    @Override
                    public void onPgError(Throwable e, boolean isNetwork) {
                        mResult.setText("fail");
                    }
                }.create(getContext(), "加载中...", true));
    }
}
