package com.lodz.android.component.widget.adapter.snap;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 支持TabLayout联动的PagerSnapHelper
 * Created by zhouL on 2018/6/8.
 */
public class TabPagerSnapHelper extends ViewPagerSnapHelper{

    private RecyclerView mRecyclerView;
    private TabLayout mTabLayout;

    /** 是否滚动了RecyclerView */
    private boolean isScrollRv = false;
    /** 是否选择了TabLayout */
    private boolean isSelectedTab = false;

    public TabPagerSnapHelper(int startPosition) {
        super(startPosition);
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
        if (mRecyclerView == recyclerView) {
            return;
        }
        mRecyclerView = recyclerView;
        setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (isSelectedTab){// 如果是tablayout选择则不做处理
                    isSelectedTab = false;
                    return;
                }
                if (mTabLayout != null){
                    linkage(mTabLayout, position);
                }
            }
        });
    }

    /** 处理联动逻辑 */
    private void linkage(TabLayout tabLayout, int position) {
        if (position < tabLayout.getTabCount()){
            TabLayout.Tab tab = tabLayout.getTabAt(position);
            if (tab != null) {
                isScrollRv = true;
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
                if (isScrollRv){//RV滚动的tab设置不做处理
                    isScrollRv = false;
                    return;
                }
                isSelectedTab = true;
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
