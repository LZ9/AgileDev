package com.lodz.android.agiledev.ui.design.cardview;

import android.os.Bundle;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.core.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * CardView测试类
 * Created by zhouL on 2018/4/25.
 */
public class CardViewTestActivity extends BaseActivity{

    /** 列表 */
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    /** 适配器 */
    private CardViewAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_view_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        getTitleBarLayout().setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mAdapter = new CardViewAdapter(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOpenItemAnim(true);
        mAdapter.setAnimationType(BaseRecyclerViewAdapter.SCALE_IN);
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
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter.setData(getDataList());
        mAdapter.notifyDataSetChanged();
        showStatusCompleted();
    }

    private List<String> getDataList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("测试数据 " + i);
        }
        return list;
    }
}
