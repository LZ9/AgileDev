package com.lodz.android.agiledev.ui.index;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.bean.NationBean;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.decoration.SectionItemDecoration;
import com.lodz.android.component.widget.adapter.decoration.StickyItemDecoration;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.core.utils.ArrayUtils;
import com.lodz.android.core.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 索引栏测试类
 * Created by zhouL on 2018/6/1.
 */
public class IndexBarTestActivity extends BaseActivity{

    /** 列表 */
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private NationAdapter mAdapter;

    private List<NationBean> mList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index_bar_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        getTitleBarLayout().setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new NationAdapter(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter.onAttachedToRecyclerView(mRecyclerView);// 如果使用网格布局请设置此方法
        mRecyclerView.addItemDecoration(getItemDecoration());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private RecyclerView.ItemDecoration getItemDecoration() {
        return StickyItemDecoration.<String>create(getContext())
                .setOnSectionCallback(new SectionItemDecoration.OnSectionCallback<String>() {
                    @Override
                    public String getSourceItem(int position) {
                        return mList.get(position).getSortStr();
                    }
                })
                .setSectionTextSize(18)
                .setSectionHeight(36)
                .setSectionTextTypeface(Typeface.DEFAULT_BOLD)
                .setSectionTextColorRes(R.color.color_999999)
                .setSectionTextPaddingLeftDp(8)
                .setSectionBgColorRes(R.color.color_d9d9d9);
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<NationBean>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, NationBean item, int position) {
                ToastUtils.showShort(getContext(), item.getTitle());
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mList = getNations();
        mAdapter.setData(mList);
        mAdapter.notifyDataSetChanged();
        showStatusCompleted();
    }

    private List<NationBean> getNations() {
        List<NationBean> list = NationDataFactory.getNations();
        return ArrayUtils.groupList(list, NationDataFactory.getIndexTitle());
    }
}
