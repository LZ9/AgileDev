package com.lodz.android.agiledev.ui.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.rx.subscribe.observer.BaseObserver;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.NotificationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 通知测试类
 * Created by zhouL on 2017/12/1.
 */

public class NotificationActivity extends BaseActivity{

    /** 通知组id */
    private static final String NOTIFI_GROUP_ID = "g0001";
    /** 主频道id */
    private static final String NOTIFI_CHANNEL_MAIN_ID = "c0001";
    /** 下载频道id */
    private static final String NOTIFI_CHANNEL_DOWNLOAD_ID = "c0002";

    public static void start(Context context) {
        Intent starter = new Intent(context, NotificationActivity.class);
        context.startActivity(starter);
    }

    /** 基本通知 */
    @BindView(R.id.base_btn)
    Button mBbaseBtn;

    /** 带意图通知 */
    @BindView(R.id.intent_btn)
    Button mIntentBtn;

    /** 带进度条 */
    @BindView(R.id.progress_btn)
    Button mProgressBtn;

    /** 单行大文本样式 */
    @BindView(R.id.single_txt_btn)
    Button mSingleTxtBtn;

    /** 多行大文本样式 */
    @BindView(R.id.multi_txt_btn)
    Button mMultiTxtBtn;

    /** 大图文本样式 */
    @BindView(R.id.large_img_btn)
    Button mLargeImgBtn;

