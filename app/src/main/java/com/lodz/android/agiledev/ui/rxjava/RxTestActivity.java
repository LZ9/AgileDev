package com.lodz.android.agiledev.ui.rxjava;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;

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
        mGetCacheBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxContract contract = RxFactory.create(RxFactory.RX_GET_CACHE);
                contract.doCase();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }
}
