package com.lodz.android.component.photopicker.preview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lodz.android.component.photopicker.contract.preview.PreviewController;
import com.lodz.android.component.widget.photoview.PhotoView;
import com.lodz.android.core.utils.ArrayUtils;

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
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final Context context = container.getContext();
        final PhotoView imageView = new PhotoView(context);
        container.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        mPreviewBean.previewLoader.displayPreviewImg(context, mPreviewBean.sourceList.get(position), imageView);

        if (mPreviewBean.clickListener != null){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPreviewBean.clickListener.onClick(context, mPreviewBean.sourceList.get(position), position, mPreviewController);
                }
            });
        }

        if (mPreviewBean.longClickListener != null){
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mPreviewBean.longClickListener.onLongClick(context, mPreviewBean.sourceList.get(position), position, mPreviewController);
                    return true;
                }
            });
        }

        return imageView;
    }

}