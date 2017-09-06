package com.lodz.android.agiledev.ui.rv.drag;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.popup.BasePopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 横纵向选择
 * Created by zhouL on 2017/9/5.
 */

public class OrientationPopupWindow extends BasePopupWindow{

    /** 纵向按钮 */
    @BindView(R.id.vertical_btn)
    TextView mVerticalBtn;

    /** 横向按钮 */
    @BindView(R.id.horizontal_btn)
    TextView mHorizontalBtn;

    private Listener mListener;

    public OrientationPopupWindow(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_orientation_layout;
    }

    @Override
    protected void findViews(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mVerticalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClick(getPopup(), true);
                }
            }
        });

        mHorizontalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClick(getPopup(), false);
                }
            }
        });
    }

    /**
     * 设置是否纵向
     * @param isVertical 是否纵向
     */
    public void setIsVertical(boolean isVertical){
        mVerticalBtn.setBackgroundResource(isVertical ? R.drawable.bg_f0f0f0_stroke_00a0e9 : R.drawable.bg_f0f0f0_stroke_cccccc);
        mVerticalBtn.setTextColor(ContextCompat.getColor(getContext(), isVertical ? R.color.color_00a0e9 : R.color.color_9a9a9a));
        mHorizontalBtn.setBackgroundResource(isVertical ? R.drawable.bg_f0f0f0_stroke_cccccc : R.drawable.bg_f0f0f0_stroke_00a0e9);
        mHorizontalBtn.setTextColor(ContextCompat.getColor(getContext(), isVertical ? R.color.color_9a9a9a : R.color.color_00a0e9));
    }

    public void setListener(Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onClick(PopupWindow popupWindow, boolean isVertical);
    }
}
