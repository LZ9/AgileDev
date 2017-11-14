package com.lodz.android.agiledev.ui.media;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.agiledev.ui.splash.CheckDialog;
import com.lodz.android.agiledev.utils.file.FileManager;
import com.lodz.android.agiledev.utils.media.MediaRecorderHelper;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.AppUtils;
import com.lodz.android.core.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 视频录制测试
 * Created by zhouL on 2017/9/1.
 */

@RuntimePermissions
public class RecordActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, RecordActivity.class);
        context.startActivity(starter);
    }

    /** 开始按钮 */
    @BindView(R.id.start_btn)
    Button mStartBtn;
    /** 停止按钮 */
    @BindView(R.id.stop_btn)
    Button mStopBtn;
    /** 停止按钮 */
    @BindView(R.id.surface_view)
    SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    private MediaRecorderHelper mMediaRecorderHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mSurfaceHolder = mSurfaceView.getHolder();
        initTitleBarLayout(getTitleBarLayout());
    }

    /** 初始化TitleBarLayout */
    private void initTitleBarLayout(TitleBarLayout titleBarLayout) {
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
        RecordActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(RecordActivity.this);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });

        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){// 6.0以上的手机对权限进行动态申请
            RecordActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(RecordActivity.this);
        }else {
            init();
        }
    }

    private void init(){
        mStopBtn.setEnabled(false);
        mMediaRecorderHelper = new MediaRecorderHelper();
        mMediaRecorderHelper.setSurfaceHolder(mSurfaceHolder)
                .setBitRate(MediaRecorderHelper.TYPE_HIGH)
                .setVideoSize(MediaRecorderHelper.TYPE_1280_720)
                .setListener(new MediaRecorderHelper.Listener() {
                    @Override
                    public void noCamera() {
                        ToastUtils.showShort(getContext(), "没有找到后置摄像头");
                    }

                    @Override
                    public void onStart() {
                        mStartBtn.setEnabled(false);
                        mStopBtn.setEnabled(true);
                        ToastUtils.showShort(getContext(), "开始录像");
                    }

                    @Override
                    public void onStartFail(Exception e) {
                        mStartBtn.setEnabled(true);
                        mStopBtn.setEnabled(false);
                        ToastUtils.showShort(getContext(), "启动失败");
                    }

                    @Override
                    public void onStop() {
                        mStartBtn.setEnabled(true);
                        mStopBtn.setEnabled(false);
                        ToastUtils.showShort(getContext(), "停止录像，并保存文件");
                    }

                    @Override
                    public void onRunError() {
                        mStartBtn.setEnabled(true);
                        mStopBtn.setEnabled(false);
                        ToastUtils.showShort(getContext(), "录制出错");
                    }
                });
        showStatusCompleted();
    }

    protected void start() {
        if (mMediaRecorderHelper == null){
            return;
        }
        mMediaRecorderHelper.start("video_v_" + System.currentTimeMillis(), FileManager.getDownloadFolderPath());
    }


    protected void stop() {
        if (mMediaRecorderHelper == null){
            return;
        }
        mMediaRecorderHelper.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaRecorderHelper == null){
            return;
        }
        mMediaRecorderHelper.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        RecordActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);//将回调交给代理类处理
    }



    /** 权限申请成功 */
    @NeedsPermission({
            Manifest.permission.RECORD_AUDIO,// 声音
            Manifest.permission.CAMERA// 照相
    })
    protected void requestPermission() {
        if (!AppUtils.isPermissionGranted(getContext(), Manifest.permission.RECORD_AUDIO)){
            return;
        }
        if (!AppUtils.isPermissionGranted(getContext(), Manifest.permission.CAMERA)){
            return;
        }
        init();
    }

    /** 被拒绝 */
    @OnPermissionDenied({
            Manifest.permission.RECORD_AUDIO,// 声音
            Manifest.permission.CAMERA// 照相
    })
    protected void onDenied() {
        ToastUtils.showShort(this, "你拒绝了此权限，该功能不可用");
        showStatusError();
    }

    /** 用户拒绝后再次申请前告知用户为什么需要该权限 */
    @OnShowRationale({
            Manifest.permission.RECORD_AUDIO,// 声音
            Manifest.permission.CAMERA// 照相
    })
    protected void showRationaleBeforeRequest(PermissionRequest request) {
        request.proceed();//请求权限
    }

    /** 被拒绝并且勾选了不再提醒 */
    @OnNeverAskAgain({
            Manifest.permission.RECORD_AUDIO,// 声音
            Manifest.permission.CAMERA// 照相
    })
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
                RecordActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(RecordActivity.this);//申请权限
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