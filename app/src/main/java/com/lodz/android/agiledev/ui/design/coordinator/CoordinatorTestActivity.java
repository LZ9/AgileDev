package com.lodz.android.agiledev.ui.design.coordinator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * CoordinatorLayout测试类
 * Created by zhouL on 2018/3/23.
 */

public class CoordinatorTestActivity extends AbsActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, CoordinatorTestActivity.class);
        context.startActivity(starter);
    }

    /** AppBarLayout */
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    /** Toolbar */
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    /** 标题栏 */
    @BindView(R.id.title_bar_layout)
    TitleBarLayout mTitleBarLayout;
    /** 数据列表 */
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private SimpleDataAdapter mAdapter;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_coordinator_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mTitleBarLayout.setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
        initRecyclerView();
        StatusBarUtil.setTranslucentForImageView(this, Color.TRANSPARENT, mToolbar);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new SimpleDataAdapter(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void setListeners() {
        super.setListeners();

        // 标题栏
        mTitleBarLayout.setOnBackBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { //barLayout偏移量的监听
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (verticalOffset == 0) {
//                    //张开
//                    mDetailMaxTitleTextView.setVisibility(View.VISIBLE);
//                    mDetailMinTitleTextView.setVisibility(View.INVISIBLE);
//                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
//                    //收缩
//                    mDetailMaxTitleTextView.setVisibility(View.GONE);
//                    mDetailMinTitleTextView.setVisibility(View.VISIBLE);
//                } else {
//                    double percent = (double) Math.abs(verticalOffset) / (double) appBarLayout.getTotalScrollRange();
//                    mDetailMaxTitleTextView.setAlpha((float) (1f - percent));
//                    mDetailMinTitleTextView.setAlpha((float) percent);
//                    mDetailMaxTitleTextView.setVisibility(View.VISIBLE);
//                    mDetailMinTitleTextView.setVisibility(View.VISIBLE);
//                }
//            }
//        });

    }

    @Override
    protected void initData() {
        super.initData();
        initListData();
    }

    private void initListData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add((i + 1) + "");
        }
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }
}
