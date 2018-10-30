package com.lodz.android.agiledev.ui.design.coordinator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.core.utils.DensityUtils;
import com.lodz.android.core.utils.ScreenUtils;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * CoordinatorLayout位移测试类
 * Created by zhouL on 2018/5/7.
 */
public class CoordinatorTranslationActivity extends AbsActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, CoordinatorTranslationActivity.class);
        context.startActivity(starter);
    }

    @BindView(R.id.back)
    ImageView mBackBtn;

    @BindView(R.id.iv_head)
    ImageView mHeadImage;
    @BindView(R.id.subscription_title)
    TextView mSubscriptionTitle;
    @BindView(R.id.subscribe)
    TextView mSubscribe;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    /** 标题栏高度 */
    private float mToolbarHeight;
    /** 标题内容高度 */
    private float mTitleContentHeight;

    private float mSelfHeight = 0;//用以判断是否得到正确的宽高值
    private float mTitleScale;
    private float mSubScribeScale;
    private float mSubScribeScaleX;
    private float mHeadImgScale;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_coordinator_translation_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setOffset();
    }

    private void setOffset() {
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (mSelfHeight == 0) {
                    mSelfHeight = mSubscriptionTitle.getHeight();

                    float distanceHeadImg = mHeadImage.getY() + (mHeadImage.getHeight() - mToolbarHeight) / 2.0f;
                    mHeadImgScale = distanceHeadImg / (mTitleContentHeight - mToolbarHeight);

                    float distanceTitle = mSubscriptionTitle.getTop() + (mSubscriptionTitle.getHeight() - mToolbarHeight) / 2.0f;
                    mTitleScale = distanceTitle / (mTitleContentHeight - mToolbarHeight);

                    float distanceSubscribe = mSubscribe.getY() + (mSubscribe.getHeight() - mToolbarHeight) / 2.0f;
                    float distanceSubscribeX = ScreenUtils.getScreenWidth(getContext()) / 2.0f - (mSubscribe.getWidth() / 2.0f + DensityUtils.dp2px(getContext(), 5));
                    mSubScribeScale = distanceSubscribe / (mTitleContentHeight - mToolbarHeight);
                    mSubScribeScaleX = distanceSubscribeX / (mTitleContentHeight - mToolbarHeight);
                }

                float scale = 1.0f - (-verticalOffset) / (mTitleContentHeight - mToolbarHeight);
                mHeadImage.setScaleX(scale);
                mHeadImage.setScaleY(scale);
                mHeadImage.setTranslationY(mHeadImgScale * verticalOffset);
                mSubscriptionTitle.setTranslationY(mTitleScale * verticalOffset);
                mSubscribe.setTranslationY(mSubScribeScale * verticalOffset);
                mSubscribe.setTranslationX(-mSubScribeScaleX * verticalOffset);
            }
        });
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mToolbarHeight = DensityUtils.dp2pxFloat(getContext(), 56);
        mTitleContentHeight = DensityUtils.dp2pxFloat(getContext(), 150);
    }
}
