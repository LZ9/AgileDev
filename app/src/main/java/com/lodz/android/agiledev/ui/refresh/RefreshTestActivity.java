package com.lodz.android.agiledev.ui.refresh;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.BaseRefreshActivity;
import com.lodz.android.component.widget.adapter.recycler.BaseLoadMoreRecyclerViewAdapter;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.component.widget.adapter.recycler.RecyclerLoadMoreHelper;
import com.lodz.android.core.utils.ToastUtils;
import com.lodz.android.core.utils.UiHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * 刷新测试
 * Created by zhouL on 2017/2/28.
 */
public class RefreshTestActivity extends BaseRefreshActivity {

    private RecyclerView mRecyclerView;

    private RefreshAdapter mAdapter;

    private RecyclerLoadMoreHelper<String> mLoadMoreHelper;

    private List<String> mList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refresh_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
//        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new RefreshAdapter(getContext());
        mAdapter.setOpenItemAnim(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter.onAttachedToRecyclerView(mRecyclerView);// 如果使用网格布局请设置此方法
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mLoadMoreHelper = new RecyclerLoadMoreHelper<>();
        mLoadMoreHelper.init(mAdapter);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mLoadMoreHelper.setListener(new RecyclerLoadMoreHelper.Listener() {
            @Override
            public void onLoadMore(int currentPage, int nextPage, int size, int position) {
//                UiHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mLoadMoreHelper.loadMoreFail();
//                    }
//                }, 3000);
                UiHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mList.addAll(getList());
                        mLoadMoreHelper.loadMoreSuccess(mList);
//                        mLoadMoreHelper.loadComplete();
                    }
                }, 2000);
            }

            @Override
            public void onClickLoadFail(int reloadPage, int size) {

                UiHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mList.addAll(getList());
                        mLoadMoreHelper.loadMoreSuccess(mList);
                    }
                }, 2000);
            }
        });

        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, String item, int position) {
                ToastUtils.showShort(getContext(), position+" : " + item);
            }
        });

        mAdapter.setListener(new RefreshAdapter.Listener() {
            @Override
            public void onClickDelete(int position) {
//                ToastUtils.showShort(getContext(), "delete : " + position);
                mLoadMoreHelper.hideItem(position);
//                mAdapter.notifyItemRemovedChanged(position);
            }
        });

        mAdapter.setOnAllItemHideListener(new BaseLoadMoreRecyclerViewAdapter.OnAllItemHideListener() {
            @Override
            public void onAllItemHide() {
                showStatusNoData();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mList = new ArrayList<>();
        UiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                requestData();
            }
        }, 1000);
    }

    private void requestData() {
        mList.clear();
        mList.addAll(getList());
        mLoadMoreHelper.config(mList, 120, 40, true, 0);
        showStatusCompleted();
    }

    @Override
    protected void onDataRefresh() {
        UiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                requestData();
                setSwipeRefreshFinish();
                showStatusCompleted();
            }
        }, 3000);
    }

    private List<String> getList(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            String str = System.currentTimeMillis() + "_" + i;
            list.add(str);
        }
        return list;
    }

    @Override
    protected void clickReload() {
        super.clickReload();
        showStatusLoading();
        UiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showStatusNoData();
            }
        }, 3000);
    }
}
