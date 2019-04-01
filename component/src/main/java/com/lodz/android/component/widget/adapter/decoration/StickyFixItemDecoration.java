package com.lodz.android.component.widget.adapter.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 固定数据的粘黏标签装饰器
 * Created by zhouL on 2018/5/31.
 */
public class StickyFixItemDecoration<T> extends SectionFixItemDecoration<T>{

    public static <T> StickyFixItemDecoration <T>create(Context context, List<String> sections, List<List<T>> source){
        return new StickyFixItemDecoration<>(context, sections, source);
    }

    /**
     * 创建
     * @param context 上下文
     * @param sections 分组标题列表
     * @param sources 各组数据列表集
     */
    private StickyFixItemDecoration(Context context, List<String> sections, List<List<T>> sources) {
        super(context, sections, sources);
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
        int childCount = parent.getChildCount();
        if (childCount == 0){
            return;
        }

        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);

            int top = 0;
            int bottom = mSectionHeightPx;

            int sectionTop = Math.max(top, view.getTop() - mSectionHeightPx);// 顶部section上坐标，取top和viewtop最大值
            int sectionBottom = Math.max(bottom, view.getTop());// 顶部section下坐标，取bottom和viewbottom的最大值

            if (isFirstGroupItem(position)) {// 分组的第一个数据，绘制分组样式
                drawBgPaint(canvas, left, sectionTop, right, sectionBottom);
                drawTextPaint(canvas, getSectionText(position), left, sectionTop, right, sectionBottom);
                continue;
            }

            if (isLastGroupItem(position) && view.getBottom() <= mSectionHeightPx) {// 分组的最后一个数据并且已经到达顶部，绘制过度动画样式
                drawBgPaint(canvas, left, top, right, view.getBottom());
                drawTextPaint(canvas, getSectionText(position), left, top, right, view.getBottom());
                continue;
            }

            if (i == 0){// 第一个view属于某个分组内的中间数据，则在顶部绘制固定section
                drawBgPaint(canvas, left, sectionTop, right, sectionBottom);
                drawTextPaint(canvas, getSectionText(position), left, sectionTop, right, sectionBottom);
            }
        }
    }

    /**
     * 是否是分组的最后一个数据
     * @param position 位置
     */
    private boolean isLastGroupItem(int position){
        int size = 0;
        for (List<T> list : mSources) {
            size += list.size();
            if (position + 1 == size){
                return true;
            }
            if (position < size){
                return false;
            }
        }
        return false;
    }
}
