package com.lodz.android.agiledev.ui.share.element;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.config.Constant;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.utils.TransitionHelper;

import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.core.util.Pair;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 共享元素
 * Created by zhouL on 2018/5/25.
 */
public class ElementActivity extends BaseActivity{

    private static final String EXTRA_IMG_RES_ID = "extra_img_res_id";
    private static final String EXTRA_TITLE = "extra_title";

    public static void start(Activity activity, @DrawableRes int resId, String title, List<Pair<View, String>> sharedElements) {
        Intent starter = new Intent(activity, ElementActivity.class);
        starter.putExtra(EXTRA_IMG_RES_ID, resId);
        starter.putExtra(EXTRA_TITLE, title);
        TransitionHelper.jumpTransition(activity, starter, sharedElements);
    }

    /** 标题名字 */
    @BindView(R.id.title_name)
    TextView mTitleTv;
    /** 图标 */
    @BindView(R.id.icon_img)
    ImageView mIconImg;

    /** 标题 */
    private String mTitle;
    /** 图标资源id */
    @DrawableRes
    private int mImgResId;

    @Override
    protected void startCreate() {
        super.startCreate();
        mTitle = getIntent().getStringExtra(EXTRA_TITLE);
        mImgResId = getIntent().getIntExtra(EXTRA_IMG_RES_ID, R.drawable.ic_launcher);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_element_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        getTitleBarLayout().setTitleName(R.string.share_element_title);
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        TransitionHelper.finish(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mTitleTv.setText(mTitle);
        mIconImg.setImageResource(mImgResId);
        TransitionHelper.setTransition(mTitleTv, Constant.ShareElementName.TITLE);
        TransitionHelper.setTransition(mIconImg, Constant.ShareElementName.IMG);
        TransitionHelper.setEnterTransitionDuration(this, 500);
        showStatusCompleted();
    }
}
