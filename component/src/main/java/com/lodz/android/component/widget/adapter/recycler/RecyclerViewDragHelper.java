package com.lodz.android.component.widget.adapter.recycler;

import android.support.annotation.ColorInt;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;
import java.util.List;

/**
 * RecyclerView拖拽帮助类
 * Created by zhouL on 2017/3/16.
 */
public class RecyclerViewDragHelper<T> {

    /** 允许拖拽 */
    private boolean mUseDrag = true;
    /** 允许从右往左滑动 */
    private boolean mUseRightToLeftSwipe = false;
    /** 允许从左往右滑动 */
    private boolean mUseLeftToRightSwipe = false;
    /** 启用拖拽效果 */
    private boolean isEnabled = true;

    /** 拖拽时的背景颜色 */
    @ColorInt
    private int mDragingColor = 0;
    /** 拖拽完成的背景颜色 */
    @ColorInt
    private int mDraggedColor = 0;

    /** 适配器 */
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;
    /** 监听器 */
    private Listener<T> mListener;
    /** 数据列表 */
    private List<T> mList;

    /**
     * 设置是否允许拖拽
     * @param useDrag 允许拖动
     */
    public RecyclerViewDragHelper setUseDrag(boolean useDrag) {
        this.mUseDrag = useDrag;
        return this;
    }

    /**
     * 设置允许从右往左滑动
     * @param rightToLeftSwipe 从右往左滑动
     */
    public RecyclerViewDragHelper setUseRightToLeftSwipe(boolean rightToLeftSwipe) {
        this.mUseRightToLeftSwipe = rightToLeftSwipe;
        return this;
    }

    /**
     * 设置允许从左往右滑动
     * @param leftToRightSwipe 从左往右滑动
     */
    public RecyclerViewDragHelper setUseLeftToRightSwipe(boolean leftToRightSwipe) {
        this.mUseLeftToRightSwipe = leftToRightSwipe;
        return this;
    }

    /**
     * 设置是否启用
     * @param enabled 是否启用
     */
    public RecyclerViewDragHelper setEnabled(boolean enabled) {
        isEnabled = enabled;
        return this;
    }

    /**
     * 设置拖拽时的背景颜色
     * @param dragingColor 拖拽时背景颜色
     */
    public RecyclerViewDragHelper setDragingColor(@ColorInt int dragingColor) {
        this.mDragingColor = dragingColor;
        return this;
    }

    /**
     * 设置拖拽完成的背景颜色
     * @param draggedColor 拖拽完成背景颜色
     */
    public RecyclerViewDragHelper setDraggedColor(@ColorInt int draggedColor) {
        this.mDraggedColor = draggedColor;
        return this;
    }

    /**
     * 设置数据
     * @param list 数据列表
     */
    public void setList(List<T> list) {
        this.mList = list;
    }

    /**
     * 设置监听器
     * @param listener 监听器
     */
    public void setListener(Listener<T> listener) {
        this.mListener = listener;
    }

    /**
     * 完成构建
     * @param recyclerView 控件
     * @param adapter 适配器
     */
    public RecyclerViewDragHelper build(RecyclerView recyclerView, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter){
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mItemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        this.mAdapter = adapter;
        return this;
    }

    private ItemTouchHelper.Callback mItemTouchHelperCallback = new ItemTouchHelper.Callback() {

        // 配置拖拽类型
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags;
            int swipeFlags;
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager){// 网格布局
                dragFlags = mUseDrag ? ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT : 0;
                swipeFlags = 0;
            }else {// 其他布局
                dragFlags = mUseDrag ? ItemTouchHelper.UP | ItemTouchHelper.DOWN : 0;

                swipeFlags = (mUseRightToLeftSwipe ?ItemTouchHelper.START : 0) // START允许从右往左、
                        | (mUseLeftToRightSwipe ? ItemTouchHelper.END : 0); // END允许从左往右
            }
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        // 拖拽
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            if (mList == null){
                return false;
            }
            // 得到拖动ViewHolder的Position
            int fromPosition = viewHolder.getAdapterPosition();
            // 得到目标ViewHolder的Position
            int toPosition = target.getAdapterPosition();

            if (fromPosition < toPosition){//顺序小到大
                for (int i = fromPosition; i < toPosition; i++){
                    Collections.swap(mList, i, i + 1);
                }
            }else {//顺序大到小
                for (int i = fromPosition; i > toPosition; i--){
                    Collections.swap(mList, i, i - 1);
                }
            }
            mAdapter.notifyItemMoved(fromPosition, toPosition);
            if (mListener != null){
                mListener.onListChanged(mList);
            }
            return true;
        }

        // 滑动
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            if (mList == null){
                return;
            }
            int position = viewHolder.getAdapterPosition();
            mList.remove(position);
            mAdapter.notifyItemRemoved(position);
            if (position != mList.size()) { // 如果移除的是最后一个，忽略
                mAdapter.notifyItemRangeChanged(position, mList.size() - position);
            }
            if (mListener != null){
                mListener.onListChanged(mList);
            }
        }

        // 当长按选中item时（拖拽开始时）调用
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && mDragingColor != 0){
                viewHolder.itemView.setBackgroundColor(mDragingColor);//拖拽时的背景颜色
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        // 当手指松开时（拖拽完成时）调用
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            if (mDraggedColor != 0){
                viewHolder.itemView.setBackgroundColor(mDraggedColor);//拖拽完成的背景颜色
            }
        }

        // 是否禁止拖拽
        @Override
        public boolean isLongPressDragEnabled() {
            return isEnabled;
        }
    };

    public interface Listener<T>{
        /**
         * 列表状态变化回调
         * @param list 列表数据
         */
        void onListChanged(List<T> list);
    }
}
