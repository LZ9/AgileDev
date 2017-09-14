package com.lodz.android.agiledev.ui.rv.swipe;

import android.os.Bundle;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.swipe.SwipeMenuLayout;
import com.lodz.android.component.widget.base.TitleBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RecyclerView侧滑菜单测试
 * Created by zhouL on 2017/3/6.
 */
public class SwipeRecyclerViewActivity extends BaseActivity{

    /** 列表 */
    @BindView(R.id.swipe_layout)
    SwipeMenuLayout mSwipeMenuLayout;


    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBar(getTitleBarLayout());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_swip_layout;
    }

    /** 初始化标题栏 */
    private void initTitleBar(TitleBarLayout titleBarLayout) {
        titleBarLayout.setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }
}
