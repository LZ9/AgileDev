package com.lodz.android.agiledev.ui.rv.swipe;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.component.widget.adapter.swipe.SwipeMenuLayout;
import com.lodz.android.component.widget.adapter.swipe.SwipeMenuRecyclerView;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RecyclerView侧滑菜单测试
 * Created by zhouL on 2017/3/6.
 */
public class SwipeRecyclerViewActivity extends BaseActivity{

    /** 侧滑布局 */
    @BindView(R.id.swipe_layout)
    SwipeMenuLayout mSwipeMenuLayout;

    /** 列表 */
    @BindView(R.id.recycler_view)
    SwipeMenuRecyclerView mRecyclerView;

    private SwipeAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_swip_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBar(getTitleBarLayout());
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new SwipeAdapter(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
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
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, String item, int position) {
                ToastUtils.showShort(getContext(), item);
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseRecyclerViewAdapter.OnItemLongClickListener<String>() {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, String item, int position) {
                ToastUtils.showShort(getContext(), item + " long");
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter.setData(getDatas());
        mAdapter.notifyDataSetChanged();
        showStatusCompleted();
    }

    private List<String> getDatas() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("测试+" + i);
        }
        return list;
    }

}
