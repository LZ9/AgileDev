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
import com.lodz.android.component.base.application.config.NoDataLayoutConfig;


/**
 * 无数据控件
 * Created by zhouL on 2016/11/17.
 */
public class NoDataLayout extends LinearLayout{

    /** 无数据页面配置 */
    private NoDataLayoutConfig mConfig = new NoDataLayoutConfig();

    /** 无数据图片 */
    private ImageView mNoDataImageView;
    /** 无数据提示语 */
    private TextView mNoDataTextView;

    private LinearLayout mRootView;

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NoDataLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        if (!isInEditMode()){
            if (BaseApplication.get() != null){
                mConfig = BaseApplication.get().getBaseLayoutConfig().getNoDataLayoutConfig();
            }
        }
        findViews();
        initData();
    }

    private void findViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.component_view_no_data_layout, this);
        mNoDataImageView = (ImageView) findViewById(R.id.no_data_imageview);
        mNoDataTextView = (TextView) findViewById(R.id.no_data_textview);
        mRootView = (LinearLayout) findViewById(R.id.root_view);
    }

    private void initData() {
        if (!isInEditMode()){
            config();
        }
    }

    private void config() {
        setLayoutOrientation(mConfig.getOrientation());
        needImg(mConfig.getIsNeedImg());
        needTips(mConfig.getIsNeedTips());// 默认不需要提示语
        setImg(mConfig.getImg() == 0 ? R.drawable.component_ic_no_data : mConfig.getImg());
        setTips(TextUtils.isEmpty(mConfig.getTips()) ? getContext().getString(R.string.component_no_data) : mConfig.getTips());
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
        mNoDataImageView.setVisibility(isNeed ? View.VISIBLE : View.GONE);
    }

    /**
     * 需要提示文字
     * @param isNeed 是否需要
     */
    public void needTips(boolean isNeed){
        mNoDataTextView.setVisibility(isNeed ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置无数据图片
     * @param drawableResId 图片资源id
     */
    public void setImg(@DrawableRes int drawableResId){
        mNoDataImageView.setImageResource(drawableResId);
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
     * 设置文字颜色
     * @param colorRes 颜色资源id
     */
    public void setTipsTextColor(@ColorRes int colorRes){
        mNoDataTextView.setTextColor(ContextCompat.getColor(getContext(), colorRes));
    }

    /**
     * 设置文字大小
     * @param size 文字大小（单位sp）
     */
    public void setTipsTextSize(float size){
        mNoDataTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    /**
     * 设置无数据页面的布局方向
     * @param orientation LinearLayout.HORIZONTAL或LinearLayout.VERTICAL
     */
    public void setLayoutOrientation(int orientation){
        if (orientation == LinearLayout.HORIZONTAL || orientation == LinearLayout.VERTICAL){
            mRootView.setOrientation(orientation);
        }
    }
}
