package com.lodz.android.component.widget.adapter.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.widget.LinearLayout;

import com.lodz.android.core.utils.DensityUtils;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.FloatRange;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 分组标签基类
 * Created by zhouL on 2018/5/31.
 */
public class BaseSectionItemDecoration extends BaseItemDecoration{

    /** 默认文字大小 */
    public static final float DEFAULT_TEXT_SIZE_SP = 20;
    /** 默认分组高度 */
    public static final int DEFAULT_SECTION_HEIGHT_DP = 32;

    /** 文字画笔 */
    private TextPaint mTextPaint;
    /** 背景画笔 */
    private Paint mBgPaint;
    /** 分组高度 */
    int mSectionHeightPx;
    /** 文字左侧间距 */
    private int mTextPaddingLeftDp = 0;

    BaseSectionItemDecoration(Context context) {
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
    public BaseSectionItemDecoration setSectionHeight(int dp){
        mSectionHeightPx = DensityUtils.dp2px(getContext(), dp);
        return this;
    }

    /**
     * 设置文字左侧间距
     * @param dp 间距
     */
    public BaseSectionItemDecoration setSectionTextPaddingLeftDp(int dp){
        mTextPaddingLeftDp = DensityUtils.dp2px(getContext(), dp);
        return this;
    }

    /**
     * 设置文字的大小
     * @param sp 文字大小
     */
    public BaseSectionItemDecoration setSectionTextSize(@FloatRange(from = 1) float sp){
        mTextPaint.setTextSize(DensityUtils.sp2pxFloat(getContext(), sp <= 0 ? DEFAULT_TEXT_SIZE_SP : sp));
        return this;
    }

    /**
     * 设置文字的字体样式
     * @param typeface 字体样式，例如：Typeface.DEFAULT、Typeface.DEFAULT_BOLD等等
     */
    public BaseSectionItemDecoration setSectionTextTypeface(Typeface typeface){
        mTextPaint.setTypeface(typeface);
        return this;
    }

    /**
     * 设置分组文字颜色
     * @param color 文字颜色
     */
    public BaseSectionItemDecoration setSectionTextColorRes(@ColorRes int color){
        if (color != 0){
            mTextPaint.setColor(ContextCompat.getColor(getContext(), color));
        }
        return this;
    }

    /**
     * 设置分组文字颜色
     * @param color 文字颜色
     */
    public BaseSectionItemDecoration setSectionTextColorInt(@ColorInt int color){
        mTextPaint.setColor(color);
        return this;
    }

    /**
     * 设置分组背景颜色
     * @param color 颜色
     */
    public BaseSectionItemDecoration setSectionBgColorRes(@ColorRes int color){
        if (color != 0){
            mBgPaint.setColor(ContextCompat.getColor(getContext(), color));
        }
        return this;
    }

    /**
     * 设置分组背景颜色
     * @param color 颜色
     */
    public BaseSectionItemDecoration setSectionBgColorInt(@ColorInt int color){
        mBgPaint.setColor(color);
        return this;
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
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        int baseline = (int) ((bottom + top - fontMetrics.bottom - fontMetrics.top) / 2);//文字居中基线
        canvas.drawText(text, checkValue(left + mTextPaddingLeftDp), checkValue(baseline), mTextPaint);//绘制文本
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
}
