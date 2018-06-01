package com.lodz.android.agiledev.ui.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义View
 * Created by zhouL on 2018/6/1.
 */
public class CustomView extends View{

    private Paint mPaint;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getSize(100, widthMeasureSpec);
        int height = getSize(100, heightMeasureSpec);
        int size = Math.min(width, height);
        setMeasuredDimension(size, size);
    }


    private int getSize(int defaultSize, int measureSpec) {
        int result = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                result = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                result = size > defaultSize ? defaultSize : size;//最大值大于默认值时取默认值，否则取最大值
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                result = size;
                break;
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = (getContentRight() - getContentLeft()) / 2;
        int centerX = (getRight() - getLeft()) / 2;
        int centerY = (getBottom() - getTop()) / 2;
        canvas.drawCircle(centerX, centerY, radius, mPaint);

    }

    private int getContentLeft(){
        return getLeft() + getPaddingLeft();
    }

    private int getContentRight(){
        return getRight() - getPaddingRight();
    }

    private int getContentTop(){
        return getTop() + getPaddingTop();
    }

    private int getContentBottom(){
        return getBottom() - getPaddingBottom();
    }
}
