package com.lodz.android.component.widget.adapter.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * 带Header和Footer的RecyclerView
 * Created by zhouL on 2016/12/29.
 */
public abstract class BaseHeaderFooterRVAdapter<H, T, F> extends BaseRecyclerViewAdapter<T>{

    /** 头部 */
    private static final int VIEW_TYPE_HEADER = 0;
    /** 数据列表 */
    private static final int VIEW_TYPE_ITEM = 1;
    /** 底部 */
    private static final int VIEW_TYPE_FOOTER = 2;

    /** 头信息数据 */
    private H mHeaderData;

    /** 头信息数据 */
    private F mFooterData;

    /** 头部点击 */
    private OnHeaderClickListener<H> mOnHeaderClickListener;
    /** 头部长按 */
    private OnHeaderLongClickListener<H> mOnHeaderLongClickListener;
    /** 底部点击 */
    private OnFooterClickListener<F> mOnFooterClickListener;
    /** 底部长按 */
    private OnFooterLongClickListener<F> mOnFooterLongClickListener;

    public BaseHeaderFooterRVAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderData != null){
            if (position == 0){
                return VIEW_TYPE_HEADER;
            }
        }
        if (mFooterData != null){
            if (position == getItemCount() - 1){
                return VIEW_TYPE_FOOTER;
            }
        }
        return VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER){
            return getHeaderViewHolder(parent);
        }
        if (viewType == VIEW_TYPE_FOOTER){
            return getFooterViewHolder(parent);
        }
        return getItemViewHolder(parent, viewType);
    }

    /** 获取头布局的ViewHolder */
    public abstract RecyclerView.ViewHolder getHeaderViewHolder(ViewGroup parent);
    /** 获取列表布局的ViewHolder */
    public abstract RecyclerView.ViewHolder getItemViewHolder(ViewGroup parent, int viewType);
    /** 获取头布局的ViewHolder */
    public abstract RecyclerView.ViewHolder getFooterViewHolder(ViewGroup parent);

    /**
     * 设置头部数据
     * @param headerData 头部数据
     */
    public void setHeaderData(H headerData){
        mHeaderData = headerData;
    }

    /**
     * 设置底部数据
     * @param footerData 底部数据
     */
    public void setFooterData(F footerData){
        mFooterData = footerData;
    }

    @Override
    public int getItemCount() {
        int count = super.getItemCount();//列表数据的数量
        if (mHeaderData != null){
            count += 1;
        }
        if (mFooterData != null){
            count += 1;
        }
        return count;
    }

    /** 获取头部数据 */
    public H getHeaderData(){
        return mHeaderData;
    }

    /** 获取底部数据 */
    public F getFooterData(){
        return mFooterData;
    }

    @Override
    public T getItem(int position) {
        if (mHeaderData != null){
            return super.getItem(position - 1);
        }
        return super.getItem(position);
    }

    @Override
    protected void setItemClick(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHeaderData == null && mFooterData == null){//没有头部 没有底部
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(holder, getItem(position), position);
                    }
                    return;
                }
                if (mHeaderData != null && mFooterData == null){// 有头部 没有底部
                    if (position == 0){
                        if (mOnHeaderClickListener != null){
                            mOnHeaderClickListener.onHeaderClick(holder, mHeaderData, position);
                        }
                        return;
                    }
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(holder, getItem(position), position - 1);
                    }
                    return;
                }

                if (mHeaderData == null){// 没有头部 有底部
                    if (position == getItemCount() - 1){
                        if (mOnFooterClickListener != null){
                            mOnFooterClickListener.onFooterClick(holder, mFooterData, position);
                        }
                        return;
                    }
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(holder, getItem(position), position);
                    }
                    return;
                }

                // 有头部 有底部
                if (position == 0){
                    if (mOnHeaderClickListener != null){
                        mOnHeaderClickListener.onHeaderClick(holder, mHeaderData, position);
                    }
                    return;
                }
                if (position == getItemCount() - 1){
                    if (mOnFooterClickListener != null){
                        mOnFooterClickListener.onFooterClick(holder, mFooterData, position);
                    }
                    return;
                }
                if(mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder, getItem(position), position - 1);
                }
            }
        });
    }

    @Override
    protected void setItemLongClick(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mHeaderData == null && mFooterData == null){//没有头部 没有底部
                    if (mOnItemLongClickListener != null){
                        mOnItemLongClickListener.onItemLongClick(holder, getItem(position), position);
                    }
                    return true;
                }
                if (mHeaderData != null && mFooterData == null){// 有头部 没有底部
                    if (position == 0){
                        if (mOnHeaderLongClickListener != null){
                            mOnHeaderLongClickListener.onHeaderLongClick(holder, mHeaderData, position);
                        }
                        return true;
                    }
                    if (mOnItemLongClickListener != null){
                        mOnItemLongClickListener.onItemLongClick(holder, getItem(position), position - 1);
                    }
                    return true;
                }

                if (mHeaderData == null){// 没有头部 有底部
                    if (position == getItemCount() - 1){
                        if (mOnFooterLongClickListener != null){
                            mOnFooterLongClickListener.onFooterLongClick(holder, mFooterData, position);
                        }
                        return true;
                    }
                    if (mOnItemLongClickListener != null){
                        mOnItemLongClickListener.onItemLongClick(holder, getItem(position), position);
                    }
                    return true;
                }

                // 有头部 有底部
                if (position == 0){
                    if (mOnHeaderLongClickListener != null){
                        mOnHeaderLongClickListener.onHeaderLongClick(holder, mHeaderData, position);
                    }
                    return true;
                }
                if (position == getItemCount() - 1){
                    if (mOnFooterLongClickListener != null){
                        mOnFooterLongClickListener.onFooterLongClick(holder, mFooterData, position);
                    }
                    return true;
                }
                if(mOnItemLongClickListener != null) {
                    mOnItemLongClickListener.onItemLongClick(holder, getItem(position), position - 1);
                }
                return true;
            }
        });
    }


    /** 设置头部点击事件监听器 */
    public void setOnHeaderClickListener(OnHeaderClickListener<H> listener){
        mOnHeaderClickListener = listener;
    }
    /** 设置头部长按事件监听器 */
    public void setOnHeaderLongClickListener(OnHeaderLongClickListener<H> listener){
        mOnHeaderLongClickListener = listener;
    }

    /** 设置底部点击事件监听器 */
    public void setOnFooterClickListener(OnFooterClickListener<F> listener){
        mOnFooterClickListener = listener;
    }
    /** 设置底部长按事件监听器 */
    public void setOnFooterLongClickListener(OnFooterLongClickListener<F> listener){
        mOnFooterLongClickListener = listener;
    }

    public interface OnHeaderClickListener<H> {
        void onHeaderClick(RecyclerView.ViewHolder viewHolder, H headerData, int position);
    }

    public interface OnHeaderLongClickListener<H> {
        void onHeaderLongClick(RecyclerView.ViewHolder viewHolder, H headerData, int position);
    }

    public interface OnFooterClickListener<F> {
        void onFooterClick(RecyclerView.ViewHolder viewHolder, F footerData, int position);
    }

    public interface OnFooterLongClickListener<F> {
        void onFooterLongClick(RecyclerView.ViewHolder viewHolder, F footerData, int position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {// 网格布局时要优化加载排版
            adapterGridLayoutManager((GridLayoutManager) manager);
        }
    }

    /** 适配GridLayoutManager */
    private void adapterGridLayoutManager(final GridLayoutManager layoutManager) {
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mHeaderData == null && mFooterData == null){//没有头部 没有底部
                    return 1;
                }
                if (mHeaderData != null && mFooterData == null){// 有头部 没有底部
                    return position == 0 ? layoutManager.getSpanCount() : 1;
                }

                if (mHeaderData == null){// 没有头部 有底部
                    return position == getItemCount() - 1 ? layoutManager.getSpanCount() : 1;
                }
                // 有头部 有底部
                if (position == 0 || position == getItemCount() - 1){
                    return layoutManager.getSpanCount();
                }
                return 1;
            }
        });
    }


    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        adapterStaggeredGridLayoutManager(holder);
    }

    /** 适配StaggeredGridLayoutManager */
    private void adapterStaggeredGridLayoutManager(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            if (holder.getItemViewType() == VIEW_TYPE_HEADER || holder.getItemViewType() == VIEW_TYPE_FOOTER){//item的类型是头部 || 底部
                p.setFullSpan(true);
            }
        }
    }

}
