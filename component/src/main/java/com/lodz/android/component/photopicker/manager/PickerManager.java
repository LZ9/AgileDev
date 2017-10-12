package com.lodz.android.component.photopicker.manager;

import android.content.Context;
import android.support.annotation.DrawableRes;

import com.lodz.android.component.photopicker.preview.PicturePreviewActivity;
import com.lodz.android.component.photopicker.preview.PreviewBean;

import java.util.ArrayList;

/**
 * 照片管理类
 * Created by zhouL on 2017/10/12.
 */

public class PickerManager {

    public static void previewPicture(Context context, @DrawableRes int resId){
        PreviewBean bean = new PreviewBean();
        bean.pictureType = PreviewBean.TYPE_DRAWABLE_RES;
        bean.drawableResList = new ArrayList<>();
        bean.drawableResList.add(resId);
        PicturePreviewActivity.start(context, bean);
    }


}
