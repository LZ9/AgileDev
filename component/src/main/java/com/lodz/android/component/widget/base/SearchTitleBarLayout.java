package com.lodz.android.component.widget.base;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lodz.android.component.R;
import com.lodz.android.component.base.application.BaseApplication;
import com.lodz.android.component.base.application.config.TitleBarLayoutConfig;
import com.lodz.android.core.utils.DensityUtils;

/**
 * 搜索标题栏
 * Created by zhouL on 2018/5/21.
 */
public class SearchTitleBarLayout extends FrameLayout{

    /** 标题栏配置 */
    private TitleBarLayoutConfig mConfig = new TitleBarLayoutConfig();

    /** 返回按钮布局 */
    private LinearLayout mBackLayout;
    /** 返回按钮 */
    private TextView mBackBtn;
    /** 输入框布局 */
    private ViewGroup mInputLayout;
    /** 输入框 */
    private EditText mInputEdit;
    /** 扩展区清空按钮布局 */
    private ImageView mClearBtn;
    private View mVerticalLineView;
    /** 返回按钮 */
    private ImageView mSearchBtn;
    /** 分割线 */
    private View mDivideLineView;

    /** 是否需要清空按钮 */
    private boolean isNeedCleanBtn = true;
    /** 输入框监听 */
    private TextWatcher mTextWatcher;

    public SearchTitleBarLayout(Context context) {
        super(context);
        init(null);
    }

