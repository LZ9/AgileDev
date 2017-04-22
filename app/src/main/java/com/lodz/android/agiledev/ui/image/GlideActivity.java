package com.lodz.android.agiledev.ui.image;

import android.animation.ObjectAnimator;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.NotificationTarget;
import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.utils.FileManager;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.core.utils.DensityUtils;
import com.lodz.android.core.utils.ToastUtils;
import com.lodz.android.imageloader.ImageLoader;
import com.lodz.android.imageloader.utils.UriUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Glide展示类
 * Created by zhouL on 2017/4/7.
 */

public class GlideActivity extends AbsActivity{

    @BindView(R.id.img_view)
    ImageView mImageView;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_glide_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        // 自定义通知栏
        findViewById(R.id.custom_notification_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteViews remoteViews = getRemoteViews(getContext());
                Notification notification =
                        new NotificationCompat.Builder(getContext())
                                .setSmallIcon(R.drawable.ic_launcher)
                                .setContent(remoteViews)
                                .setPriority( NotificationCompat.PRIORITY_MIN)
                                .build();

                NotificationManager mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(1235, notification);

                NotificationTarget notificationTarget = new NotificationTarget(
                        getContext(),
                        remoteViews,
                        R.id.remoteview_icon,
                        notification,
                        1235);

                ImageLoader.create(getContext())
                        .load(UriUtils.parseResId(R.drawable.ic_launcher))
                        .joinGlide()
                        .asBitmapInto(notificationTarget);
            }

            private RemoteViews getRemoteViews(Context context) {
                final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.view_remote_notification);
                remoteViews.setImageViewResource(R.id.remoteview_icon, R.drawable.ic_launcher);
                remoteViews.setTextViewText(R.id.remoteview_title, "耿鬼");
                remoteViews.setTextViewText(R.id.remoteview_msg, "鬼斯-鬼斯通-耿鬼");
                return remoteViews;
            }
        });

        // 原生通知栏
        findViewById(R.id.notification_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notification notification =
                        new NotificationCompat.Builder(getContext())
                                .setSmallIcon(R.drawable.ic_launcher)
                                .setColor(Color.YELLOW)
                                .setContentTitle("胡地")
                                .setContentText("凯西-勇吉拉-胡地")
                                .setPriority(NotificationCompat.PRIORITY_MIN)
                                .build();
                NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1234, notification);
            }
        });

        // 本地图片
        findViewById(R.id.local_image_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.create(getContext())
                        .load(R.drawable.ic_default)
                        .joinGlide()
                        .setFitCenter()
                        .into(mImageView);
            }
        });

        // 网络图片
        findViewById(R.id.url_image_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.create(getContext())
                        .load("http://i0.hdslb.com/group1/M00/44/5B/oYYBAFbRLIuARLeSAAJAaSQWy9s392.jpg")
                        .joinGlide()
                        .setFitCenter()
                        .into(mImageView);
            }
        });

        // 网络gif
        findViewById(R.id.url_gif_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.create(getContext())
                        .load("http://image2.sina.com.cn/gm/ol/cross/tujian/446034.gif")
                        .joinGlide()
                        .setFitCenter()
                        .into(mImageView);
            }
        });

        // 动画显示
        findViewById(R.id.anim_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.create(getContext())
                        .load("http://i0.hdslb.com/group1/M00/44/5B/oYYBAFbRLIuARLeSAAJAaSQWy9s392.jpg")
                        .joinGlide()
                        .setAnim(new ViewPropertyAnimation.Animator() {
                            @Override
                            public void animate(View view) {
                                view.setAlpha(0.7f);
                                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0.7f, 1f);
                                fadeAnim.setDuration(1500);
                                fadeAnim.start();
                            }
                        })
                        .setFitCenter()
                        .into(mImageView);
            }
        });

        // 毛玻璃
        findViewById(R.id.url_blur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.create(getContext())
                        .load("http://i0.hdslb.com/group1/M00/44/5B/oYYBAFbRLIuARLeSAAJAaSQWy9s392.jpg")
                        .useBlur()
                        .setImageSize(DensityUtils.dp2px(getContext(), 100), DensityUtils.dp2px(getContext(), 100))// 如果不设置宽高会导致图片拉伸
                        .joinGlide()
                        .setFitCenter()
                        .into(mImageView);
            }
        });

        // 覆盖颜色
        findViewById(R.id.url_filter_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.create(getContext())
                        .load("http://i0.hdslb.com/group1/M00/44/5B/oYYBAFbRLIuARLeSAAJAaSQWy9s392.jpg")
                        .setImageSize(DensityUtils.dp2px(getContext(), 100), DensityUtils.dp2px(getContext(), 100))// 如果不设置宽高会导致图片拉伸
                        .joinGlide()
                        .setFitCenter()
                        .setFilterColor(ContextCompat.getColor(getContext(), R.color.color_60ea413c))
                        .useFilterColor()
                        .into(mImageView);
            }
        });

        // 圆角
        findViewById(R.id.url_rounded_corners).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.create(getContext())
                        .load("http://i0.hdslb.com/group1/M00/44/5B/oYYBAFbRLIuARLeSAAJAaSQWy9s392.jpg")
                        .setImageSize(DensityUtils.dp2px(getContext(), 100), DensityUtils.dp2px(getContext(), 100))// 如果不设置宽高会导致图片拉伸
                        .useRoundCorner()
                        .setRoundCorner(10f)
                        .joinGlide()
