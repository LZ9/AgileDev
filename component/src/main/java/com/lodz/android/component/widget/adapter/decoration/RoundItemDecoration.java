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
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lodz.android.core.utils.DensityUtils;

/**
 * 外围分割线装饰器
 * Created by zhouL on 2018/2/7.
 */

public class RoundItemDecoration extends BaseItemDecoration{

    /**
     * 创建
     * @param context 上下文
     */
    public static RoundItemDecoration create(Context context){
        return new RoundItemDecoration(context);
    }

    /**
     * 创建底部分割线
     * @param context 上下文
     * @param dp 间距
     * @param color 分割线颜色（使用默认值传0）
     * @param bgColor 背景颜色（使用默认值传0）
     * @param lrPaddingDp 左右间隔
     */
    public static RoundItemDecoration createBottomDivider(Context context, @IntRange(from = 1) int dp
            , @ColorRes int color, @ColorRes int bgColor, @IntRange(from = 0) int lrPaddingDp){
        RoundItemDecoration decoration = new RoundItemDecoration(context);
        decoration.setBottomDividerRes(dp, color, bgColor, lrPaddingDp);
        return decoration;
    }

    /** 顶部间距 */
    private int mTopPx = 0;
    /** 顶部左右间隔 */
    private int mTopLrPadding = 0;
    /** 顶部画笔 */
    private Paint mTopPaint;
    /** 顶部背景画笔 */
    private Paint mTopBgPaint;

    /** 底部间距 */
    private int mBottomPx = 0;
    /** 底部左右间隔 */
    private int mBottomLrPadding = 0;
    /** 底部画笔 */
    private Paint mBottomPaint;
    /** 底部背景画笔 */
    private Paint mBottomBgPaint;

    /** 左侧间距 */
    private int mLeftPx = 0;
    /** 左侧上下间隔 */
    private int mLeftTbPadding = 0;
    /** 左侧画笔 */
    private Paint mLeftPaint;
    /** 左侧背景画笔 */
    private Paint mLeftBgPaint;

    /** 右侧间距 */
    private int mRightPx = 0;
    /** 右侧上下间隔 */
    private int mRightTbPadding = 0;
    /** 右侧画笔 */
    private Paint mRightPaint;
    /** 右侧背景画笔 */
    private Paint mRightBgPaint;

    private RoundItemDecoration(Context context) {
        super(context);
    }

    /**
     * 设置顶部分割线
     * @param dp 间距
     * @param color 分割线颜色（使用默认值传0）
     * @param bgColor 背景颜色（使用默认值传0）
     * @param lrPaddingDp 左右间隔
     */
    public RoundItemDecoration setTopDividerRes(@IntRange(from = 1) int dp, @ColorRes int color, @ColorRes int bgColor, @IntRange(from = 0) int lrPaddingDp){
        return setTopDividerInt(dp, color != 0 ? ContextCompat.getColor(getContext(), color) : Color.GRAY
                , bgColor != 0 ? ContextCompat.getColor(getContext(), bgColor) : Color.WHITE, lrPaddingDp);
    }

    /**
     * 设置顶部分割线
     * @param dp 间距
     * @param color 分割线颜色
     * @param bgColor 背景颜色
     * @param lrPaddingDp 左右间隔
     */
    public RoundItemDecoration setTopDividerInt(@IntRange(from = 1) int dp, @ColorInt int color, @ColorInt int bgColor, @IntRange(from = 0) int lrPaddingDp){
        mTopPx = DensityUtils.dp2px(getContext(), dp);
        mTopLrPadding = DensityUtils.dp2px(getContext(), lrPaddingDp);

        if (mTopPaint == null){
            mTopPaint = new Paint();
        }
        mTopPaint.setColor(color);

        if (mTopBgPaint == null){
            mTopBgPaint = new Paint();
        }
        mTopBgPaint.setColor(bgColor);
        return this;
    }

    /**
     * 设置底部分割线
     * @param dp 间距
     * @param color 颜色（使用默认值传0）
     * @param bgColor 背景颜色（使用默认值传0）
     * @param lrPaddingDp 左右间隔
     */
    public RoundItemDecoration setBottomDividerRes(@IntRange(from = 1) int dp, @ColorRes int color, @ColorRes int bgColor, @IntRange(from = 0) int lrPaddingDp){
        return setBottomDividerInt(dp, color != 0 ? ContextCompat.getColor(getContext(), color) : Color.GRAY
                , bgColor != 0 ? ContextCompat.getColor(getContext(), bgColor) : Color.WHITE, lrPaddingDp);
    }

