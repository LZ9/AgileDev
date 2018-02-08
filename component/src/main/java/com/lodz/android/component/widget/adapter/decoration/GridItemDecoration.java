package com.lodz.android.component.widget.adapter.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IntRange;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lodz.android.core.utils.DensityUtils;

/**
 * 网格分割线装饰器
 * Created by zhouL on 2018/2/8.
 */

public class GridItemDecoration extends BaseItemDecoration{

    public GridItemDecoration(Context context) {
        super(context);
    }

    /** 外部间距 */
    private int mPx = 0;
    /** 顶部画笔 */
    private Paint mPaint;

    /**
     * 设置分割线
     * @param dp 间距
     * @param color 分割线颜色
     */
    public void setDividerRes(@IntRange(from = 1) int dp, @ColorRes int color){
        setDividerInt(dp, color > 0 ? ContextCompat.getColor(getContext(), color) : Color.GRAY);
    }

    /**
     * 设置分割线
     * @param dp 间距
     * @param color 分割线颜色
     */
    public void setDividerInt(@IntRange(from = 1) int dp, @ColorInt int color){
        mPx = DensityUtils.dp2px(getContext(), dp);

        if (mPaint == null){
            mPaint = new Paint();
        }
        mPaint.setColor(color);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mPx <= 0){
            return;
        }

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager){// 网格
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            setGridOffsets(outRect, view, parent, gridLayoutManager);
            return;
        }

        if (layoutManager instanceof LinearLayoutManager){// 线性
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            setLinearOffsets(outRect, view, parent, linearLayoutManager);
            return;
        }
    }

    /** 设置LinearLayoutManager的边距 */
    private void setLinearOffsets(Rect outRect, View view, RecyclerView parent, LinearLayoutManager layoutManager) {
        int position = parent.getChildAdapterPosition(view);
        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL){//纵向
            outRect.top = position == 0 ? mPx : 0;
            outRect.bottom = mPx;
            outRect.left = mPx;
            outRect.right = mPx;
            return;
        }

        // 横向
        outRect.top = mPx;
        outRect.bottom = mPx;
        outRect.left = position == 0 ? mPx : 0;
        outRect.right = mPx;
    }

    /** 设置GridLayoutManager的边距 */
    private void setGridOffsets(Rect outRect, View view, RecyclerView parent, GridLayoutManager layoutManager) {
        int position = parent.getChildAdapterPosition(view);
        int spanCount = layoutManager.getSpanCount();

        if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL){//纵向
            if (position == 0){// 第一个
                outRect.top = mPx;
                outRect.bottom = mPx;
                outRect.left = mPx;
                outRect.right = mPx;
                return;
            }
            if (position < spanCount){//第一排
                outRect.top = mPx;
                outRect.bottom = mPx;
                outRect.left = 0;
                outRect.right = mPx;
                return;
            }
            if (position % spanCount == 0){//最左侧
                outRect.top = 0;
                outRect.bottom = mPx;
                outRect.left = mPx;
                outRect.right = mPx;
                return;
            }

            outRect.top = 0;
            outRect.bottom = mPx;
            outRect.left = 0;
            outRect.right = mPx;
            return;
        }


        // 横向
        parent.setPadding(0, 0, 0, mPx);
        // 横向
        if (position < spanCount){//最左侧
            outRect.top = mPx;
            outRect.bottom = 0;
            outRect.left = mPx;
            outRect.right = mPx;
            return;
        }

        outRect.top = mPx;
        outRect.bottom = 0;
        outRect.left = 0;
        outRect.right = mPx;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (mPx <= 0){
            return;
        }
        int childCount = parent.getChildCount();
        if (childCount == 0){
            return;
        }

        drawTopDivider(canvas, parent, childCount, mPx, mPaint);
        drawBottomDivider(canvas, parent, childCount, mPx, mPaint);
        drawLeftDivider(canvas, parent, childCount, mPx, mPaint);
        drawRightDivider(canvas, parent, childCount, mPx, mPaint);

    }

    /**
     * 绘制顶部分割线
     * @param canvas 画布
     * @param parent 父控件
     * @param childCount 子项数量
     * @param px 间距
     * @param paint 画笔
     */
    private void drawTopDivider(Canvas canvas, RecyclerView parent, int childCount, int px, Paint paint) {
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int top = view.getTop() - px;
            int bottom = view.getTop();
            int left = view.getLeft() - px;
            int right = view.getRight() + px;
            canvas.drawRect(checkValue(left), checkValue(top), checkValue(right), checkValue(bottom), paint);
        }
    }

    /**
     * 绘制底部分割线
     * @param canvas 画布
     * @param parent 父控件
     * @param childCount 子项数量
     * @param px 间距
     * @param paint 画笔
     */
    private void drawBottomDivider(Canvas canvas, RecyclerView parent, int childCount, int px, Paint paint) {
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int top = view.getBottom();
            int bottom = view.getBottom() + px;
            int left = view.getLeft() - px;
            int right = view.getRight() + px;
            canvas.drawRect(checkValue(left), checkValue(top), checkValue(right), checkValue(bottom), paint);
        }
    }

    /**
     * 绘制左侧分割线
     * @param canvas 画布
     * @param parent 父控件
     * @param childCount 子项数量
     * @param px 间距
     * @param paint 画笔
     */
    private void drawLeftDivider(Canvas canvas, RecyclerView parent, int childCount, int px, Paint paint) {
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int top = view.getTop();
            int bottom = view.getBottom();
            int left = view.getLeft() - px;
            int right = view.getLeft();
            canvas.drawRect(checkValue(left), checkValue(top), checkValue(right), checkValue(bottom), paint);
        }
    }

    /**
     * 绘制右侧分割线
     * @param canvas 画布
     * @param parent 父控件
     * @param childCount 子项数量
     * @param px 间距
     * @param paint 画笔
     */
    private void drawRightDivider(Canvas canvas, RecyclerView parent, int childCount, int px, Paint paint) {
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int top = view.getTop();
            int bottom = view.getBottom();
            int left = view.getRight();
            int right = view.getRight() + px;
            canvas.drawRect(checkValue(left), checkValue(top), checkValue(right), checkValue(bottom), paint);
        }
    }


}
