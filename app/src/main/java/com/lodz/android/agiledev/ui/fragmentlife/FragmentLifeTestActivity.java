package com.lodz.android.agiledev.ui.fragmentlife;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.AbsActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * fragment生命周期测试类
 * Created by zhouL on 2017/7/12.
 */

public class FragmentLifeTestActivity extends AbsActivity{

    /** 主页tab名称 */
    private String[] tabNameResId = {"英超", "德甲"};

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;


    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_fragment_life_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initViewPager();
    }

    private void initViewPager() {
        mViewPager.setOffscreenPageLimit(tabNameResId.length);
        mViewPager.setAdapter(new MainTabAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private class MainTabAdapter extends FragmentPagerAdapter {

        private MainTabAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return TabLifeTestFragment.newInstance(tabNameResId[position]);
                case 1:
                    return TabLifeTestFragment.newInstance(tabNameResId[position]);
                default:
                    return TabLifeTestFragment.newInstance("test");
            }
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
