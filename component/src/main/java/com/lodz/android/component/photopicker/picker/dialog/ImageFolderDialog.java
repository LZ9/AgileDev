package com.lodz.android.component.photopicker.picker.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lodz.android.component.widget.dialog.BaseDialog;

/**
 * 图片文件弹框
 * Created by zhouL on 2017/10/19.
 */

public class ImageFolderDialog extends BaseDialog{

    public ImageFolderDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void findViews() {

    }
}
