package com.lodz.android.component.photopicker.preview;

import android.support.annotation.ColorRes;

import com.lodz.android.component.photopicker.contract.OnClickListener;
import com.lodz.android.component.photopicker.contract.OnLongClickListener;
import com.lodz.android.component.photopicker.contract.preview.PreviewLoader;

import java.io.Serializable;
import java.util.List;

/**
 * 预览数据
 * Created by zhouL on 2017/10/12.
 */

public class PreviewBean<T> implements Serializable{

    /** 本地资源列表 */
    public List<T> sourceList;
    /** 图片加载接口 */
    public PreviewLoader<T> previewLoader;
    /** 默认显示的图片位置 */
    public int showPosition = 0;
    /** 预览页的背景色 */
    @ColorRes
    public int backgroundColor = android.R.color.black;
    /** 顶部状态栏颜色 */
    @ColorRes
    public int statusBarColor = 0;
    /** 底部导航栏颜色 */
    @ColorRes
    public int navigationBarColor = 0;
    /** 页码文字颜色 */
    @ColorRes
    public int pagerTextColor = android.R.color.darker_gray;
    /** 页码文字大小（单位SP） */
    public int pagerTextSize = 16;
    /** 点击事件 */
    public OnClickListener<T> clickListener;
    /** 长按事件事件 */
    public OnLongClickListener<T> longClickListener;
    /** 是否可缩放 */
    public boolean isScale = true;

}
