package com.lodz.android.component.widget.adapter.recycler;

import java.util.List;

/**
 * RecyclerView加载更多帮助类
 * Created by zhouL on 2017/2/17.
 */
public class RecyclerLoadMoreHelper<T> {

    /** 加载更多适配器 */
    private BaseLoadMoreRecyclerViewAdapter<T> mAdapter;
    /** 监听器 */
    private Listener mListener;

    /**
     * 初始化
     * @param adapter 适配器
     */
    public void init(BaseLoadMoreRecyclerViewAdapter<T> adapter){
        mAdapter = adapter;
    }

    /**
     * 配置加载更多适配器（请在获得数据后进行初始化）
     * @param list 数据
     * @param sumSize 总条数
     * @param size 每页条数
     * @param isShowBottomLayout 是否显示底部提示界面
     * @param index 预加载偏移量，滑动到倒数第index个item时就回调加载接口（默认值为3）
     */
    public void config(List<T> list, int sumSize, int size, boolean isShowBottomLayout, int index){
        mAdapter.setLoadMoreParam(sumSize, size, isShowBottomLayout);
        mAdapter.setLoadIndex(index);
        mAdapter.setIsLoadMore(true);
        mAdapter.setIsShowLoadFail(false);
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 加载更多数据获取成功
     * @param list 总数据
     */
    public void loadMoreSuccess(List<T> list){
        mAdapter.setIsLoadMore(true);
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    /** 手动设置加载完成 */
    public void loadComplete(){
        mAdapter.setIsLoadMore(true);
        mAdapter.setLoadCompleted();
        mAdapter.notifyDataSetChanged();
    }

    /** 加载更多数据获取失败 */
    public void loadMoreFail(){
        mAdapter.setIsLoadMore(true);
        mAdapter.setIsShowLoadFail(true);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 设置监听器
     * @param listener 监听器
     */
    public void setListener(Listener listener){
        mListener = listener;
        mAdapter.setOnLoadMoreListener(new BaseLoadMoreRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int currentPage, int nextPage, int size, int position) {
                mAdapter.setIsLoadMore(false);
                if (mListener != null){
                    mListener.onLoadMore(currentPage, nextPage, size, position);
                }
            }
        });

        mAdapter.setOnLoadFailClickListener(new BaseLoadMoreRecyclerViewAdapter.OnLoadFailClickListener() {
            @Override
            public void onClickLoadFail(int reloadPage, int size) {
                mAdapter.setIsShowLoadFail(false);
                mAdapter.setIsLoadMore(false);
                mAdapter.notifyDataSetChanged();
                if (mListener != null){
                    mListener.onClickLoadFail(reloadPage, size);
                }
            }
        });
    }

    public interface Listener{
        /**
         * 加载更多
         * @param currentPage 当前页码
         * @param nextPage 需要加载的页码
         * @param size 每页大小
         * @param position 回调位置
         */
        void onLoadMore(int currentPage, int nextPage, int size, int position);

        /**
         * 点击加载失败
         * @param reloadPage 需要重载的页码
         * @param size 每页大小
         */
        void onClickLoadFail(int reloadPage, int size);
    }

}
