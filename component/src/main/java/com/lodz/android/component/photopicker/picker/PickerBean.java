package com.lodz.android.component.photopicker.picker;

import com.lodz.android.component.photopicker.contract.PhotoLoader;

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

}
