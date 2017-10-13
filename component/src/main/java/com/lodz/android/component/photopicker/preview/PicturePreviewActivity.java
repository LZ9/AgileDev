package com.lodz.android.component.photopicker.preview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.lodz.android.component.R;
import com.lodz.android.component.base.activity.AbsActivity;
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

    /** 翻页适配器 */
    private PhotoViewPager mViewPager;
    /** 页码提示 */
    private TextView mPagerTips;

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
        mPagerTips = (TextView) findViewById(R.id.pager_tips);
        initViewPager();
    }

    private void initViewPager() {
        mViewPager = (PhotoViewPager) findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new PicturePagerAdapter(mPreviewBean));
        setPagerNum(mPreviewBean.showPosition);
        mViewPager.setCurrentItem(mPreviewBean.showPosition);
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

    /** 设置页码 */
    private void setPagerNum(int position){
        mPagerTips.setText((position + 1) + " / " + ArrayUtils.getSize(mPreviewBean.sourceList));
    }

}
