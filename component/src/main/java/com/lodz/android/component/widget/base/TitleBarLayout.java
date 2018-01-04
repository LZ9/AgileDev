package com.lodz.android.component.widget.base;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
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
    private TitleBarLayoutConfig mConfig = new TitleBarLayoutConfig();

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
        init(null);
    }

    public TitleBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TitleBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TitleBarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (BaseApplication.get() != null){
            mConfig = BaseApplication.get().getBaseLayoutConfig().getTitleBarLayoutConfig();
        }
        findViews();
        config(attrs);
    }

    private void findViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.component_view_title_layout, this);
        mBackLayout = findViewById(R.id.back_layout);
        mBackBtn = findViewById(R.id.back_btn);
        mTitleTextView = findViewById(R.id.title_textview);
        mExpandLinearLayout = findViewById(R.id.expand_layout);
        mDivideLineView = findViewById(R.id.divide_line);
    }

    private void config(AttributeSet attrs) {
        TypedArray typedArray = null;
        if (attrs != null){
            typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBarLayout);
        }

        // 默认显示返回按钮
        needBackButton(typedArray == null ? mConfig.getIsNeedBackBtn()
                : typedArray.getBoolean(R.styleable.TitleBarLayout_isNeedBackBtn, mConfig.getIsNeedBackBtn()));

        Drawable backDrawable = typedArray == null ? null : typedArray.getDrawable(R.styleable.TitleBarLayout_backDrawable);
        if (backDrawable != null){
            mBackBtn.setCompoundDrawablesWithIntrinsicBounds(backDrawable, null, null, null);
        }else if (mConfig.getBackBtnResId() != 0){
            mBackBtn.setCompoundDrawablesWithIntrinsicBounds(mConfig.getBackBtnResId(), 0, 0, 0);
        }

        String backText = typedArray == null ? "" : typedArray.getString(R.styleable.TitleBarLayout_backText);
        if (!TextUtils.isEmpty(backText)) {
            setBackBtnName(backText);
        }else if (!TextUtils.isEmpty(mConfig.getBackBtnText())){
            setBackBtnName(mConfig.getBackBtnText());
        }

        ColorStateList backTextColor = typedArray == null ? null : typedArray.getColorStateList(R.styleable.TitleBarLayout_backTextColor);
        if (backTextColor != null) {
            setBackBtnTextColor(backTextColor);
        } else if (mConfig.getBackBtnTextColor() != 0) {
            setBackBtnTextColor(mConfig.getBackBtnTextColor());
        }

        int backTextSize = typedArray == null ? 0 : typedArray.getDimensionPixelSize(R.styleable.TitleBarLayout_backTextSize, 0);
        if (backTextSize != 0){
            setBackBtnTextSize(DensityUtils.px2sp(getContext(), backTextSize));
        }else if(mConfig.getBackBtnTextSize() != 0){
            setBackBtnTextSize(mConfig.getBackBtnTextSize());
        }

        String titleText = typedArray == null ? "" : typedArray.getString(R.styleable.TitleBarLayout_titleText);
        if (!TextUtils.isEmpty(titleText)) {
            setTitleName(titleText);
        }

        ColorStateList titleTextColor = typedArray == null ? null : typedArray.getColorStateList(R.styleable.TitleBarLayout_titleTextColor);
        if (titleTextColor != null) {
            setTitleTextColor(titleTextColor);
        } else if (mConfig.getTitleTextColor() != 0) {
            setTitleTextColor(mConfig.getTitleTextColor());
        }

        int titleTextSize = typedArray == null ? 0 : typedArray.getDimensionPixelSize(R.styleable.TitleBarLayout_titleTextSize, 0);
        if (titleTextSize != 0){
            setTitleTextSize(DensityUtils.px2sp(getContext(), titleTextSize));
        }else if(mConfig.getTitleTextSize() != 0){
            setTitleTextSize(mConfig.getTitleTextSize());
        }

        boolean isShowDivideLine = typedArray == null ? mConfig.getIsShowDivideLine()
                : typedArray.getBoolean(R.styleable.TitleBarLayout_isShowDivideLine, mConfig.getIsShowDivideLine());
        mDivideLineView.setVisibility(isShowDivideLine ? View.VISIBLE : View.GONE);

        Drawable divideLineDrawable = typedArray == null ? null : typedArray.getDrawable(R.styleable.TitleBarLayout_divideLineColor);
        if (divideLineDrawable != null){
            setDivideLineColor(divideLineDrawable);
        }else if(mConfig.getDivideLineColor() != 0){
            setDivideLineColor(mConfig.getDivideLineColor());
        }

        int divideLineHeight = typedArray == null ? 0 : typedArray.getDimensionPixelSize(R.styleable.TitleBarLayout_divideLineHeight, 0);
        if (divideLineHeight > 0){
            setDivideLineHeight(DensityUtils.px2dp(getContext(), divideLineHeight));
        }else if(mConfig.getDivideLineHeight() > 0){
            setDivideLineHeight(mConfig.getDivideLineHeight());
        }

        Drawable drawableBackground = typedArray == null ? null : typedArray.getDrawable(R.styleable.TitleBarLayout_titleBarBackground);
        if (drawableBackground != null){
            setBackground(drawableBackground);
        } else if (mConfig.getBackgroundResId() != 0){
            setBackgroundResource(mConfig.getBackgroundResId());
        } else {
            setBackgroundColor(ContextCompat.getColor(getContext(), mConfig.getBackgroundColor() == 0 ? android.R.color.holo_blue_light : mConfig.getBackgroundColor()));
        }

        boolean isNeedElevation = typedArray == null ? mConfig.getIsNeedElevation()
                : typedArray.getBoolean(R.styleable.TitleBarLayout_isNeedElevation, mConfig.getIsNeedElevation());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && isNeedElevation) {
            int elevationVale = typedArray == null ? 0 : typedArray.getDimensionPixelSize(R.styleable.TitleBarLayout_elevationVale, 0);
            setElevation(elevationVale != 0 ? elevationVale : mConfig.getElevationVale());
        }

        // 默认不需要右侧扩展区域
        needExpandView(typedArray != null && typedArray.getBoolean(R.styleable.TitleBarLayout_isNeedExpandView, false));
        int expandViewId = typedArray == null ? 0 : typedArray.getResourceId(R.styleable.TitleBarLayout_expandViewId, 0);
        if (expandViewId > 0){
            View view = LayoutInflater.from(getContext()).inflate(expandViewId, null);
            if (view != null){
                addExpandView(view);
            }
        }

        if (typedArray != null){
            typedArray.recycle();
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
     * 设置返回按钮文字颜色
     * @param colorStateList 颜色
     */
    public void setBackBtnTextColor(ColorStateList colorStateList){
        if (colorStateList == null){
            return;
        }
        mBackBtn.setTextColor(colorStateList);
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
     * 设置文字颜色
     * @param colorStateList 颜色
     */
    public void setTitleTextColor(ColorStateList colorStateList){
        if (colorStateList == null){
            return;
        }
        mTitleTextView.setTextColor(colorStateList);
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

    /** 获取扩展区域的View */
    public View getExpandView(){
        return mExpandLinearLayout;
    }

    /** 隐藏分割线 */
    public void goneDivideLine(){
        mDivideLineView.setVisibility(View.GONE);
    }

    /** 设置分割线颜色 */
    public void setDivideLineColor(@ColorRes int colorRes){
        mDivideLineView.setBackgroundColor(ContextCompat.getColor(getContext(), colorRes));
    }

    /** 设置分割线颜色 */
    public void setDivideLineColor(Drawable drawable){
        mDivideLineView.setBackground(drawable);
    }

    /**
     * 设置分割线高度
     * @param height 高度（单位dp）
     */
    public void setDivideLineHeight(float height){
        ViewGroup.LayoutParams layoutParams = mDivideLineView.getLayoutParams();
        layoutParams.height = DensityUtils.dp2px(getContext(), height);
        mDivideLineView.setLayoutParams(layoutParams);
    }
}
