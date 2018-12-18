package com.lodz.android.component.photopicker.preview;

import com.lodz.android.component.photopicker.contract.OnClickListener;
import com.lodz.android.component.photopicker.contract.OnLongClickListener;
import com.lodz.android.component.photopicker.contract.PhotoLoader;
import com.lodz.android.core.utils.ArrayUtils;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.ColorRes;

/**
 * 预览数据
 * Created by zhouL on 2017/10/12.
 */

class PreviewBean<T> implements Serializable{

    /** 资源列表 */
    List<T> sourceList;
    /** 图片加载接口 */
    PhotoLoader<T> photoLoader;
    /** 默认显示的图片位置 */
    int showPosition = 0;
    /** 预览页的背景色 */
    @ColorRes
    int backgroundColor = android.R.color.black;
    /** 顶部状态栏颜色 */
    @ColorRes
    int statusBarColor = android.R.color.black;
    /** 底部导航栏颜色 */
    @ColorRes
    int navigationBarColor = android.R.color.black;
    /** 页码文字颜色 */
    @ColorRes
    int pagerTextColor = android.R.color.darker_gray;
    /** 页码文字大小（单位SP） */
    int pagerTextSize = 16;
    /** 是否显示页码文字 */
    boolean isShowPagerText = true;
    /** 点击事件 */
    OnClickListener<T> clickListener;
    /** 长按事件事件 */
    OnLongClickListener<T> longClickListener;
    /** 是否可缩放 */
    boolean isScale = true;

    void clear(){
        if (!ArrayUtils.isEmpty(sourceList)){
            sourceList = null;
        }
        photoLoader = null;
        clickListener = null;
        longClickListener = null;
    }
}
