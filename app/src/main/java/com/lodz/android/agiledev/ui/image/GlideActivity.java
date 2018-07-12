package com.lodz.android.agiledev.ui.image;

import android.animation.ObjectAnimator;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.NotificationTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.agiledev.utils.file.FileManager;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.core.log.PrintLog;
import com.lodz.android.core.utils.BitmapUtils;
import com.lodz.android.core.utils.NotificationUtils;
import com.lodz.android.core.utils.ToastUtils;
import com.lodz.android.imageloader.ImageLoader;
import com.lodz.android.imageloader.glide.transformations.RoundedCornersTransformation;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Glide测试
 * Created by zhouL on 2017/4/7.
 */

public class GlideActivity extends BaseActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, GlideActivity.class);
        context.startActivity(starter);
    }

    /** 主频道id */
    private static final String NOTIFI_CHANNEL_MAIN_ID = "c0001";
    /** 网络图片地址 */
    private static final String IMG_URL = "http://hiphotos.baidu.com/zhidao/pic/item/d439b6003af33a87dd932ba4cd5c10385243b595.jpg";
    /** 网络gif地址 */
    private static final String GIF_URL = "http://image2.sina.com.cn/gm/ol/cross/tujian/446034.gif";
    /** 图片的BASE64 */
    private static final String PIC_BASE64 = "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";

    /** BASE64图片 */
    @BindView(R.id.base64_img)
    ImageView mBase64Img;
    /** 本地居中剪切图片 */
    @BindView(R.id.local_crop_img)
    ImageView mLocalCropImg;
    /** 网络图片 */
    @BindView(R.id.url_img)
    ImageView mUrlImg;
    /** 动画显示 */
    @BindView(R.id.anim_img)
    ImageView mAnimImg;
    /** 网络gif */
    @BindView(R.id.url_gif_img)
    ImageView mUrlGifImg;
    /** 本地gif */
    @BindView(R.id.local_gif_img)
    ImageView mLocalGifImg;
    /** 毛玻璃 */
    @BindView(R.id.blur_img)
    ImageView mBlurImg;
    /** 覆盖颜色 */
    @BindView(R.id.filter_color_img)
    ImageView mFilterColorImg;
    /** 全圆角 */
    @BindView(R.id.corners_all_img)
    ImageView mCornersAllImg;
    /** 顶部圆角 */
    @BindView(R.id.corners_top_img)
    ImageView mCornersTopImg;
    /** 灰度化 */
    @BindView(R.id.grayscale_img)
    ImageView mGrayscaleImg;
    /** 圆角/灰度化 */
    @BindView(R.id.corners_grayscale_img)
    ImageView mCornersGrayscaleImg;
    /** 圆形 */
    @BindView(R.id.circle_img)
    ImageView mCircleImg;
    /** 圆形/毛玻璃 */
    @BindView(R.id.circle_blur_img)
    ImageView mCircleBlurImg;
    /** 圆形/灰度化/毛玻璃 */
    @BindView(R.id.circle_grayscale_blur_img)
    ImageView mCircleGrayscaleBlurImg;
    /** 蒙板效果 */
    @BindView(R.id.mask_img)
    ImageView mMaskImg;
    /** 正方形 */
    @BindView(R.id.square_img)
    ImageView mSquareImg;
    /** 正方形/毛玻璃 */
    @BindView(R.id.square_blur_img)
    ImageView mSquareBlurImg;
    /** 本地webp */
    @BindView(R.id.local_webp_img)
    ImageView mLocalWebpImg;
    /** 本地视频 */
    @BindView(R.id.local_video_img)
    ImageView mLocalVideoImg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_glide_layout;
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

        // 自定义通知栏
        findViewById(R.id.custom_notification_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), NOTIFI_CHANNEL_MAIN_ID);// 获取构造器
                builder.setTicker("");// 通知栏显示的文字
                builder.setAutoCancel(true);// 设置为true，点击该条通知会自动删除，false时只能通过滑动来删除（一般都是true）
                builder.setSmallIcon(R.mipmap.ic_launcher);//通知上面的小图标（必传）
                builder.setDefaults(NotificationCompat.DEFAULT_ALL);//通知默认的声音 震动 呼吸灯
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);//设置优先级，级别高的排在前面

                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.view_remote_notification);
                remoteViews.setImageViewResource(R.id.remoteview_icon, R.drawable.ic_launcher);
                remoteViews.setTextViewText(R.id.remoteview_title, "耿鬼");
                remoteViews.setTextViewText(R.id.remoteview_msg, "鬼斯-鬼斯通-耿鬼");
                builder.setContent(remoteViews);

                ImageLoader.create(getContext())
                        .load(IMG_URL)
                        .setCenterCrop()
                        .asBitmapInto(new NotificationTarget(getContext(), R.id.remoteview_icon, remoteViews, builder.build(), 1235));
            }
        });

        // 原生通知栏
        findViewById(R.id.notification_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageLoader.create(getContext())
                        .load(IMG_URL)
                        .setCenterCrop()
                        .asBitmapInto(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), NOTIFI_CHANNEL_MAIN_ID);// 获取构造器
                                builder.setTicker("震惊！谁是超能力霸主");// 通知栏显示的文字
                                builder.setContentTitle("胡地");// 通知栏通知的标题
                                builder.setContentText("凯西-勇吉拉-胡地");// 通知栏通知的详细内容（只有一行）
                                builder.setAutoCancel(true);// 设置为true，点击该条通知会自动删除，false时只能通过滑动来删除（一般都是true）
                                builder.setLargeIcon(resource);
                                builder.setSmallIcon(R.mipmap.ic_launcher);//通知上面的小图标（必传）
                                builder.setDefaults(NotificationCompat.DEFAULT_ALL);//通知默认的声音 震动 呼吸灯
                                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);//设置优先级，级别高的排在前面
                                Notification notification = builder.build();//构建通知
                                NotificationUtils.create(getContext()).send(notification);
                            }
                        });
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        initNotificationChannel();
        showStatusCompleted();
        showLocalImg();// 本地图片
        showLocalCropImg();// 本地居中剪切图片
        showUrlImg();// 网络图片
        showAnimImg();// 动画显示
        showUrlGifImg();// 网络gif
        showLocalGifImg();// 本地gif
        showBlurImg();// 毛玻璃
        showFilterColorImg();// 覆盖颜色
        showCornersAllImg();// 全圆角
        showCornersTopImg();// 顶部圆角
        showGrayscaleImg();// 灰度化
        showCornersGrayscaleImg();// 圆角/灰度化
        showCircleImg();// 圆形
        showCircleBlurImg();// 圆形/毛玻璃
        showCircleGrayscaleBlurImg();// 圆形/灰度化/毛玻璃
        showMaskImg();// 蒙板效果
        showSquareImg();// 正方形
        showSquareBlurImg();// 正方形/毛玻璃
        showLocalWebpImg();// 本地webp
        showLocalVideoImg();// 本地视频
    }

    /** 初始化通知通道 */
    private void initNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationUtils.create(getContext()).createNotificationChannel(getMainChannel());// 设置频道
        }
    }

    /** 获取主通道 */
    private NotificationChannel getMainChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFI_CHANNEL_MAIN_ID, "主通知", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);// 开启指示灯，如果设备有的话。
            channel.setLightColor(Color.GREEN);// 设置指示灯颜色
            channel.setDescription("应用主通知频道");// 通道描述
            channel.enableVibration(true);// 开启震动
            channel.setVibrationPattern(new long[]{100, 200, 400, 300, 100});// 设置震动频率
            channel.canBypassDnd();// 检测是否绕过免打扰模式
            channel.setBypassDnd(true);// 设置绕过免打扰模式
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            channel.canShowBadge();// 检测是否显示角标
            channel.setShowBadge(true);// 设置是否显示角标
            return channel;
        }
        return null;
    }

    /** BASE64图片 */
    private void showLocalImg() {
        ImageLoader.create(getContext())
                .load(BitmapUtils.base64ToByte(PIC_BASE64))
                .setCenterInside()
                .into(mBase64Img);
    }

    /** 本地居中剪切图片 */
    private void showLocalCropImg() {
        ImageLoader.create(getContext())
                .load(R.drawable.bg_pokemon)
                .setCenterCrop()
                .into(mLocalCropImg);
    }

    /** 网络图片 */
    private void showUrlImg() {
        ImageLoader.create(getContext())
                .load(IMG_URL)
                .setFitCenter()
                .setRequestListener(new RequestListener<Object>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Object> target, boolean isFirstResource) {
                        PrintLog.e("testtag", model == null ? "" : model.toString(), e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Object resource, Object model, Target<Object> target, DataSource dataSource, boolean isFirstResource) {
                        PrintLog.d("testtag", model == null ? "" : model.toString());
                        return false;
                    }
                })
                .into(mUrlImg);
    }

    /** 动画显示 */
    private void showAnimImg() {
        ImageLoader.create(getContext())
                .load(IMG_URL)
                .setAnim(new ViewPropertyTransition.Animator() {
                    @Override
                    public void animate(View view) {
                        view.setAlpha(0.5f);
                        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0.5f, 1f);
                        fadeAnim.setDuration(5000);
                        fadeAnim.start();
                    }
                })
                .setFitCenter()
                .into(mAnimImg);
    }

    /** 网络gif */
    private void showUrlGifImg() {
        ImageLoader.create(getContext())
                .load(GIF_URL)
                .setFitCenter()
                .into(mUrlGifImg);
    }

    /** 本地gif */
    private void showLocalGifImg() {
        ImageLoader.create(getContext())
                .load(R.drawable.ic_gif)
                .setFitCenter()
                .into(mLocalGifImg);
    }

    /** 毛玻璃 */
    private void showBlurImg() {
        ImageLoader.create(getContext())
                .load(IMG_URL)
                .useBlur()
                .setFitCenter()
                .into(mBlurImg);
    }

    /** 覆盖颜色 */
    private void showFilterColorImg() {
        ImageLoader.create(getContext())
                .load(IMG_URL)
                .setFitCenter()
                .setFilterColor(ContextCompat.getColor(getContext(), R.color.color_60ea413c))
                .useFilterColor()
                .into(mFilterColorImg);
    }

    /** 全圆角 */
    private void showCornersAllImg() {
        ImageLoader.create(getContext())
                .load(IMG_URL)
                .useRoundCorner()
                .setRoundCorner(10)
                .setFitCenter()
                .into(mCornersAllImg);
    }

    /** 顶部圆角 */
    private void showCornersTopImg() {
        ImageLoader.create(getContext())
                .load(IMG_URL)
                .useRoundCorner()
                .setRoundCorner(10)
                .setRoundedCornerType(RoundedCornersTransformation.CornerType.TOP)
                .setFitCenter()
                .into(mCornersTopImg);
    }

    /** 灰度化 */
    private void showGrayscaleImg() {
        ImageLoader.create(getContext())
                .load(IMG_URL)
                .setFitCenter()
                .useGrayscale()
                .into(mGrayscaleImg);
    }

    /** 圆角/灰度化 */
    private void showCornersGrayscaleImg() {
        ImageLoader.create(getContext())
                .load(IMG_URL)
                .useRoundCorner()
                .setRoundCorner(10)
                .useGrayscale()
                .setFitCenter()
                .into(mCornersGrayscaleImg);
    }

    /** 圆形 */
    private void showCircleImg() {
        ImageLoader.create(getContext())
                .load(IMG_URL)
                .useCircle()
                .setFitCenter()
                .into(mCircleImg);
    }

    /** 圆形/毛玻璃 */
    private void showCircleBlurImg() {
        ImageLoader.create(getContext())
                .load(IMG_URL)
                .useBlur()
                .useCircle()
                .setFitCenter()
                .into(mCircleBlurImg);
    }

    /** 圆形/灰度化/毛玻璃 */
    private void showCircleGrayscaleBlurImg() {
        ImageLoader.create(getContext())
                .load(IMG_URL)
                .useCircle()
                .useBlur()
                .useGrayscale()
                .setFitCenter()
                .into(mCircleGrayscaleBlurImg);
    }

    /** 蒙板效果 */
    private void showMaskImg() {
        ImageLoader.create(getContext())
                .load(IMG_URL)
                .useMask()
                .setFitCenter()
                .into(mMaskImg);
    }

    /** 正方形 */
    private void showSquareImg() {
        ImageLoader.create(getContext())
                .load(IMG_URL)
                .setCenterCrop()
                .useCropSquare()
                .into(mSquareImg);
    }

    /** 正方形/毛玻璃 */
    private void showSquareBlurImg() {
        ImageLoader.create(getContext())
                .load(IMG_URL)
                .useBlur()
                .setCenterCrop()
                .useCropSquare()
                .into(mSquareBlurImg);
    }

    /** 本地webp */
    private void showLocalWebpImg() {
        ImageLoader.create(getContext())
                .load(R.drawable.ic_webp)
                .setFitCenter()
                .into(mLocalWebpImg);
    }

    /** 本地视频 */
    private void showLocalVideoImg() {
        String filePath = FileManager.getDownloadFolderPath() + "20170422.mp4";
        File file = new File(filePath);
        if (!file.exists()){
            ToastUtils.showShort(getContext(), "文件不存在");
            return;
        }
        ImageLoader.create(getContext())
                .load(Uri.fromFile(file))
                .setVideo()
                .into(mLocalVideoImg);
    }
}
