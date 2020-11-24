package com.lodz.android.agiledev.ui.photopicker;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.agiledev.ui.splash.CheckDialog;
import com.lodz.android.agiledev.utils.file.FileManager;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.photopicker.contract.OnClickListener;
import com.lodz.android.component.photopicker.contract.OnLongClickListener;
import com.lodz.android.component.photopicker.contract.PhotoLoader;
import com.lodz.android.component.photopicker.contract.picker.OnPhotoPickerListener;
import com.lodz.android.component.photopicker.contract.preview.PreviewController;
import com.lodz.android.component.photopicker.picker.PickerManager;
import com.lodz.android.component.photopicker.picker.PickerUIConfig;
import com.lodz.android.component.photopicker.preview.PreviewManager;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.component.widget.ninegrid.NineGridView;
import com.lodz.android.component.widget.ninegrid.OnNineGridViewListener;
import com.lodz.android.component.widget.ninegrid.OnSimpleNineGridViewListener;
import com.lodz.android.component.widget.ninegrid.SimpleNineGridView;
import com.lodz.android.core.utils.AppUtils;
import com.lodz.android.core.utils.ArrayUtils;
import com.lodz.android.core.utils.ToastUtils;
import com.lodz.android.imageloader.ImageLoader;
import com.lodz.android.imageloader.glide.impl.GlideBuilderBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


/**
 * 照片选择器测试
 * Created by zhouL on 2017/9/21.
 */
