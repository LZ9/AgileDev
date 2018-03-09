package com.lodz.android.component.widget.ninegrid;

import android.content.Context;
import android.widget.ImageView;

/**
 * 九宫格接口
 * Created by zhouL on 2018/3/9.
 */

public interface OnNineGridViewListener {
    /**
     * 添加图片
     * @param addCount 可添加的数量
     */
    void onAddPic(int addCount);

    /**
     * 展示图片
     * @param context 上下文
     * @param data 数据
     * @param imageView 控件
     */
    void onDisplayImg(Context context, String data, ImageView imageView);

    /**
     * 删除图片
     * @param data 数据
     * @param position 位置
     */
    void onDeletePic(String data, int position);

    /**
     * 点击图片
     * @param data 图片
     * @param position 位置
     */
    void onClickPic(String data, int position);
}
