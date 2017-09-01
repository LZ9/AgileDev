package com.lodz.android.agiledev.utils.media;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.view.SurfaceHolder;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 视频录制帮助类
 * Created by zhouL on 2017/9/1.
 */

public class MediaRecorderHelper {

    /** 低帧频率 */
    public static final int TYPE_LOW = 1;
    /** 中帧频率 */
    public static final int TYPE_MIDDLE = 3;
    /** 高帧频率 */
    public static final int TYPE_HIGH = 5;
    @IntDef({TYPE_LOW, TYPE_MIDDLE, TYPE_HIGH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BitRateType {
    }

    /** 1920x1080 */
    public static final int TYPE_1920_1080 = 1;
    /** 1280x720 */
    public static final int TYPE_1280_720 = 2;
    /** 640x480 */
    public static final int TYPE_640_480 = 3;
    @IntDef({TYPE_1920_1080, TYPE_1280_720, TYPE_640_480})
    @Retention(RetentionPolicy.SOURCE)
    public @interface VideoSizeType {
    }

    /** 视频录制 */
    private MediaRecorder mMediaRecorder;
    /** 相机 */
    private Camera mCamera;
    /** 监听器 */
    private Listener mListener;

    /** 分辨率 */
    private Pair<Integer, Integer> mVideoSize = new Pair<>(640, 480);
    /** 帧频率 */
    @BitRateType
    private int mBitRateType = TYPE_MIDDLE;
    private SurfaceHolder mSurfaceHolder;

    /** 设置分辨率 */
    public MediaRecorderHelper setVideoSize(@VideoSizeType int videoSize){
        if (videoSize == TYPE_1920_1080){
            mVideoSize = new Pair<>(1920, 1080);
        }
        if (videoSize == TYPE_1280_720){
            mVideoSize = new Pair<>(1280, 720);
        }
        if (videoSize == TYPE_640_480){
            mVideoSize = new Pair<>(640, 480);
        }
        return this;
    }

    /** 设置帧频率 */
    public MediaRecorderHelper setBitRate(@BitRateType int bitRate){
        mBitRateType = bitRate;
        return this;
    }

    /** 设置SurfaceHolder */
    public MediaRecorderHelper setSurfaceHolder(SurfaceHolder surfaceHolder){
        mSurfaceHolder = surfaceHolder;
        return this;
    }

    /**
     * 开始录制
     * @param saveName 保存名字
     * @param savePath 保存路径
     */
    public void start(@NonNull String saveName, @NonNull String savePath){
        try {
            if (!savePath.endsWith(File.separator)){
                savePath += File.separator;
            }

            String path = savePath + saveName +".mp4";
            File file = new File(path);
            if (file.exists()){// 如果文件存在则把他删掉
                file.delete();
            }

            release();

            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.reset();
            int cameraId = getBackCameraId();
            if (cameraId == -1){
                if (mListener != null){
                    mListener.noCamera();
                }
                return;
            }
            mCamera= Camera.open(cameraId);
            mCamera.setDisplayOrientation(90);// 旋转了90度
            mCamera.unlock();// 解锁
            mMediaRecorder.setCamera(mCamera);

            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置音频录入源
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);// 设置视频图像的录入源
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);// 设置录入媒体的输出格式
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);// 设置音频的编码格式
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);// 设置视频的编码格式
            mMediaRecorder.setVideoSize(mVideoSize.first, mVideoSize.second);// 设置分辨率
            mMediaRecorder.setVideoEncodingBitRate(mBitRateType * 1024 * 1024);// 设置帧频率，然后就清晰了
            mMediaRecorder.setOrientationHint(90);// 输出旋转90度，保持竖屏录制
            mMediaRecorder.setOutputFile(file.getAbsolutePath());// 设置录制视频文件的输出路径
            if (mSurfaceHolder != null){
                mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());// 设置捕获视频图像的预览界面
            }

            mMediaRecorder.setOnErrorListener(mOnErrorListener);

            mMediaRecorder.prepare();// 准备
            mMediaRecorder.start();// 开始
            if (mListener != null){
                mListener.onStart();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (mListener != null){
                mListener.onStartFail(e);
            }
        }
    }

    private MediaRecorder.OnErrorListener mOnErrorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mediaRecorder, int what, int extra) {
            release();// 发生错误，停止录制
            if (mListener != null){
                mListener.onRunError();
            }
        }
    };

    /** 获取后置摄像头id */
    private int getBackCameraId() {
        int totalCameraCount = Camera.getNumberOfCameras();
        try {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            for (int i = 0; i < totalCameraCount; i++) {
                Camera.getCameraInfo(i, cameraInfo);
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//后置摄像头
                    return i;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       return -1;
    }

    /** 结束录制 */
    public void stop() {
        if (mMediaRecorder != null){// 如果正在录制，停止并释放资源
            release();
            if (mListener != null){
                mListener.onStop();
            }
        }
    }

    /** 释放资源 */
    private void release(){
        try {
            if (mMediaRecorder != null){
                mMediaRecorder.stop();
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
            if (mCamera != null){
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /** 设置监听器 */
    public void setListener(Listener listener){
        mListener = listener;
    }

    public interface Listener{

        /** 找不到后置摄像头 */
        void noCamera();
        /** 开始录制 */
        void onStart();
        /** 启动失败 */
        void onStartFail(Exception e);
        /** 停止录制 */
        void onStop();
        /** 录制异常 */
        void onRunError();
    }

}
