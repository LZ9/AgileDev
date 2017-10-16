package com.lodz.android.component.widget.adapter.recycler;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView加载更多基类适配器
 * Created by zhouL on 2017/1/6.
 */
public abstract class BaseLoadMoreRVAdapter<T> extends BaseRecyclerViewAdapter<T> {

    /** 列表内容 */
    private static final int VIEW_TYPE_ITEM = 0;
    /** 正在加载更多 */
    private static final int VIEW_TYPE_LOADING_MORE = 1;
    /** 已加载完全部数据 */
    private static final int VIEW_TYPE_LOAD_FINISH = 2;
    /** 加载失败 */
    private static final int VIEW_TYPE_LOAD_FAIL = 3;
    /** 隐藏数据 */
    private static final int VIEW_TYPE_HIDE_ITEM = 4;


    /** 总条数 */
    private int mSumSize = 0;
    /** 每页条数 */
    private int mSize = 0;
    /** 当前为第一页 */
    private int mPage = 1;
    /** 预加载偏移量，默认滑动到倒数第3个item时就回调加载接口 */
    private int mLoadIndex = 3;
    /** 是否启动加载更多 */
    private boolean isLoadMore = false;
    /** 是否显示底部提示界面 */
    private boolean isShowBottomLayout = false;
    /** 是否显示加载失败页面 */
    private boolean isShowLoadFail = false;
    /** 存放需要隐藏位置的position */
    private List<Integer> mHidePositionList;

    /** 加载更多回调 */
    private OnLoadMoreListener mOnLoadMoreListener;
    /** 加载失败回调 */
    private OnLoadFailClickListener mOnLoadFailClickListener;
    /** 所有item都隐藏回调 */
    private OnAllItemHideListener mOnAllItemHideListener;

    /** 是否是GridLayoutManager */
    protected boolean isGridLayoutManager = false;

