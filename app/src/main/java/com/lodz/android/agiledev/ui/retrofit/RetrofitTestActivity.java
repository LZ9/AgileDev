package com.lodz.android.agiledev.ui.retrofit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.apiservice.ApiServiceImpl;
import com.lodz.android.agiledev.bean.SpotBean;
import com.lodz.android.agiledev.bean.SpotRequestBean;
import com.lodz.android.agiledev.bean.base.ResponseBean;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.rx.subscribe.observer.ProgressObserver;
import com.lodz.android.component.rx.utils.RxUtils;
import com.lodz.android.core.utils.ArrayUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * Retrofit测试类
 * Created by zhouL on 2018/2/6.
 */

public class RetrofitTestActivity extends BaseActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, RetrofitTestActivity.class);
        context.startActivity(starter);
    }

    /** Post请求按钮 */
    @BindView(R.id.post_btn)
    Button mPostBtn;
    /** Get请求按钮 */
    @BindView(R.id.get_btn)
    Button mGetBtn;
    /** 自定义请求按钮 */
    @BindView(R.id.custom_btn)
    Button mCustomBtn;

    /** 数据结果 */
    @BindView(R.id.result)
    TextView mResultTv;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_retrofit_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        getTitleBarLayout().setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPost();
            }
        });

        mGetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestGet();
            }
        });

        mCustomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCustom();
            }
        });
    }

    /** post请求 */
    private void requestPost() {
        ApiServiceImpl.get()// 本地数据
//        ApiServiceManager.get().create(ApiService.class)// 网络数据
                .postSpot("","")
                .compose(RxUtils.<ResponseBean<SpotBean>>io_main())
                .compose(this.<ResponseBean<SpotBean>>bindUntilEvent(ActivityEvent.DESTROY))
//                .compose(this.<ResponseBean>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(new ProgressObserver<ResponseBean<SpotBean>>() {
                    @Override
                    public void onPgSubscribe(Disposable d) {

                    }

                    @Override
                    public void onPgNext(ResponseBean<SpotBean> responseBean) {
                        SpotBean spotBean = responseBean.data;
                        mResultTv.setText(spotBean.spotName + "\n" + spotBean.star);
                    }

                    @Override
                    public void onPgError(Throwable e, boolean isNetwork) {

                    }

                    @Override
                    public void onPgComplete() {

                    }
                }.create(getContext(), "正在查询", false));
    }

    /** get请求 */
    private void requestGet() {
        ApiServiceImpl.get()// 本地数据
//        ApiServiceManager.get().create(ApiService.class)// 网络数据
                .getSpot("","")
                .compose(RxUtils.<ResponseBean<SpotBean>>io_main())
                .compose(this.<ResponseBean<SpotBean>>bindUntilEvent(ActivityEvent.DESTROY))
//                .compose(this.<ResponseBean>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(new ProgressObserver<ResponseBean<SpotBean>>() {
                    @Override
                    public void onPgSubscribe(Disposable d) {

                    }

                    @Override
                    public void onPgNext(ResponseBean<SpotBean> responseBean) {
                        SpotBean spotBean = responseBean.data;
                        mResultTv.setText(spotBean.spotName + "\n" + spotBean.star);
                    }

                    @Override
                    public void onPgError(Throwable e, boolean isNetwork) {

                    }

                    @Override
                    public void onPgComplete() {

                    }
                }.create(getContext(), "正在查询", false));
    }

    /** 自定义请求 */
    private void requestCustom() {
        ApiServiceImpl.get()// 本地数据
//        ApiServiceManager.get().create(ApiService.class)// 网络数据
                .querySpot(new SpotRequestBean("", "").createRequestBody())
                .compose(RxUtils.<ResponseBean<List<SpotBean>>>io_main())
                .compose(this.<ResponseBean<List<SpotBean>>>bindUntilEvent(ActivityEvent.DESTROY))
//                .compose(this.<ResponseBean>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(new ProgressObserver<ResponseBean<List<SpotBean>>>() {
                    @Override
                    public void onPgSubscribe(Disposable d) {

                    }

                    @Override
                    public void onPgNext(ResponseBean<List<SpotBean>> responseBean) {
                        List<SpotBean> list = responseBean.data;
                        if (ArrayUtils.isEmpty(list)){
                            mResultTv.setText("list is null");
                            return;
                        }
                        for (SpotBean spotBean : list) {
                            mResultTv.setText(spotBean.spotName + "\n" + spotBean.star);
                        }
                    }

                    @Override
                    public void onPgError(Throwable e, boolean isNetwork) {

                    }

                    @Override
                    public void onPgComplete() {

                    }
                }.create(getContext(), "正在查询", false));
    }


    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }
}
