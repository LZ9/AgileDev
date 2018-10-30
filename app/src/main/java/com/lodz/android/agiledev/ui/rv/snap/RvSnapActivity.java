package com.lodz.android.agiledev.ui.rv.snap;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.bean.NationBean;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.snap.TabPagerSnapHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RvSnap测试类
 * Created by zhouL on 2018/6/6.
 */
public class RvSnapActivity extends BaseActivity{

    private static final String[] IMGS = new String[]{
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528284956583&di=673c40bfcb1603c4547f0e684619b636&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F5bafa40f4bfbfbed0528385b72f0f736afc31fa4.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528879767&di=ade0e932e2a79fc67f6eaf0a10a85f0a&imgtype=jpg&er=1&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fe1fe9925bc315c601e092f9f8db1cb1349547725.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528879811&di=3705bdd84e8ec5e5e874bb42369a9170&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fd4628535e5dde711fd3a64f6adefce1b9c1661c1.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528285142563&di=5c338bb523a9aa41dcae9d003c03bb67&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fb7003af33a87e950909a752316385343faf2b4f4.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1513270247,2810085725&fm=27&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1479658781,2612823267&fm=200&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=373478989,3635638859&fm=27&gp=0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528285365045&di=92f5f3a4dbf36274e381b14a2b8c6ed5&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F01%2F29%2F39%2F45bOOOPIC74.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528285464640&di=bec9062ec87cfb18b4bed7fc66e9e068&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D5f6687252c9759ee5e5d6888da922963%2F3c6d55fbb2fb43163abe99522aa4462309f7d371.jpg",};
    private static final String[] NAMES = new String[]{"中国", "美国", "俄罗斯", "日本", "韩国", "澳大利亚", "乌克兰", "朝鲜", "巴西"};
    private static final String[] CODES = new String[]{"CHN", "USA", "RUS", "JPN", "KOR", "AUS", "UKR", "PRK", "BRA"};

    /** 列表 */
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private SnapAdapter mAdapter;
    /** tab栏 */
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_snap_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        getTitleBarLayout().setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
        initRecyclerView();
        initTabLayout();
    }

    private void initTabLayout() {
        for (int i = 0; i < NAMES.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(NAMES[i]), i == 0);
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mAdapter = new SnapAdapter(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        TabPagerSnapHelper snapHelper = new TabPagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
        snapHelper.setupWithTabLayout(mTabLayout);
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter.setData(getTestList());
        mAdapter.notifyDataSetChanged();
        showStatusCompleted();
    }

    public List<NationBean> getTestList() {
        List<NationBean> list = new ArrayList<>();
        for (int i = 0; i < IMGS.length; i++) {
            NationBean bean = new NationBean();
            bean.imgUrl = IMGS[i];
            bean.name = NAMES[i];
            bean.code = CODES[i];
            list.add(bean);
        }
        return list;
    }
}
