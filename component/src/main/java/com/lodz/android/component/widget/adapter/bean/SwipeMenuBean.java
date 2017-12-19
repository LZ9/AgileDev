package com.lodz.android.component.widget.adapter.bean;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

/**
 * 侧滑菜单数据
 * Created by zhouL on 2017/12/19.
 */

public class SwipeMenuBean {

    /** 菜单id */
    public int id = 0;

    /** 背景颜色 */
    @ColorRes
    public int bgColor = 0;

    /** 图片资源 */
    @DrawableRes
    public int imgRes = 0;

    /** 图片大小 */
    public int imgSizeDp = 30;

    /** 文字描述 */
    public String text = "";

    /** 文字颜色 */
    @ColorRes
    public int textColor = 0;

    /** 文字大小 */
    public int textSizeSp = 15;

}
