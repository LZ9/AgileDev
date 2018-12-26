package com.lodz.android.component.widget.ninegrid;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lodz.android.component.photopicker.contract.PhotoLoader;
import com.lodz.android.component.photopicker.contract.picker.OnPhotoPickerListener;
import com.lodz.android.component.photopicker.contract.preview.PreviewController;
import com.lodz.android.component.photopicker.picker.PickerManager;
import com.lodz.android.component.photopicker.picker.PickerUIConfig;
import com.lodz.android.component.photopicker.preview.PreviewManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * 简单的九宫格实现
 * Created by zhouL on 2018/3/14.
 */

public class SimpleNineGridView extends NineGridView{

    /** 接口 */
    private OnSimpleNineGridViewListener mListener;
    /** 照片保存地址 */
    private String mCameraSavePath = "";
    /** 7.0的FileProvider名字 */
    private String mAuthority = "";


    public SimpleNineGridView(@NonNull Context context) {
        super(context);
        init();
    }

    public SimpleNineGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleNineGridView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SimpleNineGridView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        setListener(new OnNineGridViewListener() {
            @Override
            public void onAddPic(int addCount) {
                PickerManager
                        .create()
                        .setImgLoader(new PhotoLoader<String>() {
                            @Override
                            public void displayImg(Context context, String source, ImageView imageView) {
                                if (mListener != null){
                                    mListener.onDisplayPickerImg(context, source, imageView);
                                }
                            }
                        })
                        .setPreviewImgLoader(new PhotoLoader<String>() {
                            @Override
                            public void displayImg(Context context, String source, ImageView imageView) {
                                if (mListener != null){
                                    mListener.onDisplayPreviewImg(context, source, imageView);
                                }
                            }
                        })
                        .setOnPhotoPickerListener(new OnPhotoPickerListener() {
                            @Override
                            public void onPickerSelected(List<String> photos) {
                                addDatas(photos);
                            }
                        })
                        .setMaxCount(addCount)
                        .setNeedCamera(true)
                        .setNeedItemPreview(true)
                        .setCameraSavePath(mCameraSavePath)
                        .setPickerUIConfig(PickerUIConfig.createDefault())
                        .setAuthority(mAuthority)
                        .build()
                        .open(getContext());
            }

            @Override
            public void onDisplayImg(Context context, String data, ImageView imageView) {
                if (mListener != null){
                    mListener.onDisplayNineGridImg(context, data, imageView);
                }
            }

            @Override
            public void onDeletePic(String data, int position) {
                removeDatas(position);
            }

            @Override
            public void onClickPic(String data, int position) {
                PreviewManager
                        .<String>create()
                        .setPosition(position)
                        .setScale(true)
                        .setBackgroundColor(android.R.color.black)
                        .setStatusBarColor(android.R.color.black)
                        .setPagerTextColor(android.R.color.white)
                        .setPagerTextSize(14)
                        .setShowPagerText(true)
                        .setOnClickListener(new com.lodz.android.component.photopicker.contract.OnClickListener<String>() {
                            @Override
                            public void onClick(Context context, String source, int position, PreviewController controller) {
                                controller.close();
                            }
                        })
                        .setImgLoader(new PhotoLoader<String>() {
                            @Override
                            public void displayImg(Context context, String source, ImageView imageView) {
                                if (mListener != null){
                                    mListener.onDisplayPreviewImg(context, source, imageView);
                                }
                            }
                        })
                        .build(getPicData())
                        .open(getContext());
            }
        });
    }

    /**
     * 配置数据
     * @param savePath 图片保存地址
     * @param authority FileProvider名字
     */
    public void config(@NonNull String savePath, @NonNull String authority){
        mCameraSavePath = savePath;
        mAuthority = authority;
    }

    /**
     * 设置监听器
     * @param listener 监听器
     */
    public void setOnSimpleNineGridViewListener(OnSimpleNineGridViewListener listener){
        mListener = listener;
    }

    @Override
    public void addData(@NonNull List<String> data) {}

    @Override
    public void removeData(int position) {}

    @Override
    public void setOnNineGridViewListener(OnNineGridViewListener listener) {}

    private void addDatas(@NonNull List<String> data) {
        super.addData(data);
    }

    private void removeDatas(int position) {
        super.removeData(position);
    }

    private void setListener(OnNineGridViewListener listener) {
        super.setOnNineGridViewListener(listener);
    }

}
