package com.lodz.android.agiledev.ui.other;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.fragment.TestFragment;
import com.lodz.android.component.rx.subscribe.observer.RxObserver;
import com.lodz.android.component.rx.utils.RxObservableOnSubscribe;
import com.lodz.android.component.widget.base.LoadingLayout;
import com.lodz.android.core.utils.ToastUtils;
import com.lodz.android.core.utils.UiHandler;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by zhouL on 2017/2/23.
 */
public class Test2Activity extends AppCompatActivity{

    private LinearLayout mContentLayout;

    private LoadingLayout mLoadingLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_2_layout);
        findViews(savedInstanceState);
        setListeners();
        initData();
    }


    /** 详情页tab名称 */
    private int[] tabNameResId = {R.string.lesson_detail_tab_introduction, R.string.lesson_detail_tab_video,
            R.string.lesson_detail_tab_document, R.string.lesson_detail_tab_comment};

    TabLayout mTabLayout;
    ViewPager mViewPager;
    /** 返回按钮 */
    ImageView mBackImageView;
    /** 收藏按钮 */
    ImageView mCollectionImageView;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
//    SimpleDraweeView mBgDraweeView;

    protected void findViews(Bundle savedInstanceState) {
        mContentLayout = findViewById(R.id.content_layout);
        mLoadingLayout = findViewById(R.id.loading_layout);

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        mBackImageView = findViewById(R.id.detail_back_imageview);
        mCollectionImageView = findViewById(R.id.detail_collection_imageview);
        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
//        mBgDraweeView = findViewById(R.id.drawee_view);
    }


    protected void setListeners() {
        // 返回按钮
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 收藏按钮
        mCollectionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(Test2Activity.this, "收藏");
            }
        });
    }

    protected void initData() {
        initViewPager();
        showStatusCompleted();
        reuqestData();
    }

    /** 请求数据 */
    private void reuqestData() {
        Observable.create(new RxObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                UiHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        emitter.onNext("测试啦");
                        emitter.onComplete();
                    }
                }, 3000);


            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(new Consumer<ResponseBean<LessonDetailInfoBean>>() {
//                    @Override
//                    public void accept(@NonNull ResponseBean<LessonDetailInfoBean> responseBean) throws Exception {
//                        showStatusLoading();
//                    }
//                })
                .subscribe(new RxObserver<String>() {

                    @Override
                    public void onRxSubscribe(Disposable d) {

                    }

                    @Override
                    public void onRxNext(String str) {
                        initCollapsingToolbarLayout(str);
                        showBgImg("http://p.jianke.net/article/201507/20150709164730105.jpg");
                        showStatusCompleted();
                    }

                    @Override
                    public void onRxError(Throwable e, boolean isNetwork) {
                    }

                    @Override
                    public void onRxComplete() {

                    }
                });
    }

    private void showBgImg(String imgUrl) {
//        ImageLoader.create(this)
//                .load(UriUtils.parseUrl(imgUrl))
//                .setPlaceholder(R.drawable.imageloader_ic_default)
//                .setError(R.drawable.imageloader_ic_default)
//                .setImageSize(ScreenUtils.getScreenWidth(this), DensityUtils.dp2px(this, 200))
//                .useFresco()
//                .setPlaceholderScaleType(ScalingUtils.ScaleType.CENTER_CROP)
//                .setErrorScaleType(ScalingUtils.ScaleType.CENTER_CROP)
//                .setImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
//                .setFadeDuration(1000)
//                .into(mBgDraweeView);
    }

    /** 初始化ViewPager */
    private void initViewPager() {
        mViewPager.setOffscreenPageLimit(tabNameResId.length);
        mViewPager.setAdapter(new LessonTabAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }
    /** 初始化CollapsingToolbarLayout */
    private void initCollapsingToolbarLayout(String courseName) {
        mCollapsingToolbarLayout.setTitle(courseName);
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
    }

    private class LessonTabAdapter extends FragmentPagerAdapter {

        public LessonTabAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return TestFragment.newInstance();
        }

        @Override
        public int getCount() {
            return tabNameResId.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Test2Activity.this.getString(tabNameResId[position]);
        }
    }

    private void showStatusLoading() {
        mContentLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    private void showStatusCompleted() {
        mContentLayout.setVisibility(View.VISIBLE);
        mLoadingLayout.setVisibility(View.GONE);
    }
}
