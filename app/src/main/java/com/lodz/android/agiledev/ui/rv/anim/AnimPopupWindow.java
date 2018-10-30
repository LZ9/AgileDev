package com.lodz.android.agiledev.ui.rv.anim;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.component.widget.popup.BasePopupWindow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 横纵向选择
 * Created by zhouL on 2017/9/5.
 */

public class AnimPopupWindow extends BasePopupWindow{

    /** 自定义 */
    public static final int TYPE_CUSTOM = 0;

    @IntDef({BaseRecyclerViewAdapter.ALPHA_IN, BaseRecyclerViewAdapter.SCALE_IN, BaseRecyclerViewAdapter.SLIDE_IN_BOTTOM,
            BaseRecyclerViewAdapter.SLIDE_IN_LEFT, BaseRecyclerViewAdapter.SLIDE_IN_RIGHT, TYPE_CUSTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimType {}

    /** 淡入 */
    @BindView(R.id.alpha_in_btn)
    TextView mAlphaInBtn;

    /** 缩放 */
    @BindView(R.id.scale_in_btn)
    TextView mScaleInBtn;

    /** 底部进入 */
    @BindView(R.id.slide_in_bottom_btn)
    TextView mSlideInBottomBtn;

    /** 左侧进入 */
    @BindView(R.id.slide_in_left_btn)
    TextView mSlideInLeftBtn;

    /** 右侧进入 */
    @BindView(R.id.slide_in_right_btn)
    TextView mSlideInRightBtn;

    /** 自定义 */
    @BindView(R.id.custom_btn)
    TextView mCustomBtn;

    private Listener mListener;

    public AnimPopupWindow(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_anim_layout;
    }

    @Override
    protected void findViews(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mAlphaInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClick(getPopup(), BaseRecyclerViewAdapter.ALPHA_IN);
                }
            }
        });

        mScaleInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClick(getPopup(), BaseRecyclerViewAdapter.SCALE_IN);
                }
            }
        });

        mSlideInBottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClick(getPopup(), BaseRecyclerViewAdapter.SLIDE_IN_BOTTOM);
                }
            }
        });

        mSlideInLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClick(getPopup(), BaseRecyclerViewAdapter.SLIDE_IN_LEFT);
                }
            }
        });

        mSlideInRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClick(getPopup(), BaseRecyclerViewAdapter.SLIDE_IN_RIGHT);
                }
            }
        });

        mCustomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClick(getPopup(), TYPE_CUSTOM);
                }
            }
        });
    }

    /**
     * 设置动画类型
     * @param type 类型
     */
    public void setAnimType(@AnimType int type){
        mAlphaInBtn.setBackgroundResource(type == BaseRecyclerViewAdapter.ALPHA_IN ? R.drawable.bg_f0f0f0_stroke_00a0e9 : R.drawable.bg_f0f0f0_stroke_cccccc);
        mAlphaInBtn.setTextColor(ContextCompat.getColor(getContext(), type == BaseRecyclerViewAdapter.ALPHA_IN ? R.color.color_00a0e9 : R.color.color_9a9a9a));
        mScaleInBtn.setBackgroundResource(type == BaseRecyclerViewAdapter.SCALE_IN ? R.drawable.bg_f0f0f0_stroke_00a0e9 : R.drawable.bg_f0f0f0_stroke_cccccc);
        mScaleInBtn.setTextColor(ContextCompat.getColor(getContext(), type == BaseRecyclerViewAdapter.SCALE_IN ? R.color.color_00a0e9 : R.color.color_9a9a9a));
        mSlideInBottomBtn.setBackgroundResource(type == BaseRecyclerViewAdapter.SLIDE_IN_BOTTOM ? R.drawable.bg_f0f0f0_stroke_00a0e9 : R.drawable.bg_f0f0f0_stroke_cccccc);
        mSlideInBottomBtn.setTextColor(ContextCompat.getColor(getContext(), type == BaseRecyclerViewAdapter.SLIDE_IN_BOTTOM ? R.color.color_00a0e9 : R.color.color_9a9a9a));
        mSlideInLeftBtn.setBackgroundResource(type == BaseRecyclerViewAdapter.SLIDE_IN_LEFT ? R.drawable.bg_f0f0f0_stroke_00a0e9 : R.drawable.bg_f0f0f0_stroke_cccccc);
        mSlideInLeftBtn.setTextColor(ContextCompat.getColor(getContext(), type == BaseRecyclerViewAdapter.SLIDE_IN_LEFT ? R.color.color_00a0e9 : R.color.color_9a9a9a));
        mSlideInRightBtn.setBackgroundResource(type == BaseRecyclerViewAdapter.SLIDE_IN_RIGHT ? R.drawable.bg_f0f0f0_stroke_00a0e9 : R.drawable.bg_f0f0f0_stroke_cccccc);
        mSlideInRightBtn.setTextColor(ContextCompat.getColor(getContext(), type == BaseRecyclerViewAdapter.SLIDE_IN_RIGHT ? R.color.color_00a0e9 : R.color.color_9a9a9a));
        mCustomBtn.setBackgroundResource(type == TYPE_CUSTOM ? R.drawable.bg_f0f0f0_stroke_00a0e9 : R.drawable.bg_f0f0f0_stroke_cccccc);
        mCustomBtn.setTextColor(ContextCompat.getColor(getContext(), type == TYPE_CUSTOM ? R.color.color_00a0e9 : R.color.color_9a9a9a));
    }

    public void setListener(Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onClick(PopupWindow popupWindow, @AnimType int type);
    }

}