    /**
     * 设置底部分割线
     * @param dp 间距
     * @param color 颜色
     * @param bgColor 背景颜色
     * @param lrPaddingDp 左右间隔
     */
    public RoundItemDecoration setBottomDividerInt(@IntRange(from = 1) int dp, @ColorInt int color, @ColorInt int bgColor, @IntRange(from = 0) int lrPaddingDp){
        mBottomPx = DensityUtils.dp2px(getContext(), dp);
        mBottomLrPadding = DensityUtils.dp2px(getContext(), lrPaddingDp);

        if (mBottomPaint == null){
            mBottomPaint = new Paint();
        }
        mBottomPaint.setColor(color);

        if (mBottomBgPaint == null){
            mBottomBgPaint = new Paint();
        }
        mBottomBgPaint.setColor(bgColor);
        return this;
    }

    /**
     * 设置左侧分割线
     * @param dp 间距
     * @param color 颜色（使用默认值传0）
     * @param bgColor 背景颜色（使用默认值传0）
     * @param tbPaddingDp 上下间隔
     */
    public RoundItemDecoration setLeftDividerRes(@IntRange(from = 1) int dp, @ColorRes int color, @ColorRes int bgColor, @IntRange(from = 0) int tbPaddingDp){
        return setLeftDividerInt(dp, color != 0 ? ContextCompat.getColor(getContext(), color) : Color.GRAY
                , bgColor != 0 ? ContextCompat.getColor(getContext(), bgColor) : Color.WHITE, tbPaddingDp);
    }

    /**
     * 设置左侧分割线
     * @param dp 间距
     * @param color 颜色
     * @param bgColor 背景颜色
     * @param tbPaddingDp 上下间隔
     */
    public RoundItemDecoration setLeftDividerInt(@IntRange(from = 1) int dp, @ColorInt int color, @ColorInt int bgColor, @IntRange(from = 0) int tbPaddingDp){
        mLeftPx = DensityUtils.dp2px(getContext(), dp);
        mLeftTbPadding = DensityUtils.dp2px(getContext(), tbPaddingDp);

        if (mLeftPaint == null){
            mLeftPaint = new Paint();
        }
        mLeftPaint.setColor(color);

        if (mLeftBgPaint == null){
            mLeftBgPaint = new Paint();
        }
        mLeftBgPaint.setColor(bgColor);
        return this;
    }


    /**
     * 设置右侧分割线
     * @param dp 间距
     * @param color 颜色（使用默认值传0）
     * @param bgColor 背景颜色（使用默认值传0）
     * @param tbPaddingDp 上下间隔
     */
    public RoundItemDecoration setRightDividerRes(@IntRange(from = 1) int dp, @ColorRes int color, @ColorRes int bgColor, @IntRange(from = 0) int tbPaddingDp){
        return setRightDividerInt(dp, color != 0 ? ContextCompat.getColor(getContext(), color) : Color.GRAY
                , bgColor != 0 ? ContextCompat.getColor(getContext(), bgColor) : Color.WHITE, tbPaddingDp);
    }

