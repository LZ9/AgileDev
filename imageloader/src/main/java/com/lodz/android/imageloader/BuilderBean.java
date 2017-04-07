package com.lodz.android.imageloader;

import android.net.Uri;
import android.support.annotation.DrawableRes;

/**
 * 图片加载库的构建类
 * Created by zhouL on 2017/4/7.
 */

public class BuilderBean {

    /** 加载路径 */
    public Uri uri = null;

    /** 加载图的资源id */
    @DrawableRes
    public int placeholderResId = R.drawable.ic_launcher;

    /** 失败图的资源id */
    @DrawableRes
    public int errorResId = R.drawable.ic_launcher;

    /** 控件宽度 */
    public int width = 0;

    /** 控件高度 */
    public int height = 0;

    /** 使用高斯模糊 */
    public boolean useBlur = false;

    /** 高斯模糊率（0-25） */
    public int blurRadius = 5;

    /** 是否使用圆形图片 */
    public boolean useCircle = false;

    /** 是否使用圆角 */
    public boolean useRoundCorner = false;

    /** 圆角半径 */
    public float roundCornerRadius = 10f;
}
