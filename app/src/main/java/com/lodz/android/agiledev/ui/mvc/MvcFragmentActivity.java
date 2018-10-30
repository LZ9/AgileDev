package com.lodz.android.agiledev.ui.mvc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.mvc.abs.MvcTestLazyFragment;
import com.lodz.android.agiledev.ui.mvc.base.MvcTestBaseFragment;
import com.lodz.android.agiledev.ui.mvc.refresh.MvcTestRefreshFragment;
import com.lodz.android.agiledev.ui.mvc.sandwich.MvcTestSandwichFragment;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.base.TitleBarLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * fragment生命周期测试类
 * Created by zhouL on 2018/4/17.
 */
public class MvcFragmentActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, MvcFragmentActivity.class);
        context.startActivity(starter);
    }

    /** 主页tab名称 */
    private String[] tabNameResId = {"普通", "状态", "刷新", "三明治"};

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment_life_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBarLayout(getTitleBarLayout());
        initViewPager();
    }

    private void initTitleBarLayout(TitleBarLayout titleBarLayout) {
        titleBarLayout.setTitleName(R.string.mvc_demo_fragment_title);
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
                    return MvcTestLazyFragment.newInstance();
                case 1:
                    return MvcTestBaseFragment.newInstance();
                case 2:
                    return MvcTestRefreshFragment.newInstance();
                case 3:
                    return MvcTestSandwichFragment.newInstance();
                default:
                    return MvcTestLazyFragment.newInstance();
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

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }
}
