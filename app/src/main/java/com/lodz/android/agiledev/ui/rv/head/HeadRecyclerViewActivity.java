package com.lodz.android.agiledev.ui.rv.head;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.agiledev.ui.rv.drag.LayoutManagerPopupWindow;
import com.lodz.android.agiledev.ui.rv.drag.OrientationPopupWindow;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.recycler.BaseHeaderFooterRVAdapter;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 带头/底部RecyclerView测试
 * Created by zhouL on 2017/4/6.
 */
public class HeadRecyclerViewActivity extends BaseActivity{

    /** 头部选择按钮 */
    @BindView(R.id.header_switch)
    Switch mHeaderSwitch;
    /** 底部选择按钮 */
    @BindView(R.id.footer_switch)
    Switch mFooterSwitch;
    /** 方向按钮 */
    @BindView(R.id.orientation_btn)
    TextView mOrientationBtn;
    /** 布局按钮 */
    @BindView(R.id.layout_manager_btn)
    TextView mLayoutManagerBtn;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    /** 适配器 */
    private HeadRecyclerViewAdapter mAdapter;

    /** 当前布局 */
    @LayoutManagerPopupWindow.LayoutManagerType
    private int mCurrentLayoutManagerType = LayoutManagerPopupWindow.TYPE_LINEAR;
    /** 当前是否纵向 */
    private boolean mIsVertical = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_head_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBar(getTitleBarLayout());
        initRecyclerView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mOrientationBtn.setElevation(12f);
            mLayoutManagerBtn.setElevation(12f);
        }
    }

    /** 初始化标题栏 */
    private void initTitleBar(TitleBarLayout titleBarLayout) {
        titleBarLayout.setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
    }

    private void initRecyclerView() {
        mAdapter = new HeadRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(getLayoutManager());
        mAdapter.onAttachedToRecyclerView(mRecyclerView);// 如果使用网格布局请设置此方法
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        if (mCurrentLayoutManagerType == LayoutManagerPopupWindow.TYPE_GRID){
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
            layoutManager.setOrientation(mIsVertical ? GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL);
            return layoutManager;
        }

        if (mCurrentLayoutManagerType == LayoutManagerPopupWindow.TYPE_STAGGERED){
            return new StaggeredGridLayoutManager(3, mIsVertical ? StaggeredGridLayoutManager.VERTICAL : StaggeredGridLayoutManager.HORIZONTAL);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(mIsVertical ? RecyclerView.VERTICAL : RecyclerView.HORIZONTAL);
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

        mHeaderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAdapter.setHeaderData(isChecked ? getString(R.string.rvhead_header_tips) : null);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.smoothScrollToPosition(0);
            }
        });

        mFooterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAdapter.setFooterData(isChecked ? getString(R.string.rvhead_footer_tips) : null);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
            }
        });

        mOrientationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrientationPopupWindow(v);
            }
        });

        mLayoutManagerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLayoutManagerPopupWindow(v);
            }
        });

        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, String item, int position) {
                ToastUtils.showShort(getContext(), item);
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseRecyclerViewAdapter.OnItemLongClickListener<String>() {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, String item, int position) {
                ToastUtils.showShort(getContext(), item + "  " + getString(R.string.rvhead_item_long_click_tips));
            }
        });

        mAdapter.setOnHeaderClickListener(new BaseHeaderFooterRVAdapter.OnHeaderClickListener<String>() {
            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, String headerData, int position) {
                ToastUtils.showShort(getContext(), headerData);
            }
        });

        mAdapter.setOnHeaderLongClickListener(new BaseHeaderFooterRVAdapter.OnHeaderLongClickListener<String>() {
            @Override
            public void onHeaderLongClick(RecyclerView.ViewHolder viewHolder, String headerData, int position) {
                ToastUtils.showShort(getContext(), headerData + "  " + getString(R.string.rvhead_header_long_click_tips));
            }
        });

        mAdapter.setOnFooterClickListener(new BaseHeaderFooterRVAdapter.OnFooterClickListener<String>() {
            @Override
            public void onFooterClick(RecyclerView.ViewHolder viewHolder, String footerData, int position) {
                ToastUtils.showShort(getContext(), footerData);
            }
        });

        mAdapter.setOnFooterLongClickListener(new BaseHeaderFooterRVAdapter.OnFooterLongClickListener<String>() {
            @Override
            public void onFooterLongClick(RecyclerView.ViewHolder viewHolder, String footerData, int position) {
                ToastUtils.showShort(getContext(), footerData + "  " + getString(R.string.rvhead_footer_long_click_tips));
            }
        });
    }

    /** 显示方向的PopupWindow */
    private void showOrientationPopupWindow(View view) {
        OrientationPopupWindow popupWindow = new OrientationPopupWindow(getContext());
        popupWindow.setIsVertical(mIsVertical);
        popupWindow.getPopup().showAsDropDown(view, -15, 20);
        popupWindow.setListener(new OrientationPopupWindow.Listener() {
            @Override
            public void onClick(PopupWindow popupWindow, boolean isVertical) {
                mIsVertical = isVertical;
                mRecyclerView.setLayoutManager(getLayoutManager());
                mAdapter.onAttachedToRecyclerView(mRecyclerView);
                mAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });
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

    @Override
    protected void initData() {
        super.initData();
        mAdapter.setData(getItemList());
        mAdapter.notifyDataSetChanged();
        showStatusCompleted();
    }

    private List<String> getItemList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("-.- " + (i + 1));
        }
        return list;
    }


}
