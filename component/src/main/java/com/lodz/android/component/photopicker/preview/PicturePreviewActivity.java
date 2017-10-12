package com.lodz.android.component.photopicker.preview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.lodz.android.component.R;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.core.utils.ToastUtils;

/**
 * 图片预览页面
 * Created by zhouL on 2017/9/22.
 */

public class PicturePreviewActivity extends AbsActivity{

    private static final String EXTRA_PREVIEW_BEAN = "extra_preview_bean";

    public static void start(Context context, PreviewBean bean) {
        Intent starter = new Intent(context, PicturePreviewActivity.class);
        starter.putExtra(EXTRA_PREVIEW_BEAN, bean);
        context.startActivity(starter);
    }

    /** 翻页适配器 */
    private ViewPager mViewPager;
    /** 页码提示 */
    private TextView mPagerTips;

    /** 图片数据 */
    private PreviewBean mPreviewBean;

    @Override
    protected void startCreate() {
        super.startCreate();
        mPreviewBean = (PreviewBean) getIntent().getSerializableExtra(EXTRA_PREVIEW_BEAN);
        if (mPreviewBean == null || mPreviewBean.pictureType == PreviewBean.TYPE_NONE){
            ToastUtils.showShort(getContext(), "图片数据错误");
            finish();
        }
    }

    @Override
    protected int getAbsLayoutId() {
        return R.layout.component_activity_preview_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        mPagerTips = (TextView) findViewById(R.id.pager_tips);
        initViewPager();
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new PicturePagerAdapter(mPreviewBean));
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
                mPagerTips.setText((position + 1) + " / " + mPreviewBean.getPictureCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
