package com.lodz.android.component.widget.base;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lodz.android.component.R;


/**
 * 加载失败控件
 * Created by zhouL on 2016/11/17.
 */

public class ErrorLayout extends LinearLayout{

    /** 加载失败布局 */
    private LinearLayout mErrorRootLayout;
    /** 失败图片 */
    private ImageView mErrorImageView;
    /** 提示语 */
    private TextView mErrorTipsTextView;

    public ErrorLayout(Context context) {
        super(context);
        init();
    }

    public ErrorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ErrorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        findViews();
        initData();
    }

    private void findViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_error_view, this);
        mErrorRootLayout = (LinearLayout) findViewById(R.id.error_root_layout);
        mErrorImageView = (ImageView) findViewById(R.id.error_imageview);
        mErrorTipsTextView = (TextView) findViewById(R.id.error_tips_textview);
    }

    private void initData() {
        needImg(true);
        needTips(false);
    }

    /**
     * 设置无数据图片
     * @param drawableResId 图片资源id
     */
    public void setImg(@DrawableRes int drawableResId){
        mErrorImageView.setImageResource(drawableResId);
    }

    /**
     * 需要提示图片
     * @param isNeed 是否需要
     */
    public void needImg(boolean isNeed){
        mErrorImageView.setVisibility(isNeed ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置提示文字
     * @param str 文字描述
     */
    public void setTips(String str){
        mErrorTipsTextView.setText(str);
    }

    /**
     * 设置提示文字
     * @param strResId 文字资源id
     */
    public void setTips(@StringRes int strResId){
        mErrorTipsTextView.setText(getContext().getString(strResId));
    }

    /**
     * 需要提示文字
     * @param isNeed 是否需要
     */
    public void needTips(boolean isNeed){
        mErrorTipsTextView.setVisibility(isNeed ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置重载监听器
     * @param listener 监听器
     */
    public void setReloadListener(OnClickListener listener){
        mErrorRootLayout.setOnClickListener(listener);
    }

}
