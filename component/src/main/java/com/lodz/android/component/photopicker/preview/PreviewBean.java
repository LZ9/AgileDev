package com.lodz.android.component.photopicker.preview;

import java.io.Serializable;
import java.util.List;

/**
 * 预览数据
 * Created by zhouL on 2017/10/12.
 */

public class PreviewBean<T> implements Serializable{

    /** 本地资源列表 */
    public List<T> sourceList;
    /** 图片加载接口 */
    public PreviewLoader<T> previewLoader;
    /** 默认显示的图片位置 */
    public int showPosition = 0;

}
