package com.lodz.android.agiledev.ui.photopicker;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.agiledev.ui.splash.CheckDialog;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.photopicker.preview.PreviewManager;
import com.lodz.android.component.photopicker.preview.PreviewLoader;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.AppUtils;
import com.lodz.android.core.utils.ToastUtils;
import com.lodz.android.imageloader.ImageLoader;

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

    /** 选择按钮 */
    @BindView(R.id.take_btn)
    TextView mTakeBtn;

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
        PhotoPickerTestActivityPermissionsDispatcher.requestPermissionWithCheck(PhotoPickerTestActivity.this);
    }


    @Override
    protected void setListeners() {
        super.setListeners();
        final String[] urls = new String[]{
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507892144572&di=a34f5d0692bdd2bf09518a247216678f&imgtype=0&src=http%3A%2F%2Fimg1.ph.126.net%2F6bytMk5nTddW4FRg2OrdGQ%3D%3D%2F1547830896949199659.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508486908&di=5d41971051b00f15c1f23113380d67af&imgtype=jpg&er=1&src=http%3A%2F%2Fimg0.ph.126.net%2FbuWcqJ7E9zV-jAy-Wh42Bw%3D%3D%2F1119425982395581236.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507892191605&di=0dfd0f9b87ce36e35b6ad873236a82bb&imgtype=0&src=http%3A%2F%2Fimg2.ph.126.net%2FsmHY3894QY9w9KakIl9mJg%3D%3D%2F54606145499167538.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508486932&di=c74340412dfa4a5ebed40bf3d1c73193&imgtype=jpg&er=1&src=http%3A%2F%2Fimg2.ph.126.net%2F1MhXiknpoplqL9mHDf98yw%3D%3D%2F2662753279700113973.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507892227491&di=22c7f59c1fcb47098da64b4e4b1ccede&imgtype=0&src=http%3A%2F%2Fimg2.ph.126.net%2FU78LqnWDqm5r7BQUaPMAwg%3D%3D%2F3062729221606374682.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507892253859&di=3c31a941cde1f77a8a04549a7661aa47&imgtype=0&src=http%3A%2F%2Fa3.mzstatic.com%2Fus%2Fr30%2FPurple%2Fv4%2Fab%2Fa7%2F00%2Faba7007e-2297-ab89-78d5-9469c9d9f40b%2Fscreen640x960.jpeg",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=238811131,1278881919&fm=27&gp=0.jpg",
        };

        mTakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreviewManager
                        .<String>create()
                        .setImgLoader(new PreviewLoader<String>() {
                            @Override
                            public void displayPreviewImg(Context context, String source, ImageView imageView) {
                                ImageLoader.create(context).load(source).joinGlide().setFitCenter().into(imageView);
                            }
                        })
                        .setPosition(12)
                        .build(urls)
                        .open(getContext());
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){// 6.0以上的手机对权限进行动态申请
            PhotoPickerTestActivityPermissionsDispatcher.requestPermissionWithCheck(PhotoPickerTestActivity.this);
        }else {
            init();
        }
    }

    /** 初始化 */
    private void init(){
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
                PhotoPickerTestActivityPermissionsDispatcher.requestPermissionWithCheck(PhotoPickerTestActivity.this);//申请权限
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
