package com.lodz.android.agiledev.ui.download.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.bean.AppInfoBean;
import com.lodz.android.agiledev.ui.download.DownloadConstant;
import com.lodz.android.agiledev.ui.download.market.MarketAdapter;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.rx.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import zlc.season.rxdownload3.RxDownload;
import zlc.season.rxdownload3.core.Mission;

/**
 * 下载管理
 * Created by zhouL on 2018/4/13.
 */
public class DownloadManagerActivity extends BaseActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, DownloadManagerActivity.class);
        context.startActivity(starter);
    }

    /** 全部开始 */
    @BindView(R.id.start_all_btn)
    Button mStartAllBtn;
    /** 全部暂停 */
    @BindView(R.id.pause_all_btn)
    Button mPauseAllBtn;
    /** 全部删除 */
    @BindView(R.id.delete_all_btn)
    Button mDeleteAllBtn;

    /** 应用市场列表 */
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    /** 应用市场列表适配器 */
    private MarketAdapter mAdapter;

    /** 应用列表 */
    private List<AppInfoBean> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_download_manager_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        getTitleBarLayout().setTitleName(R.string.download_manager_title);
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mAdapter = new MarketAdapter(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        // 全部开始
        mStartAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (AppInfoBean appInfoBean : mList) {
                    RxDownload.INSTANCE.start(appInfoBean.mission).subscribe();
                }
            }
        });

        // 全部暂停
        mPauseAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (AppInfoBean appInfoBean : mList) {
                    RxDownload.INSTANCE.stop(appInfoBean.mission).subscribe();
                }
            }
        });

        // 全部删除
        mDeleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (AppInfoBean appInfoBean : mList) {
                    RxDownload.INSTANCE.delete(appInfoBean.mission, true).subscribe();
                }
                mList.clear();
                mAdapter.notifyDataSetChanged();
            }
        });

        // 应用市场列表适配器
        mAdapter.setListener(new MarketAdapter.Listener() {
            @Override
            public void onClickDownload(AppInfoBean bean, int position) {
                RxDownload.INSTANCE.start(bean.mission).subscribe();
            }

            @Override
            public void onClickPause(AppInfoBean bean, int position) {
                RxDownload.INSTANCE.stop(bean.mission).subscribe();
            }

            @Override
            public void onClickDelete(AppInfoBean bean, int position) {
                RxDownload.INSTANCE.delete(bean.mission, true).subscribe();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        updateMissionList();
        showStatusCompleted();
    }

    private void updateMissionList() {
        Disposable disposable = RxDownload.INSTANCE.getAllMission()
                .compose(RxUtils.<List<Mission>>ioToMainMaybe())
                .subscribe(new Consumer<List<Mission>>() {
                    @Override
                    public void accept(List<Mission> missions) throws Exception {
                        for (Mission mission : missions) {
                            for (AppInfoBean appInfoBean : DownloadConstant.getMarketApps()) {
                                if (appInfoBean.mission.getUrl().equals(mission.getUrl())){
                                    mList.add(appInfoBean);
                                }
                            }
                        }
                        createDownloadMission(mList);
                        mAdapter.setData(mList);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    /** 创建下载任务 */
    private void createDownloadMission(List<AppInfoBean> list) {
        List<Mission> missions = new ArrayList<>();
        for (AppInfoBean appInfoBean : list) {
            missions.add(appInfoBean.mission);
        }
        RxDownload.INSTANCE.createAll(missions, false).subscribe();
    }

}
