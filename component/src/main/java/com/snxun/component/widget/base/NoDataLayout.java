package com.snxun.component.widget.base;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snxun.component.R;


/**
 * 无数据控件
 * Created by zhouL on 2016/11/17.
 */

public class NoDataLayout extends LinearLayout{

    /** 无数据图片 */
    private ImageView mNoDataImageView;
    /** 无数据提示语 */
    private TextView mNoDataTextView;

    public NoDataLayout(Context context) {
        super(context);
        init();
    }

    public NoDataLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NoDataLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        findViews();
        initData();
    }

    private void findViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_no_data_view, this);
        mNoDataImageView = (ImageView) findViewById(R.id.no_data_imageview);
        mNoDataTextView = (TextView) findViewById(R.id.no_data_textview);
    }

    private void initData() {
        needImg(true);// 默认需要图片
        needTips(false);// 默认不需要提示语
    }

    /**
     * 设置无数据图片
     * @param drawableResId 图片资源id
     */
    public void setImg(@DrawableRes int drawableResId){
        mNoDataImageView.setImageResource(drawableResId);
    }

    /**
     * 需要提示图片
     * @param isNeed 是否需要
     */
    public void needImg(boolean isNeed){
        mNoDataImageView.setVisibility(isNeed ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置提示文字
     * @param str 文字描述
     */
    public void setTips(String str){
        mNoDataTextView.setText(str);
    }

    /**
     * 设置提示文字
     * @param strResId 文字资源id
     */
    public void setTips(@StringRes int strResId){
        mNoDataTextView.setText(getContext().getString(strResId));
    }

    /**
     * 需要提示文字
     * @param isNeed 是否需要
     */
    public void needTips(boolean isNeed){
        mNoDataTextView.setVisibility(isNeed ? View.VISIBLE : View.GONE);
    }

}
