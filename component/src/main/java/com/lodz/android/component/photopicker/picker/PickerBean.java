package com.lodz.android.component.photopicker.picker;

import com.lodz.android.component.photopicker.contract.PhotoLoader;
import com.lodz.android.component.photopicker.contract.picker.OnPhotoPickerListener;
import com.lodz.android.core.utils.ArrayUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 图片数据
 * Created by zhouL on 2017/10/16.
 */

class PickerBean implements Serializable {

    /** 是否挑选手机的全部图片 */
    boolean isPickAllPhoto = true;
    /** 资源列表 */
    List<String> sourceList;
    /** 图片加载接口 */
    PhotoLoader<String> photoLoader;
    /** 预览图加载接口 */
    PhotoLoader<String> previewLoader;
    /** 照片回调接口 */
    OnPhotoPickerListener photoPickerListener;
    /** 可选最大数量 */
    int maxCount = 9;
    /** 是否需要相机功能 */
    boolean isNeedCamera = false;
    /** 拍照保存地址 */
    String cameraSavePath = "";
    /** UI配置 */
    PickerUIConfig pickerUIConfig;
    /** 7.0的FileProvider名字 */
    String authority = "";


    void clear(){
        if (!ArrayUtils.isEmpty(sourceList)){
            sourceList = null;
        }
        photoLoader = null;
        previewLoader = null;
        photoPickerListener = null;
    }
}
