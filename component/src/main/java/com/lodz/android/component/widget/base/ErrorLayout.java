package com.lodz.android.component.widget.base;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lodz.android.component.R;
import com.lodz.android.component.base.application.BaseApplication;
import com.lodz.android.component.base.application.config.ErrorLayoutConfig;


/**
 * 加载失败控件
 * Created by zhouL on 2016/11/17.
 */

public class ErrorLayout extends LinearLayout{

    /** 异常界面配置 */
    private ErrorLayoutConfig mConfig = new ErrorLayoutConfig();;

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ErrorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        if (!isInEditMode()){
            if (BaseApplication.get() != null){
                mConfig = BaseApplication.get().getBaseLayoutConfig().getErrorLayoutConfig();
            }
        }
        findViews();
        initData();
    }

    private void findViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.component_view_error_layout, this);
        mErrorRootLayout = (LinearLayout) findViewById(R.id.error_root_layout);
        mErrorImageView = (ImageView) findViewById(R.id.error_imageview);
        mErrorTipsTextView = (TextView) findViewById(R.id.error_tips_textview);
    }

    private void initData() {
        if (!isInEditMode()){
            configLayout();
        }
    }

    /** 配置加载失败页面 */
    private void configLayout() {
        setLayoutOrientation(mConfig.getOrientation());
        needImg(mConfig.getIsNeedImg());
        needTips(mConfig.getIsNeedTips());
        setImg(mConfig.getImg() == 0 ? R.drawable.component_ic_data_fail : mConfig.getImg());
        setTips(TextUtils.isEmpty(mConfig.getTips()) ? getContext().getString(R.string.component_load_fail) : mConfig.getTips());
        if (mConfig.getTextColor() != 0){
            setTipsTextColor(mConfig.getTextColor());
        }
        if (mConfig.getTextSize() != 0f){
            setTipsTextSize(mConfig.getTextSize());
        }
        setBackgroundColor(ContextCompat.getColor(getContext(), mConfig.getBackgroundColor() == 0 ? android.R.color.white : mConfig.getBackgroundColor()));
    }

    /**
     * 需要提示图片
     * @param isNeed 是否需要
     */
    public void needImg(boolean isNeed){
        mErrorImageView.setVisibility(isNeed ? View.VISIBLE : View.GONE);
    }

    /**
     * 需要提示文字
     * @param isNeed 是否需要
     */
    public void needTips(boolean isNeed){
        mErrorTipsTextView.setVisibility(isNeed ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置界面错误图片
     * @param drawableResId 图片资源id
     */
    public void setImg(@DrawableRes int drawableResId){
        mErrorImageView.setImageResource(drawableResId);
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
     * 设置文字颜色
     * @param colorRes 颜色资源id
     */
    public void setTipsTextColor(@ColorRes int colorRes){
        mErrorTipsTextView.setTextColor(ContextCompat.getColor(getContext(), colorRes));
    }

    /**
     * 设置文字大小
     * @param size 文字大小（单位sp）
     */
    public void setTipsTextSize(float size){
        mErrorTipsTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    /**
     * 设置重载监听器
     * @param listener 监听器
     */
    public void setReloadListener(OnClickListener listener){
        mErrorRootLayout.setOnClickListener(listener);
    }

    /**
     * 设置错误页面的布局方向
     * @param orientation LinearLayout.HORIZONTAL或LinearLayout.VERTICAL
     */
    public void setLayoutOrientation(int orientation){
        if (orientation == LinearLayout.HORIZONTAL || orientation == LinearLayout.VERTICAL){
            mErrorRootLayout.setOrientation(orientation);
        }
    }
}
