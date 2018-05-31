package com.lodz.android.component.widget.adapter.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.FloatRange;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.lodz.android.core.array.Groupable;
import com.lodz.android.core.utils.DensityUtils;

/**
 * 分组标签装饰器
 * Created by zhouL on 2018/3/27.
 */

public class SectionItemDecoration<T> extends BaseItemDecoration{

    /** 默认文字大小 */
    private static final float DEFAULT_TEXT_SIZE_SP = 20;
    /** 默认分组高度 */
    private static final int DEFAULT_SECTION_HEIGHT_DP = 32;

    /** 数据回调 */
    OnGroupCallback<T> mGroupCallback;
    /** 文字画笔 */
    private TextPaint mTextPaint;
    /** 背景画笔 */
    private Paint mBgPaint;
    /** 分组高度 */
    int mSectionHeightPx;
    /** 文字左侧间距 */
    private int mTextPaddingLeftDp = 0;

    public static <T> SectionItemDecoration <T>create(Context context){
        return new SectionItemDecoration<>(context);
    }

    SectionItemDecoration(Context context) {
        super(context);
        mBgPaint = new Paint();
        mBgPaint.setColor(Color.GRAY);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setTextSize(DensityUtils.sp2pxFloat(getContext(), DEFAULT_TEXT_SIZE_SP));
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        mSectionHeightPx = DensityUtils.dp2px(getContext(), DEFAULT_SECTION_HEIGHT_DP);
    }

    /**
     * 设置分组的高度
     * @param dp 高度
     */
    public SectionItemDecoration setSectionHeight(int dp){
        mSectionHeightPx = DensityUtils.dp2px(getContext(), dp);
        return this;
    }

    /**
     * 设置回调
     * @param callback 回调
     */
    public SectionItemDecoration setOnGroupCallback(OnGroupCallback<T> callback){
        mGroupCallback = callback;
        return this;
    }

    /**
     * 设置文字左侧间距
     * @param dp 间距
     */
    public SectionItemDecoration setSectionTextPaddingLeftDp(int dp){
        mTextPaddingLeftDp = DensityUtils.dp2px(getContext(), dp);
        return this;
    }

    /**
     * 设置文字的大小
     * @param sp 文字大小
     */
    public SectionItemDecoration setSectionTextSize(@FloatRange(from = 1) float sp){
        mTextPaint.setTextSize(DensityUtils.sp2pxFloat(getContext(), sp <= 0 ? DEFAULT_TEXT_SIZE_SP : sp));
        return this;
    }

    /**
     * 设置文字的字体样式
     * @param typeface 字体样式，例如：Typeface.DEFAULT、Typeface.DEFAULT_BOLD等等
     */
    public SectionItemDecoration setSectionTextTypeface(Typeface typeface){
        mTextPaint.setTypeface(typeface);
        return this;
    }

    /**
     * 设置分组文字颜色
     * @param color 文字颜色
     */
    public SectionItemDecoration setSectionTextColorRes(@ColorRes int color){
        if (color != 0){
            mTextPaint.setColor(ContextCompat.getColor(getContext(), color));
        }
        return this;
    }

    /**
     * 设置分组文字颜色
     * @param color 文字颜色
     */
    public SectionItemDecoration setSectionTextColorInt(@ColorInt int color){
        mTextPaint.setColor(color);
        return this;
    }

    /**
     * 设置分组背景颜色
     * @param color 颜色
     */
    public SectionItemDecoration setSectionBgColorRes(@ColorRes int color){
        if (color != 0){
            mBgPaint.setColor(ContextCompat.getColor(getContext(), color));
        }
        return this;
    }

    /**
     * 设置分组背景颜色
     * @param color 颜色
     */
    public SectionItemDecoration setSectionBgColorInt(@ColorInt int color){
        mBgPaint.setColor(color);
        return this;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mGroupCallback == null){
            return;
        }
        if (!isVerLinearLayout(parent)){
            return;
        }
        outRect.top = isGroupItem(parent.getChildAdapterPosition(view)) ? mSectionHeightPx : 0;// 设置分组高度
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        if (!isVerLinearLayout(parent)){
            return;
        }
        if (mGroupCallback == null){
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
            if (!isGroupItem(position)){
                continue;
            }

            int top = view.getTop() - mSectionHeightPx;
            int bottom = view.getTop();
            drawBgPaint(canvas, left, top, right, bottom);
            drawTextPaint(canvas, getItem(position), left, top, right, bottom);
        }
    }

    /**
     * 画背景
     * @param canvas 画布
     * @param left 左侧坐标
     * @param top 顶部坐标
     * @param right 右侧坐标
     * @param bottom 底部坐标
     */
    void drawBgPaint(Canvas canvas, int left, int top, int right, int bottom){
        canvas.drawRect(checkValue(left), checkValue(top), checkValue(right), checkValue(bottom), mBgPaint);//绘制背景色
    }

    /**
     * 画文字
     * @param canvas 画布
     * @param text 文字
     * @param left 左侧坐标
     * @param top 顶部坐标
     * @param right 右侧坐标
     * @param bottom 底部坐标
     */
    void drawTextPaint(Canvas canvas, String text, int left, int top, int right, int bottom){
        if (!TextUtils.isEmpty(text)) {
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            int baseline = (int) ((bottom + top - fontMetrics.bottom - fontMetrics.top) / 2);//文字居中基线
            canvas.drawText(text.substring(0, 1),
                    checkValue(left + mTextPaddingLeftDp), checkValue(baseline), mTextPaint);//绘制文本
        }
    }

    /** 是否是垂直列表 */
    boolean isVerLinearLayout(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof GridLayoutManager){
            return false;
        }
        if (parent.getLayoutManager() instanceof LinearLayoutManager){
            LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();
            return manager.getOrientation() == LinearLayout.VERTICAL;
        }
        return false;
    }

    /**
     * 是否是分组的第一个数据
     * @param position 位置
     */
    boolean isGroupItem(int position) {
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
     * 是否是分组的最后一个数据
     * @param position 位置
     * @param itemCount 列表长度
     */
    boolean isLastGroupItem(int position, int itemCount){
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

    /**
     * 获取数据
     * @param position 位置
     */
    String getItem(int position) {
        if (position < 0){
            return "";
        }
        T t = mGroupCallback.getSourceItem(position);
        if (!(t instanceof Groupable) && !(t instanceof String)) {
            return "";
        }
        String item = t instanceof Groupable ? ((Groupable) t).getSortStr() : (String) t;
        if (TextUtils.isEmpty(item)) {
            return "";
        }
        return item;
    }

    public interface OnGroupCallback<T>{

        /**
         * 获取列表内容
         * @param position 位置
         */
        T getSourceItem(int position);
    }

}
