package com.lodz.android.agiledev.ui.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.rx.subscribe.observer.BaseObserver;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.NotificationUtils;

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

            }
        });

        // 大图文本样式
        mLargeImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 自定义内容样式
        mCustomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /** 显示基础通知 */
    private void showBaseNotify() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "123456");// 获取构造器，channelId在O（26）才有用可以随便传
        builder.setTicker("震惊！拉玛西亚竟然是这个意思");// 通知栏显示的文字
        builder.setContentTitle("拉玛西亚由来");// 通知栏通知的标题
        builder.setContentText("拉玛西亚足球学校始建于1979年，位于巴塞罗那阿里斯蒂德斯大街左侧。当时的球场非常简陋，都是人工草皮，巴萨四个年龄梯队的使用这片拥挤的训练空间。");// 通知栏通知的详细内容（只有一行）
        builder.setAutoCancel(true);// 设置为true，点击该条通知会自动删除，false时只能通过滑动来删除（一般都是true）
        builder.setSmallIcon(R.drawable.ic_launcher);//通知上面的小图标（必传）
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);//通知默认的声音 震动 呼吸灯
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);//设置优先级，级别高的排在前面
        Notification notification = builder.build();//构建通知
        NotificationUtils.create(getContext()).send(notification);
    }

    /** 显示带意图的通知 */
    private void showIntentNotify() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "123456");// 获取构造器，channelId在O（26）才有用可以随便传
        builder.setTicker("希尔斯堡惨案真相");// 通知栏显示的文字
        builder.setContentTitle("希尔斯堡惨案");// 通知栏通知的标题
        builder.setContentText("因在场警官的谎言、媒体的恶意报道与政府的失公处理，迄今还没有人为希尔斯堡惨案负起应有的责任。");// 通知栏通知的详细内容（只有一行）
        builder.setAutoCancel(true);// 设置为true，点击该条通知会自动删除，false时只能通过滑动来删除（一般都是true）
        builder.setSmallIcon(R.drawable.ic_launcher);//通知上面的小图标（必传）
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
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "123456");// 获取构造器，channelId在O（26）才有用可以随便传
        builder.setTicker(isComplete ? "下载完成" : "下载支付宝");// 通知栏显示的文字
        builder.setContentTitle("支付宝V8.8.12");// 通知栏通知的标题
        builder.setContentText(isComplete ? "支付宝下载完成" : "正在为您下载支付宝");// 通知栏通知的详细内容（只有一行）
        builder.setAutoCancel(true);// 设置为true，点击该条通知会自动删除，false时只能通过滑动来删除（一般都是true）
        builder.setSmallIcon(R.drawable.ic_launcher);//通知上面的小图标（必传）
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
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "123456");// 获取构造器，channelId在O（26）才有用可以随便传
        builder.setTicker("震惊！拉玛西亚竟然是这个意思");// 通知栏显示的文字
        builder.setContentTitle("拉玛西亚由来");// 通知栏通知的标题
        builder.setContentText("拉玛西亚足球学校始建于1979年，位于巴塞罗那阿里斯蒂德斯大街左侧。当时的球场非常简陋，都是人工草皮，巴萨四个年龄梯队的使用这片拥挤的训练空间。");// 通知栏通知的详细内容（只有一行）
        builder.setAutoCancel(true);// 设置为true，点击该条通知会自动删除，false时只能通过滑动来删除（一般都是true）
        builder.setSmallIcon(R.drawable.ic_launcher);//通知上面的小图标（必传）
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);//通知默认的声音 震动 呼吸灯
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);//设置优先级，级别高的排在前面

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("大文本的标题");// 给样式设置大文本的标题
        bigTextStyle.bigText("这里是点击通知后要显示的正文，正文很多字正文很多字正文很多字");// 给样式设置大文本内容
        bigTextStyle.setSummaryText("末尾只一行的文字内容");//总结，可以不设置
        builder.setStyle(bigTextStyle);// 将样式添加到通知

        Notification notification = builder.build();//构建通知
        NotificationUtils.create(getContext()).send(notification);
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }
}
