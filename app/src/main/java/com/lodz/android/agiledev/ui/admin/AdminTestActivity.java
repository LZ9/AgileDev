package com.lodz.android.agiledev.ui.admin;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.agiledev.utils.admin.AdminManager;
import com.lodz.android.agiledev.utils.admin.AdminReceiver;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.core.utils.AppUtils;
import com.lodz.android.core.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 设备管理功能测试
 * Created by zhouL on 2018/6/19.
 */
public class AdminTestActivity extends BaseActivity{

    /** 是否已激活 */
    @BindView(R.id.is_active_btn)
    Button mIsActiveBtn;
    /** 激活 */
    @BindView(R.id.active_btn)
    Button mActiveBtn;
    /** 取消激活 */
    @BindView(R.id.remove_active_btn)
    Button mRemoveActiveBtn;
    /** 跳转设置新密码 */
    @BindView(R.id.jump_pswd_btn)
    Button mJumpPswdBtn;
    /** 重置密码为1234 */
    @BindView(R.id.reset_pswd_btn)
    Button mResetPswdBtn;
    /** 锁屏 */
    @BindView(R.id.lock_btn)
    Button mLockBtn;
    /** 恢复出厂设置 */
    @BindView(R.id.wipe_data_btn)
    Button mWipeDataBtn;
    /** 禁用相机 */
    @BindView(R.id.disabled_camera_btn)
    Button mDisabledCameraBtn;
    /** 启用相机 */
    @BindView(R.id.enabled_camera_btn)
    Button mEnabledCameraBtn;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_admin_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        getTitleBarLayout().setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        // 是否已激活
        mIsActiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(getContext(), AdminManager.isAdminActive(getContext()) ? "已激活" : "尚未激活");
            }
        });

        // 激活
        mActiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AdminManager.isAdminActive(getContext())){
                    ToastUtils.showShort(getContext(), "已激活");
                    return;
                }
                AdminManager.active(getContext());
            }
        });

        // 取消激活
        mRemoveActiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AdminManager.isAdminActive(getContext())){
                    ToastUtils.showShort(getContext(), "尚未激活");
                    return;
                }
                AdminManager.removeActiveAdmin(getContext());
            }
        });

        // 跳转设置新密码
        mJumpPswdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AdminManager.isAdminActive(getContext())){
                    ToastUtils.showShort(getContext(), "尚未激活");
                    return;
                }
                AdminManager.setPasswordQuality(getContext(), DevicePolicyManager.PASSWORD_QUALITY_UNSPECIFIED);
                AppUtils.jumpSetPswdSetting(getContext());
            }
        });

        // 重置密码为1234
        mResetPswdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AdminManager.isAdminActive(getContext())){
                    ToastUtils.showShort(getContext(), "尚未激活");
                    return;
                }
                AdminManager.resetPassword(getContext(), "1234");
            }
        });

        // 锁屏
        mLockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AdminManager.isAdminActive(getContext())){
                    ToastUtils.showShort(getContext(), "尚未激活");
                    return;
                }
                AdminManager.lockNow(getContext());
            }
        });

        // 恢复出厂设置
        mWipeDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AdminManager.isAdminActive(getContext())){
                    ToastUtils.showShort(getContext(), "尚未激活");
                    return;
                }
                ToastUtils.showShort(getContext(), "请在代码中去掉注释后再测试");
//                AdminManager.wipeData(getContext());
            }
        });

        // 禁用相机
        mDisabledCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AdminManager.isAdminActive(getContext())){
                    ToastUtils.showShort(getContext(), "尚未激活");
                    return;
                }
                if (AdminManager.get(getContext()).getCameraDisabled(new ComponentName(getContext(), AdminReceiver.class))){
                    ToastUtils.showShort(getContext(), "已禁用");
                    return;
                }
                AdminManager.setCameraDisabled(getContext(), true);
            }
        });

        // 启用相机
        mEnabledCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AdminManager.isAdminActive(getContext())){
                    ToastUtils.showShort(getContext(), "尚未激活");
                    return;
                }
                if (!AdminManager.get(getContext()).getCameraDisabled(new ComponentName(getContext(), AdminReceiver.class))){
                    ToastUtils.showShort(getContext(), "已启用");
                    return;
                }
                AdminManager.setCameraDisabled(getContext(), false);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }
}
