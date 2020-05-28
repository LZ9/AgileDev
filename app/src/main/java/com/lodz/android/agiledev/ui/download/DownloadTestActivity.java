package com.lodz.android.agiledev.ui.download;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.download.market.DownloadMarketActivity;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.agiledev.utils.file.FileManager;
import com.lodz.android.component.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload3.RxDownload;
import zlc.season.rxdownload3.core.Deleted;
import zlc.season.rxdownload3.core.Downloading;
import zlc.season.rxdownload3.core.Failed;
import zlc.season.rxdownload3.core.Mission;
import zlc.season.rxdownload3.core.Normal;
import zlc.season.rxdownload3.core.Status;
import zlc.season.rxdownload3.core.Succeed;
import zlc.season.rxdownload3.core.Suspend;
import zlc.season.rxdownload3.core.Waiting;
import zlc.season.rxdownload3.extension.ApkInstallExtension;

/**
 * 下载测试类
 * Created by zhouL on 2018/4/12.
 */
public class DownloadTestActivity extends BaseActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, DownloadTestActivity.class);
        context.startActivity(starter);
    }


    private static final String WECHAT_URL = "http://p.gdown.baidu.com/fd3709584fae95ddc1250aa4f4faf971f38a1dd04bfe1bc7b59dafad5b116329585059d6a99f8304b57dd857f56a56e474eb006c8b61c14e2a12d03fadc715cf87f84d2df0e34dbdb86750aee2093491bc993a6b2bdaeb75";

    /** 下载进度 */
    @BindView(R.id.progress)
    TextView mProgressTv;
    /** 下载进度 */
    @BindView(R.id.download_wechat_btn)
    Button mDownloadWechatBtn;
    /** 暂停按钮 */
    @BindView(R.id.pause_btn)
    Button mPauseBtn;
    /** 删除按钮 */
    @BindView(R.id.delete_btn)
    Button mDeleteBtn;

    /** 应用市场按钮 */
    @BindView(R.id.market_btn)
    Button mMarketBtn;


    /** 微信下载编号 */
    private Mission mWechatMission;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_download_test_layout;
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

        mDownloadWechatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxDownload.INSTANCE.start(mWechatMission).subscribe();
            }
        });

        mPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxDownload.INSTANCE.stop(mWechatMission).subscribe();
            }
        });

        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxDownload.INSTANCE.delete(mWechatMission, true).subscribe();
            }
        });

        mMarketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadMarketActivity.start(getContext());
            }
        });
    }

    private void setActionText(Status status) {
        String text = "";
        if (status instanceof Normal) {// 开始下载
            text = "开始下载";
        } else if (status instanceof Suspend) {// 暂停
            text = "已暂停";
        } else if (status instanceof Waiting) {// 排队等待
            text = "等待中";
        } else if (status instanceof Downloading) {//下载中
            text = "下载进度：" + status.percent();
        } else if (status instanceof Failed) {//下载失败
            text = "下载失败";
        } else if (status instanceof Succeed) {//下载成功
            text = "下载完成";
        } else if (status instanceof ApkInstallExtension.Installing) {//安装中
            text = "安装中";
        } else if (status instanceof ApkInstallExtension.Installed) {//安装完成
            text = "打开";
        } else if (status instanceof Deleted) {//删除
            text = "已删除";
        }
        mProgressTv.setText(text);
    }

    @Override
    protected void initData() {
        super.initData();
        mProgressTv.setText("");

        mWechatMission = new Mission(WECHAT_URL, "微信.apk", FileManager.getDownloadFolderPath());
        Disposable disposable = RxDownload.INSTANCE.create(mWechatMission, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Status>() {
                    @Override
                    public void accept(Status status) throws Exception {
                        setActionText(status);
                    }
                });
        showStatusCompleted();
    }
}
