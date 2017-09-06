package com.lodz.android.agiledev.ui.rvanim;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.DateUtils;
import com.lodz.android.core.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RV动画测试
 * Created by zhouL on 2017/9/5.
 */
public class AnimRecyclerViewActivity extends BaseActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, AnimRecyclerViewActivity.class);
        context.startActivity(starter);
    }

    /** 列表 */
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    /** 适配器 */
    private AnimAdapter mAdapter;

    /** 当前动画类型 */
    @AnimPopupWindow.AnimType
    private int mCurrentAnimType = BaseRecyclerViewAdapter.SCALE_IN;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_anim_recycler_view_layout;
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new AnimAdapter(getContext());
        mAdapter.setOpenItemAnim(true);//开启动画
        mAdapter.setItemAnimStartPosition(7);//设置动画起始位置
        mAdapter.setAnimationType(mCurrentAnimType);//设置动画类型
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
    protected void initData() {
        super.initData();
        mAdapter.setData(createList());
        mAdapter.notifyDataSetChanged();
        showStatusCompleted();
    }

    private List<String> createList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 120; i++) {
            list.add(DateUtils.getCurrentFormatString(DateUtils.TYPE_10));
        }
        return list;
    }

    /** 获取扩展view */
    private View getExpandView() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        final TextView animTv = getTextView(R.string.rvanim_popup_name);//动画
        animTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimPopupWindow(v);
            }
        });
        linearLayout.addView(animTv, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final TextView resetTv = getTextView(R.string.rvanim_reset);//重置
        resetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.scrollToPosition(0);//滚动到顶部
                mAdapter.resetItemAnimPosition();//重置效果
            }
        });
        linearLayout.addView(resetTv, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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

    /** 显示动画的PopupWindow */
    private void showAnimPopupWindow(View view) {
        AnimPopupWindow popupWindow = new AnimPopupWindow(getContext());
        popupWindow.setAnimType(mCurrentAnimType);
        popupWindow.getPopup().showAsDropDown(view, -50, 20);
        popupWindow.setListener(new AnimPopupWindow.Listener() {
            @Override
            public void onClick(PopupWindow popupWindow, @AnimPopupWindow.AnimType int type) {
                mCurrentAnimType = type;
                if (type == AnimPopupWindow.TYPE_CUSTOM){
                    mAdapter.setBaseAnimation(new ScaleAlphaInAnimation());//设置自定义的淡入缩放效果
                    popupWindow.dismiss();
                    return;
                }
                mAdapter.setBaseAnimation(null);
                mAdapter.setAnimationType(mCurrentAnimType);
                popupWindow.dismiss();
            }
        });
    }
}
