package com.lodz.android.component.widget.base;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
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
import android.widget.TextView;

import com.lodz.android.component.R;
import com.lodz.android.component.base.application.BaseApplication;
import com.lodz.android.component.base.application.config.TitleBarLayoutConfig;
import com.lodz.android.core.utils.DensityUtils;


/**
 * 标题栏布局
 * Created by zhouL on 2016/11/17.
 */
public class TitleBarLayout extends LinearLayout{

    /** 标题栏配置 */
    private TitleBarLayoutConfig mConfig;

    /** 返回按钮布局 */
    private LinearLayout mBackLayout;
    /** 返回按钮 */
    private TextView mBackBtn;
    /** 标题 */
    private TextView mTitleTextView;
    /** 扩展区布局 */
    private LinearLayout mExpandLinearLayout;
    /** 分割线 */
    private View mDivideLineView;

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TitleBarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mConfig = BaseApplication.get().getBaseLayoutConfig().getTitleBarLayoutConfig();
        findViews();
        initData();
    }

    private void findViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.component_view_title_layout, this);
        mBackLayout = (LinearLayout) findViewById(R.id.back_layout);
        mBackBtn = (TextView) findViewById(R.id.back_btn);
        mTitleTextView = (TextView) findViewById(R.id.title_textview);
        mExpandLinearLayout = (LinearLayout) findViewById(R.id.expand_layout);
        mDivideLineView = findViewById(R.id.divide_line);
    }

    private void initData() {
        config();
    }

    private void config() {
        needBackButton(mConfig.getIsNeedBackBtn());// 默认显示返回按钮
        needExpandView(false);// 默认不需要右侧扩展区域
        if (mConfig.getBackBtnResId() != 0){
            mBackBtn.setCompoundDrawablesWithIntrinsicBounds(mConfig.getBackBtnResId(), 0, 0, 0);
        }
        if (!TextUtils.isEmpty(mConfig.getBackBtnText())){
            setBackBtnName(mConfig.getBackBtnText());
        }
        if (mConfig.getBackBtnTextColor() != 0){
            setBackBtnTextColor(mConfig.getBackBtnTextColor());
        }
        if (mConfig.getBackBtnTextSize() != 0f){
            setBackBtnTextSize(mConfig.getBackBtnTextSize());
        }
        if (mConfig.getTitleTextColor() != 0){
            setTitleTextColor(mConfig.getTitleTextColor());
        }
        if (mConfig.getTitleTextSize() != 0f){
            setTitleTextSize(mConfig.getTitleTextSize());
        }
        mDivideLineView.setVisibility(mConfig.getIsShowDivideLine() ? View.VISIBLE : View.GONE);
        if (mConfig.getDivideLineColor() != 0){
            setDivideLineColor(mConfig.getDivideLineColor());
        }
        if (mConfig.getDivideLineHeight() > 0){
            setDivideLineHeight(mConfig.getDivideLineHeight());
        }
        setBackgroundColor(ContextCompat.getColor(getContext(), mConfig.getBackgroundColor() == 0 ? android.R.color.holo_blue_light : mConfig.getBackgroundColor()));
        if (mConfig.getBackgroundResId() != 0){
            setBackgroundResource(mConfig.getBackgroundResId());
        }
    }

    /**
     * 需要显示返回按钮
     * @param isNeed 是否需要
     */
    public void needBackButton(boolean isNeed){
        mBackLayout.setVisibility(isNeed ? View.VISIBLE : View.GONE);
    }

    /** 请重写实现返回按钮监听 */
    public void setOnBackBtnClickListener(OnClickListener listener) {
        mBackLayout.setOnClickListener(listener);
    }

    /**
     * 替换默认的返回按钮
     * @param view 返回按钮的View
     */
    public void replaceBackBtn(View view){
        mBackLayout.removeAllViews();
        mBackLayout.addView(view);
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
     * 设置返回按钮文字颜色
     * @param colorRes 颜色资源id
     */
    public void setBackBtnTextColor(@ColorRes int colorRes){
        mBackBtn.setTextColor(ContextCompat.getColor(getContext(), colorRes));
    }

    /**
     * 设置返回按钮文字大小
     * @param size 文字大小（单位sp）
     */
    public void setBackBtnTextSize(float size){
        mBackBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
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
     * 设置标题文字颜色
     * @param colorRes 颜色资源id
     */
    public void setTitleTextColor(@ColorRes int colorRes){
        mTitleTextView.setTextColor(ContextCompat.getColor(getContext(), colorRes));
    }

    /**
     * 设置标题文字大小
     * @param size 文字大小（单位sp）
     */
    public void setTitleTextSize(float size){
        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
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

    /** 隐藏分割线 */
    public void goneDivideLine(){
        mDivideLineView.setVisibility(View.GONE);
    }

    /** 设置分割线颜色 */
    public void setDivideLineColor(@ColorRes int colorRes){
        mDivideLineView.setBackgroundColor(ContextCompat.getColor(getContext(), colorRes));
    }

    /**
     * 设置分割线高度
     * @param height 高度（单位dp）
     */
    public void setDivideLineHeight(int height){
        ViewGroup.LayoutParams layoutParams = mDivideLineView.getLayoutParams();
        layoutParams.height = DensityUtils.dp2px(getContext(), height);
        mDivideLineView.setLayoutParams(layoutParams);
    }
}
