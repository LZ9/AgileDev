package com.lodz.android.component.photopicker.preview;

import android.content.Context;
import android.widget.ImageView;

/**
 * 预览图片加载接口
 * Created by zhouL on 2017/10/13.
 */

public interface PreviewLoader<T> {
    void displayPreviewImg(Context context, T source, ImageView imageView);
}
