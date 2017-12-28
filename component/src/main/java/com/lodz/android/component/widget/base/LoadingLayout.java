package com.lodz.android.component.widget.base;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
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

        float tipsSize = typedArray == null ? mConfig.getTextSize() : typedArray.getDimension(R.styleable.LoadingLayout_tipsSize, mConfig.getTextSize());
        if (tipsSize != 0f){
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


//        ColorStateList backgroundColor = typedArray == null ? null : typedArray.getColorStateList(R.styleable.LoadingLayout_contentBackground);
//        if (backgroundColor != null){
//            int colo = getContext().getco
//        }
//
//        ContextCompat.getColor(getContext(), mConfig.getBackgroundColor() == 0 ? android.R.color.white : mConfig.getBackgroundColor());
//
//        setBackgroundColor();

        if (typedArray != null){
            typedArray.recycle();
        }
    }


    private void initLayoutByAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LoadingLayout);
        setLayoutOrientation(typedArray.getInteger(R.styleable.LoadingLayout_contentOrientation, LinearLayout.VERTICAL));
        needTips(typedArray.getBoolean(R.styleable.LoadingLayout_isNeedTips, true));



        setTips(TextUtils.isEmpty(mConfig.getTips()) ? getContext().getString(R.string.component_loading) : mConfig.getTips());
        if (mConfig.getTextColor() != 0){
            setTipsTextColor(mConfig.getTextColor());
        }
        if (mConfig.getTextSize() != 0f){
            setTipsTextSize(mConfig.getTextSize());
        }

        setBackgroundColor(ContextCompat.getColor(getContext(), mConfig.getBackgroundColor() == 0 ? android.R.color.white : mConfig.getBackgroundColor()));




//        mSelectedBtn.setVisibility(typedArray.getBoolean(R.styleable.UdTextView_isSelectedVisibility, true) ? VISIBLE : GONE);
//        mSelectedBtn.setSelected(typedArray.getBoolean(R.styleable.UdTextView_isSelected, true));
//        mNameTv.setText(typedArray.getString(R.styleable.UdTextView_nameText));
//        mContentTv.setText(typedArray.getString(R.styleable.UdTextView_contentText));
//        mContentTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, typedArray.getResourceId(R.styleable.UdTextView_drawableRight, 0), 0);
//        mRequiredTipsImg.setVisibility(typedArray.getBoolean(R.styleable.UdTextView_isRequired, true) ? VISIBLE : INVISIBLE);
//        mJumpBtn.setVisibility(typedArray.getBoolean(R.styleable.UdTextView_isNeedJump, false) ? VISIBLE : GONE);
//        mJumpBtn.setText(typedArray.getString(R.styleable.UdTextView_jumpText));
//        mKey = typedArray.getInteger(R.styleable.UdTextView_nameKey, 0);

//        mSelectedBtn.setVisibility(typedArray.getBoolean(R.styleable.UdDoubleEditText_isSelectedVisibility, true) ? VISIBLE : GONE);
//        mSelectedBtn.setSelected(typedArray.getBoolean(R.styleable.UdDoubleEditText_isSelected, true));
//        mRequiredTipsImg.setVisibility(typedArray.getBoolean(R.styleable.UdDoubleEditText_isRequired, true) ? VISIBLE : INVISIBLE);
//        mNameTv.setText(typedArray.getString(R.styleable.UdDoubleEditText_nameText));
//        mContentEdit.setText(typedArray.getString(R.styleable.UdDoubleEditText_contentText));
//        setEditInputType(true, typedArray.getInteger(R.styleable.UdDoubleEditText_inputType, UdEditText.TYPE_TEXT));
//        mSecondContentEdit.setText(typedArray.getString(R.styleable.UdDoubleEditText_secondContentText));
//        setEditInputType(false, typedArray.getInteger(R.styleable.UdDoubleEditText_secondInputType, UdEditText.TYPE_TEXT));
//        int drawableImg = typedArray.getResourceId(R.styleable.UdDoubleEditText_drawableImg, 0);
//        mIconImg.setImageResource(drawableImg > 0 ? drawableImg : R.drawable.icon_phone);
//        mKey = typedArray.getInteger(R.styleable.UdDoubleEditText_nameKey, 0);
//        mSecondKey = typedArray.getInteger(R.styleable.UdDoubleEditText_secondKey, 0);


        typedArray.recycle();
    }

//    /**
//     * 设置文本输入类型
//     * @param type 输入类型
//     */
//    public void setEditInputType(boolean isMain, @UdEditText.EditInputType int type) {
//        EditText editText = isMain ? mContentEdit : mSecondContentEdit;
//        if (type < 0){
//            type = UdEditText.TYPE_TEXT;
//        }
//        switch (type){
//            case UdEditText.TYPE_TEXT:
//                editText.setInputType(InputType.TYPE_CLASS_TEXT);
//                break;
//            case UdEditText.TYPE_ID_CARD:
//                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
//                editText.setKeyListener(DigitsKeyListener.getInstance("1234567890xX"));
//                break;
//            case UdEditText.TYPE_PHONE:
//                editText.setInputType(InputType.TYPE_CLASS_PHONE);
//                break;
//            case UdEditText.TYPE_NUMBER:
//                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
//                break;
//            case UdEditText.TYPE_NUMBER_DECIMAL:
//                mContentEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
//                mContentEdit.setKeyListener(DigitsKeyListener.getInstance("1234567890."));
//                break;
//            default:
//                editText.setInputType(InputType.TYPE_CLASS_TEXT);
//                break;
//        }
//    }

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
