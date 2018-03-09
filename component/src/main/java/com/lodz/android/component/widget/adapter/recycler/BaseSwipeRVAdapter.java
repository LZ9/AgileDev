package com.lodz.android.component.widget.adapter.recycler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lodz.android.component.R;
import com.lodz.android.component.widget.adapter.bean.SwipeViewHolder;
import com.lodz.android.component.widget.adapter.swipe.SwipeMenuLayout;

/**
 * 带侧滑按钮的适配器基类
 * Created by zhouL on 2017/12/18.
 */

public abstract class BaseSwipeRVAdapter<T, VH extends SwipeViewHolder> extends BaseRecyclerViewAdapter<T>{

    public BaseSwipeRVAdapter(Context context) {
        super(context);
    }

    /** 获取内容布局 */
    @LayoutRes
    protected abstract int getContentLayout();

    /** 初始化右侧布局 */
    @LayoutRes
    protected int getRightLayout(){
        return 0;
    }

    /** 初始化左侧布局 */
    @LayoutRes
    protected int getLeftLayout(){
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VH holder = getViewHolder(parent, viewType);
        configSwipeViewHolder(holder);
        holder.bindView();
        return holder;
    }

    protected abstract VH getViewHolder(ViewGroup parent, int viewType);

    /** 获取侧滑的ItemView */
    protected View getSwipeItemView(ViewGroup parent){
        return getLayoutView(parent, R.layout.component_item_swipe_layout);
    }

    /** 配置侧滑菜单的ViewHolder */
    protected void configSwipeViewHolder(VH holder){
        if (getContentLayout() > 0){
            View contentView = getLayoutView(holder.contentLayout, getContentLayout());
            holder.contentLayout.addView(contentView);
        }
        if (getRightLayout() > 0){
            View rightView = getLayoutView(holder.rightLayout, getRightLayout());
            holder.rightLayout.addView(rightView);
        }
        if (getLeftLayout() > 0){
            View leftView = getLayoutView(holder.leftLayout, getLeftLayout());
            holder.leftLayout.addView(leftView);
        }
        holder.swipeMenuLayout.setSwipeEnable(getRightLayout() > 0 || getLeftLayout() > 0);//没有侧滑菜单禁止滑动
    }

    /** 关闭侧滑菜单 */
    protected void smoothCloseMenu(VH holder){
        holder.swipeMenuLayout.smoothCloseMenu();
    }

    /** 获取侧滑控件 */
    protected SwipeMenuLayout getSwipeMenuLayout(VH holder) {
        return holder.swipeMenuLayout;
    }

    @Override
    protected void setItemClick(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SwipeViewHolder){
            ((SwipeViewHolder) holder).contentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position >= 0 && mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(holder, getItem(position), position);
                    }
                }
            });
        }
    }

    @Override
    protected void setItemLongClick(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SwipeViewHolder){
            ((SwipeViewHolder) holder).contentLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (position >= 0 && mOnItemLongClickListener != null){
                        mOnItemLongClickListener.onItemLongClick(holder, getItem(position), position);
                    }
                    return true;
                }
            });
        }
    }
}
