package com.lodz.android.agiledev.ui.design.coordinator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.component.widget.contract.OnAppBarStateChangeListener;
import com.lodz.android.core.utils.DensityUtils;
import com.lodz.android.core.utils.SnackbarUtils;
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
    /** 右侧按钮 */
    @BindView(R.id.right_btn)
    ImageView mRightBtn;
    /** 浮动按钮 */
    @BindView(R.id.fa_btn)
    FloatingActionButton mFloatingActionBtn;

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

        // 右侧按钮
        mRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 浮动按钮
        mFloatingActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackbarUtils.createShort(mRecyclerView, "测试")
                        .setTextColor(R.color.white)
                        .addLeftImage(R.drawable.ic_launcher, DensityUtils.dp2px(getContext(), 5))
                        .getSnackbar()
                        .setActionTextColor(ContextCompat.getColor(getContext(), R.color.color_ea5e5e))
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();

//                Snackbar snackbar = SnackbarUtils.createShort(mRecyclerView, "123")
//                        .replaceLayoutView(R.layout.view_custom_snackbar_layout)
//                        .getSnackbar();
//                TextView textView = snackbar.getView().findViewById(R.id.content_txt);
//                textView.setText("自定义测试");
//                Button button = snackbar.getView().findViewById(R.id.btn);
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ToastUtils.showShort(getContext(), R.string.coordinator_collapsing);
//                    }
//                });
//                snackbar.show();
            }
        });

        // AppBarLayout偏移量的监听
        mAppBarLayout.addOnOffsetChangedListener(new OnAppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, int state, double delta) {
                if (state == OnAppBarStateChangeListener.EXPANDED){// 完全展开
                    mRightBtn.setVisibility(View.VISIBLE);
                    mRightBtn.setAlpha(1f);
                    mTitleBarLayout.setVisibility(View.GONE);
                } else if (state == OnAppBarStateChangeListener.COLLAPSED){// 完全折叠
                    mRightBtn.setVisibility(View.GONE);
                    mTitleBarLayout.setVisibility(View.VISIBLE);
                    mTitleBarLayout.setAlpha(1f);
                }else {// 滑动中
                    mRightBtn.setAlpha((float) (1f - delta));
                    mTitleBarLayout.setAlpha((float) delta);
                    mRightBtn.setVisibility(View.VISIBLE);
                    mTitleBarLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        // 修复底部RV无法展示所有item的问题
//        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (state == OnAppBarStateChangeListener.EXPANDED) {//张开
//                    mTitleBarLayout.setVisibility(View.GONE);
//                    mTitleBarLayout.setVisibility(View.VISIBLE);
//                }else if (state == OnAppBarStateChangeListener.COLLAPSED) {//收缩
//                    mTitleBarLayout.setVisibility(View.GONE);
//                    mTitleBarLayout.setVisibility(View.VISIBLE);
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
