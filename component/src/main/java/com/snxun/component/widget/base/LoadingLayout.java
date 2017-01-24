package com.snxun.component.widget.base;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.snxun.component.R;


/**
 * 加载控件
 * Created by zhouL on 2016/11/17.
 */

public class LoadingLayout extends LinearLayout{

    /** 进度条 */
    private ProgressBar mLoadingProgressBar;
    /** 提示语 */
    private TextView mLoadingTipsTextView;

    public LoadingLayout(Context context) {
        super(context);
        init();
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        findViews();
        initData();
    }

    private void findViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_loading_view, this);
        mLoadingProgressBar = (ProgressBar) findViewById(R.id.loading_progressbar);
        mLoadingTipsTextView = (TextView) findViewById(R.id.loading_tips_textview);
    }

    private void initData() {
        needTips(true);// 默认需要
    }

    /**
     * 设置提示文字
     * @param str 文字描述
     */
    public void setTips(String str){
        mLoadingTipsTextView.setText(str);
    }

    /**
     * 设置提示文字
     * @param strResId 文字资源id
     */
    public void setTips(@StringRes int strResId){
        mLoadingTipsTextView.setText(getContext().getString(strResId));
    }

    /**
     * 需要提示文字
     * @param isNeed 是否需要
     */
    public void needTips(boolean isNeed){
        mLoadingTipsTextView.setVisibility(isNeed ? View.VISIBLE : View.GONE);
    }


}
