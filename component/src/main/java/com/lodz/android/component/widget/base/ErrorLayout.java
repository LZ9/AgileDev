package com.lodz.android.component.widget.base;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
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
import com.lodz.android.core.utils.DensityUtils;


/**
 * 加载失败控件
 * Created by zhouL on 2016/11/17.
 */

public class ErrorLayout extends LinearLayout{

    /** 异常界面配置 */
    private ErrorLayoutConfig mConfig = new ErrorLayoutConfig();

    /** 加载失败布局 */
    private LinearLayout mErrorRootLayout;
    /** 失败图片 */
    private ImageView mErrorImageView;
    /** 提示语 */
    private TextView mErrorTipsTextView;

    public ErrorLayout(Context context) {
        super(context);
        init(null);
    }

    public ErrorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ErrorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ErrorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        if (BaseApplication.get() != null){
            mConfig = BaseApplication.get().getBaseLayoutConfig().getErrorLayoutConfig();
        }
        findViews();
        config(attrs);
    }

    private void findViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.component_view_error_layout, this);
        mErrorRootLayout = findViewById(R.id.error_root_layout);
        mErrorImageView = findViewById(R.id.error_imageview);
        mErrorTipsTextView = findViewById(R.id.error_tips_textview);
    }

    /** 配置加载失败页面 */
    private void config(AttributeSet attrs) {
        TypedArray typedArray = null;
        if (attrs != null){
            typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ErrorLayout);
        }

        setLayoutOrientation(typedArray == null ? mConfig.getOrientation()
                : typedArray.getInt(R.styleable.ErrorLayout_contentOrientation, mConfig.getOrientation()));

        needImg(typedArray == null ? mConfig.getIsNeedImg()
                : typedArray.getBoolean(R.styleable.ErrorLayout_isNeedImg, mConfig.getIsNeedImg()));

        needTips(typedArray == null ? mConfig.getIsNeedTips()
                : typedArray.getBoolean(R.styleable.ErrorLayout_isNeedTips, mConfig.getIsNeedTips()));

        Drawable src = typedArray == null ? null : typedArray.getDrawable(R.styleable.ErrorLayout_src);
        if (src != null){
            setImg(src);
        }else {
            setImg(mConfig.getImg() == 0 ? R.drawable.component_ic_data_fail : mConfig.getImg());
        }

        String defaultTips = TextUtils.isEmpty(mConfig.getTips()) ? getContext().getString(R.string.component_load_fail) : mConfig.getTips();
        String attrsTips = typedArray == null ? defaultTips : typedArray.getString(R.styleable.ErrorLayout_tips);
        setTips(TextUtils.isEmpty(attrsTips) ? defaultTips : attrsTips);

        ColorStateList tipsColor = typedArray == null ? null : typedArray.getColorStateList(R.styleable.ErrorLayout_tipsColor);
        if (tipsColor != null) {
            setTipsTextColor(tipsColor);
        } else if (mConfig.getTextColor() != 0) {
            setTipsTextColor(mConfig.getTextColor());
        }

        int tipsSize = typedArray == null ? 0 : typedArray.getDimensionPixelSize(R.styleable.ErrorLayout_tipsSize, 0);
        if (tipsSize != 0){
            setTipsTextSize(DensityUtils.px2sp(getContext(), tipsSize));
        }else if(mConfig.getTextSize() != 0){
            setTipsTextSize(mConfig.getTextSize());
        }

        Drawable drawableBackground = typedArray == null ? null : typedArray.getDrawable(R.styleable.ErrorLayout_contentBackground);
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
     * 设置无数据图片
     * @param drawable 图片
     */
    public void setImg(Drawable drawable){
        mErrorImageView.setImageDrawable(drawable);
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
     * 设置文字颜色
     * @param color 颜色
     */
    public void setTipsTextColorInt(@ColorInt int color){
        mErrorTipsTextView.setTextColor(color);
    }

    /**
     * 设置文字颜色
     * @param colorStateList 颜色
     */
    public void setTipsTextColor(ColorStateList colorStateList){
        if (colorStateList == null){
            return;
        }
        mErrorTipsTextView.setTextColor(colorStateList);
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
