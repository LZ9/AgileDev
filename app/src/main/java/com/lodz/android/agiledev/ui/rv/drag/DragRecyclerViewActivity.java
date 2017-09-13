package com.lodz.android.agiledev.ui.rv.drag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.component.widget.adapter.recycler.RecyclerViewDragHelper;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.log.PrintLog;
import com.lodz.android.core.utils.DensityUtils;
import com.lodz.android.core.utils.ToastUtils;
import com.lodz.android.core.utils.VibratorUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RV拖拽测试
 * Created by zhouL on 2017/3/6.
 */
public class DragRecyclerViewActivity extends BaseActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, DragRecyclerViewActivity.class);
        context.startActivity(starter);
    }

    /** 列表 */
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    /** 适配器 */
    private DragAdapter mAdapter;
    /** 拖拽帮助类 */
    private RecyclerViewDragHelper<String> mRecyclerViewDragHelper;
    /** 数据列表 */
    private List<String> mList;

    /** 当前布局 */
    @LayoutManagerPopupWindow.LayoutManagerType
    private int mCurrentLayoutManagerType = LayoutManagerPopupWindow.TYPE_GRID;
    /** 当前是否纵向 */
    private boolean mIsVertical = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_drag_recycler_view_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBar(getTitleBarLayout());
        initRecyclerView();
    }

    /** 初始化标题栏 */
    private void initTitleBar(TitleBarLayout titleBarLayout) {
        titleBarLayout.setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
        titleBarLayout.needExpandView(true);
        titleBarLayout.addExpandView(getExpandView());
    }

    /** 初始化RecyclerView */
    private void initRecyclerView() {
        mAdapter = new DragAdapter(getContext());
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerViewDragHelper = new RecyclerViewDragHelper<>();
        mRecyclerViewDragHelper
                .setUseDrag(true)// 设置是否允许拖拽
                .setLongPressDragEnabled(true)// 是否启用长按拖拽效果
                .setUseLeftToRightSwipe(true)// 设置允许从左往右滑动
                .setUseRightToLeftSwipe(true)// 设置允许从右往左滑动
                .setSwipeEnabled(false)// 设置是否允许滑动
                .build(mRecyclerView, mAdapter);
//        mAdapter.setItemTouchHelper(mRecyclerViewDragHelper.getItemTouchHelper());//由适配器来控制何时进行拖动
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        if (mCurrentLayoutManagerType == LayoutManagerPopupWindow.TYPE_GRID){
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
            layoutManager.setOrientation(mIsVertical ? GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL);
            return layoutManager;
        }

        if (mCurrentLayoutManagerType == LayoutManagerPopupWindow.TYPE_STAGGERED){
            return new StaggeredGridLayoutManager(4, mIsVertical ? StaggeredGridLayoutManager.VERTICAL : StaggeredGridLayoutManager.HORIZONTAL);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(mIsVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        return layoutManager;
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
                VibratorUtil.vibrate(getContext(), 100);//长按震动
            }
        });

        mRecyclerViewDragHelper.setListener(new RecyclerViewDragHelper.Listener<String>() {
            @Override
            public void onListChanged(List<String> list) {
                mList = list;
                PrintLog.d("testtag", mList.toString());
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mList = createList();
        mAdapter.setData(mList);
        mRecyclerViewDragHelper.setList(mList);
        mAdapter.notifyDataSetChanged();
        showStatusCompleted();
    }

    private List<String> createList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 120; i++) {
            list.add(i+"");
        }
        return list;
    }

    /** 获取扩展view */
    private View getExpandView() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        final TextView orientationTv = getTextView(R.string.drag_orientation);//方向
        orientationTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrientationPopupWindow(v);
            }
        });
        linearLayout.addView(orientationTv, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final TextView LayoutManagerTv = getTextView(R.string.drag_layout_manager);// 布局
        LayoutManagerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLayoutManagerPopupWindow(v);
            }
        });
        linearLayout.addView(LayoutManagerTv, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return linearLayout;
    }

    /** 获取TextView */
    private TextView getTextView(@StringRes int resId) {
        final TextView textView = new TextView(getContext());
        textView.setText(resId);
        textView.setPadding(DensityUtils.dp2px(getContext(), 6), 0 , DensityUtils.dp2px(getContext(), 6), 0);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        return textView;
    }

    /** 显示方向的PopupWindow */
    private void showOrientationPopupWindow(View view) {
        OrientationPopupWindow popupWindow = new OrientationPopupWindow(getContext());
        popupWindow.setIsVertical(mIsVertical);
        popupWindow.getPopup().showAsDropDown(view, -50, 20);
        popupWindow.setListener(new OrientationPopupWindow.Listener() {
            @Override
            public void onClick(PopupWindow popupWindow, boolean isVertical) {
                mIsVertical = isVertical;
                mRecyclerView.setLayoutManager(getLayoutManager());
                mAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });
    }

    /** 显示布局的PopupWindow */
    private void showLayoutManagerPopupWindow(View view) {
        LayoutManagerPopupWindow popupWindow = new LayoutManagerPopupWindow(getContext());
        popupWindow.setLayoutManagerType(mCurrentLayoutManagerType);
        popupWindow.getPopup().showAsDropDown(view, 0, 20);
        popupWindow.setListener(new LayoutManagerPopupWindow.Listener() {
            @Override
            public void onClick(PopupWindow popupWindow, @LayoutManagerPopupWindow.LayoutManagerType int type) {
                mCurrentLayoutManagerType = type;
                mRecyclerView.setLayoutManager(getLayoutManager());
                mAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });
    }
}
