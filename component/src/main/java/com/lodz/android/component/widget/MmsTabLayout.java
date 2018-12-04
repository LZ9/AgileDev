package com.lodz.android.component.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.lodz.android.component.R;
import com.lodz.android.core.utils.DensityUtils;
import com.lodz.android.core.utils.ReflectUtils;

/**
 * 可设置底线宽度的TabLayout
 * Created by zhouL on 2017/5/11.
 */
public class MmsTabLayout extends TabLayout{

    /** 两侧间距 */
    private int mTabMargin = 0;

    public MmsTabLayout(Context context) {
        super(context);
        init(null);
    }

    public MmsTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MmsTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        if (attrs == null){
            return;
        }
        config(attrs);
    }

    private void config(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MmsTabLayout);
        if (typedArray == null){
            return;
        }

        mTabMargin = typedArray.getDimensionPixelSize(R.styleable.MmsTabLayout_tabMargin, 0);
        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed){
            if (mTabMargin != 0){
                setTabIndicatorMargin((int) DensityUtils.px2dp(getContext(), mTabMargin));
            }
        }
    }

    /**
     * 设置底线两侧间距
     * @param marginDp 两侧间距
     */
    public void setTabIndicatorMargin(int marginDp) {
        Class<?> cl =  ReflectUtils.getClassForName("com.google.android.material.tabs.TabLayout");
        LinearLayout layout = null;
        try {
            layout = (LinearLayout) ReflectUtils.getFieldValue(cl, this, "slidingTabIndicator");
        }catch (Exception e){
            e.printStackTrace();
        }

        if (layout == null){
            return;
        }

        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginDp, Resources.getSystem().getDisplayMetrics());
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child != null && child.getLayoutParams() instanceof LinearLayout.LayoutParams){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) child.getLayoutParams();
                params.leftMargin = margin;
                params.rightMargin = margin;
                child.setLayoutParams(params);
                child.invalidate();
            }
        }
    }
}
