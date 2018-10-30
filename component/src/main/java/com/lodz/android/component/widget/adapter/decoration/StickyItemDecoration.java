package com.lodz.android.component.widget.adapter.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 粘黏标签装饰器（粘黏标签的文字由item里字符串的第一位截取）
 * 泛型T可以为String或者实现了Groupable的任意类
 * Created by zhouL on 2018/3/27.
 */

public class StickyItemDecoration<T> extends SectionItemDecoration<T>{

    public static <T> StickyItemDecoration <T>create(Context context){
        return new StickyItemDecoration<>(context);
    }

    /**
     * 创建
     * @param context 上下文
     */
    private StickyItemDecoration(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        if (!isVerLinearLayout(parent)){
            return;
        }
        if (mOnSectionCallback == null){
            return;
        }
        int childCount = parent.getChildCount();
        if (childCount == 0){
            return;
        }

        int itemCount = state.getItemCount();

        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            if (TextUtils.isEmpty(getItem(position))) {
                continue;
            }

            int top = parent.getTop();
            int bottom = parent.getTop() + mSectionHeightPx;

            int sectionTop = Math.max(top, view.getTop() - mSectionHeightPx);// 顶部section上坐标，取top和viewtop最大值
            int sectionBottom = Math.max(bottom, view.getTop());// 顶部section下坐标，取bottom和viewbottom的最大值

            if (isFirstGroupItem(position)) {// 分组的第一个数据，绘制分组样式
                drawBgPaint(canvas, left, sectionTop, right, sectionBottom);
                drawTextPaint(canvas, getItem(position), left, sectionTop, right, sectionBottom);
                continue;
            }

            if (isLastGroupItem(position, itemCount) && view.getBottom() <= mSectionHeightPx) {// 分组的最后一个数据并且已经到达顶部，绘制过度动画样式
                drawBgPaint(canvas, left, top, right, view.getBottom());
                drawTextPaint(canvas, getItem(position), left, top, right, view.getBottom());
                continue;
            }

            if (i == 0){// 第一个view属于某个分组内的中间数据，则在顶部绘制固定section
                drawBgPaint(canvas, left, sectionTop, right, sectionBottom);
                drawTextPaint(canvas, getItem(position), left, sectionTop, right, sectionBottom);
            }
        }
    }

    /**
     * 是否是分组的最后一个数据
     * @param position 位置
     * @param itemCount 列表长度
     */
    private boolean isLastGroupItem(int position, int itemCount){
        if (position + 1 >= itemCount){
            return true;
        }
        String current = getItem(position);
        String next = getItem(position + 1);
        if (TextUtils.isEmpty(current) || TextUtils.isEmpty(next)) {
            return false;
        }
        return !current.substring(0, 1).equals(next.substring(0, 1));
    }
}
