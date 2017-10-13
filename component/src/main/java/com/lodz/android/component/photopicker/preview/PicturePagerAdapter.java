package com.lodz.android.component.photopicker.preview;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lodz.android.component.widget.photoview.PhotoView;
import com.lodz.android.core.utils.ArrayUtils;

/**
 * 图片翻页适配器
 * Created by zhouL on 2017/10/12.
 */

class PicturePagerAdapter extends PagerAdapter {

    private PreviewBean  mPreviewBean;

    PicturePagerAdapter(PreviewBean previewBean) {
        this.mPreviewBean = previewBean;
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
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView imageView = new PhotoView(container.getContext());
        container.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        mPreviewBean.previewLoader.displayPreviewImg(container.getContext(), mPreviewBean.sourceList.get(position), imageView);
        return imageView;
    }

    public interface Listener{
        void onClickPicture();
    }


}