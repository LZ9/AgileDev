package com.lodz.android.component.photopicker.contract;

import android.content.Context;

import com.lodz.android.component.photopicker.contract.preview.PreviewController;

/**
 * 长按事件
 * Created by zhouL on 2017/10/16.
 */

public interface OnLongClickListener<T> {
    void onLongClick(Context context, T source, int position, PreviewController controller);
}
