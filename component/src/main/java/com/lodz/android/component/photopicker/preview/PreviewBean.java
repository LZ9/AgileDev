package com.lodz.android.component.photopicker.preview;

import android.graphics.Bitmap;
import android.support.annotation.IntDef;

import com.lodz.android.core.utils.ArrayUtils;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * 预览数据
 * Created by zhouL on 2017/10/12.
 */

public class PreviewBean implements Serializable{

    /** 未选择资源 */
    public static final int TYPE_NONE = 0;
    /** 本地资源 */
    public static final int TYPE_DRAWABLE_RES = 1;
    /** 网络图片 */
    public static final int TYPE_URL = 2;
    /** 文件路径 */
    public static final int TYPE_FILE_PATH = 3;
    /** bitmap */
    public static final int TYPE_BITMAP = 4;

    @IntDef({TYPE_NONE, TYPE_DRAWABLE_RES, TYPE_URL, TYPE_FILE_PATH, TYPE_BITMAP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PictureType {}

    @PictureType
    public int pictureType = TYPE_NONE;

    /** 本地资源列表 */
    public List<Integer> drawableResList;
    /** 网络图片列表 */
    public List<String> urlList;
    /** 文件路径列表 */
    public List<String> filePathList;
    /** bitmap列表 */
    public List<Bitmap> bitmapList;

    /** 获取图片总数量 */
    public int getPictureCount(){
        switch (pictureType){
            case TYPE_DRAWABLE_RES:
                return ArrayUtils.isEmpty(drawableResList) ? 0 : drawableResList.size();
            case TYPE_URL:
                return ArrayUtils.isEmpty(urlList) ? 0 : urlList.size();
            case TYPE_FILE_PATH:
                return ArrayUtils.isEmpty(filePathList) ? 0 : filePathList.size();
            case TYPE_BITMAP:
                return ArrayUtils.isEmpty(bitmapList) ? 0 : bitmapList.size();
            case TYPE_NONE:
            default:
                return 0;
        }
    }
}
