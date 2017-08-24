package com.lodz.android.component.widget.adapter.recycler;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.IntRange;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.lodz.android.component.widget.adapter.animation.AlphaInAnimation;
import com.lodz.android.component.widget.adapter.animation.BaseAnimation;

import java.util.List;

/**
 * RecyclerView基类适配器
 * Created by zhouL on 2016/12/7.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    /** 数据列表 */
    private List<T> mData;
    /** item点击 */
    protected OnItemClickListener<T> mOnItemClickListener;
    /** item长按 */
    protected OnItemLongClickListener<T> mOnItemLongClickListener;

    /** 上下文 */
    private Context mContext;

    /** 动画相关 */
    private Interpolator mInterpolator = new LinearInterpolator();
    /** item加载动画的最后位置 */
    private int mLastPosition = 0;
    /** 用户定义的item加载动画的开始位置 */
    private int mCustomStarPosition = 0;
    /** 是否需要item加载动画 */
    private boolean isOpenItemAnim = false;

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

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        addAnimation(holder);
    }

    /** 添加item加载动画 */
    private void addAnimation(RecyclerView.ViewHolder holder) {
        if (mData != null && mData.size() > 0 && mData.size() < mLastPosition){//重新设置了数据
            mLastPosition = mCustomStarPosition;
        }
        if (!isOpenItemAnim || holder.getLayoutPosition() <= mLastPosition){//不打开item动画 || 已经加载过了
            return;
        }

        BaseAnimation animation = new AlphaInAnimation();
        for (Animator anim : animation.getAnimators(holder.itemView)) {
            beginAnim(anim, animation);
        }
        mLastPosition = holder.getLayoutPosition();
    }

    /** 获取是否打开item加载动画 */
    public boolean isOpenItemAnim() {
        return isOpenItemAnim;
    }

    /**
     * 设置是否打开item加载动画
     * @param isOpen 是否打开
     */
    public void setOpenItemAnim(boolean isOpen) {
        isOpenItemAnim = isOpen;
    }

    /** 开始item加载动画 */
    private void beginAnim(Animator animator, BaseAnimation baseAnimation) {
        animator.setDuration(baseAnimation.getDuration()).start();
        animator.setInterpolator(mInterpolator);
    }

    /**
     * 设置item动画开始的位置
     * @param position 位置
     */
    public void setItemAnimStartPosition(@IntRange(from = 0) int position){
        mCustomStarPosition = position;
        mLastPosition = position;
    }

    /** 重置item动画 */
    public void resetItemAnimPosition(){
        setItemAnimStartPosition(mCustomStarPosition);
    }

    /** 设置点击事件 */
    protected void setItemClick(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position >= 0 && mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder, getItem(position), position);
                }
            }
        });
    }

    /** 设置长按事件 */
    protected void setItemLongClick(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (position >= 0 && mOnItemLongClickListener != null){
                    mOnItemLongClickListener.onItemLongClick(holder, getItem(position), position);
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

    /**
     * 设置itemview的高度
     * @param itemView holder的itemview
     * @param height 高度
     */
    protected void setItemViewHeight(View itemView, int height){
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.height = height;
        itemView.setLayoutParams(layoutParams);
    }

    /**
     * 带动画的删除item并刷新数据
     * @param position 位置
     */
    public void notifyItemRemovedChanged(int position){
        if (getItemCount() == 0){
            return;
        }
        mData.remove(position);
        notifyItemRemoved(position);
        if (position != mData.size()) { // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position, mData.size() - position);
        }
    }

    /** 设置点击事件监听器 */
    public void setOnItemClickListener(OnItemClickListener<T> listener){
        mOnItemClickListener = listener;
    }
    /** 设置长按事件监听器 */
    public void setOnItemLongClickListener(OnItemLongClickListener<T> listener){
        mOnItemLongClickListener = listener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(RecyclerView.ViewHolder viewHolder, T item, int position);
    }

    public interface OnItemLongClickListener<T> {
        void onItemLongClick(RecyclerView.ViewHolder viewHolder, T item, int position);
    }
}
