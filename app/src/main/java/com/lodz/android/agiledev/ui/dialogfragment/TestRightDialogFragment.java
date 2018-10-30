package com.lodz.android.agiledev.ui.dialogfragment;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.lodz.android.agiledev.R;
import com.lodz.android.component.widget.dialogfragment.BaseRightDialogFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 测试底部DialogFragment
 * Created by zhouL on 2018/3/27.
 */

public class TestRightDialogFragment extends BaseRightDialogFragment{

    private static final int[] TAGS = new int[]{
            R.string.dialog_test_tags_mun, R.string.dialog_test_tags_ars,
            R.string.dialog_test_tags_che, R.string.dialog_test_tags_liv};

    /** TabLayout */
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    /** ViewPager */
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fragment_test_right_layout;
    }

    @Override
    protected void findViews(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        initViewPager();
    }

    private void initViewPager() {
        mViewPager.setAdapter(new TabAdapter(getChildFragmentManager()));
        mViewPager.setOffscreenPageLimit(TAGS.length);
        mViewPager.setCurrentItem(0, true);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private class TabAdapter extends FragmentPagerAdapter {

        private TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TestDialogFragment.newInstance(getString(TAGS[position]));
        }

        @Override
        public int getCount() {
            return TAGS.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(TAGS[position]);
        }
    }


}
