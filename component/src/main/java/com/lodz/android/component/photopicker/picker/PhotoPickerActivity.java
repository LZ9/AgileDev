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
import android.support.annotation.ColorRes;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
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
import com.lodz.android.core.utils.DrawableUtils;
import com.lodz.android.core.utils.FileUtils;
import com.lodz.android.core.utils.SelectorUtils;
import com.lodz.android.core.utils.ToastUtils;
import com.lodz.android.core.utils.UiHandler;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
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
        mBackBtn = (ImageView) findViewById(R.id.back_btn);
        mConfirmBtn = (TextView) findViewById(R.id.confirm_btn);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mFolderBtn = (ViewGroup) findViewById(R.id.folder_btn);
        mFolderTextTv = (TextView) findViewById(R.id.folder_text);
        mMoreImg = (ImageView) findViewById(R.id.more_img);
        mPreviewBtn = (TextView) findViewById(R.id.preview_btn);

        initRecyclerView();
        drawBackBtn(android.R.color.white);
        drawMoreImg(android.R.color.white);
        drawConfirmBtn();
        mPreviewBtn.setTextColor(SelectorUtils.createTxPressedUnableColor(getContext(), android.R.color.white, android.R.color.darker_gray, android.R.color.darker_gray));
        mConfirmBtn.setEnabled(false);
        mPreviewBtn.setEnabled(false);
    }

    /** 初始化RecyclerView */
    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mAdapter = new PhotoPickerAdapter(getContext(), mPickerBean.photoLoader, mPickerBean.isNeedCamera);
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
                .compose(RxUtils.<Bitmap>io_main())
                .subscribe(new BaseObserver<Bitmap>() {
                    @Override
                    public void onBaseSubscribe(Disposable d) {

                    }

                    @Override
                    public void onBaseNext(Bitmap bitmap) {
                        mBackBtn.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBaseError(Throwable e) {

                    }

                    @Override
                    public void onBaseComplete() {

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
                .compose(RxUtils.<Bitmap>io_main())
                .subscribe(new BaseObserver<Bitmap>() {
                    @Override
                    public void onBaseSubscribe(Disposable d) {

                    }

                    @Override
                    public void onBaseNext(Bitmap bitmap) {
                        mMoreImg.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBaseError(Throwable e) {

                    }

                    @Override
                    public void onBaseComplete() {

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
                        getCornerDrawable(android.R.color.holo_green_light, width, height),
                        getCornerDrawable(android.R.color.white, width, height),
                        getCornerDrawable(android.R.color.holo_green_dark, width, height));
                if (drawable == null){
                    return true;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mConfirmBtn.setBackground(drawable);
                }else {
                    mConfirmBtn.setBackgroundDrawable(drawable);
                }
                mConfirmBtn.setTextColor(SelectorUtils.createTxPressedUnableColor(getContext(),
                        android.R.color.white, android.R.color.holo_green_dark, android.R.color.darker_gray));
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

                for (ImageFolder folder : AlbumUtils.getAllImageFolders(getContext())) {
                    ImageFolderIteamBean iteamBean = new ImageFolderIteamBean();
                    iteamBean.imageFolder = folder;
                    iteamBean.isSelected = mFolderTextTv.getText().toString().equals(folder.getName());
                    folders.add(iteamBean);
                }
                ImageFolderDialog dialog = new ImageFolderDialog(getContext());
                dialog.setPhotoLoader(mPickerBean.photoLoader);
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
                mPickerBean.photoPickerListener.onPickerSelected(list);
                finish();
            }
        });

        // 预览按钮
        mPreviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<>();
                for (PickerItemBean itemBean : mSelectedList) {
                    list.add(itemBean.photoPath);
                }

                PreviewManager
                        .<String>create()
                        .setPageLimit(2)
                        .setScale(true)
                        .setBackgroundColor(android.R.color.black)
                        .setStatusBarColor(android.R.color.black)
                        .setPagerTextColor(android.R.color.white)
                        .setPagerTextSize(14)
                        .setShowPagerText(true)
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
                if (mSelectedList.size() == mPickerBean.maxCount && !bean.isSelected){
                    ToastUtils.showShort(getContext(), getContext().getString(R.string.component_picker_photo_count_tips, String.valueOf(mPickerBean.maxCount)));
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
                        mConfirmBtn.setEnabled(mSelectedList.size() > 0);
                        mPreviewBtn.setEnabled(mSelectedList.size() > 0);
                        mConfirmBtn.setText(mSelectedList.size() > 0 ? getString(R.string.component_picker_confirm_num, String.valueOf(mSelectedList.size()), String.valueOf(mPickerBean.maxCount)) : getString(R.string.component_picker_confirm));
                        mPreviewBtn.setText(mSelectedList.size() > 0 ? getString(R.string.component_picker_preview_num, String.valueOf(mSelectedList.size())) : getString(R.string.component_picker_preview));
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
                PreviewManager
                        .<String>create()
                        .setPageLimit(2)
                        .setScale(true)
                        .setBackgroundColor(android.R.color.black)
                        .setStatusBarColor(android.R.color.black)
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSystemBarColor(android.R.color.black, android.R.color.black);
        }
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
        path.moveTo(centerPoint, centerPoint + DensityUtils.dp2px(getContext(), 4));
        path.lineTo(centerPoint - DensityUtils.dp2px(getContext(), 9), centerPoint - DensityUtils.dp2px(getContext(), 4));
        path.moveTo(centerPoint, centerPoint + DensityUtils.dp2px(getContext(), 4));
        path.lineTo(centerPoint + DensityUtils.dp2px(getContext(), 9), centerPoint - DensityUtils.dp2px(getContext(), 4));
        canvas.drawPath(path, paint);

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerPoint, centerPoint + DensityUtils.dp2px(getContext(), 4), 3, paint);
        return bitmap;
    }

    /**
     * 设置系统颜色
     * @param statusBarColor 状态栏颜色
     * @param navigationBarColor 导航栏颜色
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setSystemBarColor(@ColorRes int statusBarColor, @ColorRes int navigationBarColor) {
        if (statusBarColor == 0 && navigationBarColor == 0){
            return;
        }

        try {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (statusBarColor != 0){
                window.setStatusBarColor(ContextCompat.getColor(getContext(), statusBarColor));
            }
            if (navigationBarColor != 0){
                window.setNavigationBarColor(ContextCompat.getColor(getContext(), navigationBarColor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getContext(), "com.lodz.android.component.provider", FileUtils.createFile(mTempFilePath)));
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