@RuntimePermissions
public class PhotoPickerTestActivity extends BaseActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, PhotoPickerTestActivity.class);
        context.startActivity(starter);
    }

    private static final String[] URLS = new String[]{
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508135406740&di=ad56c6b92e5d9888a04f0b724e5219d0&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F3801213fb80e7beca9004ec5252eb9389b506b38.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508135426954&di=45834427b6f8ec30f1d7e1d99f59ee5c&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F0b7b02087bf40ad1cd0f99c55d2c11dfa9ecce29.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508135506833&di=6b22dd2085f18b3643fe62b0f8b8955f&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F242dd42a2834349b8d289fafcbea15ce36d3beea.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508135457903&di=e107c45dd449126ae54f0f665c558d05&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3Df49943efb8119313d34ef7f30d5166a2%2Fb17eca8065380cd736f92fc0ab44ad345982813c.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508135473894&di=27b040e674c4f9ac8b499f38612cab39&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fd788d43f8794a4c2fc3e95eb07f41bd5ac6e39d4.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508135496262&di=5cef907ceff298c8d5d6c79841a72696&imgtype=0&src=http%3A%2F%2Fimg4q.duitang.com%2Fuploads%2Fitem%2F201409%2F07%2F20140907224542_h4HvW.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508135447478&di=90ddcac4604965af5d9bc744237a27aa&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fd52a2834349b033b1c4bcdcf1fce36d3d439bde7.jpg",
    };

    /** 选择按钮 */
    @BindView(R.id.pick_btn)
    TextView mPickBtn;
    /** 预览按钮 */
    @BindView(R.id.preview_btn)
    TextView mPreviewBtn;
    /** 拍照 */
    @BindView(R.id.take_photo_btn)
    TextView mTakePhotoBtn;
    /** 获取九宫格数据 */
    @BindView(R.id.get_nine_pic_btn)
    TextView mGetNinePicBtn;


    /** 九宫格 */
    @BindView(R.id.nine_grid_view)
    NineGridView mNineGridView;

    /** 简单的九宫格实现 */
    @BindView(R.id.simple_nine_grid_view)
    SimpleNineGridView mSimpleNineGridView;


    /** 选择结果 */
    @BindView(R.id.pick_result)
    TextView mPickResultTv;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_picker_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBar(getTitleBarLayout());
    }

    /** 初始化标题栏 */
    private void initTitleBar(TitleBarLayout titleBarLayout) {
        titleBarLayout.setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void clickReload() {
        super.clickReload();
        showStatusLoading();
        PhotoPickerTestActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(PhotoPickerTestActivity.this);
    }


    @Override
    protected void setListeners() {
        super.setListeners();

        // 选择按钮
        mPickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPickResultTv.setText("");

                PickerUIConfig config = PickerUIConfig.createDefault()
//                        .setCameraImg(R.drawable.ic_launcher)
                        .setCameraBgColor(R.color.black)
                        .setItemBgColor(R.color.black)
                        .setBackBtnColor(R.color.white)
                        .setMainTextColor(R.color.white)
//                        .setMoreFolderImg(R.drawable.ic_launcher)
                        .setSelectedBtnUnselect(R.color.color_ebece7)
                        .setSelectedBtnSelected(R.color.color_00a0e9)
                        .setMaskColor(R.color.color_a0191919)
                        .setTopLayoutColor(R.color.color_1a1a1a)
                        .setBottomLayoutColor(R.color.color_1a1a1a)
                        .setPreviewBtnNormal(R.color.white)
                        .setPreviewBtnUnable(R.color.color_666666)
                        .setConfirmBtnNormal(R.color.color_00a0e9)
                        .setConfirmBtnPressed(R.color.color_2f6dc9)
                        .setConfirmBtnUnable(R.color.color_2f6dc9)
                        .setConfirmTextNormal(R.color.white)
                        .setConfirmTextPressed(R.color.color_999999)
                        .setConfirmTextUnable(R.color.color_999999)
                        .setStatusBarColor(R.color.color_1a1a1a)
                        .setNavigationBarColor(R.color.black)
                        .setFolderSelectColor(R.color.color_00a0e9);

                PickerManager
                        .create()
                        .setImgLoader(new PhotoLoader<String>() {
                            @Override
                            public void displayImg(Context context, String source, ImageView imageView) {
                                ImageLoader.create(context)
                                        .load(source)
                                        .diskCacheStrategy(GlideBuilderBean.DiskCacheStrategy.NONE)
                                        .setCenterCrop()
                                        .into(imageView);
                            }
                        })
                        .setPreviewImgLoader(new PhotoLoader<String>() {
                            @Override
                            public void displayImg(Context context, String source, ImageView imageView) {
                                ImageLoader.create(context)
                                        .load(source)
                                        .diskCacheStrategy(GlideBuilderBean.DiskCacheStrategy.NONE)
                                        .into(imageView);
                            }
                        })
                        .setOnPhotoPickerListener(new OnPhotoPickerListener() {
                            @Override
                            public void onPickerSelected(List<String> photos) {
                                if (!ArrayUtils.isEmpty(photos)){
                                    StringBuilder stringBuilder = new StringBuilder();
                                    for (String photo : photos) {
                                        stringBuilder.append(photo).append("\n");
                                    }
                                    mPickResultTv.setText(stringBuilder.toString());
                                }
                            }
                        })
                        .setMaxCount(9)
                        .setNeedCamera(true)
                        .setNeedItemPreview(true)
                        .setCameraSavePath(FileManager.getCacheFolderPath())
//                        .setPickerUIConfig(PickerUIConfig.createDefault())
                        .setPickerUIConfig(config)
                        .setAuthority("com.lodz.android.agiledev.fileprovider")
                        .build()
                        .open(getContext());
            }
        });

        // 预览按钮
        mPreviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreviewManager
                        .<String>create()
                        .setPosition(0)
                        .setScale(false)
                        .setBackgroundColor(R.color.black)
                        .setStatusBarColor(R.color.black)
