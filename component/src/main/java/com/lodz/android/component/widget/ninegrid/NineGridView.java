package com.lodz.android.component.widget.ninegrid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.lodz.android.component.R;
import com.lodz.android.component.widget.adapter.decoration.GridItemDecoration;
import com.lodz.android.core.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片九宫格控件
 * Created by zhouL on 2018/3/8.
 */

public class NineGridView extends FrameLayout{

    /** 默认列数 */
    private static final int DEFAULT_SPAN_COUNT = 3;
    /** 默认图片最大数 */
    private static final int DEFAULT_MAX_PIC = 1;

    /** 列表 */
    private RecyclerView mRecyclerView;
    /** 适配器 */
    private NineGridAdapter mAdapter;
    /** 网格布局管理器 */
    private GridLayoutManager mGridLayoutManager;

    /** 数据列表 */
    private List<String> mDataList;

    public NineGridView(@NonNull Context context) {
        super(context);
        init(null);
    }

    public NineGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public NineGridView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NineGridView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        findViews();
        if (!isInEditMode()){
            initRecyclerView();
        }
        configLayout(attrs);
    }

    private void findViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.component_view_nine_grid_layout, this);
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    /** 初始化RecyclerView */
    private void initRecyclerView() {
        mGridLayoutManager = new GridLayoutManager(getContext(), DEFAULT_SPAN_COUNT);
        mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mAdapter = new NineGridAdapter(getContext());
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(getItemDecoration());
        mRecyclerView.setAdapter(mAdapter);
    }

    /** 设置装饰器 */
    private RecyclerView.ItemDecoration getItemDecoration() {
        return GridItemDecoration.createDividerInt(getContext(), 1, Color.TRANSPARENT);
    }

    private void configLayout(AttributeSet attrs) {
        TypedArray typedArray = null;
        if (attrs != null){
            typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NineGridView);
        }
        mAdapter.setNeedAddBtn(typedArray == null || typedArray.getBoolean(R.styleable.NineGridView_isNeedAddBtn, true));

        Drawable add = typedArray == null ? null : typedArray.getDrawable(R.styleable.NineGridView_addBtnDrawable);
        if (add != null){
            mAdapter.setAddBtnDrawable(add);
        }

        mAdapter.setShowDelete(typedArray == null || typedArray.getBoolean(R.styleable.NineGridView_isShowDeleteBtn, true));

        Drawable delete = typedArray == null ? null : typedArray.getDrawable(R.styleable.NineGridView_deleteDrawable);
        if (delete != null){
            mAdapter.setDeleteBtnDrawable(delete);
        }

        mGridLayoutManager.setSpanCount(typedArray == null ? DEFAULT_SPAN_COUNT : typedArray.getInt(R.styleable.NineGridView_spanCount, DEFAULT_SPAN_COUNT));

        mAdapter.setMaxPic(typedArray == null ? DEFAULT_MAX_PIC : typedArray.getInt(R.styleable.NineGridView_maxPic, DEFAULT_MAX_PIC));


        int itemHigh = typedArray == null ? 0 : typedArray.getDimensionPixelSize(R.styleable.NineGridView_itemHigh, 0);
        if (itemHigh > 0){
            mAdapter.setItemHigh(itemHigh);
        }

        if (typedArray != null){
            typedArray.recycle();
        }
    }

    /**
     * 设置是否需要添加图标
     * @param needAddBtn 是否需要添加图标
     */
    public void setNeedAddBtn(boolean needAddBtn){
        if (mAdapter != null){
            mAdapter.setNeedAddBtn(needAddBtn);
        }
    }

    /**
     * 设置添加图标
     * @param resId 图标
     */
    public void setAddBtnDrawable(@DrawableRes int resId) {
        if (mAdapter != null){
            mAdapter.setAddBtnDrawable(ContextCompat.getDrawable(getContext(), resId));
        }
    }

    /**
     * 设置是否显示删除按钮
     * @param isShowDelete 是否显示删除按钮
     */
    public void setShowDelete(boolean isShowDelete) {
        if (mAdapter != null){
            mAdapter.setShowDelete(isShowDelete);
        }
    }

    /**
     * 设置删除图标
     * @param resId 图标
     */
    public void setDeleteBtnDrawable(@DrawableRes int resId) {
        if (mAdapter != null){
            mAdapter.setDeleteBtnDrawable(ContextCompat.getDrawable(getContext(), resId));
        }
    }

    /**
     * 设置最大图片数
     * @param count 最大图片数
     */
    public void setMaxPic(@IntRange(from = 1) int count) {
        if (mAdapter != null){
            mAdapter.setMaxPic(count);
        }
    }

    /** 设置数据 */
    public void setData(@NonNull List<String> data) {
        if (mAdapter == null){
            return;
        }
        mDataList = new ArrayList<>();
        //如果数据大于最大图片数，则取前n位数据
        int length = data.size() > mAdapter.getMaxPic() ? mAdapter.getMaxPic() : data.size();
        for (int i = 0; i < length; i++) {
            mDataList.add(data.get(i));
        }
        mAdapter.setData(mDataList);
        mAdapter.notifyDataSetChanged();
    }

    /** 添加数据 */
    public void addData(@NonNull List<String> data){
        if (mAdapter == null){
            return;
        }
        if (ArrayUtils.isEmpty(mDataList)){
            mDataList = new ArrayList<>();
        }
        int length = (data.size() + mDataList.size()) > mAdapter.getMaxPic() ? (mAdapter.getMaxPic() - mDataList.size()) : data.size();
        for (int i = 0; i < length; i++) {
            mDataList.add(data.get(i));
        }
        mAdapter.setData(mDataList);
        mAdapter.notifyDataSetChanged();
    }

    /** 删除数据 */
    public void removeData(int position) {
        if (mAdapter == null || position >= mDataList.size()){
            return;
        }
        mAdapter.notifyItemRemovedChanged(position);
    }

    /** 设置监听器 */
    public void setOnNineGridViewListener(OnNineGridViewListener listener){
        if (mAdapter != null){
            mAdapter.setOnNineGridViewListener(listener);
        }
    }

}
