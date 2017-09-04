package com.lodz.android.agiledev.ui.swipe;

import android.os.Bundle;

import com.lodz.android.component.base.activity.BaseActivity;

/**
 * RecyclerView划动测试类
 * Created by zhouL on 2017/3/6.
 */
public class SwipeRecyclerViewActivity extends BaseActivity{
    @Override
    protected void findViews(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

//    public static void start(Context context) {
//        Intent starter = new Intent(context, SwipeRecyclerViewActivity.class);
//        context.startActivity(starter);
//    }
//
//    private RecyclerView mRecyclerView;
//
//    private DragAdapter mAdapter;
//
//    private List<String> mList;
//
//    private RecyclerViewDragHelper<String> mRecyclerViewDragHelper;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_drag_recycler_view_layout;
//    }
//
//    @Override
//    protected void findViews(Bundle savedInstanceState) {
//        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        initRecyclerView();
//    }
//
//    /** 初始化RecyclerView */
//    private void initRecyclerView() {
//        mAdapter = new DragAdapter(getContext());
//        mRecyclerView.setLayoutManager(getLayoutManager());
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerViewDragHelper = new RecyclerViewDragHelper<>();
//        mRecyclerViewDragHelper
//                .setUseDrag(true)// 设置是否允许拖拽
//                .setUseLeftToRightSwipe(false)// 设置允许从左往右滑动
//                .setUseRightToLeftSwipe(false)// 设置允许从右往左滑动
//                .setEnabled(true)// 是否启用
//                .build(mRecyclerView, mAdapter);
//    }
//
//    private RecyclerView.LayoutManager getLayoutManager() {
////        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
////        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
//        layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
////        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
//        return layoutManager;
//    }
//
//    @Override
//    protected void setListeners() {
//        super.setListeners();
//
//        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<String>() {
//            @Override
//            public void onItemClick(RecyclerView.ViewHolder viewHolder, String item, int position) {
//                PrintLog.e("testtag", item);
//            }
//        });
//
//        mAdapter.setOnItemLongClickListener(new BaseRecyclerViewAdapter.OnItemLongClickListener<String>() {
//            @Override
//            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, String item, int position) {
//                VibratorUtil.vibrate(getContext(), 100);
//            }
//        });
//
//        mRecyclerViewDragHelper.setListener(new RecyclerViewDragHelper.Listener<String>() {
//            @Override
//            public void onListChanged(List<String> list) {
//                mList = list;
//                PrintLog.d("testtag", mList.toString());
//            }
//        });
//    }
//
//    @Override
//    protected void initData() {
//        super.initData();
//        mList = createList();
//        mAdapter.setData(mList);
//        mRecyclerViewDragHelper.setList(mList);
//        mAdapter.notifyDataSetChanged();
//        showStatusCompleted();
//    }
//
//    private List<String> createList() {
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < 120; i++) {
//            list.add(i+"");
//        }
//        return list;
//    }
}
