package com.lodz.android.component.photopicker.picker;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.lodz.android.component.R;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.component.photopicker.contract.OnClickListener;
import com.lodz.android.component.photopicker.contract.PhotoLoader;
import com.lodz.android.component.photopicker.contract.preview.PreviewController;
import com.lodz.android.component.photopicker.picker.dialog.ImageFolderDialog;
import com.lodz.android.component.photopicker.picker.dialog.ImageFolderIteamBean;
import com.lodz.android.component.photopicker.preview.PreviewManager;
import com.lodz.android.component.rx.subscribe.observer.BaseObserver;
import com.lodz.android.component.rx.utils.RxUtils;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.core.album.AlbumUtils;
import com.lodz.android.core.album.ImageFolder;
import com.lodz.android.core.utils.AnimUtils;
import com.lodz.android.core.utils.ArrayUtils;
import com.lodz.android.core.utils.BitmapUtils;
import com.lodz.android.core.utils.DateUtils;
import com.lodz.android.core.utils.DensityUtils;
import com.lodz.android.core.utils.DeviceUtils;
import com.lodz.android.core.utils.DrawableUtils;
import com.lodz.android.core.utils.FileUtils;
import com.lodz.android.core.utils.SelectorUtils;
import com.lodz.android.core.utils.ToastUtils;
import com.lodz.android.core.utils.UiHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 照片选择页面
 * Created by zhouL on 2017/9/22.
 */

public class PhotoPickerActivity extends AbsActivity{

    /**
     * 启动页面
     * @param context 上下文
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, PhotoPickerActivity.class);
        context.startActivity(starter);
    }

    /** 照相请求码 */
    private static final int REQUEST_CAMERA = 777;

    /** 返回按钮 */
    private ImageView mBackBtn;
    /** 确定按钮 */
    private TextView mConfirmBtn;
    /** 文件夹按钮 */
    private ViewGroup mFolderBtn;
    /** 文件夹名称 */
    private TextView mFolderTextTv;
    /** 更多图片 */
    private ImageView mMoreImg;
    /** 预览按钮 */
    private TextView mPreviewBtn;

    /** 列表 */
    private RecyclerView mRecyclerView;
    /** 列表适配器 */
    private PhotoPickerAdapter mAdapter;

    /** 选择数据 */
    private PickerBean mPickerBean;
    /** 当前照片 */
    private List<PickerItemBean> mCurrentPhotoList = new ArrayList<>();
    /** 已选中的照片 */
    private List<PickerItemBean> mSelectedList = new ArrayList<>();

    /** 临时文件路径 */
    private String mTempFilePath = "";

    @Override
    protected void startCreate() {
        super.startCreate();
        mPickerBean = PickerManager.sPickerBean;
    }

