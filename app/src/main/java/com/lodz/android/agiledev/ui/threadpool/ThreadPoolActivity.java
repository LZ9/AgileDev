package com.lodz.android.agiledev.ui.threadpool;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.core.threadpool.ThreadPoolManager;
import com.lodz.android.core.utils.ScreenUtils;
import com.lodz.android.core.utils.UiHandler;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 线程池测试类
 * Created by zhouL on 2018/3/1.
 */

public class ThreadPoolActivity extends BaseActivity{

    /** 开始按钮 */
    @BindView(R.id.start_btn)
    Button mStartBtn;

    /** 关闭按钮 */
    @BindView(R.id.close_btn)
    Button mCloseBtn;

    /** 清空按钮 */
    @BindView(R.id.clear_btn)
    Button mClearBtn;

    @BindView(R.id.scroll_view)
    ScrollView mScrollView;

    /** 结果 */
    @BindView(R.id.result_tv)
    TextView mResultTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_thread_pool_layout;
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
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLongThread();
                for (int i = 0; i < 30; i++) {
                    startShortThread(i);
                }
            }
        });

        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadPoolManager.get().releaseNormalExecutor();
            }
        });

        mClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultTv.setText("");
            }
        });
    }

    private void startLongThread() {
        ThreadPoolManager.get().executeNormal(new Runnable() {
            @Override
            public void run() {
                printResult("long start");
                try {
                    Thread.sleep(12000);
                    printResult("long end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    printResult("long InterruptedException");
                }
            }
        });
    }

    private void startShortThread(final int index) {
        ThreadPoolManager.get().executeNormal(new Runnable() {
            @Override
            public void run() {
                printResult("short " + index + " start");
                try {
                    Thread.sleep(2000);
                    printResult("short " + index + " end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    printResult("short " + index + " InterruptedException");
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
//        ThreadPoolManager.get().newBuilder()
//                .setAwaitTime(50)// 设置线程结束等待时间
//                .setAwaitTimeUnit(TimeUnit.MILLISECONDS)// 设置线程结束等待时间单位
//                .setKeepAliveTime(1)// 设置线程数空闲时间
//                .setKeepAliveTimeUnit(TimeUnit.SECONDS)// 设置线程数空闲时间单位
//                .setCorePoolSize(4)// 设置线程数
//                .setMaximumPoolSize(8)// 设置最大线程数
//                .setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy())// 设置拒绝策略
//                .build();
        showStatusCompleted();
    }

    private void printResult(final String result){
        UiHandler.post(new Runnable() {
            @Override
            public void run() {
                mResultTv.setText(mResultTv.getText() + "\n" + result);
                mScrollView.scrollTo(ScreenUtils.getScreenWidth(getContext()), ScreenUtils.getScreenHeight(getContext()));
            }
        });
    }
}
