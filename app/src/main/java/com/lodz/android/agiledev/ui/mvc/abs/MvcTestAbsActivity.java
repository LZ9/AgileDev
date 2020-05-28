package com.lodz.android.agiledev.ui.mvc.abs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.mvp.ApiModule;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.component.rx.subscribe.observer.ProgressObserver;
import com.lodz.android.component.rx.utils.RxUtils;
import com.trello.rxlifecycle4.android.ActivityEvent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MVC基础Activity
 * Created by zhouL on 2018/4/16.
 */
public class MvcTestAbsActivity extends AbsActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, MvcTestAbsActivity.class);
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
    protected int getAbsLayoutId() {
        return R.layout.activity_mvc_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
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
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
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
