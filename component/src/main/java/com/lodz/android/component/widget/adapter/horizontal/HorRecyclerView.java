package com.lodz.android.component.widget.adapter.horizontal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 解决横向滑动冲突RecyclerView
 * Created by zhouL on 2018/5/2.
 */
public class HorRecyclerView extends RecyclerView {

    /** 按下时的X和Y */
    private float mTouchX, mTouchY;
    /** 偏移量差值，当X轴的滑动偏移量差值大于这个值时，默认由内部来处理这个滑动事件 */
    private int mOffset = 20;

    public HorRecyclerView(Context context) {
        super(context);
    }

    public HorRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HorRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        final int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mTouchX = e.getX();
                mTouchY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float dx = e.getX() - mTouchX;
                final float dy = e.getY() - mTouchY;
                if (Math.abs(dx) > mOffset) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.onInterceptTouchEvent(e);
    }

    /**
     * 设置横向偏移量差值（默认20）
     * @param offset 偏移量差值
     */
    public void setHorOffset(int offset) {
        this.mOffset = offset;
    }
}