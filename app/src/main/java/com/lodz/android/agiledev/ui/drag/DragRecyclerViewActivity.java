package com.lodz.android.agiledev.ui.drag;

import android.app.Service;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * RecyclerView拖动测试类
 * Created by zhouL on 2017/3/6.
 */
public class DragRecyclerViewActivity extends BaseActivity{

    private RecyclerView mRecyclerView;

    private DragAdapter mAdapter;

    private List<String> mList;

    private ItemTouchHelper mItemTouchHelper;

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
        mItemTouchHelper = new ItemTouchHelper(mItemTouchHelperCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private RecyclerView.LayoutManager getLayoutManager() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        return layoutManager;
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mAdapter.setOnItemLongClickLitener(new BaseRecyclerViewAdapter.OnItemLongClickLitener<String>() {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, String item, int position) {
//                if (viewHolder.getLayoutPosition() != mList.size() - 1){
//                    mItemTouchHelper.startDrag(viewHolder);
//                }
                Vibrator vibrator = (Vibrator) getContext().getSystemService(Service.VIBRATOR_SERVICE);
                if (vibrator != null){
                    vibrator.vibrate(100);
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mList = createList();
        mAdapter.setData(mList);
        mAdapter.notifyDataSetChanged();
        showStatusCompleted();
    }

    private List<String> createList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(i+"");
        }
        return list;
    }

    private ItemTouchHelper.Callback mItemTouchHelperCallback = new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags;
            int swipeFlags;
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager){// 网格布局
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                swipeFlags = 0;
            }else {// 其他布局
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;// START允许从右往左、END允许从左往右
            }
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            // 得到拖动ViewHolder的Position
            int fromPosition = viewHolder.getAdapterPosition();
            // 得到目标ViewHolder的Position
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition){
                for (int i = 0; i < toPosition; i++) {
                    Collections.swap(mList, i, i + 1);// 改变实际的数据集
                }
            }else {
                for (int i = 0; i > toPosition; i--) {
                    Collections.swap(mList, i, i - 1);// 改变实际的数据集
                }
            }
            mAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            mAdapter.notifyItemRemoved(position);
            mList.remove(position);
        }

        // 当长按选中item时（拖拽开始时）调用
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){
                viewHolder.itemView.setBackgroundColor(Color.RED);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        // 当手指松开时（拖拽完成时）调用
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(0);
        }

//        @Override
//        public boolean isLongPressDragEnabled() {
//            return false;// 禁止拖拽
//        }
    };
}
