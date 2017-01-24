package com.snxun.component.widget.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 带Head的GridRecyclerView
 * Created by zhouL on 2016/12/29.
 */
public abstract class BaseHeadGridRecyclerViewAdapter<K, T> extends BaseRecyclerViewAdapter<T>{

    /** 头部 */
    private static final int VIEW_TYPE_HEAD = 0;
    /** 网格列表 */
    private static final int VIEW_TYPE_GRID = 1;

    /** 头信息数据 */
    protected K mHeadData;

    public BaseHeadGridRecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return VIEW_TYPE_HEAD;
        }
        return VIEW_TYPE_GRID;
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
    public void setHeadData(K headData){
        mHeadData = headData;
    }

    /**
     * 通过正常的position获取带有Head的List数据
     * @param position 0开始的位置
     */
    protected T getItemUnderHead(int position) {
        if (getItem(position - 1) == null){
            return null;
        }
        return getItem(position - 1);
    }

    @Override
    protected void setItemClick(RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position > 0 && mOnItemClickLitener != null) {
                    mOnItemClickLitener.onItemClick(v, getItemUnderHead(position), position - 1);
                }
            }
        });
    }

    @Override
    protected void setItemLongClick(RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (position > 0 && mOnItemLongClickLitener != null){
                    mOnItemLongClickLitener.onItemLongClick(v, getItemUnderHead(position), position - 1);
                }
                return false;
            }
        });
    }

    /**
     * 获取网格布局管理器
     * @param context 上下文
     * @param spanCount 列数
     */
    public static GridLayoutManager getGridLayoutManager(Context context, int spanCount) {
        final GridLayoutManager layoutManager = new GridLayoutManager(context, spanCount);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? layoutManager.getSpanCount() : 1;
            }
        });
        return layoutManager;
    }

}
