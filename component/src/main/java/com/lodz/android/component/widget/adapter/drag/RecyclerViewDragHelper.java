package com.lodz.android.component.widget.adapter.drag;

import android.content.Context;

import java.util.List;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView拖拽帮助类
 * Created by zhouL on 2017/3/16.
 */
public class RecyclerViewDragHelper<T> {

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

    private DragHelperCallback<T> mCallback;
    private ItemTouchHelper mItemTouchHelper;

    public RecyclerViewDragHelper(Context context) {
        this.mContext = context;
    }

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
     * 设置是否启用长按拖拽效果
     * @param enabled 是否启用
     */
    public RecyclerViewDragHelper setLongPressDragEnabled(boolean enabled) {
        isLongPressDragEnabled = enabled;
        return this;
    }

    /**
     * 设置是否启用滑动效果
     * @param enabled 是否启用
     */
    public RecyclerViewDragHelper setSwipeEnabled(boolean enabled){
        isSwipeEnabled = enabled;
        return this;
    }

    /**
     * 启用震动效果
     * @param enabled 是否启用
     */
    public RecyclerViewDragHelper setVibrateEnabled(boolean enabled){
        isVibrateEnabled = enabled;
        return this;
    }

    /**
     * 设置数据
     * @param list 数据列表
     */
    public void setList(List<T> list) {
        if (mCallback != null){
            mCallback.setList(list);
        }
    }

    /**
     * 设置监听器
     * @param listener 监听器
     */
    public void setListener(DragHelperCallback.Listener<T> listener) {
        if (mCallback != null){
            mCallback.setListener(listener);
        }
    }

    /**
     * 完成构建（使用默认的DragHelperCallback）
     * @param recyclerView 控件
     * @param adapter 适配器
     */
    public void build(RecyclerView recyclerView, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter){
        build(recyclerView, adapter, new DragHelperCallback<T>());
    }

    /**
     * 完成构建（扩展DragHelperCallback）
     * @param recyclerView 控件
     * @param adapter 适配器
     * @param callback 拖拽回调
     */
    public void build(RecyclerView recyclerView, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, DragHelperCallback<T> callback){
        callback.setContext(mContext);
        callback.setUseDrag(mUseDrag);
        callback.setUseRightToLeftSwipe(mUseRightToLeftSwipe);
        callback.setUseLeftToRightSwipe(mUseLeftToRightSwipe);
        callback.setLongPressDragEnabled(isLongPressDragEnabled);
        callback.setSwipeEnabled(isSwipeEnabled);
        callback.setVibrateEnabled(isVibrateEnabled);
        callback.setAdapter(adapter);
        mCallback = callback;
        build(recyclerView, callback);
    }

    /**
     * 完成构建（自定义ItemTouchHelper.Callback）
     * @param recyclerView 控件
     * @param callback 拖拽回调
     */
    public void build(RecyclerView recyclerView, ItemTouchHelper.Callback callback){
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /** 获取ItemTouchHelper */
    public ItemTouchHelper getItemTouchHelper(){
        return mItemTouchHelper;
    }
}
