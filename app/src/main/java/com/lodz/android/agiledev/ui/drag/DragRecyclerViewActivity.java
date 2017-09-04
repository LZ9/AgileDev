package com.lodz.android.agiledev.ui.drag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.component.widget.adapter.recycler.RecyclerViewDragHelper;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.log.PrintLog;
import com.lodz.android.core.utils.ToastUtils;
import com.lodz.android.core.utils.VibratorUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RecyclerView拖动测试类
 * Created by zhouL on 2017/3/6.
 */
public class DragRecyclerViewActivity extends BaseActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, DragRecyclerViewActivity.class);
        context.startActivity(starter);
    }

    /** 列表 */
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    /** 适配器 */
    private DragAdapter mAdapter;
    /** 拖拽帮助类 */
    private RecyclerViewDragHelper<String> mRecyclerViewDragHelper;
    /** 数据列表 */
    private List<String> mList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_drag_recycler_view_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBar(getTitleBarLayout());
        initRecyclerView();
    }

    private void initTitleBar(TitleBarLayout titleBarLayout) {
        titleBarLayout.setTitleName(R.string.drag_title);
    }

    /** 初始化RecyclerView */
    private void initRecyclerView() {
        mAdapter = new DragAdapter(getContext());
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerViewDragHelper = new RecyclerViewDragHelper<>();
        mRecyclerViewDragHelper
                .setUseDrag(true)// 设置是否允许拖拽
                .setUseLeftToRightSwipe(false)// 设置允许从左往右滑动
                .setUseRightToLeftSwipe(false)// 设置允许从右往左滑动
                .setEnabled(true)// 是否启用
                .build(mRecyclerView, mAdapter);
    }

    private RecyclerView.LayoutManager getLayoutManager() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        return layoutManager;
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
                VibratorUtil.vibrate(getContext(), 100);//长按震动
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
        for (int i = 0; i < 120; i++) {
            list.add(i+"");
        }
        return list;
    }
}
