package com.lodz.android.component.widget.index;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lodz.android.core.utils.ArrayUtils;

import java.util.List;

/**
 * 索引标题栏
 * Created by zhouL on 2018/6/1.
 */
public class IndexBar extends LinearLayout{

    /** 索引文字颜色 */
    @ColorInt
    private int mTextColor = Color.BLACK;
    /** 索引文字大小 */
    private int mTextSizeSp = 13;
    /** 文字是否粗体 */
    private boolean isTextBold = true;
    /** 按下索引栏的背景色 */
    @ColorInt
    private int mPressBgColor = Color.TRANSPARENT;
    /** 索引监听器 */
    private OnIndexListener mOnIndexListener;

    /** 提示控件 */
    private TextView mHintTextView;

    public IndexBar(Context context) {
        super(context);
        init();
    }

    public IndexBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public IndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){

    }

    /**
     * 设置索引监听器
     * @param listener 监听器
     */
    public void setOnIndexListener(OnIndexListener listener){
        mOnIndexListener = listener;
    }

    /**
     * 设置索引文字颜色
     * @param color 文字颜色
     */
    public void setIndexTextColorInt(@ColorInt int color){
        mTextColor = color;
    }

    /**
     * 设置索引文字颜色
     * @param color 文字颜色
     */
    public void setIndexTextColorRes(@ColorRes int color){
        mTextColor = ContextCompat.getColor(getContext(), color);
    }

    /**
     * 设置索引文字大小
     * @param sp 文字大小（单位sp）
     */
    public void setIndexTextSize(int sp){
        mTextSizeSp = sp;
    }

    /**
     * 文字是否粗体
     * @param isBold 是否粗体
     */
    public void setTextBold(boolean isBold){
        isTextBold = isBold;
    }

    /**
     * 设置按下索引栏的背景色
     * @param color 背景色
     */
    public void setPressBgColorInt(@ColorInt int color){
        mPressBgColor = color;
    }

    /**
     * 设置按下索引栏的背景色
     * @param color 背景色
     */
    public void setPressBgColorRes(@ColorRes int color){
        mPressBgColor = ContextCompat.getColor(getContext(), color);
    }

    /**
     * 设置提示控件
     * @param textView 控件
     */
    public void setHintTextView(TextView textView){
        mHintTextView = textView;
    }

    /**
     * 设置索引数据列表
     * @param list 列表
     */
    public void setIndexList(List<String> list){
        if (ArrayUtils.isEmpty(list)){
            return;
        }
        addTextView(list);
        requestLayout();
        invalidate();
    }

    /**
     * 添加索引
     * @param list 索引列表
     */
    private void addTextView(List<String> list) {
        for (String str : list) {
            TextView textView = new TextView(getContext());
            textView.setText(str);
            textView.setTextColor(mTextColor);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSizeSp);
            textView.setTypeface(isTextBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            textView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    getOrientation() == VERTICAL ? LayoutParams.MATCH_PARENT : 0,
                    getOrientation() == VERTICAL ? 0: LayoutParams.MATCH_PARENT, 1);
            addView(textView, layoutParams);
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                setBackgroundColor(mPressBgColor);
                if (mHintTextView != null){
                    mHintTextView.setVisibility(VISIBLE);
                }
                callbackTouch(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                if (mOnIndexListener != null){
                    mOnIndexListener.onEnd();
                }
                if (mHintTextView != null){
                    mHintTextView.setVisibility(GONE);
                }
                setBackgroundColor(Color.TRANSPARENT);
                break;
        }
        return true;
    }

    /**
     * 计算触摸回调
     * @param x x轴坐标
     * @param y y轴坐标
     */
    private void callbackTouch(float x, float y) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (!(view instanceof TextView)){
                continue;
            }
            TextView textView = (TextView) view;
            if (y >= textView.getTop() && y <= textView.getBottom()){
                if (mHintTextView != null){
                    mHintTextView.setText(textView.getText());
                }
                if (mOnIndexListener != null){
                    mOnIndexListener.onStart(i, textView.getText().toString());
                }
                return;
            }
        }
    }

    public interface OnIndexListener{

        /**
         * 开始
         * @param position 索引位置
         * @param indexText 索引文字
         */
        void onStart(int position, String indexText);

        /** 结束 */
        void onEnd();
    }

}
