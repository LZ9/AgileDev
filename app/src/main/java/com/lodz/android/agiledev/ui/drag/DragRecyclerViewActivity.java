package com.lodz.android.agiledev.ui.drag;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.component.widget.adapter.recycler.RecyclerViewDragHelper;
import com.lodz.android.core.log.PrintLog;
import com.lodz.android.core.utils.VibratorUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView拖动测试类
 * Created by zhouL on 2017/3/6.
 */
public class DragRecyclerViewActivity extends BaseActivity{

    private RecyclerView mRecyclerView;

    private DragAdapter mAdapter;

    private List<String> mList;

    private RecyclerViewDragHelper<String> mRecyclerViewDragHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_drag_recycler_view_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        initRecyclerView();
    }

    /** 初始化RecyclerView */
    private void initRecyclerView() {
        mAdapter = new DragAdapter(getContext());
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerViewDragHelper = new RecyclerViewDragHelper<>();
        mRecyclerViewDragHelper
                .setUseDrag(false)// 设置是否允许拖拽
                .setUseLeftToRightSwipe(true)// 设置允许从左往右滑动
                .setUseRightToLeftSwipe(false)// 设置允许从右往左滑动
                .setEnabled(true)// 是否启用
//                .setDragingColor(Color.GRAY)// 设置拖拽时的背景颜色
//                .setDraggedColor(Color.RED)// 设置拖拽完成的背景颜色
                .build(mRecyclerView, mAdapter);
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        return layoutManager;
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, String item, int position) {
                PrintLog.e("testtag", item);
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseRecyclerViewAdapter.OnItemLongClickListener<String>() {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, String item, int position) {
                VibratorUtil.vibrate(getContext(), 100);
            }
        });

        mRecyclerViewDragHelper.setListener(new RecyclerViewDragHelper.Listener<String>() {
            @Override
            public void onListChanged(List<String> list) {
                mList = list;
                PrintLog.d("testtag", mList.toString());
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mList = createList();
        mAdapter.setData(mList);
        mRecyclerViewDragHelper.setList(mList);
        mAdapter.notifyDataSetChanged();
        showStatusCompleted();
    }

    private List<String> createList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list.add(i+"");
        }
        return list;
    }
}