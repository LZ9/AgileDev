package com.lodz.android.agiledev.ui.tab;

import android.os.Bundle;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.fragment.TestFragment;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.component.widget.MmsTabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * tablayout测试类
 * Created by zhouL on 2017/7/11.
 */

public class TabActivity extends AbsActivity{

    /** 主页tab名称 */
    private String[] tabNameResId = {"曼联", "切尔西", "利物浦", "阿森纳"};

    @BindView(R.id.tab_layout)
    MmsTabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_tab_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initViewPager();
        initTabLayout();
    }

    private void initTabLayout() {
    }

    /** 初始化ViewPager */
    private void initViewPager() {
        mViewPager.setOffscreenPageLimit(tabNameResId.length);
        mViewPager.setAdapter(new MainTabAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabIndicatorMargin(20);
    }

    private class MainTabAdapter extends FragmentPagerAdapter {

        private MainTabAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return TestFragment.newInstance();
        }

        @Override
        public int getCount() {
            return tabNameResId.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNameResId[position];
        }
    }
}
