package com.lodz.android.component.photopicker.picker;

import com.lodz.android.component.photopicker.contract.PhotoLoader;
import com.lodz.android.component.photopicker.contract.picker.OnPhotoPickerListener;

import java.io.Serializable;
import java.util.List;

/**
 * 图片数据
 * Created by zhouL on 2017/10/16.
 */

public class PickerBean implements Serializable {

    /** 是否挑选手机的全部图片 */
    public boolean isPickAllPhoto = true;
    /** 资源列表 */
    public List<String> sourceList;
    /** 图片加载接口 */
    public PhotoLoader<String> photoLoader;
    /** 预览图加载接口 */
    public PhotoLoader<String> previewLoader;
    /** 照片回调接口 */
    public OnPhotoPickerListener photoPickerListener;
    /** 可选最大数量 */
    public int maxCount = 9;



    /** 按钮主颜色 */
    public int btnMainColor;
    /** 按钮副颜色 */
    public int btnViceColor;
    /** 按钮不可用颜色 */
    public int btnUnableColor;


}
