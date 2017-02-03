package com.lodz.android.component.widget.base;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lodz.android.component.R;


/**
 * 标题栏布局
 * Created by zhouL on 2016/11/17.
 */
public class TitleBarLayout extends LinearLayout{

    /** 标题跟布局 */
    private RelativeLayout mTitleRootLayout;
    /** 返回按钮 */
    private TextView mBackBtn;
    /** 标题 */
    private TextView mTitleTextView;
    /** 扩展区布局 */
    private LinearLayout mExpandLinearLayout;

    public TitleBarLayout(Context context) {
        super(context);
        init();
    }

    public TitleBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        findViews();
        setListeners();
        initData();
    }

    private void findViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_title_view, this);
        mTitleRootLayout = (RelativeLayout) findViewById(R.id.title_root_layout);
        mBackBtn = (TextView) findViewById(R.id.back_btn);
        mTitleTextView = (TextView) findViewById(R.id.title_textview);
        mExpandLinearLayout = (LinearLayout) findViewById(R.id.expand_layout);
    }

    private void setListeners() {

    }

    private void initData() {
        needBackButton(true);// 默认显示返回按钮
        needExpandView(false);// 默认不需要右侧扩展区域
    }

    /** 请重写实现返回按钮监听 */
    public void setOnBackBtnClickListener(OnClickListener listener) {
        mBackBtn.setOnClickListener(listener);
    }

    /**
     * 设置返回按钮文字
     * @param str 文字描述
     */
    public void setBackBtnName(String str){
        mBackBtn.setText(str);
    }

    /**
     * 设置返回按钮文字
     * @param strResId 文字资源id
     */
    public void setBackBtnName(@StringRes int strResId){
        mBackBtn.setText(getContext().getString(strResId));
    }

    /**
     * 需要显示返回按钮
     * @param isNeed 是否需要
     */
    public void needBackButton(boolean isNeed){
        mBackBtn.setVisibility(isNeed ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置标题名
     * @param title 标题名
     */
    public void setTitleName(String title){
        mTitleTextView.setText(title);
    }

    /**
     * 设置标题名
     * @param strResId 文字资源id
     */
    public void setTitleName(@StringRes int strResId){
        mTitleTextView.setText(getContext().getString(strResId));
    }

    /**
     * 需要右侧扩展区
     * @param isNeed 是否需要
     */
    public void needExpandView(boolean isNeed){
        mExpandLinearLayout.setVisibility(isNeed ? View.VISIBLE : View.GONE);
    }

    /**
     * 添加扩展区域的View
     * @param view 控件
     */
    public void addExpandView(View view){
        mExpandLinearLayout.addView(view);
        needExpandView(true);
    }

    /**
     * 设置标题栏背景色
     * @param backgroundColor 背景色
     */
    public void setTitleBackgroundColor(@ColorRes int backgroundColor){
        mTitleRootLayout.setBackgroundColor(ContextCompat.getColor(getContext(), backgroundColor));
    }

    /**
     * 设置标题栏背景图片
     * @param drawableResId 背景图片
     */
    public void setTitleBackgroundResource(@DrawableRes int drawableResId){
        mTitleRootLayout.setBackgroundResource(drawableResId);
    }
}