    public BaseLoadMoreRVAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowBottomLayout && (position == getItemCount() - 1)){// 启用底部提示页面 && 滑动到底部
            if (isShowLoadFail){
                return VIEW_TYPE_LOAD_FAIL;
            }
            if (mSumSize > getListItemCount()){
                return VIEW_TYPE_LOADING_MORE;
            }
            return VIEW_TYPE_LOAD_FINISH;
        }
        if (isHidePosition(position)){// 需要隐藏的数据
            return VIEW_TYPE_HIDE_ITEM;
        }
        return VIEW_TYPE_ITEM;
    }

    /**
     * 当前的position是否是需要隐藏的position
     * @param position 位置
     */
    private boolean isHidePosition(int position){
        if (mHidePositionList == null || mHidePositionList.size() == 0){
            return false;
        }
        for (int p: mHidePositionList) {
            if (p == position){
                return true;
            }
        }
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOAD_FAIL){
            return getLoadFailViewHolder(parent);
        }
        if (viewType == VIEW_TYPE_LOADING_MORE){
            return getLoadingMoreViewHolder(parent);
        }
        if (viewType == VIEW_TYPE_LOAD_FINISH){
            return getLoadFinishViewHolder(parent);
        }
        if (viewType == VIEW_TYPE_HIDE_ITEM){
            return getBlankViewHolder(parent);
        }
        return getItemViewHolder(parent);
    }

    /** 获取加载完毕的ViewHolder */
    private RecyclerView.ViewHolder getLoadFinishViewHolder(ViewGroup parent){
        return new LoadFinishViewHolder(getLayoutView(parent, getLoadFinishLayoutId()));
    }

    /** 获取正在加载更多的ViewHolder */
    private RecyclerView.ViewHolder getLoadingMoreViewHolder(ViewGroup parent){
        return new LoadingMoreViewHolder(getLayoutView(parent, getLoadingMoreLayoutId()));
    }

    /** 获取加载失败的ViewHolder */
    private RecyclerView.ViewHolder getLoadFailViewHolder(ViewGroup parent){
        return new LoadFailViewHolder(getLayoutView(parent, getLoadFailLayoutId()));
    }

    /** 获取空白占位的ViewHolder */
    private RecyclerView.ViewHolder getBlankViewHolder(ViewGroup parent) {
        return new BlankViewHolder(new LinearLayout(parent.getContext()));
    }

    /** 获取加载完毕的LayoutId */
    protected abstract int getLoadFinishLayoutId();

    /** 获取加载完毕的LayoutId */
    protected abstract int getLoadingMoreLayoutId();

    /** 获取加载失败的LayoutId */
    protected abstract int getLoadFailLayoutId();

    /** 获取Item的ViewHolder */
    protected abstract RecyclerView.ViewHolder getItemViewHolder(ViewGroup parent);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseLoadMoreRVAdapter.LoadFinishViewHolder){
            showLoadFinish(holder);
        }else if (holder instanceof BaseLoadMoreRVAdapter.LoadingMoreViewHolder){
            showLoadingMore(holder);
        } else if (holder instanceof BaseLoadMoreRVAdapter.LoadFailViewHolder){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnLoadFailClickListener != null){
                        mOnLoadFailClickListener.onClickLoadFail(mPage + 1, mSize);
                    }
                }
            });
            showLoadFail(holder);
        } else if (holder instanceof BaseLoadMoreRVAdapter.BlankViewHolder){
            // 空白占位不需要操作
        } else {
            super.onBindViewHolder(holder, position);
        }
        handleLoadMore(position);
    }

    /** 显示正在加载界面 */
    protected abstract void showLoadingMore(RecyclerView.ViewHolder holder);
    /** 显示加载完毕界面 */
    protected abstract void showLoadFinish(RecyclerView.ViewHolder holder);
    /** 显示加载失败界面 */
    protected abstract void showLoadFail(RecyclerView.ViewHolder holder);

    /**
     * 处理加载更多
     * @param position 当前滚动位置
     */
    private void handleLoadMore(int position) {
        if (getListItemCount() >= mSumSize){// 已经全部加载完成
            return;
        }
        if (getListItemCount() > mSize * mPage){// 已加载的数据总数大于预测总数则把当前页+1
            mPage++;
        }

        int loadIndex = mSize - mLoadIndex > 0 ? mLoadIndex : 0;// 计算预加载item的偏移量
        if ((getListItemCount() - loadIndex) == (position + 1) && isLoadMore && !isShowLoadFail){
            if (mOnLoadMoreListener != null){
                mOnLoadMoreListener.onLoadMore(mPage, (mPage + 1), mSize, position);
            }
        }
    }

    /** 获取数据列表Item数量 */
    private int getListItemCount() {
        return super.getItemCount();
    }

    /** 获取RecyclerView全部Item数量 */
    @Override
    public int getItemCount() {
        return isShowBottomLayout ? super.getItemCount() + 1 : super.getItemCount();
    }

    /**
     * 设置加载更多参数
     * @param sumSize 总条数
     * @param size 每页条数
     * @param isShowBottomLayout 是否显示底部提示界面
     */
    public void setLoadMoreParam(int sumSize, int size, boolean isShowBottomLayout){
        this.mSumSize = sumSize;
        this.mSize = size;
        this.isShowBottomLayout = isShowBottomLayout;
        this.mPage = 1;
        if (mHidePositionList != null){
            mHidePositionList.clear();
            mHidePositionList = null;
        }
        this.mHidePositionList = new ArrayList<>();
    }

    /**
     * 预加载偏移量，滑动到倒数第index个item时就回调加载接口（默认值为3）
     * @param index 倒数个数
     */
    public void setLoadIndex(int index){
        this.mLoadIndex = index;
    }

    /** 手动设置加载完成 */
    public void setLoadCompleted(){
        this.mSumSize = getListItemCount();
    }

    /** 是否开启加载更多 */
    public boolean isLoadMore() {
        return isLoadMore;
    }

    /**
     * 设置加载更多（正在加载时可以把开关关掉，加载完毕后再打开）
     * @param isLoadMore 是否加载更多
     */
    public void setIsLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    /** 是否处于加载失败状态 */
    public boolean isShowLoadFail() {
        return isShowLoadFail;
    }

    /**
     * 设置是否进入加载失败状态
     * @param isShowLoadFail 是否加载失败
     */
    public void setIsShowLoadFail(boolean isShowLoadFail) {
        this.isShowLoadFail = isShowLoadFail;
    }

    /**
     * 隐藏某个位置的Item
     * @param position 位置
     */
    public void hideItem(int position){
        if (mHidePositionList != null){
            mHidePositionList.add(position);
            if (mHidePositionList.size() == mSumSize && mOnAllItemHideListener != null){// 隐藏的item数等于总数
                mOnAllItemHideListener.onAllItemHide();
            }
        }
    }

    /**
     * 设置加载更多监听器
     * @param Listener 监听器
     */
    public void setOnLoadMoreListener(OnLoadMoreListener Listener){
        this.mOnLoadMoreListener = Listener;
    }

    /**
     * 设置加载失败点击监听器
     * @param Listener 监听器
     */
    public void setOnLoadFailClickListener(OnLoadFailClickListener Listener){
        this.mOnLoadFailClickListener = Listener;
    }

    /**
     * 设置所有item隐藏后的回调监听器
     * @param listener 监听器
     */
    public void setOnAllItemHideListener(OnAllItemHideListener listener){
        this.mOnAllItemHideListener = listener;
    }

    /** 加载完毕布局的ViewHolder */
    private class LoadFinishViewHolder extends RecyclerView.ViewHolder{
        private LoadFinishViewHolder(View itemView) {
            super(itemView);
        }
    }

    /** 加载更多布局的ViewHolder */
    private class LoadingMoreViewHolder extends RecyclerView.ViewHolder{
        private LoadingMoreViewHolder(View itemView) {
            super(itemView);
        }
    }

    /** 加载失败布局的ViewHolder */
    private class LoadFailViewHolder extends RecyclerView.ViewHolder{
        private LoadFailViewHolder(View itemView) {
            super(itemView);
        }
    }

    /** 空白的ViewHolder */
    private class BlankViewHolder extends RecyclerView.ViewHolder{
        private BlankViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnLoadMoreListener {
        /**
         * 加载更多
         * @param currentPage 当前页码
         * @param nextPage 需要加载的页码
         * @param size 每页大小
         * @param position 回调位置
         */
        void onLoadMore(int currentPage, int nextPage, int size, int position);
    }

    public interface OnLoadFailClickListener {
        /**
         * 点击加载失败
         * @param reloadPage 需要重载的页码
         * @param size 每页大小
         */
        void onClickLoadFail(int reloadPage, int size);
    }

    public interface OnAllItemHideListener {
        /** 所有item都隐藏了 */
        void onAllItemHide();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        isGridLayoutManager = manager instanceof GridLayoutManager;// 网格布局时要优化加载排版
        if(isGridLayoutManager) {
            adapterGridLayoutManager((GridLayoutManager) manager);
        }
    }

    /** 适配GridLayoutManager */
    private void adapterGridLayoutManager(final GridLayoutManager layoutManager) {
        if (layoutManager.getOrientation() == GridLayoutManager.HORIZONTAL){//横向排版不处理
            return;
        }
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                // 如果开启底部加载提示则需要减去一个item数量
                int size = isShowBottomLayout ?  layoutManager.getItemCount() - 1 : layoutManager.getItemCount();
                if ((position + 1) == size){// 滚到底部
                    return layoutManager.getSpanCount() - position % layoutManager.getSpanCount();
                }
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        adapterStaggeredGridLayoutManager(holder);
    }

    /** 适配StaggeredGridLayoutManager */
    private void adapterStaggeredGridLayoutManager(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            if (holder.getItemViewType() == VIEW_TYPE_LOADING_MORE || holder.getItemViewType() == VIEW_TYPE_LOAD_FINISH
                    || holder.getItemViewType() == VIEW_TYPE_LOAD_FAIL){//item的类型是加载更多 || 加载完成 || 加载失败时
                p.setFullSpan(true);
            }
        }
    }

}
