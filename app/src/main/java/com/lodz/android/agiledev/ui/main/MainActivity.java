package com.lodz.android.agiledev.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lodz.android.agiledev.App;
import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.dialog.DialogTestActivity;
import com.lodz.android.agiledev.ui.drag.DragRecyclerViewActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.component.widget.base.TitleBarLayout;

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
    private static final List<String> mNameList = Arrays.asList("弹框测试", "RecyclerView拖拽测试");
    /** 功能的activity */
    private static final Class<?>[] mClassList = {DialogTestActivity.class, DragRecyclerViewActivity.class};

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
    protected void clickBackBtn() {
        super.clickBackBtn();
        App.get().exit();
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
                startActivity(new Intent(getContext(), mClassList[position]));
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
