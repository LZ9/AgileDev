package com.lodz.android.agiledev.ui.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.mvp.abs.fragment.MvpTestLazyFragment;
import com.lodz.android.agiledev.ui.mvp.base.fragment.MvpTestBaseFragment;
import com.lodz.android.agiledev.ui.mvp.refresh.fragment.MvpTestRefreshFragment;
import com.lodz.android.component.base.activity.AbsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * fragment生命周期测试类
 * Created by zhouL on 2017/7/12.
 */

public class MvpFragmentActivity extends AbsActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, MvpFragmentActivity.class);
        context.startActivity(starter);
    }

    /** 主页tab名称 */
    private String[] tabNameResId = {"普通", "状态", "刷新"};

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
                    return MvpTestLazyFragment.newInstance();
                case 1:
                    return MvpTestBaseFragment.newInstance();
                case 2:
                    return MvpTestRefreshFragment.newInstance();
                default:
                    return MvpTestLazyFragment.newInstance();
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
