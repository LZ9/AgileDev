package com.lodz.android.agiledev.ui.rv.swipe;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.bean.SwipeMenuBean;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.component.widget.adapter.recycler.SimpleSwipeRVAdapter;
import com.lodz.android.component.widget.adapter.swipe.SwipeMenuLayout;
import com.lodz.android.component.widget.adapter.swipe.SwipeMenuRecyclerView;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RecyclerView侧滑菜单测试
 * Created by zhouL on 2017/3/6.
 */
public class SwipeRecyclerViewActivity extends BaseActivity{

    /** 侧滑布局 */
    @BindView(R.id.swipe_layout)
    SwipeMenuLayout mSwipeMenuLayout;

    /** 列表 */
    @BindView(R.id.recycler_view)
    SwipeMenuRecyclerView mRecyclerView;

//    private SwipeAdapter mAdapter;

    private SwipeSimpleAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_swip_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBar(getTitleBarLayout());
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new SwipeSimpleAdapter(getContext(), createLeftMenu(), createRightMenu());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<SwipeMenuBean> createLeftMenu() {
        List<SwipeMenuBean> list = new ArrayList<>();
        SwipeMenuBean bean1 = new SwipeMenuBean();
        bean1.id = 1;
        bean1.bgColor = R.color.color_ff4081;
        bean1.imgRes = R.drawable.ic_progress_loading_cutom_1;
        bean1.text = "新增一个页面";
        bean1.textColor = R.color.white;
        bean1.textSizeSp = 16;
        list.add(bean1);

        SwipeMenuBean bean2 = new SwipeMenuBean();
        bean2.id = 2;
        bean2.bgColor = R.color.color_d28928;
        bean2.imgRes = R.drawable.ic_progress_loading_cutom_3;
        list.add(bean2);

        SwipeMenuBean bean3 = new SwipeMenuBean();
        bean3.id = 3;
        bean3.bgColor = R.color.color_00a0e9;
        bean3.text = "删除";
        bean3.textColor = R.color.white;
        bean3.textSizeSp = 16;
        list.add(bean3);
        return list;
    }

    private List<SwipeMenuBean> createRightMenu() {
        List<SwipeMenuBean> list = new ArrayList<>();
        SwipeMenuBean bean1 = new SwipeMenuBean();
        bean1.id = 4;
        bean1.bgColor = R.color.color_ea413c;
        bean1.imgRes = R.drawable.ic_launcher;
        bean1.text = "排行";
        bean1.textColor = R.color.white;
        bean1.textSizeSp = 16;
        list.add(bean1);

        SwipeMenuBean bean2 = new SwipeMenuBean();
        bean2.id = 5;
        bean2.bgColor = R.color.color_ff00ff;
        bean2.imgRes = R.drawable.ic_progress_loading_cutom_5;
        list.add(bean2);

        SwipeMenuBean bean3 = new SwipeMenuBean();
        bean3.id = 6;
        bean3.bgColor = R.color.white;
        bean3.text = "详情";
        bean3.textColor = R.color.color_2f6dc9;
        bean3.textSizeSp = 16;
        list.add(bean3);
        return list;
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
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, String item, int position) {
                ToastUtils.showShort(getContext(), item);
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseRecyclerViewAdapter.OnItemLongClickListener<String>() {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, String item, int position) {
                ToastUtils.showShort(getContext(), item + " long");
            }
        });

        mAdapter.setOnMenuClickListener(new SimpleSwipeRVAdapter.OnMenuClickListener<String>() {
            @Override
            public void onMenuClick(String item, SwipeMenuBean menu, int position) {
                ToastUtils.showShort(getContext(), item + " Menu " + menu.id);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter.setData(getDatas());
        mAdapter.notifyDataSetChanged();
        showStatusCompleted();
    }

    private List<String> getDatas() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("测试+" + i);
        }
        return list;
    }

}
