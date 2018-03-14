package com.lodz.android.component.widget.ninegrid;

import android.content.Context;
import android.widget.ImageView;

/**
 * 简单实现的九宫格接口
 * Created by zhouL on 2018/3/14.
 */

public interface OnSimpleNineGridViewListener {

    /**
     * 展示九宫格图片
     * @param context 上下文
     * @param data 数据
     * @param imageView 控件
     */
    void onDisplayNineGridImg(Context context, String data, ImageView imageView);

    /**
     * 展示预览器图片
     * @param context 上下文
     * @param data 数据
     * @param imageView 控件
     */
    void onDisplayPreviewImg(Context context, String data, ImageView imageView);

    /**
     * 展示选择器图片
     * @param context 上下文
     * @param data 数据
     * @param imageView 控件
     */
    void onDisplayPickerImg(Context context, String data, ImageView imageView);


}
