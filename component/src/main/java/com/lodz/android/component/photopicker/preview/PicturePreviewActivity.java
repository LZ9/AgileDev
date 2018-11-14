package com.lodz.android.component.photopicker.preview;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lodz.android.component.R;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.component.photopicker.contract.preview.PreviewController;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.component.widget.adapter.snap.ViewPagerSnapHelper;
import com.lodz.android.component.widget.photoview.PhotoView;
import com.lodz.android.component.widget.photoview.PhotoViewAttacher;
import com.lodz.android.core.utils.ArrayUtils;
import com.lodz.android.core.utils.DeviceUtils;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    /** 翻页控件 */
    private RecyclerView mRecyclerView;
    /** 页码提示 */
    private TextView mPagerTipsTv;

    /** 图片数据 */
    private PreviewBean mPreviewBean;
    /** 适配器 */
    private PicturePagerAdapter mAdapter;
    /** 滑动帮助类 */
    private ViewPagerSnapHelper mSnapHelper;

    @Override
    protected void startCreate() {
        super.startCreate();
        mPreviewBean = PreviewManager.sPreviewBean;
    }

    @Override
    protected int getAbsLayoutId() {
        return R.layout.component_activity_preview_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        mRootView = findViewById(R.id.root_view);
        mPagerTipsTv = findViewById(R.id.pager_tips);
        mRecyclerView = findViewById(R.id.recycler_view);
        initRecyclerView();
    }

    @SuppressWarnings("unchecked")
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mAdapter = new PicturePagerAdapter(getContext(), mPreviewBean.isScale, mPreviewBean.photoLoader);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mSnapHelper = new ViewPagerSnapHelper(mPreviewBean.showPosition);
        mSnapHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void initData() {
        super.initData();
        mRootView.setBackgroundColor(ContextCompat.getColor(getContext(), mPreviewBean.backgroundColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DeviceUtils.setStatusBarColor(getContext(), getWindow(), mPreviewBean.statusBarColor);
            DeviceUtils.setNavigationBarColor(getContext(), getWindow(), mPreviewBean.navigationBarColor);
        }
        setPagerNum(mPreviewBean.showPosition);
        mPagerTipsTv.setVisibility(mPreviewBean.isShowPagerText ? View.VISIBLE : View.GONE);
        mPagerTipsTv.setTextColor(ContextCompat.getColor(getContext(), mPreviewBean.pagerTextColor));
        mPagerTipsTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, mPreviewBean.pagerTextSize);

        mAdapter.setData(mPreviewBean.sourceList);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mPreviewBean.showPosition);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void setListeners() {
        super.setListeners();

        mSnapHelper.setOnPageChangeListener(new ViewPagerSnapHelper.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
//                resetPhoto(position);
                setPagerNum(position);
            }
        });

        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<Object>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, Object item, int position) {
                if (mPreviewBean.clickListener != null){
                    mPreviewBean.clickListener.onClick(viewHolder.itemView.getContext(), item, position, mPreviewController);
                }
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseRecyclerViewAdapter.OnItemLongClickListener<Object>() {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, Object item, int position) {
                if (mPreviewBean.longClickListener != null) {
                    mPreviewBean.longClickListener.onLongClick(viewHolder.itemView.getContext(), item, position, mPreviewController);
                }
            }
        });
    }


    /**
     * 还原照片
     * @param position 位置
     */
    private void resetPhoto(int position) {
        RecyclerView.ViewHolder viewHolder = mRecyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder == null){
            return;
        }
        if (viewHolder instanceof PicturePagerAdapter.DataViewHolder){
            PicturePagerAdapter.DataViewHolder holder = (PicturePagerAdapter.DataViewHolder) viewHolder;
            if (holder.photoImg instanceof PhotoView){
                PhotoView photoView = (PhotoView) holder.photoImg;
                PhotoViewAttacher attacher = photoView.getAttacher();
                attacher.update();
            }
        }
    }

    private PreviewController mPreviewController = new PreviewController() {
        @Override
        public void close() {
            finish();
        }
    };

    /** 设置页码 */
    private void setPagerNum(int position){
        mPagerTipsTv.setText(new StringBuffer().append(position + 1).append(" / ").append(ArrayUtils.getSize(mPreviewBean.sourceList)));
    }

    @Override
    public void finish() {
        super.finish();
        mAdapter.release();
        PreviewManager.sPreviewBean.clear();
        mPreviewBean.clear();
    }
}
