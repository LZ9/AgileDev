package com.lodz.android.agiledev.ui.rv.head;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.recycler.BaseHeaderFooterRVAdapter;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 带头/底部RecyclerView测试
 * Created by zhouL on 2017/4/6.
 */
public class HeadRecyclerViewActivity extends BaseActivity{

    private HeadRecyclerViewAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_head_layout;
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
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
//        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new HeadRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter.onAttachedToRecyclerView(mRecyclerView);// 如果使用网格布局请设置此方法
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
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
                ToastUtils.showShort(getContext(), item + " position : " + position);
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseRecyclerViewAdapter.OnItemLongClickListener<String>() {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, String item, int position) {
                ToastUtils.showShort(getContext(), item + " position : " + position + " long");
            }
        });

        mAdapter.setOnHeaderClickListener(new BaseHeaderFooterRVAdapter.OnHeaderClickListener<String>() {
            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, String headerData, int position) {
                ToastUtils.showShort(getContext(), headerData + " position : " + position);
            }
        });

        mAdapter.setOnHeaderLongClickListener(new BaseHeaderFooterRVAdapter.OnHeaderLongClickListener<String>() {
            @Override
            public void onHeaderLongClick(RecyclerView.ViewHolder viewHolder, String headerData, int position) {
                ToastUtils.showShort(getContext(), headerData + " position : " + position + " long");
            }
        });

        mAdapter.setOnFooterClickListener(new BaseHeaderFooterRVAdapter.OnFooterClickListener<String>() {
            @Override
            public void onFooterClick(RecyclerView.ViewHolder viewHolder, String footerData, int position) {
                ToastUtils.showShort(getContext(), footerData + " position : " + position);
            }
        });

        mAdapter.setOnFooterLongClickListener(new BaseHeaderFooterRVAdapter.OnFooterLongClickListener<String>() {
            @Override
            public void onFooterLongClick(RecyclerView.ViewHolder viewHolder, String footerData, int position) {
                ToastUtils.showShort(getContext(), footerData + " position : " + position + " long");
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        String headerData = "我是头布局";
        mAdapter.setHeaderData(headerData);
        String footerData = "我是底布局";
        mAdapter.setFooterData(footerData);
        mAdapter.setData(getItemList());
        mAdapter.notifyDataSetChanged();
        showStatusCompleted();
    }

    private List<String> getItemList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("-.- " + (i + 1));
        }
        return list;
    }


}
