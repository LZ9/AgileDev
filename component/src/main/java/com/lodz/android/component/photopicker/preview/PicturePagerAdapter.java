package com.lodz.android.component.photopicker.preview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lodz.android.component.photopicker.contract.preview.PreviewController;
import com.lodz.android.component.widget.photoview.PhotoView;
import com.lodz.android.core.utils.ArrayUtils;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * 图片翻页适配器
 * Created by zhouL on 2017/10/12.
 */

class PicturePagerAdapter extends PagerAdapter {

    private PreviewBean  mPreviewBean;

    private PreviewController mPreviewController;

    PicturePagerAdapter(PreviewBean previewBean, PreviewController controller) {
        this.mPreviewBean = previewBean;
        this.mPreviewController = controller;
    }

    @Override
    public int getCount() {
        return ArrayUtils.getSize(mPreviewBean.sourceList);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        final Context context = container.getContext();
        if (mPreviewBean == null){
            return container;
        }
        ImageView imageView = mPreviewBean.isScale ? new PhotoView(context) : new ImageView(context);
        container.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        if (mPreviewBean.photoLoader != null){
            mPreviewBean.photoLoader.displayImg(context, mPreviewBean.sourceList.get(position), imageView);
        }

        if (mPreviewBean.clickListener != null){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPreviewBean.clickListener != null) {
                        mPreviewBean.clickListener.onClick(context, mPreviewBean.sourceList.get(position), position, mPreviewController);
                    }
                }
            });
        }

        if (mPreviewBean.longClickListener != null){
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mPreviewBean.longClickListener != null) {
                        mPreviewBean.longClickListener.onLongClick(context, mPreviewBean.sourceList.get(position), position, mPreviewController);
                    }
                    return true;
                }
            });
        }

        return imageView;
    }

    void release(){
        mPreviewController = null;
        if (mPreviewBean != null){
            mPreviewBean.clear();
        }
    }
}