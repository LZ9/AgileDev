package com.lodz.android.component.widget.ninegrid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lodz.android.component.R;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;

/**
 * 图片九宫格适配器
 * Created by zhouL on 2018/3/8.
 */

public class NineGridAdapter extends BaseRecyclerViewAdapter<String>{

    /** 添加按钮 */
    private static final int VIEW_TYPE_ADD = 0;
    /** 图片 */
    private static final int VIEW_TYPE_ITEM = 1;

    /** 是否需要添加图标 */
    private boolean isNeedAddBtn = true;
    /** 添加图标 */
    private Drawable mAddBtnDrawable;
    /** 是否需要删除按钮 */
    private boolean isShowDelete = true;
    /** 删除图标 */
    private Drawable mDeleteBtnDrawable;
    /** 最大图片数 */
    private int maxPic = 1;
    /** itme高度 */
    private int mItemHighPx = 0;

    /** 监听器 */
    private OnNineGridViewListener mListener;

    public NineGridAdapter(Context context) {
        super(context);
    }

    /**
     * 设置是否需要添加图标
     * @param needAddBtn 是否需要添加图标
     */
    public void setNeedAddBtn(boolean needAddBtn) {
        isNeedAddBtn = needAddBtn;
    }

    /**
     * 设置添加图标
     * @param drawable 图标
     */
    public void setAddBtnDrawable(Drawable drawable) {
        this.mAddBtnDrawable = drawable;
    }

    /**
     * 设置是否显示删除按钮
     * @param isShowDelete 是否显示删除按钮
     */
    public void setShowDelete(boolean isShowDelete) {
        this.isShowDelete = isShowDelete;
    }

    /**
     * 设置删除图标
     * @param drawable 图标
     */
    public void setDeleteBtnDrawable(Drawable drawable) {
        this.mDeleteBtnDrawable = drawable;
    }

    /**
     * 设置最大图片数
     * @param count 最大图片数
     */
    public void setMaxPic(@IntRange(from = 1) int count) {
        maxPic = count;
    }

    /** 获取最大图片数 */
    public int getMaxPic() {
        return maxPic;
    }

    /**
     * 设置itme的高度（单位px）
     * @param px 高度
     */
    public void setItemHigh(int px) {
        this.mItemHighPx = px;
    }

    @Override
    public int getItemViewType(int position) {
        if (!isNeedAddBtn){//不需要添加按钮
            return VIEW_TYPE_ITEM;
        }
        if (getDataSize() == maxPic){//已经添加满数据了
            return VIEW_TYPE_ITEM;
        }

        if (getDataSize() == 0){//没有数据
            return VIEW_TYPE_ADD;
        }

        if (position == getDataSize()){//最后一位
            return VIEW_TYPE_ADD;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        if (!isNeedAddBtn){
            return super.getItemCount();
        }
        if (super.getItemCount() == maxPic){//照片数量和总数相等
            return super.getItemCount();
        }
        return super.getItemCount() + 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return viewType == VIEW_TYPE_ADD ? new NineGridAddViewHolder(getLayoutView(parent, R.layout.component_item_nine_grid_add_layout))
                : new NineGridViewHolder(getLayoutView(parent, R.layout.component_item_nine_grid_layout));
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        if (mItemHighPx > 0){
            setItemViewHeight(holder.itemView, mItemHighPx);
        }
        if (holder instanceof NineGridAddViewHolder){
            showAddItem((NineGridAddViewHolder) holder);
            return;
        }

        String data = getItem(position);
        if (TextUtils.isEmpty(data)) {
            return;
        }
        showItem((NineGridViewHolder) holder, data);
    }

    private void showAddItem(NineGridAddViewHolder holder) {
        if (mAddBtnDrawable != null){
            holder.addBtn.setImageDrawable(mAddBtnDrawable);
        }else {
            holder.addBtn.setImageResource(R.drawable.component_ic_nine_grid_add);
        }
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onAddPic(maxPic - getDataSize());
                }
            }
        });
    }

    private void showItem(final NineGridViewHolder holder, String data) {
        if (mListener != null){
            mListener.onDisplayImg(getContext(), data, holder.img);
        }
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClickPic(getItem(holder.getAdapterPosition()), holder.getAdapterPosition());
                }
            }
        });

        if (mDeleteBtnDrawable != null){
            holder.deleteBtn.setImageDrawable(mDeleteBtnDrawable);
        }else {
            holder.deleteBtn.setImageResource(R.drawable.component_ic_nine_grid_delete);
        }
        holder.deleteBtn.setVisibility(isShowDelete ? View.VISIBLE : View.GONE);
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onDeletePic(getItem(holder.getAdapterPosition()), holder.getAdapterPosition());
                }
            }
        });
    }

    private class NineGridAddViewHolder extends RecyclerView.ViewHolder{

        /** 添加按钮 */
        private ImageView addBtn;

        private NineGridAddViewHolder(View itemView) {
            super(itemView);
            addBtn = itemView.findViewById(R.id.add_btn);
        }
    }

    protected class NineGridViewHolder extends RecyclerView.ViewHolder{

        /** 图片 */
        private ImageView img;
        /** 删除按钮 */
        private ImageView deleteBtn;

        private NineGridViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
        }
    }

    public void setOnNineGridViewListener(OnNineGridViewListener listener){
        mListener = listener;
    }

}
