package com.lodz.android.agiledev.ui.rv.refresh;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.agiledev.ui.rv.drag.LayoutManagerPopupWindow;
import com.lodz.android.component.base.activity.BaseRefreshActivity;
import com.lodz.android.component.rx.subscribe.observer.BaseObserver;
import com.lodz.android.component.rx.utils.RxUtils;
import com.lodz.android.component.widget.adapter.recycler.BaseLoadMoreRVAdapter;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.component.widget.adapter.recycler.RecyclerLoadMoreHelper;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


/**
 * 刷新/加载更多测试
 * Created by zhouL on 2017/2/28.
 */
public class RefreshTestActivity extends BaseRefreshActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, RefreshTestActivity.class);
        context.startActivity(starter);
    }

    /** 每页数量 */
    private static final int PAGE_SIZE = 20;
    /** 最大数量 */
    private static final int MAX_SIZE = 120;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    /** 加载失败开关 */
    @BindView(R.id.load_fail_switch)
    Switch mLoadFailSwitch;
    /** 布局按钮 */
    @BindView(R.id.layout_manager_btn)
    TextView mLayoutManagerBtn;

    /** 适配器 */
    private RefreshAdapter mAdapter;
    /** 加载更多帮助类 */
    private RecyclerLoadMoreHelper<String> mLoadMoreHelper;
    /** 数据列表 */
    private List<String> mList;
    /** 数据模块 */
    private DataModule mDataModule;

    /** 是否加载失败 */
    private boolean isLoadFail;

    /** 当前布局 */
    @LayoutManagerPopupWindow.LayoutManagerType
    private int mCurrentLayoutManagerType = LayoutManagerPopupWindow.TYPE_LINEAR;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refresh_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBar(getTitleBarLayout());
        initRecyclerView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mLayoutManagerBtn.setElevation(12f);
        }
    }

    /** 初始化标题栏 */
    private void initTitleBar(TitleBarLayout titleBarLayout) {
        titleBarLayout.setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
    }

    private void initRecyclerView() {
        mAdapter = new RefreshAdapter(getContext());
        mRecyclerView.setLayoutManager(getLayoutManager());
        mAdapter.onAttachedToRecyclerView(mRecyclerView);// 如果使用网格布局请设置此方法
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mLoadMoreHelper = new RecyclerLoadMoreHelper<>();
        mLoadMoreHelper.init(mAdapter);
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        if (mCurrentLayoutManagerType == LayoutManagerPopupWindow.TYPE_GRID){
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
            layoutManager.setOrientation(GridLayoutManager.VERTICAL);
            return layoutManager;
        }

        if (mCurrentLayoutManagerType == LayoutManagerPopupWindow.TYPE_STAGGERED){
            return new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }

    @Override
    protected void clickReload() {
        super.clickReload();
        showStatusLoading();
        requestData(1).subscribe(new BaseObserver<List<String>>() {
            @Override
            public void onBaseSubscribe(Disposable d) {

            }

            @Override
            public void onBaseNext(List<String> strings) {
                if (isLoadFail){
                    showStatusError();
                    return;
                }
                showStatusNoData();
            }

            @Override
            public void onBaseError(Throwable e) {

            }

            @Override
            public void onBaseComplete() {

            }
        });
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mLoadMoreHelper.setListener(new RecyclerLoadMoreHelper.Listener() {
            @Override
            public void onLoadMore(int currentPage, int nextPage, int size, int position) {
                requestData(nextPage).subscribe(new BaseObserver<List<String>>() {
                    @Override
                    public void onBaseSubscribe(Disposable d) {

                    }

                    @Override
                    public void onBaseNext(List<String> list) {
                        if (isLoadFail){
                            mLoadMoreHelper.loadMoreFail();
                            return;
                        }
                        mList.addAll(list);
                        mLoadMoreHelper.loadMoreSuccess(mList);
                    }

                    @Override
                    public void onBaseError(Throwable e) {

                    }

                    @Override
                    public void onBaseComplete() {

                    }
                });
            }

            @Override
            public void onClickLoadFail(int reloadPage, int size) {
                requestData(reloadPage).subscribe(new BaseObserver<List<String>>() {
                    @Override
                    public void onBaseSubscribe(Disposable d) {

                    }

                    @Override
                    public void onBaseNext(List<String> list) {
                        if (isLoadFail){
                            mLoadMoreHelper.loadMoreFail();
                            return;
                        }
                        mList.addAll(list);
                        mLoadMoreHelper.loadMoreSuccess(mList);
                    }

                    @Override
                    public void onBaseError(Throwable e) {

                    }

                    @Override
                    public void onBaseComplete() {

                    }
                });
            }
        });

        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, String item, int position) {
                ToastUtils.showShort(getContext(), item);
            }
        });

        mAdapter.setListener(new RefreshAdapter.Listener() {
            @Override
            public void onClickDelete(int position) {
                mLoadMoreHelper.hideItem(position);
            }
        });

        mAdapter.setOnAllItemHideListener(new BaseLoadMoreRVAdapter.OnAllItemHideListener() {
            @Override
            public void onAllItemHide() {
                showStatusNoData();
            }
        });

        mLoadFailSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isLoadFail = isChecked;
            }
        });

        mLayoutManagerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLayoutManagerPopupWindow(v);
            }
        });
    }

    @Override
    protected void onDataRefresh() {
        requestData(1).subscribe(new BaseObserver<List<String>>() {
            @Override
            public void onBaseSubscribe(Disposable d) {

            }

            @Override
            public void onBaseNext(List<String> list) {
                setSwipeRefreshFinish();
                if (isLoadFail) {
                    ToastUtils.showShort(getContext(), R.string.rvrefresh_refresh_fail);
                    return;
                }
                mList.clear();
                mList.addAll(list);
                mLoadMoreHelper.config(mList, MAX_SIZE, PAGE_SIZE, true, 0);
                showStatusCompleted();
            }

            @Override
            public void onBaseError(Throwable e) {
                setSwipeRefreshFinish();

            }

            @Override
            public void onBaseComplete() {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mDataModule = new DataModule();
        mList = new ArrayList<>();
        requestFirstData();
    }

    private void requestFirstData() {
        requestData(1).subscribe(new BaseObserver<List<String>>() {
            @Override
            public void onBaseSubscribe(Disposable d) {

            }

            @Override
            public void onBaseNext(List<String> list) {
                if (isLoadFail) {
                    showStatusError();
                    return;
                }
                mList.clear();
                mList.addAll(list);
                mLoadMoreHelper.config(mList, MAX_SIZE, PAGE_SIZE, true, 0);
                showStatusCompleted();
            }

            @Override
            public void onBaseError(Throwable e) {

            }

            @Override
            public void onBaseComplete() {

            }
        });
    }

    private Observable<List<String>> requestData(int page) {
        return mDataModule.requestData(page).compose(RxUtils.<List<String>>io_main());
    }

    /** 显示布局的PopupWindow */
    private void showLayoutManagerPopupWindow(View view) {
        LayoutManagerPopupWindow popupWindow = new LayoutManagerPopupWindow(getContext());
        popupWindow.setLayoutManagerType(mCurrentLayoutManagerType);
        popupWindow.getPopup().showAsDropDown(view, -45, 20);
        popupWindow.setListener(new LayoutManagerPopupWindow.Listener() {
            @Override
            public void onClick(PopupWindow popupWindow, @LayoutManagerPopupWindow.LayoutManagerType int type) {
                mCurrentLayoutManagerType = type;
                mRecyclerView.setLayoutManager(getLayoutManager());
                mAdapter.onAttachedToRecyclerView(mRecyclerView);
                mAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });
    }

}
