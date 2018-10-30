package com.lodz.android.agiledev.ui.share;

import android.os.Bundle;
import android.view.View;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.bean.ShareAnimBean;
import com.lodz.android.agiledev.config.Constant;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.agiledev.ui.share.element.ElementActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 共享元素动画
 * Created by zhouL on 2018/5/24.
 */
public class ShareAnimationActivity extends BaseActivity{

    private static final int[] IMGS = new int[]{
            R.drawable.ic_progress_loading_cutom_1,
            R.drawable.ic_progress_loading_cutom_3,
            R.drawable.ic_progress_loading_cutom_5,
            R.drawable.ic_progress_loading_cutom_1,
            R.drawable.ic_progress_loading_cutom_3,
            R.drawable.ic_progress_loading_cutom_5,
            R.drawable.ic_progress_loading_cutom_1,
            R.drawable.ic_progress_loading_cutom_3,
            R.drawable.ic_progress_loading_cutom_5};

    private static final String[] TITLES = new String[]{"测试1", "测试2", "测试3", "测试1", "测试2", "测试3", "测试1", "测试2", "测试3"};


    /** 列表 */
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private ShareAnimAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_animation_layout;
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
        mAdapter = new ShareAnimAdapter(getContext());
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
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<ShareAnimBean>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, ShareAnimBean item, int position) {
                List<Pair<View, String>> sharedElements = new ArrayList<>();
                sharedElements.add(Pair.<View, String>create(((ShareAnimAdapter.DataViewHolder)viewHolder).img, Constant.ShareElementName.IMG));
                sharedElements.add(Pair.<View, String>create(((ShareAnimAdapter.DataViewHolder)viewHolder).titleTv, Constant.ShareElementName.TITLE));
                ElementActivity.start(ShareAnimationActivity.this, item.imgRes, item.title, sharedElements);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter.setData(getData());
        showStatusCompleted();
    }

    private List<ShareAnimBean> getData() {
        List<ShareAnimBean> list = new ArrayList<>();
        for (int i = 0; i < IMGS.length; i++) {
            ShareAnimBean bean = new ShareAnimBean();
            bean.imgRes = IMGS[i];
            bean.title = TITLES[i];
            list.add(bean);
        }
        return list;
    }


}
