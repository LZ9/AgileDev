package com.lodz.android.core.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.widget.RemoteViews;

import java.util.List;
import java.util.Random;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.core.app.NotificationCompat;

/**
 * 通知帮助类
 * Created by zhouL on 2017/12/1.
 */

public class NotificationUtils {

    /** 通知管理 */
    private NotificationManager mNotificationManager;

    /**
     * 创建通知管理
     * @param context 上下文
     */
    public static NotificationUtils create(Context context){
        return new NotificationUtils(context);
    }

    private NotificationUtils(Context context) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * 发送通知
     * @param id 通知id
     * @param notification 通知内容
     */
    public void send(int id, Notification notification){
        if (notification == null || mNotificationManager == null){
            return;
        }
        mNotificationManager.notify(id, notification);
    }

    /**
     * 发送通知（随机id）
     * @param notification 通知内容
     */
    public void send(Notification notification){
        if (notification == null || mNotificationManager == null){
            return;
        }
        mNotificationManager.notify(getRandomId(), notification);
    }

    /**
     * 创建一个通知通道
     * @param channel 通知通道
     */
    public NotificationUtils createNotificationChannel(NotificationChannel channel){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (channel != null){
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        return this;
    }

    /**
     * 创建多个通知通道
     * @param channels 通道数组
     */
    public NotificationUtils createNotificationChannels(List<NotificationChannel> channels){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (!ArrayUtils.isEmpty(channels)){
                mNotificationManager.createNotificationChannels(channels);
            }
        }
        return this;
    }

    /**
     * 创建一个通知通道组
     * @param group 通道组
     */
    public NotificationUtils createNotificationChannelGroup(NotificationChannelGroup group){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (group != null){
                mNotificationManager.createNotificationChannelGroup(group);
            }
        }
        return this;
    }

    /**
     * 创建多个通知通道组
     * @param groups 通道组数组
     */
    public NotificationUtils createNotificationChannelGroups(List<NotificationChannelGroup> groups){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (!ArrayUtils.isEmpty(groups)){
                mNotificationManager.createNotificationChannelGroups(groups);
            }
        }
        return this;
    }

    /** 获取一个1-999999 */
    private int getRandomId(){
        Random random = new Random();
        return random.nextInt(999998) + 1;
    }

    /** 如何创建一个NotificationChannel */
    private void notificationChannelBuild(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannelGroup group = new NotificationChannelGroup("g0001", "测试分组");

            String channelId = "c00001";
            String channelName = "测试通道";
            int level = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, level);
            channel.enableLights(true);// 开启指示灯，如果设备有的话。
            channel.setLightColor(Color.RED);// 设置指示灯颜色
            channel.setDescription("通道描述");// 通道描述
            channel.enableVibration(true);// 开启震动
            channel.setVibrationPattern(new long[]{100, 200, 400, 300, 100});// 设置震动频率
            channel.setGroup(group.getId());
            channel.canBypassDnd();// 检测是否绕过免打扰模式
            channel.setBypassDnd(true);// 设置绕过免打扰模式
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            channel.canShowBadge();// 检测是否显示角标
            channel.setShowBadge(true);// 设置是否显示角标
        }
    }

    /** 如何创建一个Notification */
    private void notificationBuild(Context context, Bitmap bitmap, @DrawableRes int drawableId, @LayoutRes int layoutId){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "123456");// 获取构造器，channelId在O（26）才有用可以随便传

        //---------------------- 常用 ---------------------
        builder.setTicker("状态栏显示的提示");// 在5.0以上不显示Ticker属性信息
        builder.setContentTitle("内容标题");// 通知栏通知的标题
        builder.setContentText("内容文本信息");// 通知栏通知的详细内容（只有一行）
        builder.setAutoCancel(true);// 设置为true，点击该条通知会自动删除，false时只能通过滑动来删除（一般都是true）
        builder.setSmallIcon(drawableId);//通知上面的小图标（必传）

        //---------------------- 不常用 ---------------------
        builder.setLargeIcon(bitmap);// 通知消息上的大图标（可传可不传）
        builder.setColor(Color.RED);//这边设置颜色，可以给5.0及以上版本smallIcon设置背景色（基本不使用）
        builder.setWhen(System.currentTimeMillis());// 设置该条通知时间（基本不使用）
        builder.setOngoing(false);//设置是否为一个正在进行中的通知，这一类型的通知将无法删除（基本不使用）

        //---------------------- 设置意图 ---------------------
        PendingIntent pIntent = PendingIntent.getActivity(context, 1,
                new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com")), PendingIntent.FLAG_ONE_SHOT);//创建一个意图
        builder.setContentIntent(pIntent);// 将意图设置到通知上
        builder.setFullScreenIntent(pIntent, true);// 横幅通知（有的系统会直接打开PendingIntent）推荐使用setContentIntent()

        //---------------------- 提示音和优先级 ---------------------
        // NotificationCompat.DEFAULT_SOUND/NotificationCompat.DEFAULT_VIBRATE/NotificationCompat.DEFAULT_LIGHTS
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);//通知默认的声音 震动 呼吸灯
        // NOTIFI_PRIORITY_MAX, NOTIFI_PRIORITY_HIGH, NOTIFI_PRIORITY_DEFAULT, NOTIFI_PRIORITY_LOW, NOTIFI_PRIORITY_MIN
        builder.setPriority(NotificationCompat.PRIORITY_MAX);//设置优先级，级别高的排在前面

        //---------------------- 进度条 ---------------------
        builder.setProgress(100, 10, true);// 设置进度条setProgress(0, 0, false)为移除进度条

        //---------------------- 设置大文本样式 ---------------------
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("大文本的标题");// 给样式设置大文本的标题
        bigTextStyle.bigText("这里是点击通知后要显示的正文，正文很多字正文很多字正文很多字");// 给样式设置大文本内容
        bigTextStyle.setSummaryText("末尾只一行的文字内容");//总结，可以不设置
        builder.setStyle(bigTextStyle);// 将样式添加到通知

        //---------------------- 多行大文本样式 ---------------------
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("大文本的标题");// 给样式设置大文本的标题
        inboxStyle.addLine("啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦");//设置每行内容
        inboxStyle.addLine("哈哈哈哈哈");
        inboxStyle.addLine("嘻嘻嘻嘻");
        inboxStyle.setSummaryText("末尾只一行的文字内容");//总结，可以不设置
        builder.setStyle(inboxStyle);

        //---------------------- 大图文本样式 ---------------------
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle("大文本的标题");// 给样式设置大文本的标题
        bigPictureStyle.setSummaryText("末尾只一行的文字内容");//大文本的内容（只有一行）
        bigPictureStyle.bigPicture(bitmap);//设置大图
        builder.setStyle(bigPictureStyle);

        //---------------------- 自定义内容样式 ---------------------
        int R_id_title = 1;//模拟layout里面的资源id
        int R_id_img = 2;//模拟layout里面的资源id
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), layoutId);
        remoteViews.setTextViewText(R_id_title, "自定义文本");//设置对应id的内容
        remoteViews.setImageViewResource(R_id_img, drawableId);
        builder.setContent(remoteViews);

        Notification notification = builder.build();//构建通知
    }

}
