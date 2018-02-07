package com.lodz.android.agiledev.ui.rv.decoration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.decoration.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RV装饰器测试类
 * Created by zhouL on 2018/2/7.
 */

public class ItemDecorationTestActivity extends BaseActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, ItemDecorationTestActivity.class);
        context.startActivity(starter);
    }

    /** 列表 */
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    /** 适配器 */
    private ItemDecorationTestAdapter mAdapter;

    private List<String> mList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_item_decoration_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        getTitleBarLayout().setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
        initRecyclerView();
    }

    private void initRecyclerView() {
//        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
//        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new ItemDecorationTestAdapter(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter.onAttachedToRecyclerView(mRecyclerView);// 如果使用网格布局请设置此方法

        mRecyclerView.addItemDecoration(getItemDecoration());
//        mRecyclerView.addItemDecoration(new TestItemDecoration(getContext()));
//        mRecyclerView.addItemDecoration(new TestItemDecoration(getContext(), new TestItemDecoration.DecorationCallback() {
//            @Override
//            public long getGroupId(int position) {
//                return Character.toUpperCase(mList.get(position).charAt(0));
//            }
//
//            @Override
//            public String getGroupFirstLine(int position) {
//                return mList.get(position).substring(0, 1).toUpperCase();
//            }
//        }));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private RecyclerView.ItemDecoration getItemDecoration() {
        DividerItemDecoration decoration = new DividerItemDecoration(getContext());
        decoration.setTopDivider(6, R.color.color_9a9a9a, 0, 0);
        decoration.setBottomDivider(6, R.color.color_9a9a9a, 0, 0);
        decoration.setLeftDivider(6, R.color.color_9a9a9a, 0, 0);
        decoration.setRightDivider(6, R.color.color_9a9a9a, 0, 0);
        return decoration;
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void setListeners() {
        super.setListeners();

    }

    @Override
    protected void initData() {
        super.initData();
        mList = getList();
        mAdapter.setData(mList);
        mAdapter.notifyDataSetChanged();
        showStatusCompleted();
    }

    private List<String> getList(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add((i + 1) + "");
        }
        return list;
    }
}
