package com.lodz.android.component.widget.adapter.snap;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 预览图片PagerSnapHelper
 * Created by zhouL on 2018/11/13.
 */
public class ViewPagerSnapHelper extends PagerSnapHelper {

    /** 监听器 */
    private OnPageChangeListener mListener;
    /** 起始位置 */
    private int mStartPosition;

    public ViewPagerSnapHelper(int startPosition) {
        mStartPosition = startPosition;
    }

    @Override
    public void attachToRecyclerView(@Nullable final RecyclerView recyclerView) throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastPosition = mStartPosition;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    View centerView = findSnapView(recyclerView.getLayoutManager());
                    int position = recyclerView.getLayoutManager().getPosition(centerView);
                    if (lastPosition == 1 && position == 0){// 修复用户连续滑动回到第一个item时不会回调RecyclerView.SCROLL_STATE_IDLE状态的BUG
                        if (mListener != null) {
                            mListener.onPageSelected(position);
                        }
                        lastPosition = position;
                    }
                    return;
                }

                View centerView = findSnapView(recyclerView.getLayoutManager());
                int position = -1;
                if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    position = recyclerView.getLayoutManager().getPosition(centerView);//记录滑动中的位置
                }

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int idlePosition = recyclerView.getLayoutManager().getPosition(centerView);
                    if (position != idlePosition) {//滑动中位置和滑动结束位置不一致时更新位置数据
                        position = idlePosition;
                    }
                }
                if (lastPosition != position) {//获取的位置和最近一次的位置不一致时，通知外部变化
                    if (mListener != null) {
                        mListener.onPageSelected(position);
                    }
                }
                lastPosition = position;//更新最近一次的位置
            }
        });

    }

    /**
     * 设置选中监听器
     * @param listener 监听器
     */
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mListener = listener;
    }

    public interface OnPageChangeListener {
        void onPageSelected(int position);
    }
}