    @Override
    protected int getAbsLayoutId() {
        return R.layout.component_activity_picker_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        TextView titleTv = findViewById(R.id.title);
        ViewGroup topLayout = findViewById(R.id.top_layout);
        ViewGroup bottomLayout = findViewById(R.id.bottom_layout);
        mBackBtn = findViewById(R.id.back_btn);
        mConfirmBtn = findViewById(R.id.confirm_btn);
        mRecyclerView = findViewById(R.id.recycler_view);
        mFolderBtn = findViewById(R.id.folder_btn);
        mFolderTextTv = findViewById(R.id.folder_text);
        mMoreImg = findViewById(R.id.more_img);
        mPreviewBtn = findViewById(R.id.preview_btn);

        initRecyclerView();
        topLayout.setBackgroundColor(ContextCompat.getColor(getContext(), mPickerBean.pickerUIConfig.getTopLayoutColor()));
        bottomLayout.setBackgroundColor(ContextCompat.getColor(getContext(), mPickerBean.pickerUIConfig.getBottomLayoutColor()));
        titleTv.setTextColor(ContextCompat.getColor(getContext(), mPickerBean.pickerUIConfig.getMainTextColor()));
        mFolderTextTv.setTextColor(ContextCompat.getColor(getContext(), mPickerBean.pickerUIConfig.getMainTextColor()));
        drawBackBtn(mPickerBean.pickerUIConfig.getBackBtnColor());
        if (mPickerBean.pickerUIConfig.getMoreFolderImg() != 0){
            mMoreImg.setImageResource(mPickerBean.pickerUIConfig.getMoreFolderImg());
        }else {
            drawMoreImg(mPickerBean.pickerUIConfig.getMainTextColor());
        }
        drawConfirmBtn();
        mPreviewBtn.setTextColor(SelectorUtils.createTxPressedUnableColor(getContext(),
                mPickerBean.pickerUIConfig.getPreviewBtnNormal(),
                mPickerBean.pickerUIConfig.getPreviewBtnUnable(),
                mPickerBean.pickerUIConfig.getPreviewBtnUnable()));
        mConfirmBtn.setEnabled(false);
        mPreviewBtn.setEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DeviceUtils.setStatusBarColor(getContext(), getWindow(), mPickerBean.pickerUIConfig.getStatusBarColor());
            DeviceUtils.setNavigationBarColor(getContext(), getWindow(), mPickerBean.pickerUIConfig.getNavigationBarColor());
        }
    }

    /** 初始化RecyclerView */
    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mAdapter = new PhotoPickerAdapter(getContext(), mPickerBean.photoLoader, mPickerBean.isNeedCamera, mPickerBean.pickerUIConfig);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 绘制返回按钮
     * @param color 颜色
     */
    private void drawBackBtn(@ColorRes int color) {
        Observable.just(color)
                .map(new Function<Integer, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull Integer colorRes) throws Exception {
                        return BitmapUtils.rotateBitmap(getArrowBitmap(colorRes), 90);
                    }
                })
                .compose(RxUtils.<Bitmap>ioToMainObservable())
                .subscribe(new BaseObserver<Bitmap>() {
                    @Override
                    public void onBaseNext(Bitmap bitmap) {
                        mBackBtn.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBaseError(Throwable e) {

                    }
                });
    }

    /**
     * 绘制更多图标
     * @param color 颜色
     */
    private void drawMoreImg(@ColorRes int color) {
        Observable.just(color)
                .map(new Function<Integer, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull Integer colorRes) throws Exception {
                        return getArrowBitmap(colorRes);
                    }
                })
                .compose(RxUtils.<Bitmap>ioToMainObservable())
                .subscribe(new BaseObserver<Bitmap>() {
                    @Override
                    public void onBaseNext(Bitmap bitmap) {
                        mMoreImg.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBaseError(Throwable e) {

                    }
                });
    }

    /** 绘制确定按钮 */
    private void drawConfirmBtn() {
        mConfirmBtn.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mConfirmBtn.getViewTreeObserver().removeOnPreDrawListener(this);
                int width = mConfirmBtn.getMeasuredWidth();
                int height = mConfirmBtn.getMeasuredHeight();

                StateListDrawable drawable = SelectorUtils.createBgPressedUnableDrawable(
                        getCornerDrawable(mPickerBean.pickerUIConfig.getConfirmBtnNormal(), width, height),
                        getCornerDrawable(mPickerBean.pickerUIConfig.getConfirmBtnPressed(), width, height),
                        getCornerDrawable(mPickerBean.pickerUIConfig.getConfirmBtnUnable(), width, height));
                if (drawable == null){
                    return true;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mConfirmBtn.setBackground(drawable);
                }else {
                    mConfirmBtn.setBackgroundDrawable(drawable);
                }
                mConfirmBtn.setTextColor(SelectorUtils.createTxPressedUnableColor(getContext(),
                        mPickerBean.pickerUIConfig.getConfirmTextNormal(),
                        mPickerBean.pickerUIConfig.getConfirmTextPressed(),
                        mPickerBean.pickerUIConfig.getConfirmTextUnable()));
                return true;
            }
        });
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        // 返回按钮
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 文件夹按钮
        mFolderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ImageFolderIteamBean> folders = new ArrayList<>();

                // 组装数据
                for (ImageFolder folder : AlbumUtils.getAllImageFolders(getContext())) {
                    ImageFolderIteamBean iteamBean = new ImageFolderIteamBean();
                    iteamBean.imageFolder = folder;
                    iteamBean.isSelected = mFolderTextTv.getText().toString().equals(folder.getName());
                    folders.add(iteamBean);
                }

                ImageFolderDialog dialog = new ImageFolderDialog(getContext());
                dialog.setPhotoLoader(mPickerBean.photoLoader);
                dialog.setPickerUIConfig(mPickerBean.pickerUIConfig);
                dialog.setData(folders);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        AnimUtils.startRotateSelf(mMoreImg, -180, 0, 500, true);
                    }
                });
                dialog.setListener(new ImageFolderDialog.Listener() {
                    @Override
                    public void onSelected(DialogInterface dialog, ImageFolderIteamBean bean) {
                        dialog.dismiss();
                        if (mFolderTextTv.getText().toString().equals(bean.imageFolder.getName())){// 选择了同一个文件夹
                            return;
                        }
                        mFolderTextTv.setText(bean.imageFolder.getName());
                        configAdapterData(AlbumUtils.getImageListOfFolder(getContext(), bean.imageFolder));
                        AnimUtils.startRotateSelf(mMoreImg, -180, 0, 500, true);
                    }
                });
                dialog.show();
                AnimUtils.startRotateSelf(mMoreImg, 0, -180, 500, true);
            }
        });

        // 确定按钮
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<>();
                for (PickerItemBean itemBean : mSelectedList) {
                    list.add(itemBean.photoPath);
                }
                if (mPickerBean != null && mPickerBean.photoPickerListener != null){
                    mPickerBean.photoPickerListener.onPickerSelected(list);
                }
                finish();
            }
        });

        // 预览按钮
        mPreviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<>();
                for (PickerItemBean itemBean : mSelectedList) {
                    list.add(itemBean.photoPath);// 组装数据
                }

                PreviewManager
                        .<String>create()
                        .setBackgroundColor(mPickerBean.pickerUIConfig.getItemBgColor())
                        .setStatusBarColor(mPickerBean.pickerUIConfig.getStatusBarColor())
                        .setNavigationBarColor(mPickerBean.pickerUIConfig.getNavigationBarColor())
                        .setPagerTextColor(mPickerBean.pickerUIConfig.getMainTextColor())
                        .setOnClickListener(new OnClickListener<String>() {
                            @Override
                            public void onClick(Context context, String source, int position, PreviewController controller) {
                                controller.close();
                            }
                        })
                        .setImgLoader(new PhotoLoader<String>() {
                            @Override
                            public void displayImg(Context context, String source, ImageView imageView) {
                                mPickerBean.previewLoader.displayImg(context, source, imageView);
                            }
                        })
                        .build(list)
                        .open(getContext());
            }
        });

        // 图片选中回调
        mAdapter.setListener(new PhotoPickerAdapter.Listener() {
            @Override
            public void onSelected(PickerItemBean bean, int position) {
                if (mSelectedList.size() == mPickerBean.maxCount && !bean.isSelected){// 已经选满图片
                    ToastUtils.showShort(getContext(),
                            getContext().getString(R.string.component_picker_photo_count_tips, String.valueOf(mPickerBean.maxCount)));
                    return;
                }

                for (int i = 0; i < mCurrentPhotoList.size(); i++) {
                    if (bean.photoPath.equals(mCurrentPhotoList.get(i).photoPath)){
                        mCurrentPhotoList.get(i).isSelected = !bean.isSelected;
                        mAdapter.setData(mCurrentPhotoList);
                        mAdapter.notifyItemChanged(position);
                        if (mCurrentPhotoList.get(i).isSelected){// 点击后是选中状态
                            mSelectedList.add(bean);
                        }else {
                            for (PickerItemBean itemBean : mSelectedList) {
                                if (itemBean.photoPath.equals(bean.photoPath)){
                                    mSelectedList.remove(itemBean);
                                    break;
                                }
                            }
                        }
                        // 设置按钮状态
                        mConfirmBtn.setEnabled(mSelectedList.size() > 0);
                        mPreviewBtn.setEnabled(mSelectedList.size() > 0);
                        mConfirmBtn.setText(mSelectedList.size() > 0 ? getString(R.string.component_picker_confirm_num,
                                String.valueOf(mSelectedList.size()), String.valueOf(mPickerBean.maxCount))
                                : getString(R.string.component_picker_confirm));
                        mPreviewBtn.setText(mSelectedList.size() > 0 ? getString(R.string.component_picker_preview_num,
                                String.valueOf(mSelectedList.size()))
                                : getString(R.string.component_picker_preview));
                        return;
                    }
                }
            }

            @Override
            public void onClickCamera() {
                takePhoto();
            }
        });

        // 图片点击回调
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<PickerItemBean>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, PickerItemBean item, int position) {
                if (!mPickerBean.isNeedItemPreview){
                    return;
                }
                PreviewManager
                        .<String>create()
                        .setBackgroundColor(mPickerBean.pickerUIConfig.getItemBgColor())
                        .setStatusBarColor(mPickerBean.pickerUIConfig.getStatusBarColor())
                        .setNavigationBarColor(mPickerBean.pickerUIConfig.getNavigationBarColor())
                        .setPagerTextColor(mPickerBean.pickerUIConfig.getMainTextColor())
                        .setShowPagerText(false)
                        .setOnClickListener(new OnClickListener<String>() {
                            @Override
                            public void onClick(Context context, String source, int position, PreviewController controller) {
                                controller.close();
                            }
                        })
                        .setImgLoader(new PhotoLoader<String>() {
                            @Override
                            public void displayImg(Context context, String source, ImageView imageView) {
                                mPickerBean.previewLoader.displayImg(context, source, imageView);
                            }
                        })
                        .build(item.photoPath)
                        .open(getContext());
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        if (ArrayUtils.isEmpty(mPickerBean.sourceList)){
            configAdapterData(AlbumUtils.getAllImages(getContext()));
        }else {
            configAdapterData(mPickerBean.sourceList);//让用户选择指定的图片
            mFolderBtn.setEnabled(false);
            mFolderTextTv.setText(R.string.component_picker_custom_photo);
            mMoreImg.setVisibility(View.GONE);
        }

    }

    /** 配置适配器数据 */
    private void configAdapterData(List<String> source) {
        mCurrentPhotoList.clear();
        for (String path : source) {
            PickerItemBean itemBean = new PickerItemBean();
            itemBean.photoPath = path;
            for (PickerItemBean selectedBean : mSelectedList) {
                if (selectedBean.photoPath.equals(path)){
                    itemBean.isSelected = true;
                    break;
                }
            }
            mCurrentPhotoList.add(itemBean);
        }
        mAdapter.setData(mCurrentPhotoList);
        mRecyclerView.smoothScrollToPosition(0);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 根据颜色获取圆角Drawable
     * @param color 颜色
     * @param width 宽度
     * @param height 高度
     */
    private Drawable getCornerDrawable(@ColorRes int color, int width, int height){
        Bitmap bitmap = BitmapUtils.drawableToBitmap(DrawableUtils.createColorDrawable(getContext(), color), width, height);
        if (bitmap == null){
            return null;
        }
        Bitmap cornerBitmap = BitmapUtils.createRoundedCornerBitmap(bitmap, 8);
        return DrawableUtils.createBitmapDrawable(getContext(), cornerBitmap);
    }

    /**
     * 获取箭头图片
     * @param color 颜色
     */
    private Bitmap getArrowBitmap(@ColorRes int color){
        int side = DensityUtils.dp2px(getContext(), 20);
        int centerPoint = side / 2;

        Bitmap bitmap = Bitmap.createBitmap(side, side, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(getContext(), color));
        paint.setStrokeWidth(7);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(centerPoint, centerPoint + DensityUtils.dp2px(getContext(), 3));
        path.lineTo(centerPoint - DensityUtils.dp2px(getContext(), 7), centerPoint - DensityUtils.dp2px(getContext(), 3));
        path.moveTo(centerPoint, centerPoint + DensityUtils.dp2px(getContext(), 3));
        path.lineTo(centerPoint + DensityUtils.dp2px(getContext(), 7), centerPoint - DensityUtils.dp2px(getContext(), 3));
        canvas.drawPath(path, paint);

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerPoint, centerPoint + DensityUtils.dp2px(getContext(), 3), 3, paint);
        return bitmap;
    }

    /** 拍照 */
    private void takePhoto() {
        if (!FileUtils.isFileExists(mPickerBean.cameraSavePath) && !FileUtils.createFolder(mPickerBean.cameraSavePath)){
            ToastUtils.showShort(getContext(), R.string.component_photo_folder_fail);
            return;
        }

        mTempFilePath = mPickerBean.cameraSavePath + "P_" + DateUtils.getCurrentFormatString(DateUtils.TYPE_4) + ".jpg";
        if (!FileUtils.createNewFile(mTempFilePath)){
            ToastUtils.showShort(getContext(), R.string.component_photo_temp_file_fail);
            return;
        }

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) == null) {
            ToastUtils.showShort(getContext(), R.string.component_no_camera);
            return;
        }
        // 设置系统相机拍照后的输出路径
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getContext(), mPickerBean.authority, new File(mTempFilePath)));
            }catch (Exception e){
                e.printStackTrace();
                ToastUtils.showShort(getContext(), R.string.component_photo_temp_file_fail);
                return;
            }
        }else {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(FileUtils.createFile(mTempFilePath)));
        }
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                AlbumUtils.notifyScanImage(getContext(), mTempFilePath);// 更新相册
                UiHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleCameraSuccess();
                    }
                }, 300);
                return;
            }
            FileUtils.delFile(mTempFilePath);// 删除临时文件
            mTempFilePath = "";
        }
    }

    /** 处理拍照成功 */
    private void handleCameraSuccess() {
        mTempFilePath = "";
        for (ImageFolder folder : AlbumUtils.getAllImageFolders(getContext())) {
            if (folder.getName().equals(mFolderTextTv.getText().toString())){
                configAdapterData(AlbumUtils.getImageListOfFolder(getContext(), folder));
                break;
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        mAdapter.release();
        PickerManager.sPickerBean.clear();
        mPickerBean.clear();
    }
}
