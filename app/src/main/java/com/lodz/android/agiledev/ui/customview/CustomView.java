package com.lodz.android.agiledev.ui.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义View
 * Created by zhouL on 2018/6/1.
 */
public class CustomView extends View{

    /** 画笔 */
    private Paint mPaint;

    /** 半径 */
    private int mRadius;
    /** 圆心X坐标 */
    private int mCenterX;
    /** 圆心Y坐标 */
    private int mCenterY;

    /** 按下的X坐标 */
    private float mPressX;
    /** 按下的Y坐标 */
    private float mPressY;
    /** 圆点击监听器 */
    private OnClickListener mCircleClickListener;

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

    /**
     * 设置圆点击监听器
     * @param listener 监听器
     */
    public void setOnCircleClickListener(OnClickListener listener){
        mCircleClickListener = listener;
    }

    /**
     * 设置圆的颜色
     * @param color 颜色
     */
    public void setCircleColor(@ColorInt int color){
        mPaint.setColor(color);
        invalidate();
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
        mRadius = (getContentRight() - getContentLeft()) / 2;
        mCenterX = (getRight() - getLeft()) / 2;
        mCenterY = (getBottom() - getTop()) / 2;
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 获取用户按下时的X、Y坐标
                mPressX = event.getX();
                mPressY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 手指的偏移量绝对值超过10表示当前不是点击事件
                if (Math.abs(mPressX - event.getX()) > 10){
                    mPressX = -1;
                }
                if (Math.abs(mPressY - event.getY()) > 10){
                    mPressY = -1;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mPressX != -1 && mPressY != -1 && isInCircle(mPressX, mPressY, mCenterX, mCenterY, mRadius) && mCircleClickListener != null) {
                    mCircleClickListener.onClick(this);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mPressX = -1;
                mPressY = -1;
            default:
                break;
        }
        return true;
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

    /**
     * 某个点是否在圆内
     * @param x 点的X坐标
     * @param y 要判断的点的Y坐标
     * @param cx 圆心纵坐标
     * @param cy 圆心横坐标
     * @param radius 圆的半径
     */
    private boolean isInCircle(double x, double y, double cx, double cy, double radius) {
        double distance = Math.abs(Math.hypot((x - cx), (y - cy)));
        return distance <= radius;
    }
}
