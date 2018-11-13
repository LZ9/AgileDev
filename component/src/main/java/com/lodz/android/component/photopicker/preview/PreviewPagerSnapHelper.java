package com.lodz.android.component.photopicker.preview;

import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 预览图片PagerSnapHelper
 * Created by zhouL on 2018/11/13.
 */
class PreviewPagerSnapHelper extends PagerSnapHelper {

    private OnPageChangeListener mListener;

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        int position =  super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
        if (mListener != null){
            mListener.onPageSelected(position);
        }
        return position;
    }

    /**
     * 设置选中监听器
     * @param listener 监听器
     */
    void setOnPageChangeListener(OnPageChangeListener listener){
        mListener = listener;
    }

    interface OnPageChangeListener{
        void onPageSelected(int position);
    }
}
