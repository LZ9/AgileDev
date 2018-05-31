package com.lodz.android.component.widget.adapter.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lodz.android.core.utils.ArrayUtils;

import java.util.List;

/**
 * 固定数据的分组标签装饰器
 * Created by zhouL on 2018/3/27.
 */

public class SectionFixItemDecoration<T> extends BaseSectionItemDecoration{

    /** 分组标题列表 */
    private List<String> mSections;
    /** 各组数据列表集 */
    List<List<T>> mSources;

    /**
     * 创建
     * @param context 上下文
     * @param sections 分组标题列表
     * @param sources 各组数据列表集
     */
    public static <T> SectionFixItemDecoration create(Context context, List<String> sections, List<List<T>> sources){
        if (ArrayUtils.isEmpty(sections) || ArrayUtils.isEmpty(sources)){
            throw new IllegalArgumentException("sections or sources is can not be empty");
        }

        if (ArrayUtils.getSize(sections) != ArrayUtils.getSize(sources)){
            throw new IllegalArgumentException("sections size and sources size must be consistent");
        }
        return new SectionFixItemDecoration<>(context, sections, sources);
    }

    SectionFixItemDecoration(Context context, List<String> sections, List<List<T>> sources) {
        super(context);
        mSections = sections;
        mSources = sources;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (!isVerLinearLayout(parent)){
            return;
        }
        int position = parent.getChildAdapterPosition(view);
        outRect.top = isFirstGroupItem(position) ? mSectionHeightPx : 0;// 设置分组高度
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
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
            if (!isFirstGroupItem(position)){
                continue;
            }

            int top = view.getTop() - mSectionHeightPx;
            int bottom = view.getTop();
            drawBgPaint(canvas, left, top, right, bottom);
            drawTextPaint(canvas, getSectionText(position), left, top, right, bottom);
        }
    }

    /**
     * 是否是分组的第一个数据
     * @param position 位置
     */
    boolean isFirstGroupItem(int position){
        int size = 0;
        for (List<T> list : mSources) {
            if (position == size){
                return true;
            }
            if (position < size){
                return false;
            }
            size += list.size();
        }
        return false;
    }

    /**
     * 获取分组标题文字
     * @param position 位置
     */
    String getSectionText(int position) {
        int index = 0;
        int size = 0;
        for (List<T> list : mSources) {
            if (position >= size && position < size + list.size()){
                return mSections.get(index);
            }
            size += list.size();
            index++;
        }
        return "";
    }

}
