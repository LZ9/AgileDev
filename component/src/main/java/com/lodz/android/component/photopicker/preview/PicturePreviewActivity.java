package com.lodz.android.component.photopicker.preview;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lodz.android.component.R;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.component.photopicker.contract.preview.PreviewController;
import com.lodz.android.component.widget.photoview.PhotoViewPager;
import com.lodz.android.core.utils.ArrayUtils;

/**
 * 图片预览页面
 * Created by zhouL on 2017/9/22.
 */

public class PicturePreviewActivity extends AbsActivity{

    /**
     * 启动页面
     * @param context 上下文
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, PicturePreviewActivity.class);
        context.startActivity(starter);
    }

    /** 背景控件 */
    private ViewGroup mRootView;
    /** 翻页适配器 */
    private PhotoViewPager mViewPager;
    /** 页码提示 */
    private TextView mPagerTipsTv;

    /** 图片数据 */
    private PreviewBean mPreviewBean;

    @Override
    protected void startCreate() {
        super.startCreate();
        mPreviewBean = PreviewManager.previewBean;
    }

    @Override
    protected int getAbsLayoutId() {
        return R.layout.component_activity_preview_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        mRootView = (ViewGroup) findViewById(R.id.root_view);
        mPagerTipsTv = (TextView) findViewById(R.id.pager_tips);
        initViewPager();
    }

    private void initViewPager() {
        mViewPager = (PhotoViewPager) findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new PicturePagerAdapter(mPreviewBean, mPreviewController));
        mViewPager.setCurrentItem(mPreviewBean.showPosition);
    }

    @Override
    protected void initData() {
        super.initData();
        mRootView.setBackgroundColor(ContextCompat.getColor(getContext(), mPreviewBean.backgroundColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSystemBarColor(mPreviewBean.statusBarColor, mPreviewBean.navigationBarColor);
        }
        setPagerNum(mPreviewBean.showPosition);
        mPagerTipsTv.setVisibility(mPreviewBean.isShowPagerText ? View.VISIBLE : View.GONE);
        mPagerTipsTv.setTextColor(ContextCompat.getColor(getContext(), mPreviewBean.pagerTextColor));
        mPagerTipsTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, mPreviewBean.pagerTextSize);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPagerNum(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private PreviewController mPreviewController = new PreviewController() {
        @Override
        public void close() {
            finish();
        }
    };

    /** 设置页码 */
    private void setPagerNum(int position){
        mPagerTipsTv.setText((position + 1) + " / " + ArrayUtils.getSize(mPreviewBean.sourceList));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setSystemBarColor(@ColorRes int statusBarColor, @ColorRes int navigationBarColor) {
        if (statusBarColor == 0 && navigationBarColor == 0){
            return;
        }

        try {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (statusBarColor != 0){
                window.setStatusBarColor(ContextCompat.getColor(getContext(), statusBarColor));
            }
            if (navigationBarColor != 0){
                window.setNavigationBarColor(ContextCompat.getColor(getContext(), navigationBarColor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
