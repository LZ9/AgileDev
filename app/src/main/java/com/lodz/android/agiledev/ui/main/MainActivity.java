package com.lodz.android.agiledev.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lodz.android.agiledev.App;
import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.config.ConfigLayoutActivity;
import com.lodz.android.agiledev.ui.crash.CrashTestActivity;
import com.lodz.android.agiledev.ui.dialog.DialogTestActivity;
import com.lodz.android.agiledev.ui.location.LocationActivity;
import com.lodz.android.agiledev.ui.media.RecordActivity;
import com.lodz.android.agiledev.ui.notification.NotificationActivity;
import com.lodz.android.agiledev.ui.photopicker.PhotoPickerTestActivity;
import com.lodz.android.agiledev.ui.retrofit.RetrofitTestActivity;
import com.lodz.android.agiledev.ui.rv.anim.AnimRecyclerViewActivity;
import com.lodz.android.agiledev.ui.rv.decoration.ItemDecorationTestActivity;
import com.lodz.android.agiledev.ui.rv.drag.DragRecyclerViewActivity;
import com.lodz.android.agiledev.ui.rv.head.HeadRecyclerViewActivity;
import com.lodz.android.agiledev.ui.rv.refresh.RefreshTestActivity;
import com.lodz.android.agiledev.ui.rv.swipe.SwipeRecyclerViewActivity;
import com.lodz.android.agiledev.ui.rxjava.RxTestActivity;
import com.lodz.android.agiledev.ui.threadpool.ThreadPoolActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.DensityUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页面
 * Created by zhouL on 2017/8/30.
 */

public class MainActivity extends BaseActivity{

    /**
     * 启动
     * @param context 上下文
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    /** 功能名称 */
    private static final List<String> mNameList = Arrays.asList("弹框测试", "视频录制测试", "RV拖拽测试",
            "RV动画测试", "带头/底部RV测试", "刷新/加载更多测试", "RV侧滑菜单测试",
            "崩溃测试", "基础控件配置", "照片选择器测试", "定位测试",
            "通知测试", "Retrofit测试", "RV装饰器测试类", "线程池测试类",
            "Rxjava测试类");
    /** 功能的activity */
    private static final Class<?>[] mClassList = {DialogTestActivity.class, RecordActivity.class, DragRecyclerViewActivity.class,
            AnimRecyclerViewActivity.class, HeadRecyclerViewActivity.class, RefreshTestActivity.class, SwipeRecyclerViewActivity.class,
            CrashTestActivity.class, ConfigLayoutActivity.class, PhotoPickerTestActivity.class, LocationActivity.class,
            NotificationActivity.class, RetrofitTestActivity.class, ItemDecorationTestActivity.class, ThreadPoolActivity.class,
            RxTestActivity.class};

    /** 标题名称 */
    public static final String EXTRA_TITLE_NAME = "extra_title_name";

    /** 列表 */
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private MainAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBar(getTitleBarLayout());
        initRecyclerView();
    }

    private void initTitleBar(TitleBarLayout titleBarLayout) {
        titleBarLayout.setTitleName(R.string.main_title);
        TextView refreshBtn = new TextView(getContext());
        refreshBtn.setText(R.string.main_change_mood);
        refreshBtn.setPadding(DensityUtils.dp2px(getContext(), 15), 0 , DensityUtils.dp2px(getContext(), 15), 0);
        refreshBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.notifyDataSetChanged();
            }
        });
        titleBarLayout.addExpandView(refreshBtn);
        titleBarLayout.needBackButton(false);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new MainAdapter(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean onPressBack() {
        App.get().exit();
        return true;
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, String item, int position) {
                Intent intent = new Intent(getContext(), mClassList[position]);
                intent.putExtra(EXTRA_TITLE_NAME, item);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter.setData(mNameList);
        showStatusCompleted();
    }

}
