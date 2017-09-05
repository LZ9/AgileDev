package com.lodz.android.agiledev.ui.rvanim;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RV动画测试
 * Created by zhouL on 2017/9/5.
 */
public class AnimRecyclerViewActivity extends BaseActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, AnimRecyclerViewActivity.class);
        context.startActivity(starter);
    }

    /** 列表 */
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    /** 适配器 */
    private AnimAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_anim_recycler_view_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBar(getTitleBarLayout());
        initRecyclerView();
    }

    /** 初始化标题栏 */
    private void initTitleBar(TitleBarLayout titleBarLayout) {
        titleBarLayout.setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new AnimAdapter(getContext());
        mAdapter.setOpenItemAnim(true);//开启动画
        mAdapter.setItemAnimStartPosition(7);//设置动画起始位置
        mAdapter.setAnimationType(BaseRecyclerViewAdapter.SCALE_IN);//设置动画类型
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter.setData(createList());
        mAdapter.notifyDataSetChanged();
        showStatusCompleted();
    }

    private List<String> createList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 120; i++) {
            list.add(DateUtils.getCurrentFormatString(DateUtils.TYPE_10));
        }
        return list;
    }

}