//                        .setNavigationBarColor(R.color.black)
                        .setPagerTextColor(R.color.white)
                        .setPagerTextSize(14)
                        .setShowPagerText(true)
                        .setOnClickListener(new OnClickListener<String>() {
                            @Override
                            public void onClick(Context context, String source, int position, PreviewController controller) {
                                controller.close();
                            }
                        })
                        .setOnLongClickListener(new OnLongClickListener<String>() {
                            @Override
                            public void onLongClick(Context context, String source, int position, PreviewController controller) {
                                ToastUtils.showShort(context, "long click " + position);
                            }
                        })
                        .setImgLoader(new PhotoLoader<String>() {
                            @Override
                            public void displayImg(Context context, String source, ImageView imageView) {
                                ImageLoader.create(context).load(source).setFitCenter().into(imageView);
                            }
                        })
                        .build(URLS)
                        .open(getContext());
            }
        });

        // 拍照
        mTakePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPickResultTv.setText("");

                PickerManager
                        .create()
                        .setPreviewImgLoader(new PhotoLoader<String>() {
                            @Override
                            public void displayImg(Context context, String source, ImageView imageView) {
                                ImageLoader.create(context)
                                        .load(source)
                                        .diskCacheStrategy(GlideBuilderBean.DiskCacheStrategy.NONE)
                                        .into(imageView);
                            }
                        })
                        .setOnPhotoPickerListener(new OnPhotoPickerListener() {
                            @Override
                            public void onPickerSelected(List<String> photos) {
                                if (!ArrayUtils.isEmpty(photos)){
                                    StringBuilder stringBuilder = new StringBuilder();
                                    for (String photo : photos) {
                                        stringBuilder.append(photo).append("\n");
                                    }
                                    mPickResultTv.setText(stringBuilder.toString());
                                }
                            }
                        })
                        .setCameraSavePath(FileManager.getCacheFolderPath())
                        .setAuthority("com.lodz.android.agiledev.fileprovider")
                        .build()
                        .takePhoto(getContext(), true);
            }
        });

        // 获取九宫格数据
        mGetNinePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                StringBuilder stringBuilder = new StringBuilder();
