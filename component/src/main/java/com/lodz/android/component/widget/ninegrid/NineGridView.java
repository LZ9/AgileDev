package com.lodz.android.component.widget.ninegrid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.lodz.android.component.R;
import com.lodz.android.component.widget.adapter.decoration.GridItemDecoration;
import com.lodz.android.core.utils.AnimUtils;
import com.lodz.android.core.utils.ArrayUtils;
import com.lodz.android.core.utils.VibratorUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

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
    /** 是否需要拖拽 */
    private boolean isNeedDrag = false;
    /** 是否需要拖拽震动提醒 */
    private boolean isNeedDragVibrate = true;

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
        initRecyclerView();
        configLayout(attrs);
    }

    private void findViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.component_view_nine_grid_layout, this);
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    /** 初始化RecyclerView */
    private void initRecyclerView() {
        mGridLayoutManager = new GridLayoutManager(getContext(), DEFAULT_SPAN_COUNT);
        mGridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mAdapter = new NineGridAdapter(getContext());
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(getItemDecoration());
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mItemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    /** 设置装饰器 */
    private RecyclerView.ItemDecoration getItemDecoration() {
        return GridItemDecoration.create(getContext()).setDividerSpace(1).setDividerInt(Color.TRANSPARENT);
    }

    /** 配置属性 */
    private void configLayout(AttributeSet attrs) {
        TypedArray typedArray = null;
        if (attrs != null){
            typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NineGridView);
        }

        isNeedDrag = typedArray == null || typedArray.getBoolean(R.styleable.NineGridView_isNeedDrag, false);

        isNeedDragVibrate = typedArray == null || typedArray.getBoolean(R.styleable.NineGridView_isNeedDragVibrate, true);

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

    /** 拖拽回调 */
    private ItemTouchHelper.Callback mItemTouchHelperCallback = new ItemTouchHelper.Callback() {

        // 配置拖拽类型
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = (isNeedDrag && viewHolder instanceof NineGridAdapter.NineGridViewHolder)
                    ? ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT : 0;
            int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        // 拖拽
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            if (ArrayUtils.isEmpty(mDataList)){
                return false;
            }
            if (!(viewHolder instanceof NineGridAdapter.NineGridViewHolder) || !(target instanceof NineGridAdapter.NineGridViewHolder)){
                return false;
            }

            // 得到拖动ViewHolder的Position
            int fromPosition = viewHolder.getAdapterPosition();
            // 得到目标ViewHolder的Position
            int toPosition = target.getAdapterPosition();

            if (fromPosition < toPosition){//顺序小到大
                for (int i = fromPosition; i < toPosition; i++){
                    Collections.swap(mDataList, i, i + 1);
                }
            }else {//顺序大到小
                for (int i = fromPosition; i > toPosition; i--){
                    Collections.swap(mDataList, i, i - 1);
                }
            }
            mAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        // 滑动
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        }

        // 当长按选中item时（拖拽开始时）调用
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {

            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && isNeedDragVibrate){
                VibratorUtil.vibrate(getContext(), 100);
            }
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && viewHolder != null && viewHolder.itemView != null){//开始拖拽
                AnimUtils.startScaleSelf(viewHolder.itemView,1.0f, 1.05f, 1.0f, 1.05f, 50, true);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        // 当手指松开时（拖拽完成时）调用
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            if (viewHolder != null && viewHolder.itemView != null){
                AnimUtils.startScaleSelf(viewHolder.itemView,1.05f, 1.0f, 1.05f, 1.0f, 50, true);
            }
            super.clearView(recyclerView, viewHolder);
        }

        // 是否启用长按拖拽效果
        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return false;
        }
    };

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
        // 判断添加的数据长度和已有数据长度之和是否超过总长度
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
        mRecyclerView.requestLayout();
    }

    /** 获取图片数据 */
    public List<String> getPicData(){
        if (mDataList == null){
            return new ArrayList<>();
        }
        return mDataList;
    }

    /** 清空数据 */
    public void clearData(){
        setData(new ArrayList<String>());
    }

    /** 是否允许拖拽 */
    public void setNeedDrag(boolean isNeedDrag) {
        this.isNeedDrag = isNeedDrag;
    }

    /** 是否允许拖拽震动提醒 */
    public void setNeedDragVibrate(boolean isNeedDragVibrate) {
        this.isNeedDragVibrate = isNeedDragVibrate;
    }

    /** 设置监听器 */
    public void setOnNineGridViewListener(OnNineGridViewListener listener){
        if (mAdapter != null){
            mAdapter.setOnNineGridViewListener(listener);
        }
    }
}
