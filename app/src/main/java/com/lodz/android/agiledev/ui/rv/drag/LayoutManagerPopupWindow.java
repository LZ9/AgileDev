package com.lodz.android.agiledev.ui.rv.drag;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.popup.BasePopupWindow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 横纵向选择
 * Created by zhouL on 2017/9/5.
 */

public class LayoutManagerPopupWindow extends BasePopupWindow{

    public static final int TYPE_LINEAR = 1;
    public static final int TYPE_GRID = 2;
    public static final int TYPE_STAGGERED = 3;

    @IntDef({TYPE_LINEAR, TYPE_GRID, TYPE_STAGGERED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LayoutManagerType {}

    /** 线性布局 */
    @BindView(R.id.linear_btn)
    TextView mLinearBtn;

    /** 网格布局 */
    @BindView(R.id.grid_btn)
    TextView mGridBtn;

    /** 瀑布流 */
    @BindView(R.id.staggered_btn)
    TextView mStaggeredBtn;

    private Listener mListener;

    public LayoutManagerPopupWindow(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_layout_manager_layout;
    }

    @Override
    protected void findViews(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mLinearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClick(getPopup(), TYPE_LINEAR);
                }
            }
        });

        mGridBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClick(getPopup(), TYPE_GRID);
                }
            }
        });

        mStaggeredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClick(getPopup(), TYPE_STAGGERED);
                }
            }
        });
    }

    /**
     * 设置布局类型
     * @param type 类型
     */
    public void setLayoutManagerType(@LayoutManagerType int type){
        mLinearBtn.setBackgroundResource(type == TYPE_LINEAR ? R.drawable.bg_f0f0f0_stroke_00a0e9 : R.drawable.bg_f0f0f0_stroke_cccccc);
        mLinearBtn.setTextColor(ContextCompat.getColor(getContext(), type == TYPE_LINEAR ? R.color.color_00a0e9 : R.color.color_9a9a9a));
        mGridBtn.setBackgroundResource(type == TYPE_GRID ? R.drawable.bg_f0f0f0_stroke_00a0e9 : R.drawable.bg_f0f0f0_stroke_cccccc);
        mGridBtn.setTextColor(ContextCompat.getColor(getContext(), type == TYPE_GRID ? R.color.color_00a0e9 : R.color.color_9a9a9a));
        mStaggeredBtn.setBackgroundResource(type == TYPE_STAGGERED ? R.drawable.bg_f0f0f0_stroke_00a0e9 : R.drawable.bg_f0f0f0_stroke_cccccc);
        mStaggeredBtn.setTextColor(ContextCompat.getColor(getContext(), type == TYPE_STAGGERED ? R.color.color_00a0e9 : R.color.color_9a9a9a));
    }

    public void setListener(Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onClick(PopupWindow popupWindow, @LayoutManagerType int type);
    }
}
