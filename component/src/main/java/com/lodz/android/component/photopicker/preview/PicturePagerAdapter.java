package com.lodz.android.component.photopicker.preview;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lodz.android.component.photopicker.contract.PhotoLoader;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.component.widget.photoview.PhotoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 图片翻页适配器
 * Created by zhouL on 2017/10/12.
 */

class PicturePagerAdapter extends BaseRecyclerViewAdapter<Object> {

    /** 是否缩放 */
    private boolean isScale;
    /** 图片加载器 */
    private PhotoLoader<Object> mPhotoLoader;

    /**
     * 图片翻页适配器
     * @param context 上下文
     * @param isScale 是否缩放
     * @param photoLoader 图片加载器
     */
     PicturePagerAdapter(Context context, boolean isScale, PhotoLoader<Object> photoLoader) {
        super(context);
        this.isScale = isScale;
        mPhotoLoader = photoLoader;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FrameLayout frameLayout = new FrameLayout(parent.getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        frameLayout.setLayoutParams(layoutParams);
        return new DataViewHolder(frameLayout);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PicturePagerAdapter.DataViewHolder){
            showItem((PicturePagerAdapter.DataViewHolder) holder, position);
        }
    }

    private void showItem(PicturePagerAdapter.DataViewHolder holder, int position) {
        if (mPhotoLoader != null){
            mPhotoLoader.displayImg(getContext(), getItem(position), holder.photoImg);
        }
    }

    class DataViewHolder extends RecyclerView.ViewHolder{

        ImageView photoImg;

        private DataViewHolder(ViewGroup itemView) {
            super(itemView);
            photoImg = isScale ? new PhotoView(itemView.getContext()) : new ImageView(itemView.getContext());
            itemView.addView(photoImg, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            photoImg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
    }

    /** 释放资源 */
    void release(){
        mPhotoLoader = null;
    }
}