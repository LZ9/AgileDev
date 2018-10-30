package com.lodz.android.agiledev.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lodz.android.agiledev.App;
import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.bean.MainBean;
import com.lodz.android.agiledev.ui.admin.AdminTestActivity;
import com.lodz.android.agiledev.ui.annotation.AnnotationTestActivity;
import com.lodz.android.agiledev.ui.config.ConfigLayoutActivity;
import com.lodz.android.agiledev.ui.crash.CrashTestActivity;
import com.lodz.android.agiledev.ui.customview.CustomViewTestActivity;
import com.lodz.android.agiledev.ui.design.bottomsheet.BottomSheetsTestActivity;
import com.lodz.android.agiledev.ui.design.cardview.CardViewTestActivity;
import com.lodz.android.agiledev.ui.design.coordinator.CoordinatorTestActivity;
import com.lodz.android.agiledev.ui.dialog.DialogTestActivity;
import com.lodz.android.agiledev.ui.dialogfragment.DialogFragmentTestActivity;
import com.lodz.android.agiledev.ui.download.DownloadTestActivity;
import com.lodz.android.agiledev.ui.drawer.DrawerTestActivity;
import com.lodz.android.agiledev.ui.idcard.IdcardTestActivity;
import com.lodz.android.agiledev.ui.image.GlideActivity;
import com.lodz.android.agiledev.ui.index.IndexBarTestActivity;
import com.lodz.android.agiledev.ui.keyboard.KeyboardTestActivity;
import com.lodz.android.agiledev.ui.location.LocationActivity;
import com.lodz.android.agiledev.ui.media.RecordActivity;
import com.lodz.android.agiledev.ui.mvc.MvcDemoActivity;
import com.lodz.android.agiledev.ui.mvp.MvpDemoActivity;
import com.lodz.android.agiledev.ui.notification.NotificationActivity;
import com.lodz.android.agiledev.ui.photopicker.PhotoPickerTestActivity;
import com.lodz.android.agiledev.ui.restore.RestoreTestActivity;
import com.lodz.android.agiledev.ui.retrofit.RetrofitTestActivity;
import com.lodz.android.agiledev.ui.rv.anim.AnimRecyclerViewActivity;
import com.lodz.android.agiledev.ui.rv.decoration.ItemDecorationTestActivity;
import com.lodz.android.agiledev.ui.rv.drag.DragRecyclerViewActivity;
import com.lodz.android.agiledev.ui.rv.head.HeadRecyclerViewActivity;
import com.lodz.android.agiledev.ui.rv.refresh.RefreshTestActivity;
import com.lodz.android.agiledev.ui.rv.snap.RvSnapActivity;
import com.lodz.android.agiledev.ui.rv.swipe.SwipeRecyclerViewActivity;
import com.lodz.android.agiledev.ui.rxjava.RxTestActivity;
import com.lodz.android.agiledev.ui.security.EncryptTestActivity;
import com.lodz.android.agiledev.ui.share.ShareAnimationActivity;
import com.lodz.android.agiledev.ui.threadpool.ThreadPoolActivity;
import com.lodz.android.agiledev.ui.toast.ToastTestActivity;
import com.lodz.android.agiledev.ui.webview.PgWebViewActivity;
import com.lodz.android.agiledev.ui.webview.WebViewTestActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.adapter.decoration.SectionItemDecoration;
import com.lodz.android.component.widget.adapter.decoration.StickyItemDecoration;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.component.widget.index.IndexBar;
import com.lodz.android.core.utils.ArrayUtils;
import com.lodz.android.core.utils.DensityUtils;

import java.util.Arrays;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页面
 * Created by zhouL on 2017/8/30.
 */

public class MainActivity extends BaseActivity{

