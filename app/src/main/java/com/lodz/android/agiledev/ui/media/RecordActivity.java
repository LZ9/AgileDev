package com.lodz.android.agiledev.ui.media;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.utils.file.FileManager;
import com.lodz.android.agiledev.utils.media.MediaRecorderHelper;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 视频录制测试类
 * Created by zhouL on 2017/9/1.
 */

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
        titleBarLayout.setTitleName(R.string.record_title);
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
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
        mMediaRecorderHelper.start("video_v_" + System.currentTimeMillis(), FileManager.getDownloadFolderPath());
    }


    protected void stop() {
        mMediaRecorderHelper.stop();
    }

    @Override
    protected void onDestroy() {
        mMediaRecorderHelper.stop();
        super.onDestroy();
    }
}