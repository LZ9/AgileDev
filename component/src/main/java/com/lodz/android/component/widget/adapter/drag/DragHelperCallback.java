package com.lodz.android.component.widget.adapter.drag;

import android.content.Context;

import com.lodz.android.core.utils.VibratorUtil;

import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * 拖拽帮助回调
 * Created by zhouL on 2018/12/3.
 */
public class DragHelperCallback<T> extends ItemTouchHelper.Callback{

    /** 上下文 */
    private Context mContext;

    /** 允许拖拽 */
    private boolean mUseDrag = true;
    /** 允许从右往左滑动 */
    private boolean mUseRightToLeftSwipe = true;
    /** 允许从左往右滑动 */
    private boolean mUseLeftToRightSwipe = true;
    /** 启用长按拖拽效果 */
    private boolean isLongPressDragEnabled = true;
    /** 启用滑动效果 */
    private boolean isSwipeEnabled = true;
    /** 启用震动效果 */
    private boolean isVibrateEnabled = true;

    /** 适配器 */
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;
    /** 监听器 */
    private Listener<T> mListener;
    /** 数据列表 */
    private List<T> mList;

    /**
     * 设置上下文
     * @param context 上下文
     */
    public void setContext(Context context) {
        this.mContext = context;
    }

    /**
     * 设置允许拖拽
     * @param useDrag 允许拖拽
     */
    public void setUseDrag(boolean useDrag) {
        this.mUseDrag = useDrag;
    }

    /**
     * 设置允许从右往左滑动
     * @param useRightToLeftSwipe 允许从右往左滑动
     */
    public void setUseRightToLeftSwipe(boolean useRightToLeftSwipe) {
        this.mUseRightToLeftSwipe = useRightToLeftSwipe;
    }

    /**
     * 设置允许从左往右滑动
     * @param useLeftToRightSwipe 允许从左往右滑动
     */
    public void setUseLeftToRightSwipe(boolean useLeftToRightSwipe) {
        this.mUseLeftToRightSwipe = useLeftToRightSwipe;
    }

    /**
     * 设置启用长按拖拽效果
     * @param longPressDragEnabled 启用长按拖拽效果
     */
    public void setLongPressDragEnabled(boolean longPressDragEnabled) {
        isLongPressDragEnabled = longPressDragEnabled;
    }

    /**
     * 设置启用滑动效果
     * @param swipeEnabled 启用滑动效果
     */
    public void setSwipeEnabled(boolean swipeEnabled) {
        isSwipeEnabled = swipeEnabled;
    }

    /**
     * 设置启用震动效果
     * @param vibrateEnabled 启用震动效果
     */
    public void setVibrateEnabled(boolean vibrateEnabled) {
        isVibrateEnabled = vibrateEnabled;
    }

    /**
     * 设置适配器
     * @param adapter 适配器
     */
    public void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.mAdapter = adapter;
    }

    /**
     * 设置监听器
     * @param listener 监听器
     */
    public void setListener(Listener<T> listener) {
        this.mListener = listener;
    }

    /**
     * 设置数据列表
     * @param list 数据列表
     */
    public void setList(List<T> list) {
        this.mList = list;
    }

    // 配置拖拽类型
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags;
        int swipeFlags;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager){// 网格布局
            dragFlags = mUseDrag ? ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT : 0;
            swipeFlags = 0;
        } else if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){// 线性布局
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (layoutManager.getOrientation() == RecyclerView.VERTICAL){//纵向
                dragFlags = mUseDrag ? ItemTouchHelper.UP | ItemTouchHelper.DOWN : 0;
                swipeFlags = (mUseRightToLeftSwipe ?ItemTouchHelper.START : 0) // START允许从右往左、
                        | (mUseLeftToRightSwipe ? ItemTouchHelper.END : 0); // END允许从左往右
            } else {//横向
                dragFlags = mUseDrag ? ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT : 0;
                swipeFlags = 0;//横向不允许侧滑
            }
        }else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager){// 瀑布流布局
            dragFlags = mUseDrag ? ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT : 0;
            swipeFlags = 0;
        }else {// 其他布局
            dragFlags = mUseDrag ? ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT : 0;
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
        if (mAdapter != null){
            mAdapter.notifyItemMoved(fromPosition, toPosition);
        }
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
        if (mAdapter != null){
            mAdapter.notifyItemRemoved(position);
            if (position != mList.size()) { // 如果移除的是最后一个，忽略
                mAdapter.notifyItemRangeChanged(position, mList.size() - position);
            }
        }
        if (mListener != null){
            mListener.onListChanged(mList);
        }
    }

    // 当长按选中item时（拖拽开始时）调用
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            // do something
            if (isVibrateEnabled && mContext != null){
                VibratorUtil.vibrate(mContext, 100);//长按震动
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    // 当手指松开时（拖拽完成时）调用
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        // do something
    }

    // 是否启用长按拖拽效果
    @Override
    public boolean isLongPressDragEnabled() {
        return isLongPressDragEnabled;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return isSwipeEnabled;
    }

    public interface Listener<T>{
        /**
         * 列表状态变化回调
         * @param list 列表数据
         */
        void onListChanged(List<T> list);
    }
}
