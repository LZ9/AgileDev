package com.lodz.android.component.photopicker.picker;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.lodz.android.component.R;
import com.lodz.android.component.photopicker.contract.PhotoLoader;
import com.lodz.android.component.photopicker.contract.picker.OnPhotoPickerListener;
import com.lodz.android.core.album.AlbumUtils;
import com.lodz.android.core.utils.ArrayUtils;
import com.lodz.android.core.utils.ToastUtils;

import java.io.File;
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

        /**
         * 设置是否需要相机功能
         * @param needCamera 是否需要相机功能
         */
        public Builder setNeedCamera(boolean needCamera) {
            pickerBean.isNeedCamera = needCamera;
            return this;
        }

        /**
         * 设置是否需要item的预览功能
         * @param needItemPreview 是否需要item的预览功能
         */
        public Builder setNeedItemPreview(boolean needItemPreview) {
            pickerBean.isNeedItemPreview = needItemPreview;
            return this;
        }

        /**
         * 设置拍照保存地址
         * @param savePath 保存地址
         */
        public Builder setCameraSavePath(@NonNull String savePath) {
            pickerBean.cameraSavePath = savePath;
            return this;
        }

        /**
         * 设置选择器的界面配置
         * @param config 界面配置
         */
        public Builder setPickerUIConfig(PickerUIConfig config) {
            pickerBean.pickerUIConfig = config;
            return this;
        }

        /**
         * 设置7.0的FileProvider名字
         * @param authority 名称
         */
        public Builder setAuthority(String authority){
            pickerBean.authority = authority;
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
            ToastUtils.showShort(context, R.string.component_photo_loader_unset);
            return;
        }
        if (sPickerBean.previewLoader == null){
            sPickerBean.previewLoader = sPickerBean.photoLoader;
        }
        if (sPickerBean.photoPickerListener == null){// 校验图片选中回调监听
            ToastUtils.showShort(context, R.string.component_photo_selected_listener_unset);
            return;
        }
        if (sPickerBean.maxCount < 1){// 修正最大选择数
            sPickerBean.maxCount = 1;
        }
        if (!sPickerBean.isPickAllPhoto && ArrayUtils.isEmpty(sPickerBean.sourceList)){// 校验数据列表
            ToastUtils.showShort(context, R.string.component_photo_source_list_empty);
            return;
        }
        if (sPickerBean.isPickAllPhoto && ArrayUtils.isEmpty(AlbumUtils.getAllImages(context))){// 校验手机内是否有图片
            ToastUtils.showShort(context, R.string.component_photo_source_list_empty);
            return;
        }
        if (!ArrayUtils.isEmpty(sPickerBean.sourceList)){
            sPickerBean.sourceList = ArrayUtils.deduplication(sPickerBean.sourceList);// 对指定的图片列表去重
            sPickerBean.isNeedCamera = false;// 不允许使用拍照模式
        }
        if (!ArrayUtils.isEmpty(sPickerBean.sourceList) && ArrayUtils.getSize(sPickerBean.sourceList) < sPickerBean.maxCount){
            //校验指定图片列表总数和最大可选择数
//            sPickerBean.maxCount = ArrayUtils.getSize(sPickerBean.sourceList);
        }
        if (TextUtils.isEmpty(sPickerBean.cameraSavePath)){// 校验地址
            sPickerBean.cameraSavePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
        }
        if (!sPickerBean.cameraSavePath.endsWith(File.separator)){
            sPickerBean.cameraSavePath  = sPickerBean.cameraSavePath + File.separator;
        }

        if (sPickerBean.pickerUIConfig == null){// 校验UI配置
            sPickerBean.pickerUIConfig = PickerUIConfig.createDefault();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && TextUtils.isEmpty(sPickerBean.authority)) {//当前系统是7.0以上且没有配置FileProvider
            ToastUtils.showShort(context, R.string.component_photo_authority_empty);
            return;
        }

        PhotoPickerActivity.start(context);
    }
}