//                        .setRoundedCornerType(RoundedCornersTransformation.CornerType.LEFT)
                        .setFitCenter()
                        .into(mImageView);
            }
        });

        // 灰度化
        findViewById(R.id.url_grayscale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.create(getContext())
                        .load("http://i0.hdslb.com/group1/M00/44/5B/oYYBAFbRLIuARLeSAAJAaSQWy9s392.jpg")
                        .setImageSize(DensityUtils.dp2px(getContext(), 100), DensityUtils.dp2px(getContext(), 100))// 如果不设置宽高会导致图片拉伸
                        .joinGlide()
                        .setFitCenter()
                        .useGrayscale()
                        .into(mImageView);
            }
        });

        // 圆形
        findViewById(R.id.url_crop_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.create(getContext())
                        .load("http://i0.hdslb.com/group1/M00/44/5B/oYYBAFbRLIuARLeSAAJAaSQWy9s392.jpg")
                        .setImageSize(DensityUtils.dp2px(getContext(), 100), DensityUtils.dp2px(getContext(), 100))// 如果不设置宽高会导致图片拉伸
                        .useCircle()
                        .joinGlide()
                        .setFitCenter()
                        .into(mImageView);
            }
        });

        // 正方形
        findViewById(R.id.url_crop_square).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.create(getContext())
                        .load("http://i0.hdslb.com/group1/M00/44/5B/oYYBAFbRLIuARLeSAAJAaSQWy9s392.jpg")
                        .setImageSize(DensityUtils.dp2px(getContext(), 100), DensityUtils.dp2px(getContext(), 100))// 如果不设置宽高会导致图片拉伸
                        .joinGlide()
                        .setFitCenter()
                        .useCropSquare()
                        .into(mImageView);
            }
        });

        // 圆形/毛玻璃
        findViewById(R.id.url_circle_blur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.create(getContext())
                        .load("http://i0.hdslb.com/group1/M00/44/5B/oYYBAFbRLIuARLeSAAJAaSQWy9s392.jpg")
                        .useBlur()
                        .useCircle()
                        .setImageSize(DensityUtils.dp2px(getContext(), 100), DensityUtils.dp2px(getContext(), 100))// 如果不设置宽高会导致图片拉伸
                        .joinGlide()
                        .setFitCenter()
                        .into(mImageView);
            }
        });

        // 圆角/灰度化
        findViewById(R.id.url_rounded_corners_grayscale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.create(getContext())
                        .load("http://i0.hdslb.com/group1/M00/44/5B/oYYBAFbRLIuARLeSAAJAaSQWy9s392.jpg")
                        .useRoundCorner()
                        .setImageSize(DensityUtils.dp2px(getContext(), 100), DensityUtils.dp2px(getContext(), 100))// 如果不设置宽高会导致图片拉伸
                        .joinGlide()
                        .useGrayscale()
                        .setFitCenter()
                        .into(mImageView);
            }
        });

        // 圆形/灰度化/毛玻璃
        findViewById(R.id.url_circle_grayscale_blur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.create(getContext())
                        .load("http://i0.hdslb.com/group1/M00/44/5B/oYYBAFbRLIuARLeSAAJAaSQWy9s392.jpg")
                        .useCircle()
                        .useBlur()
                        .setImageSize(DensityUtils.dp2px(getContext(), 100), DensityUtils.dp2px(getContext(), 100))// 如果不设置宽高会导致图片拉伸
                        .joinGlide()
                        .useGrayscale()
                        .setFitCenter()
                        .into(mImageView);
            }
        });

        // 蒙板效果
        findViewById(R.id.url_mask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.create(getContext())
                        .load("http://i0.hdslb.com/group1/M00/44/5B/oYYBAFbRLIuARLeSAAJAaSQWy9s392.jpg")
                        .setImageSize(DensityUtils.dp2px(getContext(), 100), DensityUtils.dp2px(getContext(), 100))// 如果不设置宽高会导致图片拉伸
                        .joinGlide()
                        .useMask()
                        .setFitCenter()
                        .into(mImageView);
            }
        });

        // 本地webp
        findViewById(R.id.local_webp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.create(getContext())
                        .load(R.drawable.ic_webp)
                        .joinGlide()
                        .setFitCenter()
                        .into(mImageView);
            }
        });

        // 本地gif
        findViewById(R.id.local_gif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.create(getContext())
                        .load(R.drawable.ic_gif)
                        .joinGlide()
                        .setFitCenter()
                        .into(mImageView);
            }
        });

        // 本地视频
        findViewById(R.id.local_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = FileManager.getDownloadFolderPath() + "20170422.mp4";
                File file = new File(filePath);
                if (!file.exists()){
                    ToastUtils.showShort(getContext(), "文件不存在");
                    return;
                }
                ImageLoader.create(getContext())
                        .load(Uri.fromFile(file))
                        .joinGlide()
                        .setVideo()
                        .into(mImageView);
            }
        });


    }
}
