package com.lodz.android.agiledev.ui.customview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

/**
 * 自定义ViewGroup
 * Created by zhouL on 2018/6/1.
 */
public class CustomViewGroup extends ViewGroup{

    public CustomViewGroup(Context context) {
        super(context);
        init();
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //将所有的子View进行测量，这会触发每个子View的onMeasure函数
        //注意要与measureChild区分，measureChild是对单个view进行测量
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = getLayoutWidth(widthMeasureSpec);
        int measuredHeight = getLayoutHeight(heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    /**
     * 获取布局的宽度
     * @param widthMeasureSpec 宽度测量规范
     */
    private int getLayoutWidth(int widthMeasureSpec) {
        int childCount = getChildCount();// 子view总数

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);// 宽度类型
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);// 宽度大小

        switch (widthMode) {
            case MeasureSpec.UNSPECIFIED://如果没有指定大小，就设置为默认大小
                return childCount == 0 ? 0 : widthSize;
            case MeasureSpec.AT_MOST: //如果测量模式是最大取值为size
                return childCount == 0 ? 0 : getMaxChildWidth();
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                return widthSize;
            }
        }
        return widthSize;
    }

    /**
     * 获取布局的高度
     * @param heightMeasureSpec 高度测量规范
     */
    private int getLayoutHeight(int heightMeasureSpec) {
        int childCount = getChildCount();// 子view总数

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);// 高度类型
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);// 高度大小

        switch (heightMode) {
            case MeasureSpec.UNSPECIFIED://如果没有指定大小，就设置为默认大小
                return childCount == 0 ? 0 : heightSize;
            case MeasureSpec.AT_MOST: //如果测量模式是最大取值为size
                return childCount == 0 ? 0 : getTotleHeight();
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                return heightSize;
            }
        }
        return heightSize;
    }

    /*** 获取子View中宽度最大的值 */
    private int getMaxChildWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getMeasuredWidth() > maxWidth) {
                maxWidth = childView.getMeasuredWidth();
            }
        }
        return maxWidth;
    }

    /***
     * 将所有子View的高度相加
     **/
    private int getTotleHeight() {
        int childCount = getChildCount();
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            height += childView.getMeasuredHeight();
        }
        return height;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int curHeight = 0;//控件内的相对高度
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
            child.layout(0, curHeight,  width, curHeight + height);
            curHeight += height;
        }
    }
}
