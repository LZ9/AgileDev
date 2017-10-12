package com.lodz.android.component.photopicker.preview;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.lodz.android.component.widget.photoview.PhotoView;

/**
 * 图片翻页适配器
 * Created by zhouL on 2017/10/12.
 */

public class PicturePagerAdapter extends PagerAdapter {

    private PreviewBean mPreviewBean;

    public PicturePagerAdapter(PreviewBean previewBean) {
        this.mPreviewBean = previewBean;
    }

    @Override
    public int getCount() {
        return mPreviewBean.getPictureCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView imageView = new PhotoView(container.getContext());
        container.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return imageView;


//        imageConfig.getImageLoader().displayImage(context, getItem(position).path, holder.photo_image);
    }

    public interface Listener{
        void onClickPicture();
    }
}


//    public class BGAPhotoPageAdapter extends PagerAdapter {
//        private ArrayList<String> mPreviewImages;
//        private PhotoViewAttacher.OnViewTapListener mOnViewTapListener;
//        private Activity mActivity;
//
//        public BGAPhotoPageAdapter(Activity activity, PhotoViewAttacher.OnViewTapListener onViewTapListener, ArrayList<String> previewImages) {
//            mOnViewTapListener = onViewTapListener;
//            mPreviewImages = previewImages;
//            mActivity = activity;
//        }
//        @Override
//        public View instantiateItem(ViewGroup container, int position) {
//            final BGAImageView imageView = new BGAImageView(container.getContext());
//            container.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            final BGABrowserPhotoViewAttacher photoViewAttacher = new BGABrowserPhotoViewAttacher(imageView);
//            photoViewAttacher.setOnViewTapListener(mOnViewTapListener);
//            imageView.setDelegate(new BGAImageView.Delegate() {
//                @Override
//                public void onDrawableChanged(Drawable drawable) {
//                    if (drawable != null && drawable.getIntrinsicHeight() > drawable.getIntrinsicWidth() && drawable.getIntrinsicHeight() > BGAPhotoPickerUtil.getScreenHeight()) {
//                        photoViewAttacher.setIsSetTopCrop(true);
//                        photoViewAttacher.setUpdateBaseMatrix();
//                    } else {
//                        photoViewAttacher.update();
//                    }
//                }
//            });
//
//            BGAImage.display(imageView, R.mipmap.bga_pp_ic_holder_dark, mPreviewImages.get(position), BGAPhotoPickerUtil.getScreenWidth(), BGAPhotoPickerUtil.getScreenHeight());
//
//            return imageView;
//        }
//
//
//
//        public String getItem(int position) {
//            return mPreviewImages == null ? "" : mPreviewImages.get(position);
//        }
//    }