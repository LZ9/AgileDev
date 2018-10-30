package com.lodz.android.component.widget.popup;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.annotation.LayoutRes;
import androidx.core.content.ContextCompat;


/**
 * PopupWindow基类
 * Created by zhouL on 2017/9/5.
 */

public abstract class BasePopupWindow {

    /** 上下文 */
    private Context mContext;
    /** PopupWindow */
    private PopupWindow mPopupWindow;

    public BasePopupWindow(Context context) {
        mContext = context;
        mPopupWindow = createPopupWindow(mContext);
        findViews(mPopupWindow.getContentView());
        setListeners();
        initData();
    }

    /**
     * 创建一个PopupWindow
     * @param context 上下文
     */
    private PopupWindow createPopupWindow(Context context) {
        View popView = LayoutInflater.from(context).inflate(getLayoutId(), null);
        PopupWindow popupWindow = new PopupWindow(popView, getWidth(), getHeight(), true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(getElevationValue());
        }
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, android.R.color.white));
        return popupWindow;
    }

    /** 设置宽度，可重写该方法 */
    protected int getWidth(){
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    /** 设置高度，可重写该方法 */
    protected int getHeight(){
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    /** 设置阴影值，可重写该方法 */
    protected float getElevationValue(){
        return 12f;
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void findViews(View view);

    protected void setListeners() {
    }

    protected void initData() {
    }

    protected Context getContext(){
        return mContext;
    }

    public PopupWindow getPopup(){
        return mPopupWindow;
    }
}
