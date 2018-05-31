package com.lodz.android.agiledev.ui.rv.decoration;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import com.lodz.android.agiledev.ui.rv.drag.LayoutManagerPopupWindow;
import com.lodz.android.agiledev.ui.rv.drag.OrientationPopupWindow;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.decoration.SectionItemDecoration;
import com.lodz.android.component.widget.adapter.decoration.StickyItemDecoration;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.ArrayUtils;
import com.lodz.android.core.utils.DensityUtils;

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

    /** 当前布局 */
    @LayoutManagerPopupWindow.LayoutManagerType
    private int mCurrentLayoutManagerType = LayoutManagerPopupWindow.TYPE_LINEAR;
    /** 当前是否纵向 */
    private boolean mIsVertical = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_item_decoration_test_layout;
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

    private void initRecyclerView() {
        mAdapter = new ItemDecorationTestAdapter(getContext());
        mRecyclerView.setLayoutManager(getLayoutManager());
        mAdapter.onAttachedToRecyclerView(mRecyclerView);// 如果使用网格布局请设置此方法

        mRecyclerView.addItemDecoration(getItemDecoration());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    /** 获取装饰器 */
    private RecyclerView.ItemDecoration getItemDecoration() {
        // 外围分割线
//        return RoundItemDecoration.create(getContext())
//                .setTopDividerRes(3, R.color.color_9a9a9a, R.color.color_ea413c, 15)
//                .setBottomDividerRes(3, R.color.color_9a9a9a, R.color.white, 15)
//                .setLeftDividerRes(3, R.color.color_9a9a9a, R.color.color_00a0e9, 5)
//                .setRightDividerRes(3, R.color.color_9a9a9a, R.color.color_ff00ff, 5);

//        return RoundItemDecoration.createBottomDivider(getContext(), 1, R.color.color_3f51b5, 0, 15);

        // 网格分割线
//        return GridItemDecoration.createDividerRes(getContext(), 1, R.color.color_00a0e9);

        // 分组
//        return SectionItemDecoration.<String>create(getContext())
//                .setOnSectionCallback(new SectionItemDecoration.OnSectionCallback<String>() {
//                    @Override
//                    public String getSourceItem(int position) {
//                        return mList.get(position);
//                    }
//                })
//                .setSectionTextSize(22)
//                .setSectionTextTypeface(Typeface.DEFAULT_BOLD)
//                .setSectionTextColorRes(R.color.white)
//                .setSectionTextPaddingLeftDp(8)
//                .setSectionBgColorRes(R.color.color_ea8380);

        // 粘黏标签
        return StickyItemDecoration.<String>create(getContext())
                .setOnSectionCallback(new SectionItemDecoration.OnSectionCallback<String>() {
                    @Override
                    public String getSourceItem(int position) {
                        return mList.get(position);
                    }
                })
                .setSectionTextSize(22)
                .setSectionHeight(50)
                .setSectionTextTypeface(Typeface.DEFAULT_BOLD)
                .setSectionTextColorRes(R.color.white)
                .setSectionTextPaddingLeftDp(8)
                .setSectionBgColorRes(R.color.color_ea8380);

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
        List<String> title = ArrayUtils.arrayToList(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"});
        mList = ArrayUtils.groupList(mList, title);
        mAdapter.setData(mList);
        mAdapter.notifyDataSetChanged();
        showStatusCompleted();
    }

    private List<String> getList(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 87; i++) {
            list.add((i + 1) + "");
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
}
