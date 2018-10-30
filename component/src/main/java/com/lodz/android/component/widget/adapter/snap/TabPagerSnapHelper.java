package com.lodz.android.component.widget.adapter.snap;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 支持TabLayout联动的PagerSnapHelper
 * Created by zhouL on 2018/6/8.
 */
public class TabPagerSnapHelper extends PagerSnapHelper{

    private RecyclerView mRecyclerView;
    private TabLayout mTabLayout;

    /** 是否滚动了RV */
    private boolean isScroll = false;

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
        if (mRecyclerView == recyclerView) {
            return;
        }
        mRecyclerView = recyclerView;
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        int position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
        if (mTabLayout != null){
            linkage(mTabLayout, position);
        }
        return position;
    }

    /** 处理联动逻辑 */
    private void linkage(TabLayout tabLayout, int position) {
        if (position < tabLayout.getTabCount()){
            TabLayout.Tab tab = tabLayout.getTabAt(position);
            if (tab != null) {
                isScroll = true;
                tab.select();
            }
        }
    }

    /** 与TabLayout联动 */
    public void setupWithTabLayout(TabLayout tabLayout){
        if (tabLayout == null){
            return;
        }
        mTabLayout = tabLayout;
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (isScroll){//RV滚动的tab设置不做处理
                    isScroll = false;
                    return;
                }
                if (mRecyclerView != null){
                    mRecyclerView.smoothScrollToPosition(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
