package com.lodz.android.component.widget.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 带Head的RecyclerView
 * Created by zhouL on 2016/12/29.
 */
public abstract class BaseHeadRecyclerViewAdapter<H, T> extends BaseRecyclerViewAdapter<T>{

    /** 头部 */
    private static final int VIEW_TYPE_HEAD = 0;
    /** 数据列表 */
    private static final int VIEW_TYPE_ITEM = 1;

    /** 头信息数据 */
    private H mHeadData;

    public BaseHeadRecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return VIEW_TYPE_HEAD;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEAD){
            return getHeadViewHolder(parent);
        }
        return getGridListViewHolder(parent);
    }

    /** 获取头布局的ViewHolder */
    protected abstract RecyclerView.ViewHolder getHeadViewHolder(ViewGroup parent);
    /** 获取网格布局的ViewHolder */
    protected abstract RecyclerView.ViewHolder getGridListViewHolder(ViewGroup parent);

    /**
     * 设置头信息数据
     * @param headData 头信息数据
     */
    public void setHeadData(H headData){
        mHeadData = headData;
    }

    /** 获取头信息数据 */
    public H getHeadData(){
        return mHeadData;
    }

    /**
     * 通过正常的position获取带有Head的List数据
     * @param position 0开始的位置
     */
    protected T getItemUnderHead(int position) {
        return getItem(position - 1);
    }

    @Override
    protected void setItemClick(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position > 0 && mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder, getItemUnderHead(position), position - 1);
                }
            }
        });
    }

    @Override
    protected void setItemLongClick(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (position > 0 && mOnItemLongClickListener != null){
                    mOnItemLongClickListener.onItemLongClick(holder, getItemUnderHead(position), position - 1);
                }
                return false;
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {// 网格布局时要优化加载排版
            final GridLayoutManager layoutManager = ((GridLayoutManager) manager);
            if (layoutManager.getOrientation() == GridLayoutManager.HORIZONTAL){//横向排版不处理
                return;
            }
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return position == 0 ? layoutManager.getSpanCount() : 1;
                }
            });
        }
    }

}
