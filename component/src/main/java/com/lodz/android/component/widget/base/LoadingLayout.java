package com.lodz.android.component.widget.base;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.IntDef;
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
import com.lodz.android.core.utils.DensityUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 加载控件
 * Created by zhouL on 2016/11/17.
 */
public class LoadingLayout extends LinearLayout{

    @IntDef({LinearLayout.HORIZONTAL, LinearLayout.VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationType {}

    /** 加载页配置 */
    private LoadingLayoutConfig mConfig = new LoadingLayoutConfig();

    private LinearLayout mRootView;
    /** 进度条 */
    private ProgressBar mLoadingProgressBar;
    /** 提示语 */
    private TextView mLoadingTipsTextView;

    public LoadingLayout(Context context) {
        super(context);
        init(null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        if (!isInEditMode()){
            if (BaseApplication.get() != null){
                mConfig = BaseApplication.get().getBaseLayoutConfig().getLoadingLayoutConfig();
            }
        }
        findViews();
        initData(attrs);
    }

    private void findViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.component_view_loading_layout, this);
        mLoadingProgressBar = findViewById(R.id.loading_progressbar);
        mLoadingTipsTextView = findViewById(R.id.loading_tips_textview);
        mRootView = findViewById(R.id.root_view);
    }

    private void initData(AttributeSet attrs) {
        if (!isInEditMode()){
            configLayout(attrs);
        }
    }

    /** 配置加载页面 */
    private void configLayout(AttributeSet attrs) {
        TypedArray typedArray = null;
        if (attrs != null){
            typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LoadingLayout);
        }

        setLayoutOrientation(typedArray == null ? mConfig.getOrientation()
                : typedArray.getInteger(R.styleable.LoadingLayout_contentOrientation, mConfig.getOrientation()));

        needTips(typedArray == null ? mConfig.getIsNeedTips()
                : typedArray.getBoolean(R.styleable.LoadingLayout_isNeedTips, mConfig.getIsNeedTips()));

        String defaultTips = TextUtils.isEmpty(mConfig.getTips()) ? getContext().getString(R.string.component_loading) : mConfig.getTips();
        String attrsTips = typedArray == null ? defaultTips : typedArray.getString(R.styleable.LoadingLayout_tips);
        setTips(TextUtils.isEmpty(attrsTips) ? defaultTips : attrsTips);

        ColorStateList tipsColor = typedArray == null ? null : typedArray.getColorStateList(R.styleable.LoadingLayout_tipsColor);
        if (tipsColor != null) {
            setTipsTextColor(tipsColor);
        } else if (mConfig.getTextColor() != 0) {
            setTipsTextColor(mConfig.getTextColor());
        }

        int tipsSize = typedArray == null ? 0 : typedArray.getDimensionPixelSize(R.styleable.LoadingLayout_tipsSize, 0);
        if (tipsSize != 0){
            setTipsTextSize(DensityUtils.px2sp(getContext(), tipsSize));
        }else if(mConfig.getTextSize() != 0){
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

        Drawable drawableBackground = typedArray == null ? null : typedArray.getDrawable(R.styleable.LoadingLayout_contentBackground);
        if (drawableBackground != null){
            setBackground(drawableBackground);
        }else {
            setBackgroundColor(ContextCompat.getColor(getContext(), mConfig.getBackgroundColor() == 0 ? android.R.color.white : mConfig.getBackgroundColor()));
        }

        if (typedArray != null){
            typedArray.recycle();
        }
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
     * 设置文字颜色
     * @param colorStateList 颜色
     */
    public void setTipsTextColor(ColorStateList colorStateList){
        if (colorStateList == null){
            return;
        }
        mLoadingTipsTextView.setTextColor(colorStateList);
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
    public void setLayoutOrientation(@OrientationType int orientation){
        if (orientation == LinearLayout.HORIZONTAL || orientation == LinearLayout.VERTICAL){
            mRootView.setOrientation(orientation);
        }
    }
}
