package com.lodz.android.component.widget.adapter.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;

import com.lodz.android.core.array.Groupable;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 分组标签装饰器（分组标题由item里字符串的第一位截取）
 * 泛型T可以为String或者实现了Groupable的任意类
 * Created by zhouL on 2018/3/27.
 */

public class SectionItemDecoration<T> extends BaseSectionItemDecoration{

    /** 数据回调 */
    OnSectionCallback<T> mOnSectionCallback;

    /**
     * 创建
     * @param context 上下文
     */
    public static <T> SectionItemDecoration <T>create(Context context){
        return new SectionItemDecoration<>(context);
    }

    SectionItemDecoration(Context context) {
        super(context);
    }

    /**
     * 设置回调
     * @param callback 回调
     */
    public SectionItemDecoration setOnSectionCallback(OnSectionCallback<T> callback){
        mOnSectionCallback = callback;
        return this;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOnSectionCallback == null){
            return;
        }
        if (!isVerLinearLayout(parent)){
            return;
        }
        int position = parent.getChildAdapterPosition(view);
        if (TextUtils.isEmpty(getItem(position))) {
            return;
        }
        outRect.top = isFirstGroupItem(position) ? mSectionHeightPx : 0;// 设置分组高度
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
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
            drawTextPaint(canvas, getItem(position), left, top, right, bottom);
        }
    }

    /**
     * 是否是分组的第一个数据
     * @param position 位置
     */
    boolean isFirstGroupItem(int position) {
        if (position == 0){
            return true;
        }
        String current = getItem(position);
        String previous = getItem(position - 1);
        if (TextUtils.isEmpty(current) || TextUtils.isEmpty(previous)) {
            return false;
        }
        return !current.substring(0, 1).equals(previous.substring(0, 1));
    }

    /**
     * 获取数据
     * @param position 位置
     */
    String getItem(int position) {
        if (position < 0){
            return "";
        }
        T t = mOnSectionCallback.getSourceItem(position);
        if (!(t instanceof Groupable) && !(t instanceof String)) {
            return "";
        }
        String item = t instanceof Groupable ? ((Groupable) t).getSortStr() : (String) t;
        if (TextUtils.isEmpty(item)) {
            return "";
        }
        return item;
    }

    @Override
    void drawTextPaint(Canvas canvas, String text, int left, int top, int right, int bottom) {
        if (!TextUtils.isEmpty(text)) {
            text  = text.substring(0, 1);
        }
        super.drawTextPaint(canvas, text, left, top, right, bottom);
    }

    public interface OnSectionCallback<T>{

        /**
         * 获取列表内容
         * @param position 位置
         */
        T getSourceItem(int position);
    }

}