    /**
     * 启动
     * @param context 上下文
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    /** 索引标题 */
    private static final String[] INDEX_TITLE = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    private static final List<MainBean> MAIN_DATA_LIST = Arrays.asList(
            new MainBean("弹框测试", "T", DialogTestActivity.class),
            new MainBean("视频录制测试", "S", RecordActivity.class),
            new MainBean("RV拖拽测试", "R", DragRecyclerViewActivity.class),
            new MainBean("RV动画测试", "R", AnimRecyclerViewActivity.class),
            new MainBean("RV带头/底部测试", "R", HeadRecyclerViewActivity.class),
            new MainBean("RV刷新/加载更多测试", "R", RefreshTestActivity.class),
            new MainBean("RV侧滑菜单测试", "R", SwipeRecyclerViewActivity.class),
            new MainBean("崩溃测试", "B", CrashTestActivity.class),
            new MainBean("基础控件配置", "J", ConfigLayoutActivity.class),
            new MainBean("照片选择器测试", "Z", PhotoPickerTestActivity.class),
            new MainBean("定位测试", "D", LocationActivity.class),
            new MainBean("通知测试", "T", NotificationActivity.class),
            new MainBean("Retrofit测试", "R", RetrofitTestActivity.class),
            new MainBean("RV装饰器测试类", "R", ItemDecorationTestActivity.class),
            new MainBean("线程池测试类", "X", ThreadPoolActivity.class),
            new MainBean("Rxjava测试类", "R", RxTestActivity.class),
            new MainBean("MVP模式测试类", "M", MvpDemoActivity.class),
            new MainBean("Coordinator测试类", "C", CoordinatorTestActivity.class),
            new MainBean("DialogFragment测试类", "D", DialogFragmentTestActivity.class),
            new MainBean("Glide测试", "G", GlideActivity.class),
            new MainBean("注解测试类", "Z", AnnotationTestActivity.class),
            new MainBean("侧滑栏测试类", "C", DrawerTestActivity.class),
            new MainBean("下载测试类", "X", DownloadTestActivity.class),
            new MainBean("MVC模式测试类", "M", MvcDemoActivity.class),
            new MainBean("身份证号码测试类", "S", IdcardTestActivity.class),
            new MainBean("BottomSheets测试类", "B", BottomSheetsTestActivity.class),
            new MainBean("CardView测试类", "C", CardViewTestActivity.class),
            new MainBean("共享元素动画", "G", ShareAnimationActivity.class),
            new MainBean("索引栏测试类", "S", IndexBarTestActivity.class),
            new MainBean("自定义控件测试类", "Z", CustomViewTestActivity.class),
            new MainBean("WebView和JS交互测试类", "W", WebViewTestActivity.class),
            new MainBean("加载进度条的WebView", "J", PgWebViewActivity.class),
            new MainBean("Toast测试类", "T", ToastTestActivity.class),
            new MainBean("RvSnap测试类", "R", RvSnapActivity.class),
            new MainBean("设备管理功能测试", "S", AdminTestActivity.class),
            new MainBean("加密测试类", "J", EncryptTestActivity.class),
            new MainBean("自定义键盘测试类", "Z", KeyboardTestActivity.class),
            new MainBean("后台回收应用数据保存测试", "H", RestoreTestActivity.class)
            );

    /** 标题名称 */
    public static final String EXTRA_TITLE_NAME = "extra_title_name";

    /** 列表 */
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    /** 索引栏 */
    @BindView(R.id.index_bar)
    IndexBar mIndexBar;
    /** 提示控件 */
    @BindView(R.id.hint)
    TextView mHintTv;

    private MainAdapter mAdapter;
    private List<MainBean> mList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBar(getTitleBarLayout());
        initRecyclerView();
        mIndexBar.setHintTextView(mHintTv);
    }

    private void initTitleBar(TitleBarLayout titleBarLayout) {
        titleBarLayout.setTitleName(R.string.main_title);
        TextView refreshBtn = new TextView(getContext());
        refreshBtn.setText(R.string.main_change_mood);
        refreshBtn.setPadding(DensityUtils.dp2px(getContext(), 15), 0 , DensityUtils.dp2px(getContext(), 15), 0);
        refreshBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.notifyDataSetChanged();
            }
        });
        titleBarLayout.addExpandView(refreshBtn);
        titleBarLayout.needBackButton(false);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mAdapter = new MainAdapter(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(getItemDecoration());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private RecyclerView.ItemDecoration getItemDecoration() {
        return StickyItemDecoration.<String>create(getContext())
                .setOnSectionCallback(new SectionItemDecoration.OnSectionCallback<String>() {
                    @Override
                    public String getSourceItem(int position) {
                        return mList.get(position).getSortStr();
                    }
                })
                .setSectionTextSize(16)
                .setSectionHeight(30)
                .setSectionTextTypeface(Typeface.DEFAULT_BOLD)
                .setSectionTextColorRes(R.color.color_00a0e9)
                .setSectionTextPaddingLeftDp(8)
                .setSectionBgColorRes(R.color.color_f0f0f0);
    }

    @Override
    protected boolean onPressBack() {
        App.get().exit();
        return true;
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<MainBean>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, MainBean item, int position) {
                Intent intent = new Intent(getContext(), item.getCls());
                intent.putExtra(EXTRA_TITLE_NAME, item.getTitleName());
                startActivity(intent);
            }
        });

        mIndexBar.setOnIndexListener(new IndexBar.OnIndexListener() {
            @Override
            public void onStart(int position, String indexText) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                layoutManager.scrollToPositionWithOffset(ArrayUtils.getPositionByIndex(mList, ArrayUtils.arrayToList(INDEX_TITLE), indexText), 0);
            }

            @Override
            public void onEnd() {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mList = ArrayUtils.groupList(MAIN_DATA_LIST, ArrayUtils.arrayToList(INDEX_TITLE));
        mIndexBar.setIndexList(ArrayUtils.arrayToList(INDEX_TITLE));
        mAdapter.setData(mList);
        showStatusCompleted();
    }
}