    /** 自定义内容样式 */
    @BindView(R.id.custom_btn)
    Button mCustomBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notification_layout;
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
    protected void setListeners() {
        super.setListeners();

        // 基本通知
        mBbaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBaseNotify();
            }
        });

        // 带意图通知
        mIntentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIntentNotify();
            }
        });

        // 带进度条
        mProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgressNotify();
            }
        });

        // 单行大文本样式
        mSingleTxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleTxtNotify();
            }
        });

        // 多行大文本样式
        mMultiTxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMultiTxtNotify();
            }
        });

        // 大图文本样式
        mLargeImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLargeImgNotify();
            }
        });

        // 自定义内容样式
        mCustomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomNotify();
            }
        });
    }

    /** 显示基础通知 */
    private void showBaseNotify() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), NOTIFI_CHANNEL_MAIN_ID);// 获取构造器
        builder.setTicker("震惊！拉玛西亚竟然是这个意思");// 通知栏显示的文字
        builder.setContentTitle("拉玛西亚由来");// 通知栏通知的标题
        builder.setContentText("拉玛西亚足球学校始建于1979年，位于巴塞罗那阿里斯蒂德斯大街左侧。当时的球场非常简陋，都是人工草皮，巴萨四个年龄梯队的使用这片拥挤的训练空间。");// 通知栏通知的详细内容（只有一行）
        builder.setAutoCancel(true);// 设置为true，点击该条通知会自动删除，false时只能通过滑动来删除（一般都是true）
        builder.setSmallIcon(R.mipmap.ic_launcher);//通知上面的小图标（必传）
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);//通知默认的声音 震动 呼吸灯
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);//设置优先级，级别高的排在前面
        Notification notification = builder.build();//构建通知
        NotificationUtils.create(getContext()).send(notification);
    }

    /** 显示带意图的通知 */
    private void showIntentNotify() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), NOTIFI_CHANNEL_MAIN_ID);// 获取构造器
        builder.setTicker("希尔斯堡惨案真相");// 通知栏显示的文字
        builder.setContentTitle("希尔斯堡惨案");// 通知栏通知的标题
        builder.setContentText("因在场警官的谎言、媒体的恶意报道与政府的失公处理，迄今还没有人为希尔斯堡惨案负起应有的责任。");// 通知栏通知的详细内容（只有一行）
        builder.setAutoCancel(true);// 设置为true，点击该条通知会自动删除，false时只能通过滑动来删除（一般都是true）
        builder.setSmallIcon(R.mipmap.ic_launcher);//通知上面的小图标（必传）
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);//通知默认的声音 震动 呼吸灯
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);//设置优先级，级别高的排在前面

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com"));
//        Intent intent = new Intent(getContext(), MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(getContext(), 1, intent , PendingIntent.FLAG_ONE_SHOT);//创建一个意图
        builder.setContentIntent(pIntent);// 将意图设置到通知上

        Notification notification = builder.build();//构建通知
        NotificationUtils.create(getContext()).send(notification);
    }

    /** 开始进度通知 */
    private void startProgressNotify() {
        Observable.interval(0,200, TimeUnit.MILLISECONDS)
                .subscribe(new BaseObserver<Long>() {
                    @Override
                    public void onBaseSubscribe(Disposable d) {

                    }

                    @Override
                    public void onBaseNext(Long times) {
                        if (times > 100) {
                            showProgressNotify(0, true);
                            dispose();
                            return;
                        }
                        showProgressNotify(times.intValue(), false);
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
     * 显示带进度条的通知
     * @param progress 进度
     * @param isComplete 是否完成
     */
    private void showProgressNotify(int progress, boolean isComplete) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), NOTIFI_CHANNEL_DOWNLOAD_ID);// 获取构造器
        builder.setTicker(isComplete ? "下载完成" : "下载支付宝");// 通知栏显示的文字
        builder.setContentTitle("支付宝V8.8.12");// 通知栏通知的标题
        builder.setContentText(isComplete ? "支付宝下载完成" : "正在为您下载支付宝");// 通知栏通知的详细内容（只有一行）
        builder.setAutoCancel(true);// 设置为true，点击该条通知会自动删除，false时只能通过滑动来删除（一般都是true）
        builder.setSmallIcon(R.mipmap.ic_launcher);//通知上面的小图标（必传）
        if (isComplete){
            builder.setProgress(0, 0, false);
            builder.setDefaults(NotificationCompat.DEFAULT_ALL);//通知默认的声音 震动 呼吸灯
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);//设置优先级，级别高的排在前面
        }else {
            builder.setProgress(100, progress, false);
        }
        Notification notification = builder.build();//构建通知
        NotificationUtils.create(getContext()).send(1112, notification);
    }

    /** 显示单行大文本样式通知 */
    private void showSingleTxtNotify() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), NOTIFI_CHANNEL_MAIN_ID);// 获取构造器
        builder.setTicker("桑切斯加盟梦剧场");// 通知栏显示的文字
        builder.setContentTitle("官宣！智利C罗加盟曼联");// 通知栏通知的标题
        builder.setContentText("北京时间1月23日，桑切斯加盟曼联，身披7号球衣。");// 通知栏通知的详细内容（只有一行）
        builder.setAutoCancel(true);// 设置为true，点击该条通知会自动删除，false时只能通过滑动来删除（一般都是true）
        builder.setSmallIcon(R.mipmap.ic_launcher);//通知上面的小图标（必传）
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);//通知默认的声音 震动 呼吸灯
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);//设置优先级，级别高的排在前面

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("互换！曼联官方宣布签下桑切斯 姆希塔良加盟阿森纳");// 给样式设置大文本的标题
        bigTextStyle.bigText("穆里尼奥表示：“桑切斯是世界上最好的进攻球员之一，他会进一步增强我们年轻而充满天赋的攻击线，他会带来他的野心、动力和个人品质，能让全队变得更好，让球迷为俱乐部感到骄傲。”");// 给样式设置大文本内容（几行都可以）
        bigTextStyle.setSummaryText("ESPN");//总结，可以不设置
        builder.setStyle(bigTextStyle);// 将样式添加到通知

        Notification notification = builder.build();//构建通知
        NotificationUtils.create(getContext()).send(notification);
    }

    /** 显示多行大文本样式通知 */
    private void showMultiTxtNotify() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), NOTIFI_CHANNEL_MAIN_ID);// 获取构造器
        builder.setTicker("你懂鬼畜吗");// 通知栏显示的文字
        builder.setContentTitle("带你了解鬼畜全明星");// 通知栏通知的标题
        builder.setContentText("鬼畜是人类进步的阶梯，你今天进步了吗");// 通知栏通知的详细内容（只有一行）
        builder.setAutoCancel(true);// 设置为true，点击该条通知会自动删除，false时只能通过滑动来删除（一般都是true）
        builder.setSmallIcon(R.mipmap.ic_launcher);//通知上面的小图标（必传）
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);//通知默认的声音 震动 呼吸灯
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);//设置优先级，级别高的排在前面

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("鬼畜常用语");// 给样式设置大文本的标题
        inboxStyle.addLine("* 蕉迟但到");//设置每行内容
        inboxStyle.addLine("* 乖乖站好");
        inboxStyle.addLine("* 我从未见过有如此厚颜无耻之人");
        inboxStyle.addLine("* 新日暮里");
        inboxStyle.setSummaryText("Bilibili动画");//总结，可以不设置
        builder.setStyle(inboxStyle);

        Notification notification = builder.build();//构建通知
        NotificationUtils.create(getContext()).send(notification);
    }

    /** 大图文本样式 */
    private void showLargeImgNotify() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), NOTIFI_CHANNEL_MAIN_ID);// 获取构造器
        builder.setTicker("阿森纳の梗");// 通知栏显示的文字
        builder.setContentTitle("阿森纳与4的不解之缘");// 通知栏通知的标题
        builder.setContentText("争4狂魔，没4找4");// 通知栏通知的详细内容（只有一行）
        builder.setAutoCancel(true);// 设置为true，点击该条通知会自动删除，false时只能通过滑动来删除（一般都是true）
        builder.setSmallIcon(R.mipmap.ic_launcher);//通知上面的小图标（必传）
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);//通知默认的声音 震动 呼吸灯
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);//设置优先级，级别高的排在前面

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle("温格的无奈");// 给样式设置大文本的标题
        bigPictureStyle.setSummaryText("忠义法 纳私利 范雄心 宋公明");//大文本的内容（只有一行）
        bigPictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.ic_regret));//设置大图
        builder.setStyle(bigPictureStyle);

        Notification notification = builder.build();//构建通知
        NotificationUtils.create(getContext()).send(notification);
    }

    /** 自定义内容样式 */
    private void showCustomNotify() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), NOTIFI_CHANNEL_MAIN_ID);// 获取构造器
        builder.setTicker("电影头文字D即将上映");// 通知栏显示的文字
        builder.setAutoCancel(true);// 设置为true，点击该条通知会自动删除，false时只能通过滑动来删除（一般都是true）
        builder.setSmallIcon(R.mipmap.ic_launcher);//通知上面的小图标（必传）
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);//通知默认的声音 震动 呼吸灯
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);//设置优先级，级别高的排在前面

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.view_remote_notification);
        remoteViews.setImageViewResource(R.id.remoteview_icon, R.drawable.ic_regret);
        remoteViews.setTextViewText(R.id.remoteview_title, "头文字D首映");//设置对应id的标题
        remoteViews.setTextViewText(R.id.remoteview_msg, "周杰伦，铃木杏，陈冠希，黄秋生，余文乐，陈小春等主演悉数参加首映式");//设置对应id的内容
        builder.setContent(remoteViews);

        Notification notification = builder.build();//构建通知
        NotificationUtils.create(getContext()).send(notification);
    }

    @Override
    protected void initData() {
        super.initData();
        initNotificationChannel();
        showStatusCompleted();
    }

    /** 初始化通知通道 */
    private void initNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannelGroup group = new NotificationChannelGroup(NOTIFI_GROUP_ID, "通知组");
            NotificationUtils.create(getContext()).createNotificationChannelGroup(group);// 设置通知组

            List<NotificationChannel> channels = new ArrayList<>();
            channels.add(getMainChannel());
            channels.add(getDownloadChannel());
            NotificationUtils.create(getContext()).createNotificationChannels(channels);// 设置频道
        }
    }

    /** 获取下载通道 */
    private NotificationChannel getDownloadChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFI_CHANNEL_DOWNLOAD_ID, "下载通知", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);// 开启指示灯，如果设备有的话。
            channel.setLightColor(Color.GREEN);// 设置指示灯颜色
            channel.setDescription("应用下载通知频道");// 通道描述
            channel.enableVibration(false);// 开启震动
            channel.setGroup(NOTIFI_GROUP_ID);
            channel.setBypassDnd(true);// 设置绕过免打扰模式
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            channel.setShowBadge(false);// 设置是否显示角标
            return channel;
        }
        return null;
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
            channel.setGroup(NOTIFI_GROUP_ID);
            channel.canBypassDnd();// 检测是否绕过免打扰模式
            channel.setBypassDnd(true);// 设置绕过免打扰模式
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            channel.canShowBadge();// 检测是否显示角标
            channel.setShowBadge(true);// 设置是否显示角标
            return channel;
        }
        return null;
    }
}
