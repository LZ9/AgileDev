package com.lodz.android.agiledev.ui.download.market;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.bean.AppInfoBean;
import com.lodz.android.agiledev.ui.download.DownloadConstant;
import com.lodz.android.agiledev.ui.download.manager.DownloadManagerActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zlc.season.rxdownload3.RxDownload;
import zlc.season.rxdownload3.core.Mission;

/**
 * 应用市场下载类
 * Created by zhouL on 2018/4/12.
 */
public class DownloadMarketActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, DownloadMarketActivity.class);
        context.startActivity(starter);
    }

    /** 应用市场列表 */
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    /** 应用市场列表适配器 */
    private MarketAdapter mAdapter;

    /** 应用列表 */
    private List<AppInfoBean> mList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_download_market_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBarLayout(getTitleBarLayout());
        initRecyclerView();
    }

    private void initTitleBarLayout(TitleBarLayout titleBarLayout) {
        titleBarLayout.setTitleName(R.string.market_title);
        titleBarLayout.needExpandView(true);
        titleBarLayout.addExpandView(getExpandView());
    }

    /** 获取扩展view */
    private View getExpandView() {
        TextView textView = new TextView(getContext());
        textView.setText(R.string.download_manager_title);
        textView.setPadding(DensityUtils.dp2px(getContext(), 15), 0 , DensityUtils.dp2px(getContext(), 15), 0);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        return textView;
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
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

        getTitleBarLayout().getExpandView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManagerActivity.start(getContext());
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mList = DownloadConstant.getMarketApps();
        createDownloadMission(mList);
        mAdapter.setData(mList);
        mAdapter.notifyDataSetChanged();
        showStatusCompleted();
    }

    /** 创建下载任务 */
    private void createDownloadMission(List<AppInfoBean> list) {
        List<Mission> missions = new ArrayList<>();
        for (AppInfoBean appInfoBean : list) {
            missions.add(appInfoBean.mission);
        }
        RxDownload.INSTANCE.createAll(missions).subscribe();
    }
}