    /**
     * 设置右侧分割线
     * @param dp 间距
     * @param color 颜色
     * @param bgColor 背景颜色
     * @param tbPaddingDp 上下间隔
     */
    public RoundItemDecoration setRightDividerInt(@IntRange(from = 1) int dp, @ColorInt int color, @ColorInt int bgColor, @IntRange(from = 0) int tbPaddingDp){
        mRightPx = DensityUtils.dp2px(getContext(), dp);
        mRightTbPadding = DensityUtils.dp2px(getContext(), tbPaddingDp);

        if (mRightPaint == null){
            mRightPaint = new Paint();
        }
        mRightPaint.setColor(color);

        if (mRightBgPaint == null){
            mRightBgPaint = new Paint();
        }
        mRightBgPaint.setColor(bgColor);
        return this;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mTopPx > 0){
            outRect.top = mTopPx;
        }
        if (mBottomPx > 0){
            outRect.bottom = mBottomPx;
        }
        if (mLeftPx > 0){
            outRect.left = mLeftPx;
        }
        if (mRightPx > 0){
            outRect.right = mRightPx;
        }
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (mTopPx <= 0 && mBottomPx <= 0 && mLeftPx <= 0 && mRightPx <= 0){
            return;
        }
        int childCount = parent.getChildCount();
        if (childCount == 0){
            return;
        }
        if (mTopPx > 0){
            drawTopDivider(canvas, parent, childCount, mTopPx, mTopLrPadding, mTopPaint, mTopBgPaint);
        }
        if (mBottomPx > 0){
            drawBottomDivider(canvas, parent, childCount, mBottomPx, mBottomLrPadding, mBottomPaint, mBottomBgPaint);
        }
        if (mLeftPx > 0){
            drawLeftDivider(canvas, parent, childCount, mLeftPx, mLeftTbPadding, mLeftPaint, mLeftBgPaint);
        }
        if (mRightPx > 0){
            drawRightDivider(canvas, parent, childCount, mRightPx, mRightTbPadding, mRightPaint, mRightBgPaint);
        }
    }

    /**
     * 绘制顶部分割线
     * @param canvas 画布
     * @param parent 父控件
     * @param childCount 子项数量
     * @param px 间距
     * @param padding 间隔
     * @param paint 画笔
     * @param bgPaint 背景画笔
     */
    private void drawTopDivider(Canvas canvas, RecyclerView parent, int childCount, int px, int padding, Paint paint, Paint bgPaint) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int top = view.getTop() - px;
            int bottom = view.getTop();
            if (bgPaint != null){
                canvas.drawRect(checkValue(left), checkValue(top), checkValue(right), checkValue(bottom), bgPaint);
            }
            canvas.drawRect(checkValue(left + padding), checkValue(top), checkValue(right - padding), checkValue(bottom), paint);
        }
    }

    /**
     * 绘制底部分割线
     * @param canvas 画布
     * @param parent 父控件
     * @param childCount 子项数量
     * @param px 间距
     * @param padding 间隔
     * @param paint 画笔
     * @param bgPaint 背景画笔
     */
    private void drawBottomDivider(Canvas canvas, RecyclerView parent, int childCount, int px, int padding, Paint paint, Paint bgPaint) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int top = view.getBottom();
            int bottom = view.getBottom() + px;
            if (bgPaint != null){
                canvas.drawRect(checkValue(left), checkValue(top), checkValue(right), checkValue(bottom), bgPaint);
            }
            canvas.drawRect(checkValue(left + padding), checkValue(top), checkValue(right - padding), checkValue(bottom), paint);
        }
    }

    /**
     * 绘制左侧分割线
     * @param canvas 画布
     * @param parent 父控件
     * @param childCount 子项数量
     * @param px 间距
     * @param padding 间隔
     * @param paint 画笔
     * @param bgPaint 背景画笔
     */
    private void drawLeftDivider(Canvas canvas, RecyclerView parent, int childCount, int px, int padding, Paint paint, Paint bgPaint) {
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int top = view.getTop();
            int bottom = view.getBottom();
            int left = view.getLeft() - px;
            int right = view.getLeft();
            if (bgPaint != null){
                canvas.drawRect(checkValue(left), checkValue(top), checkValue(right), checkValue(bottom), bgPaint);
            }
            canvas.drawRect(checkValue(left), checkValue(top + padding), checkValue(right), checkValue(bottom - padding), paint);
        }
    }

    /**
     * 绘制右侧分割线
     * @param canvas 画布
     * @param parent 父控件
     * @param childCount 子项数量
     * @param px 间距
     * @param padding 间隔
     * @param paint 画笔
     * @param bgPaint 背景画笔
     */
    private void drawRightDivider(Canvas canvas, RecyclerView parent, int childCount, int px, int padding, Paint paint, Paint bgPaint) {
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int top = view.getTop();
            int bottom = view.getBottom();
            int left = view.getRight();
            int right = view.getRight() + px;
            if (bgPaint != null){
                canvas.drawRect(checkValue(left), checkValue(top), checkValue(right), checkValue(bottom), bgPaint);
            }
            canvas.drawRect(checkValue(left), checkValue(top + padding), checkValue(right), checkValue(bottom - padding), paint);
        }
    }
}
