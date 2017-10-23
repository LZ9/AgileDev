package com.lodz.android.component.widget.base;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lodz.android.component.R;
import com.lodz.android.component.base.application.BaseApplication;
import com.lodz.android.component.base.application.config.LoadingLayoutConfig;


/**
 * 加载控件
 * Created by zhouL on 2016/11/17.
 */
public class LoadingLayout extends LinearLayout{

    /** 加载页配置 */
    private LoadingLayoutConfig mConfig = new LoadingLayoutConfig();

    private LinearLayout mRootView;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        if (!isInEditMode()){
            if (BaseApplication.get() != null){
                mConfig = BaseApplication.get().getBaseLayoutConfig().getLoadingLayoutConfig();
            }
        }
        findViews();
        initData();
    }

    private void findViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.component_view_loading_layout, this);
        mLoadingProgressBar = (ProgressBar) findViewById(R.id.loading_progressbar);
        mLoadingTipsTextView = (TextView) findViewById(R.id.loading_tips_textview);
        mRootView = (LinearLayout) findViewById(R.id.root_view);
    }

    private void initData() {
        if (!isInEditMode()){
            configLayout();
        }
    }

    /** 配置加载页面 */
    private void configLayout() {
        setLayoutOrientation(mConfig.getOrientation());
        needTips(mConfig.getIsNeedTips());
        setTips(TextUtils.isEmpty(mConfig.getTips()) ? getContext().getString(R.string.component_loading) : mConfig.getTips());
        if (mConfig.getTextColor() != 0){
            setTipsTextColor(mConfig.getTextColor());
        }
        if (mConfig.getTextSize() != 0f){
            setTipsTextSize(mConfig.getTextSize());
        }
        mLoadingProgressBar.setIndeterminate(mConfig.getIsIndeterminate());
        if (mConfig.getIndeterminateDrawable() != 0){
            mLoadingProgressBar.setIndeterminateDrawable(ContextCompat.getDrawable(getContext(), mConfig.getIndeterminateDrawable()));
        }
        ViewGroup.LayoutParams layoutParams = mLoadingProgressBar.getLayoutParams();
        if (mConfig.getPbWidth() != 0){
            layoutParams.width = mConfig.getPbWidth();
        }
        if (mConfig.getPbHeight() != 0){
            layoutParams.height = mConfig.getPbHeight();
        }

        setBackgroundColor(ContextCompat.getColor(getContext(), mConfig.getBackgroundColor() == 0 ? android.R.color.white : mConfig.getBackgroundColor()));
    }

    /**
     * 需要提示文字
     * @param isNeed 是否需要
     */
    public void needTips(boolean isNeed){
        mLoadingTipsTextView.setVisibility(isNeed ? View.VISIBLE : View.GONE);
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
     * 设置文字颜色
     * @param colorRes 颜色资源id
     */
    public void setTipsTextColor(@ColorRes int colorRes){
        mLoadingTipsTextView.setTextColor(ContextCompat.getColor(getContext(), colorRes));
    }

    /**
     * 设置文字大小
     * @param size 文字大小（单位sp）
     */
    public void setTipsTextSize(float size){
        mLoadingTipsTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    /**
     * 设置进度条控件
     * @param progressBar 进度条
     */
    public void setProgressBar(@NonNull ProgressBar progressBar){
        mLoadingProgressBar = progressBar;
    }

    /** 获取进度条控件 */
    public ProgressBar getProgressBar(){
        return mLoadingProgressBar;
    }

    /**
     * 设置加载页面的布局方向
     * @param orientation LinearLayout.HORIZONTAL或LinearLayout.VERTICAL
     */
    public void setLayoutOrientation(int orientation){
        if (orientation == LinearLayout.HORIZONTAL || orientation == LinearLayout.VERTICAL){
            mRootView.setOrientation(orientation);
        }
    }
}
