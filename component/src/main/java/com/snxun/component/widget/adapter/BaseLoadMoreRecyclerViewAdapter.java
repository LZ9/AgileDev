package com.snxun.component.widget.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * RecyclerView加载更多基类适配器
 * Created by zhouL on 2017/1/6.
 */
public abstract class BaseLoadMoreRecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T> {

    /** 列表内容 */
    private static final int VIEW_TYPE_ITEM = 0;
    /** 正在加载更多 */
    private static final int VIEW_TYPE_LOADING_MORE = 1;
    /** 已加载完全部数据 */
    private static final int VIEW_TYPE_LOAD_FINISH = 2;
    /** 加载失败 */
    private static final int VIEW_TYPE_LOAD_FAIL = 3;

    /** 总条数 */
    private int mSumSize = 0;
    /** 每页条数 */
    private int mSize = 0;
    /** 当前为第一页 */
    private int mPage = 1;
    /** 预加载偏移量，默认滑动到倒数第5个item时就回调加载接口 */
    private int mLoadIndex = 5;
    /** 是否启动加载更多 */
    private boolean isLoadMore = false;
    /** 是否显示底部提示界面 */
    private boolean isShowBottomLayout = false;
    /** 是否显示加载失败页面 */
    private boolean isShowLoadFail = false;

    /** 加载更多回调 */
    private OnLoadMoreLitener mOnLoadMoreLitener;
    /** 加载失败回调 */
    private OnLoadFailClickLitener mOnLoadFailClickLitener;

    public BaseLoadMoreRecyclerViewAdapter(Context context) {
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
        return VIEW_TYPE_ITEM;
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
        if (holder instanceof BaseLoadMoreRecyclerViewAdapter.LoadFinishViewHolder){
            showLoadFinish(holder);
        }else if (holder instanceof BaseLoadMoreRecyclerViewAdapter.LoadingMoreViewHolder){
            showLoadingMore(holder);
        } else if (holder instanceof BaseLoadMoreRecyclerViewAdapter.LoadFailViewHolder){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnLoadFailClickLitener != null){
                        mOnLoadFailClickLitener.onClickLoadFail(mPage + 1, mSize);
                    }
                }
            });
            showLoadFail(holder);
        } else {
            super.onBindViewHolder(holder, position);
        }
        handleLoadMore(position);
    }

    /** 显示加载完毕界面 */
    protected abstract void showLoadingMore(RecyclerView.ViewHolder holder);
    /** 显示正在加载界面 */
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
            if (mOnLoadMoreLitener != null){
                mOnLoadMoreLitener.onLoadMore(mPage, (mPage + 1), mSize, position);
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
    }

    /**
     * 预加载偏移量，滑动到倒数第index个item时就回调加载接口（默认值为5）
     * @param index 倒数个数
     */
    public void setLoadIndex(int index){
        this.mLoadIndex = index;
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
     * 设置加载更多监听器
     * @param litener 监听器
     */
    public void setOnLoadMoreLitener(OnLoadMoreLitener litener){
        this.mOnLoadMoreLitener = litener;
    }

    /**
     * 设置加载失败点击监听器
     * @param litener 监听器
     */
    public void setOnLoadFailClickLitener(OnLoadFailClickLitener litener){
        this.mOnLoadFailClickLitener = litener;
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

    public interface OnLoadMoreLitener {
        /**
         * 加载更多
         * @param currentPage 当前页码
         * @param nextPage 需要加载的页码
         * @param size 每页大小
         * @param position 回调位置
         */
        void onLoadMore(int currentPage, int nextPage, int size, int position);
    }

    public interface OnLoadFailClickLitener {
        /**
         * 点击加载失败
         * @param reloadPage 需要重载的页码
         * @param size 每页大小
         */
        void onClickLoadFail(int reloadPage, int size);
    }
}
