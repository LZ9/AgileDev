package com.lodz.android.agiledev.ui.rxjava;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.rx.subscribe.observer.BaseObserver;
import com.lodz.android.component.rx.utils.RxUtils;
import com.lodz.android.core.log.PrintLog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Rxjava测试类
 * Created by zhouL on 2018/3/7.
 */

public class RxTestActivity extends BaseActivity {

    /** 从磁盘、内存缓存中 获取缓存数据 */
    @BindView(R.id.get_cache_btn)
    Button mGetCacheBtn;

    /** 搜索框 */
    @BindView(R.id.search_edit)
    EditText mSearchEdit;
    /** 联想内容 */
    @BindView(R.id.associate_tv)
    TextView mAssociateTv;

    /** 快速点击 */
    @BindView(R.id.quick_click_btn)
    TextView mQucikClickBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rx_test_layout;
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

        // 从磁盘、内存缓存中 获取缓存数据
        mGetCacheBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxContract contract = RxFactory.create(RxFactory.RX_GET_CACHE);
                contract.doCase();
            }
        });

        // 联想搜索
        RxUtils.textChanges(mSearchEdit)
                .compose(RxUtils.<CharSequence>ioToMainObservable())
                .subscribe(new BaseObserver<CharSequence>() {
                    @Override
                    public void onBaseNext(CharSequence charSequence) {
                        mAssociateTv.setText("去服务端请求数据 : " + charSequence.toString());
                    }

                    @Override
                    public void onBaseError(Throwable e) {

                    }
                });

        // 快速点击
        RxUtils.viewClick(mQucikClickBtn)
                .subscribe(new BaseObserver<View>() {
                    @Override
                    public void onBaseNext(View view) {
                        PrintLog.d("testtag", "view click");
                    }

                    @Override
                    public void onBaseError(Throwable e) {

                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }
}
