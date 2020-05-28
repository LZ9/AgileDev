package com.lodz.android.agiledev.ui.drawer;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.component.rx.subscribe.observer.ProgressObserver;
import com.lodz.android.component.rx.utils.RxUtils;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.StatusBarUtil;
import com.lodz.android.core.utils.ToastUtils;
import com.lodz.android.imageloader.ImageLoader;
import com.trello.rxlifecycle4.android.ActivityEvent;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;

/**
 * 侧滑栏测试类
 * Created by zhouL on 2018/6/5.
 */
public class DrawerTestActivity extends AbsActivity{

    private static final String[] TITLES = new String[]{"无名的旅人", "路旁的落叶", "水面上的小草", "呢喃的歌声", "地上的月影", "奔跑的春风",
            "苍之风云", "摇曳的金星", "欢喜的慈雨", "蕴含的太阳", "敬畏的寂静", "无尽星空"};

    /** 内容 */
    @BindView(R.id.content_layout)
    ViewGroup mContentLayout;
    /** 标题栏 */
    @BindView(R.id.title_bar_layout)
    TitleBarLayout mTitleBarLayout;
    /** 结果 */
    @BindView(R.id.result)
    TextView mResultTv;

    /** 侧滑栏 */
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    /** 用户头像 */
    @BindView(R.id.user_logo)
    ImageView mUserLogoImg;
    /** 称号 */
    @BindView(R.id.title)
    TextView mTitleTv;
    /** 搜索按钮 */
    @BindView(R.id.search_btn)
    ImageView mSearchBtn;
    /** 刷新按钮 */
    @BindView(R.id.refresh_btn)
    ImageView mRefreshBtn;
    /** 关注按钮 */
    @BindView(R.id.collect_btn)
    ViewGroup mCollectBtn;
    /** 钱包按钮 */
    @BindView(R.id.wallet_btn)
    ViewGroup mWalletBtn;
    /** 设置按钮 */
    @BindView(R.id.setting_btn)
    ViewGroup mSettingBtn;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_drawer_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mTitleBarLayout.setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
        StatusBarUtil.setTranslucentForDrawerLayout(this, mDrawerLayout, 0);
        StatusBarUtil.setTransparentForImageView(this, mContentLayout);
    }

    @Override
    protected boolean onPressBack() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return super.onPressBack();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mTitleBarLayout.setOnBackBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        // 搜索
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultTv.setText("搜索");
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // 刷新
        mRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshTitle();
            }
        });

        // 关注
        mCollectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultTv.setText("关注");
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // 钱包
        mWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultTv.setText("钱包");
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // 设置
        mSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultTv.setText("设置");
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        ImageLoader.create(getContext())
                .load(R.drawable.bg_pokemon)
                .useCircle()
                .into(mUserLogoImg);
    }

    /** 刷新称号 */
    private void refreshTitle() {
        int random = new Random().nextInt(TITLES.length);
        Observable.just(TITLES[random])
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        Thread.sleep(1000);
                        return Observable.just(s);
                    }
                })
                .compose(RxUtils.<String>ioToMainObservable())
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new ProgressObserver<String>() {
                    @Override
                    public void onPgNext(String title) {
                        StringBuilder stringBuilder = new StringBuilder();
                        mTitleTv.setText(stringBuilder.append("称号：").append(title));
                        ToastUtils.showShort(getContext(), "已为您刷新称号");
                    }

                    @Override
                    public void onPgError(Throwable e, boolean isNetwork) {
                        ToastUtils.showShort(getContext(), "刷新失败");
                    }
                }.create(getContext(), "正在为您刷新称号", false));
    }
}
