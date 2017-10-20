package com.lodz.android.component.photopicker.picker;

import android.content.Context;
import android.support.annotation.IntRange;

import com.lodz.android.component.R;
import com.lodz.android.component.photopicker.contract.PhotoLoader;
import com.lodz.android.component.photopicker.contract.picker.OnPhotoPickerListener;
import com.lodz.android.core.album.AlbumUtils;
import com.lodz.android.core.utils.ArrayUtils;
import com.lodz.android.core.utils.ToastUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 照片选择管理类
 * Created by zhouL on 2017/10/13.
 */

public class PickerManager {

    /** 图片数据 */
    static PickerBean sPickerBean;

    /** 创建构造对象 */
    public static Builder create(){
        return new Builder();
    }

    private PickerManager(Builder builder) {
        sPickerBean = null;
        sPickerBean = builder.pickerBean;
    }

    /** 预览数据构建类 */
    public static class Builder implements Serializable {

        /** 预览数据 */
        private PickerBean pickerBean;

        private Builder() {
            this.pickerBean = new PickerBean();
        }

        /**
         * 设置图片加载器
         * @param photoLoader 图片加载器
         */
        public Builder setImgLoader(PhotoLoader<String> photoLoader) {
            pickerBean.photoLoader = photoLoader;
            return this;
        }

        /**
         * 设置预览图图加载器
         * @param previewLoader 图片加载器
         */
        public Builder setPreviewImgLoader(PhotoLoader<String> previewLoader) {
            pickerBean.previewLoader = previewLoader;
            return this;
        }

        /**
         * 设置图片选中回调
         * @param listener 监听器
         */
        public Builder setOnPhotoPickerListener(OnPhotoPickerListener listener) {
            pickerBean.photoPickerListener = listener;
            return this;
        }

        /**
         * 设置图片可选最大数量
         * @param maxCount 最大数量
         */
        public Builder setMaxCount(@IntRange(from = 1)int maxCount) {
            pickerBean.maxCount = maxCount;
            return this;
        }

        /** 完成构建（选择手机里的全部图片） */
        public PickerManager build() {
            pickerBean.isPickAllPhoto = true;
            return new PickerManager(this);
        }

        /**
         * 完成构建（选择指定的图片）
         * @param sourceList 图片列表
         */
        public PickerManager build(List<String> sourceList) {
            pickerBean.isPickAllPhoto = false;
            pickerBean.sourceList = sourceList;
            return new PickerManager(this);
        }

        /**
         * 完成构建（选择指定的图片）
         * @param sourceArray 图片数组
         */
        public PickerManager build(String[] sourceArray) {
            pickerBean.isPickAllPhoto = false;
            pickerBean.sourceList = ArrayUtils.arrayToList(sourceArray);
            return new PickerManager(this);
        }
    }

    /**
     * 打开预览器
     * @param context 上下文
     */
    public void open(Context context){
        if (sPickerBean.photoLoader == null){// 校验图片加载器
            ToastUtils.showShort(context, R.string.photo_loader_unset);
            return;
        }
        if (sPickerBean.previewLoader == null){
            sPickerBean.previewLoader = sPickerBean.photoLoader;
        }
        if (sPickerBean.photoPickerListener == null){// 校验图片选中回调监听
            ToastUtils.showShort(context, R.string.photo_selected_listener_unset);
            return;
        }
        if (!sPickerBean.isPickAllPhoto && ArrayUtils.isEmpty(sPickerBean.sourceList)){// 校验数据列表
            ToastUtils.showShort(context, R.string.photo_source_list_empty);
            return;
        }
        if (sPickerBean.isPickAllPhoto && ArrayUtils.isEmpty(AlbumUtils.getAllImages(context))){
            ToastUtils.showShort(context, R.string.photo_source_list_empty);
            return;
        }
        PhotoPickerActivity.start(context);
    }
}
