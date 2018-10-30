package com.lodz.android.component.widget.contract;

import com.google.android.material.appbar.AppBarLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * AppBarLayout的滑动偏移监听器
 * Created by zhouL on 2016/12/29.
 */
public abstract class OnAppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    /** 完全展开 */
    public static final int EXPANDED = 0;
    /** 完全折叠 */
    public static final int COLLAPSED = 1;
    /** 滑动中 */
    public static final int SCROLLING = 2;

    @IntDef({EXPANDED, COLLAPSED, SCROLLING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {}

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            //张开
            onStateChanged(appBarLayout, EXPANDED, 0.0d);
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            //收缩
            onStateChanged(appBarLayout, COLLAPSED, 1.0d);
        } else {
            double delta = (double) Math.abs(verticalOffset) / (double) appBarLayout.getTotalScrollRange();
            onStateChanged(appBarLayout, SCROLLING, delta);
        }

    }

    /**
     * 状态回调
     * @param state 状态
     * @param delta 偏移参数 0展开 1收缩 0.0-1.0滚动
     */
    public abstract void onStateChanged(AppBarLayout appBarLayout, @Status int state, double delta);
}