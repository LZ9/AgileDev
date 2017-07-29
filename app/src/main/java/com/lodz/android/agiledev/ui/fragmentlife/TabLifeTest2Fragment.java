package com.lodz.android.agiledev.ui.fragmentlife;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.fragment.LazyFragment;
import com.lodz.android.core.log.PrintLog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 生命周期测试fragment
 * Created by zhouL on 2017/7/12.
 */

public class TabLifeTest2Fragment extends LazyFragment{

    public static TabLifeTest2Fragment newInstance(String fragmentName) {
        TabLifeTest2Fragment fragment = new TabLifeTest2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("fragmentName", fragmentName);
        fragment.setArguments(bundle);
        return fragment;
    }

    /** 主页tab名称 */
    private String[] tabNameResId = {"球员A", "球员B"};

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private String mFragmentName = "";

    @Override
    protected void startCreate() {
        super.startCreate();
        Bundle bundle = getArguments();
        mFragmentName = bundle.getString("fragmentName");
    }

    @Override
    protected int getAbsLayoutId() {
        return R.layout.fragment_tab_life_test_layout;
    }

    @Override
    protected void findViews(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        initViewPager();
    }

    private void initViewPager() {
        mViewPager.setOffscreenPageLimit(tabNameResId.length);
        mViewPager.setAdapter(new MainTabAdapter(getChildFragmentManager()));
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
                    return LifeTestFragment.newInstance(mFragmentName + tabNameResId[position]);
                case 1:
                    return LifeTestFragment.newInstance(mFragmentName + tabNameResId[position]);
                default:
                    return LifeTestFragment.newInstance("test");
            }
        }

        @Override
        public int getCount() {
            return tabNameResId.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentName + tabNameResId[position];
        }
    }

    @Override
    protected void onFragmentResume() {
        super.onFragmentResume();
        PrintLog.d("testtag", mFragmentName + " : onFragmentResume");
    }

    @Override
    protected void onFragmentPause() {
        super.onFragmentPause();
        PrintLog.e("testtag", mFragmentName + " : onFragmentPause");
    }


}