//                for (String photo : mNineGridView.getPicData()) {
//                    stringBuilder.append(photo).append("\n");
//                }
//                mPickResultTv.setText(stringBuilder.toString());

                StringBuilder stringBuilder = new StringBuilder();
                for (String photo : mSimpleNineGridView.getPicData()) {
                    stringBuilder.append(photo).append("\n");
                }
                mPickResultTv.setText(stringBuilder.toString());
            }
        });

        // 九宫格
        mNineGridView.setOnNineGridViewListener(new OnNineGridViewListener() {
            @Override
            public void onAddPic(int addCount) {
                PickerManager
                        .create()
                        .setImgLoader(new PhotoLoader<String>() {
                            @Override
                            public void displayImg(Context context, String source, ImageView imageView) {
                                ImageLoader.create(context)
                                        .load(source)
                                        .diskCacheStrategy(GlideBuilderBean.DiskCacheStrategy.NONE)
                                        .setCenterCrop()
                                        .into(imageView);
                            }
                        })
                        .setPreviewImgLoader(new PhotoLoader<String>() {
                            @Override
                            public void displayImg(Context context, String source, ImageView imageView) {
                                ImageLoader.create(context)
                                        .load(source)
                                        .diskCacheStrategy(GlideBuilderBean.DiskCacheStrategy.NONE)
                                        .into(imageView);
                            }
                        })
                        .setOnPhotoPickerListener(new OnPhotoPickerListener() {
                            @Override
                            public void onPickerSelected(List<String> photos) {
                                if (!ArrayUtils.isEmpty(photos)){
                                    mNineGridView.addData(photos);
                                }
                            }
                        })
                        .setMaxCount(addCount)
                        .setNeedCamera(true)
                        .setNeedItemPreview(true)
                        .setCameraSavePath(FileManager.getCacheFolderPath())
                        .setPickerUIConfig(PickerUIConfig.createDefault())
                        .setAuthority("com.lodz.android.agiledev.fileprovider")
                        .build()
                        .open(getContext());
            }

            @Override
            public void onDisplayImg(Context context, String data, ImageView imageView) {
                ImageLoader.create(context)
                        .load(data)
                        .diskCacheStrategy(GlideBuilderBean.DiskCacheStrategy.NONE)
                        .setCenterCrop()
                        .into(imageView);
            }

            @Override
            public void onDeletePic(String data, int position) {
                mNineGridView.removeData(position);
            }

            @Override
            public void onClickPic(String data, int position) {
                PreviewManager
                        .<String>create()
                        .setPosition(position)
                        .setScale(true)
                        .setBackgroundColor(R.color.black)
                        .setStatusBarColor(R.color.black)
//                        .setNavigationBarColor(R.color.black)
                        .setPagerTextColor(R.color.white)
                        .setPagerTextSize(14)
                        .setShowPagerText(true)
                        .setOnClickListener(new OnClickListener<String>() {
                            @Override
                            public void onClick(Context context, String source, int position, PreviewController controller) {
                                controller.close();
                            }
                        })
                        .setOnLongClickListener(new OnLongClickListener<String>() {
                            @Override
                            public void onLongClick(Context context, String source, int position, PreviewController controller) {
                                ToastUtils.showShort(context, "long click " + position);
                            }
                        })
                        .setImgLoader(new PhotoLoader<String>() {
                            @Override
                            public void displayImg(Context context, String source, ImageView imageView) {
                                ImageLoader.create(context).load(source).setFitCenter().into(imageView);
                            }
                        })
                        .build(mNineGridView.getPicData())
                        .open(getContext());
            }
        });

        // 简单的九宫格实现
        mSimpleNineGridView.setOnSimpleNineGridViewListener(new OnSimpleNineGridViewListener() {
            @Override
            public void onDisplayNineGridImg(Context context, String data, ImageView imageView) {
                ImageLoader.create(context)
                        .load(data)
                        .diskCacheStrategy(GlideBuilderBean.DiskCacheStrategy.NONE)
                        .setCenterCrop()
                        .into(imageView);
            }

            @Override
            public void onDisplayPreviewImg(Context context, String data, ImageView imageView) {
                ImageLoader.create(context)
                        .load(data)
                        .diskCacheStrategy(GlideBuilderBean.DiskCacheStrategy.NONE)
                        .into(imageView);
            }

            @Override
            public void onDisplayPickerImg(Context context, String data, ImageView imageView) {
                ImageLoader.create(context)
                        .load(data)
                        .diskCacheStrategy(GlideBuilderBean.DiskCacheStrategy.NONE)
                        .setCenterCrop()
                        .into(imageView);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){// 6.0以上的手机对权限进行动态申请
            PhotoPickerTestActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(PhotoPickerTestActivity.this);
        }else {
            init();
        }
    }

    /** 初始化 */
    private void init(){
        mSimpleNineGridView.config(FileManager.getCacheFolderPath(), "com.lodz.android.agiledev.fileprovider");
        showStatusCompleted();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhotoPickerTestActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);//将回调交给代理类处理
    }

    /** 权限申请成功 */
    @NeedsPermission({Manifest.permission.CAMERA})// 照相
    protected void requestPermission() {
        if (!AppUtils.isPermissionGranted(getContext(), Manifest.permission.CAMERA)){
            return;
        }
        init();
    }

    /** 被拒绝 */
    @OnPermissionDenied({Manifest.permission.CAMERA})// 照相
    protected void onDenied() {
        ToastUtils.showShort(this, "你拒绝了此权限，该功能不可用");
        showStatusError();
    }

    /** 用户拒绝后再次申请前告知用户为什么需要该权限 */
    @OnShowRationale({Manifest.permission.CAMERA})// 照相
    protected void showRationaleBeforeRequest(PermissionRequest request) {
        request.proceed();//请求权限
    }

    /** 被拒绝并且勾选了不再提醒 */
    @OnNeverAskAgain({Manifest.permission.CAMERA})// 照相
    protected void onNeverAskAgain() {
        ToastUtils.showShort(getContext(), R.string.splash_check_permission_tips);
        showPermissionCheckDialog();
        AppUtils.jumpAppDetailSetting(this);
    }

    /** 显示权限核对弹框 */
    private void showPermissionCheckDialog() {
        CheckDialog dialog = new CheckDialog(getContext());
        dialog.setContentMsg(R.string.splash_check_permission_title);
        dialog.setPositiveText(R.string.splash_check_permission_confirm, new CheckDialog.Listener() {
            @Override
            public void onClick(Dialog dialog) {
                PhotoPickerTestActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(PhotoPickerTestActivity.this);//申请权限
                dialog.dismiss();
            }
        });
        dialog.setNegativeText(R.string.splash_check_permission_unconfirmed, new CheckDialog.Listener() {
            @Override
            public void onClick(Dialog dialog) {
                AppUtils.jumpAppDetailSetting(getContext());
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        dialog.show();
    }
}
