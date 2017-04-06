package com.lodz.android.agiledev.ui.head;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.core.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 带头数据的RecyclerView测试类
 * Created by zhouL on 2017/4/6.
 */
public class HeadRecyclerViewActivity extends AbsActivity{

    private HeadRecyclerViewAdapter mAdapter;

    private List<String> mList;

    private String mHeadData;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_head_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mAdapter = new HeadRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter.onAttachedToRecyclerView(mRecyclerView);// 如果使用网格布局请设置此方法
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
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
                ToastUtils.showShort(getContext(), item);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mList = getItemList();
        mHeadData = "我是头布局";
        mAdapter.setHeadData(mHeadData);
        mAdapter.setData(mList);
        mAdapter.notifyDataSetChanged();
    }

    private List<String> getItemList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("S " + i);
        }
        return list;
    }


}
