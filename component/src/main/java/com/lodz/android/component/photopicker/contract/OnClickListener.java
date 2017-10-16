package com.lodz.android.component.photopicker.contract;

import android.content.Context;

import com.lodz.android.component.photopicker.contract.preview.PreviewController;

/**
 * 点击事件
 * Created by zhouL on 2017/10/16.
 */

public interface OnClickListener<T> {
    void onClick(Context context, T source, int position, PreviewController controller);
}