    public SearchTitleBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SearchTitleBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SearchTitleBarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (BaseApplication.get() != null){
            mConfig = BaseApplication.get().getBaseLayoutConfig().getTitleBarLayoutConfig();
        }
        findViews();
        config(attrs);
        setListeners();
    }

    private void findViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.component_view_search_title_layout, this);
        mBackLayout = findViewById(R.id.back_layout);
        mBackBtn = findViewById(R.id.back_btn);
        mInputLayout = findViewById(R.id.input_layout);
        mInputEdit = findViewById(R.id.input_edit);
        mClearBtn = findViewById(R.id.clear_btn);
        mVerticalLineView = findViewById(R.id.vertical_line);
        mSearchBtn = findViewById(R.id.search_btn);
        mDivideLineView = findViewById(R.id.divide_line);
    }

    private void config(AttributeSet attrs) {
        TypedArray typedArray = null;
        if (attrs != null){
            typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SearchTitleBarLayout);
        }

        // 默认显示返回按钮
        needBackButton(typedArray == null ? mConfig.getIsNeedBackBtn()
                : typedArray.getBoolean(R.styleable.SearchTitleBarLayout_isNeedBackBtn, mConfig.getIsNeedBackBtn()));

        Drawable backDrawable = typedArray == null ? null : typedArray.getDrawable(R.styleable.SearchTitleBarLayout_backDrawable);
        if (backDrawable != null){
            mBackBtn.setCompoundDrawablesWithIntrinsicBounds(backDrawable, null, null, null);
        }else if (mConfig.getBackBtnResId() != 0){
            mBackBtn.setCompoundDrawablesWithIntrinsicBounds(mConfig.getBackBtnResId(), 0, 0, 0);
        }

        String backText = typedArray == null ? "" : typedArray.getString(R.styleable.SearchTitleBarLayout_backText);
        if (!TextUtils.isEmpty(backText)) {
            setBackBtnName(backText);
        }else if (!TextUtils.isEmpty(mConfig.getBackBtnText())){
            setBackBtnName(mConfig.getBackBtnText());
        }

        ColorStateList backTextColor = typedArray == null ? null : typedArray.getColorStateList(R.styleable.SearchTitleBarLayout_backTextColor);
        if (backTextColor != null) {
            setBackBtnTextColor(backTextColor);
        } else if (mConfig.getBackBtnTextColor() != 0) {
            setBackBtnTextColor(mConfig.getBackBtnTextColor());
        }

        int backTextSize = typedArray == null ? 0 : typedArray.getDimensionPixelSize(R.styleable.SearchTitleBarLayout_backTextSize, 0);
        if (backTextSize != 0){
            setBackBtnTextSize(DensityUtils.px2sp(getContext(), backTextSize));
        }else if(mConfig.getBackBtnTextSize() != 0){
            setBackBtnTextSize(mConfig.getBackBtnTextSize());
        }

        boolean isShowDivideLine = typedArray == null ? mConfig.getIsShowDivideLine()
                : typedArray.getBoolean(R.styleable.SearchTitleBarLayout_isShowDivideLine, mConfig.getIsShowDivideLine());
        mDivideLineView.setVisibility(isShowDivideLine ? View.VISIBLE : View.GONE);

        Drawable divideLineDrawable = typedArray == null ? null : typedArray.getDrawable(R.styleable.SearchTitleBarLayout_divideLineColor);
        if (divideLineDrawable != null){
            setDivideLineColor(divideLineDrawable);
        }else if(mConfig.getDivideLineColor() != 0){
            setDivideLineColor(mConfig.getDivideLineColor());
        }

        int divideLineHeight = typedArray == null ? 0 : typedArray.getDimensionPixelSize(R.styleable.SearchTitleBarLayout_divideLineHeight, 0);
        if (divideLineHeight > 0){
            setDivideLineHeight(DensityUtils.px2dp(getContext(), divideLineHeight));
        }else if(mConfig.getDivideLineHeight() > 0){
            setDivideLineHeight(mConfig.getDivideLineHeight());
        }

        Drawable drawableBackground = typedArray == null ? null : typedArray.getDrawable(R.styleable.SearchTitleBarLayout_titleBarBackground);
        if (drawableBackground != null){
            setBackground(drawableBackground);
        } else if (mConfig.getBackgroundResId() != 0){
            setBackgroundResource(mConfig.getBackgroundResId());
        } else {
            setBackgroundColor(ContextCompat.getColor(getContext(), mConfig.getBackgroundColor() == 0 ? android.R.color.holo_blue_light : mConfig.getBackgroundColor()));
        }

        boolean isNeedElevation = typedArray == null ? mConfig.getIsNeedElevation()
                : typedArray.getBoolean(R.styleable.SearchTitleBarLayout_isNeedElevation, mConfig.getIsNeedElevation());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && isNeedElevation) {
            int elevationVale = typedArray == null ? 0 : typedArray.getDimensionPixelSize(R.styleable.SearchTitleBarLayout_elevationVale, 0);
            setElevation(elevationVale != 0 ? elevationVale : mConfig.getElevationVale());
        }

        if (typedArray != null){
            setNeedCleanBtn(typedArray.getBoolean(R.styleable.SearchTitleBarLayout_isNeedCleanBtn, true));
        }

        Drawable searchDrawable = typedArray == null ? null : typedArray.getDrawable(R.styleable.SearchTitleBarLayout_searchDrawable);
        if (searchDrawable != null){
            setSearchIcon(searchDrawable);
        }

        Drawable cleanDrawable = typedArray == null ? null : typedArray.getDrawable(R.styleable.SearchTitleBarLayout_cleanDrawable);
        if (cleanDrawable != null){
            setCleanIcon(cleanDrawable);
        }

        Drawable inputBackground = typedArray == null ? null : typedArray.getDrawable(R.styleable.SearchTitleBarLayout_inputBackground);
        if (inputBackground != null){
            setInputBackground(inputBackground);
        }

        String hintText = typedArray == null ? "" : typedArray.getString(R.styleable.SearchTitleBarLayout_inputHint);
        if (!TextUtils.isEmpty(hintText)) {
            setInputHint(hintText);
        }

        ColorStateList hintTextColor = typedArray == null ? null : typedArray.getColorStateList(R.styleable.SearchTitleBarLayout_inputHintTextColor);
        if (hintTextColor != null) {
            setInputHintTextColor(hintTextColor);
        }

        String inputText = typedArray == null ? "" : typedArray.getString(R.styleable.SearchTitleBarLayout_inputText);
        if (!TextUtils.isEmpty(inputText)) {
            setInputText(inputText);
        }

        ColorStateList inputTextColor = typedArray == null ? null : typedArray.getColorStateList(R.styleable.SearchTitleBarLayout_inputTextColor);
        if (inputTextColor != null) {
            setInputTextColor(inputTextColor);
        }

        int inputTextSize = typedArray == null ? 0 : typedArray.getDimensionPixelSize(R.styleable.SearchTitleBarLayout_inputTextSize, 0);
        if (inputTextSize != 0){
            setInputTextSize(DensityUtils.px2sp(getContext(), inputTextSize));
        }

        if (typedArray != null){
            setShowVerticalLine(typedArray.getBoolean(R.styleable.SearchTitleBarLayout_isShowVerticalLine, true));
        }

        Drawable verticalLineBackground = typedArray == null ? null : typedArray.getDrawable(R.styleable.SearchTitleBarLayout_verticalLineBackground);
        if (verticalLineBackground != null){
            setVerticalLineBackground(verticalLineBackground);
        }

        if (typedArray != null){
            typedArray.recycle();
        }
    }

    /** 设置监听器 */
    private void setListeners() {

        // 输入监听
        mInputEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (mTextWatcher != null){
                    mTextWatcher.beforeTextChanged(s, start, count, after);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mTextWatcher != null){
                    mTextWatcher.onTextChanged(s, start, before, count);
                }
                if (!isNeedCleanBtn){
                    return;
                }
                mClearBtn.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mTextWatcher != null){
                    mTextWatcher.afterTextChanged(s);
                }
            }
        });

        // 清空按钮
        mClearBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputEdit.setText("");
            }
        });
    }


    /**
     * 需要显示返回按钮
     * @param isNeed 是否需要
     */
    public void needBackButton(boolean isNeed){
        mBackLayout.setVisibility(isNeed ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置返回按钮的透明度
     * @param alpha 透明度
     */
    public void setBackButtonAlpha(@FloatRange(from=0.0, to=1.0) float alpha){
        mBackLayout.setAlpha(alpha);
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
     * @param color 颜色
     */
    public void setBackBtnTextColorInt(@ColorInt int color){
        mBackBtn.setTextColor(color);
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

    /** 隐藏分割线 */
    public void goneDivideLine(){
        mDivideLineView.setVisibility(View.GONE);
    }

    /** 设置分割线颜色 */
    public void setDivideLineColor(@ColorRes int colorRes){
        mDivideLineView.setBackgroundColor(ContextCompat.getColor(getContext(), colorRes));
    }

    /** 设置分割线颜色 */
    public void setDivideLineColorInt(@ColorInt int color){
        mDivideLineView.setBackgroundColor(color);
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

    /** 请重写实现返回按钮监听 */
    public void setOnSearchClickListener(OnClickListener listener) {
        mSearchBtn.setOnClickListener(listener);
    }

    /**
     * 设置是否需要清空按钮
     * @param isNeed 是否需要清空按钮
     */
    public void setNeedCleanBtn(boolean isNeed){
        isNeedCleanBtn = isNeed;
    }

    /**
     * 设置文本输入监听器
     * @param watcher 监听器
     */
    public void setTextWatcher(TextWatcher watcher){
        mTextWatcher = watcher;
    }

    /** 获取输入框内容 */
    public String getInputText(){
        return mInputEdit.getText().toString();
    }

    /**
     * 设置搜索图标
     * @param searchDrawable 图标
     */
    public void setSearchIcon(Drawable searchDrawable){
        if (searchDrawable != null){
            mSearchBtn.setImageDrawable(searchDrawable);
        }
    }

    /**
     * 设置搜索图标
     * @param resId 图标资源
     */
    public void setSearchIcon(@DrawableRes int resId){
        mSearchBtn.setImageResource(resId);
    }

    /**
     * 设置清空图标
     * @param cleanDrawable 图标
     */
    public void setCleanIcon(Drawable cleanDrawable){
        setNeedCleanBtn(true);
        if (cleanDrawable != null){
            mClearBtn.setImageDrawable(cleanDrawable);
        }
    }

    /**
     * 设置清空图标
     * @param resId 图标资源
     */
    public void setCleanIcon(@DrawableRes int resId){
        setNeedCleanBtn(true);
        mClearBtn.setImageResource(resId);
    }

    /**
     * 设置输入框背景
     * @param drawable 背景
     */
    public void setInputBackground(Drawable drawable){
        if (drawable != null){
            mInputLayout.setBackground(drawable);
        }
    }

    /**
     * 设置输入框背景
     * @param resId 背景资源
     */
    public void setInputBackgroundResource(@DrawableRes int resId){
        mInputLayout.setBackgroundResource(resId);
    }

    /**
     * 设置输入框背景
     * @param color 背景颜色
     */
    public void setInputBackgroundColor(@ColorInt int color){
        mInputLayout.setBackgroundColor(color);
    }

    /**
     * 设置输入框提示语
     * @param hint 提示语
     */
    public void setInputHint(String hint){
        mInputEdit.setHint(hint);
    }

    /**
     * 设置输入框提示语
     * @param resId 提示语资源id
     */
    public void setInputHint(@StringRes int resId){
        mInputEdit.setHint(resId);
    }

    /**
     * 设置输入框提示语颜色
     * @param colorRes 颜色资源id
     */
    public void setInputHintTextColor(@ColorRes int colorRes){
        mInputEdit.setHintTextColor(ContextCompat.getColor(getContext(), colorRes));
    }

    /**
     * 设置输入框提示语颜色
     * @param color 颜色
     */
    public void setInputHintTextColorInt(@ColorInt int color){
        mInputEdit.setHintTextColor(color);
    }

    /**
     * 设置输入框提示语颜色
     * @param colorStateList 颜色
     */
    public void setInputHintTextColor(ColorStateList colorStateList){
        if (colorStateList == null){
            return;
        }
        mInputEdit.setHintTextColor(colorStateList);
    }


    /**
     * 设置输入框文字
     * @param text 文字
     */
    public void setInputText(String text){
        mInputEdit.setText(text);
    }

    /**
     * 设置输入框文字
     * @param resId 文字资源id
     */
    public void setInputText(@StringRes int resId){
        mInputEdit.setText(resId);
    }

    /**
     * 设置输入框文字颜色
     * @param colorRes 颜色资源id
     */
    public void setInputTextColor(@ColorRes int colorRes){
        mInputEdit.setTextColor(ContextCompat.getColor(getContext(), colorRes));
    }

    /**
     * 设置输入框文字颜色
     * @param color 颜色
     */
    public void setInputTextColorInt(@ColorInt int color){
        mInputEdit.setTextColor(color);
    }

    /**
     * 设置输入框文字颜色
     * @param colorStateList 颜色
     */
    public void setInputTextColor(ColorStateList colorStateList){
        if (colorStateList == null){
            return;
        }
        mInputEdit.setTextColor(colorStateList);
    }

    /**
     * 设置输入框文字大小
     * @param size 文字大小（单位sp）
     */
    public void setInputTextSize(float size){
        mInputEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    /** 获取输入框控件 */
    public EditText getInputEdit(){
        return mInputEdit;
    }

    /**
     * 是否显示竖线
     * @param isShow 是否显示
     */
    public void setShowVerticalLine(boolean isShow){
        mVerticalLineView.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置竖线背景
     * @param drawable 背景
     */
    public void setVerticalLineBackground(Drawable drawable){
        if (drawable != null){
            mVerticalLineView.setBackground(drawable);
        }
    }

    /**
     * 设置竖线背景
     * @param resId 背景资源
     */
    public void setVerticalLineBackgroundResource(@DrawableRes int resId){
        mVerticalLineView.setBackgroundResource(resId);
    }

    /**
     * 设置竖线背景
     * @param color 背景颜色
     */
    public void setVerticalLineBackgroundColor(@ColorInt int color){
        mVerticalLineView.setBackgroundColor(color);
    }
}
