package com.lodz.android.component.widget.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecyclerView基类适配器
 * Created by zhouL on 2016/12/7.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    /** 数据列表 */
    private List<T> mData;
    /** item点击 */
    protected OnItemClickLitener<T> mOnItemClickLitener;
    /** item长按 */
    protected OnItemLongClickLitener<T> mOnItemLongClickLitener;

    private Context mContext;

    public BaseRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    public Context getContext(){
        return mContext;
    }

    /**
     * 获取数据
     * @param position 位置
     */
    public T getItem(int position) {
        if (mData == null || mData.size() == 0){
            return null;
        }
        return mData.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setItemClick(holder, position);
        setItemLongClick(holder, position);
        onBind(holder, position);
    }

    /** 设置点击事件 */
    protected void setItemClick(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position >= 0 && mOnItemClickLitener != null) {
                    mOnItemClickLitener.onItemClick(holder, getItem(position), position);
                }
            }
        });
    }

    /** 设置长按事件 */
    protected void setItemLongClick(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (position >= 0 && mOnItemLongClickLitener != null){
                    mOnItemLongClickLitener.onItemLongClick(holder, getItem(position), position);
                }
                return false;
            }
        });
    }

    protected abstract void onBind(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        if (mData == null){
            return 0;
        }
        return mData.size();
    }

    /**
     * 设置数据
     * @param data 数据列表
     */
    public void setData(List<T> data){
        this.mData = data;
    }

    /** 在onCreateViewHolder方法中根据layoutId获取View */
    protected View getLayoutView(ViewGroup parent, int layoutId){
        return LayoutInflater.from(mContext).inflate(layoutId, parent, false);
    }

    /**
     * 设置itemview的宽度
     * @param itemView holder的itemview
     * @param width 宽度
     */
    protected void setItemViewWidth(View itemView, int width){
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.width = width;
        itemView.setLayoutParams(layoutParams);
    }

    /** 设置点击事件监听器 */
    public void setOnItemClickLitener(OnItemClickLitener<T> litener){
        mOnItemClickLitener = litener;
    }
    /** 设置长按事件监听器 */
    public void setOnItemLongClickLitener(OnItemLongClickLitener<T> litener){
        mOnItemLongClickLitener = litener;
    }

    public interface OnItemClickLitener<T> {
        void onItemClick(RecyclerView.ViewHolder viewHolder, T item, int position);
    }

    public interface OnItemLongClickLitener<T> {
        void onItemLongClick(RecyclerView.ViewHolder viewHolder, T item, int position);
    }
}
