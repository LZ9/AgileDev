package com.lodz.android.component.photopicker.picker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lodz.android.component.R;
import com.lodz.android.component.photopicker.contract.PhotoLoader;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.core.utils.DensityUtils;
import com.lodz.android.core.utils.ScreenUtils;

/**
 * 照片选择适配器
 * Created by zhouL on 2017/10/19.
 */

public class PhotoPickerAdapter extends BaseRecyclerViewAdapter<PickerItemBean>{

    /** 相机 */
    private static final int VIEW_TYPE_CAMERA = 0;
    /** 图片 */
    private static final int VIEW_TYPE_ITEM = 1;

    /** 图片加载接口 */
    private PhotoLoader<String> mPhotoLoader;
    /** 监听器 */
    private Listener mListener;

    /** 未选中图标 */
    private Bitmap mUnselectBitmap;
    /** 已选中图标 */
    private Bitmap mSelectedBitmap;

    /** 是否需要相机 */
    private boolean isNeedCamera = false;
    /** UI配置 */
    private PickerUIConfig mUIConfig;

    public PhotoPickerAdapter(Context context, PhotoLoader<String> photoLoader, boolean isNeedCamera, PickerUIConfig config) {
        super(context);
        this.mPhotoLoader = photoLoader;
        this.isNeedCamera = isNeedCamera;
        this.mUIConfig = config;
        mUnselectBitmap = getUnselectBitmap(android.R.color.holo_green_dark);
        mSelectedBitmap = getSelectedBitmap(android.R.color.holo_green_dark);
    }

    @Override
    public int getItemViewType(int position) {
        if (isNeedCamera && position == 0){// 需要相机且是第一个item
            return VIEW_TYPE_CAMERA;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return isNeedCamera ? super.getItemCount() + 1 : super.getItemCount();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewType == VIEW_TYPE_CAMERA ?
                new PickerCameraViewHolder(getLayoutView(parent, R.layout.component_item_picker_camera_layout)) :
                new PickerViewHolder(getLayoutView(parent, R.layout.component_item_picker_layout));
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PickerCameraViewHolder){
            showCameraItem((PickerCameraViewHolder) holder);
            return;
        }

        PickerItemBean bean = getItem(isNeedCamera ? position - 1 : position);
        if (bean == null){
            return;
        }
        showItem((PickerViewHolder) holder, bean, position);
    }

    private void showCameraItem(PickerCameraViewHolder holder) {
        setItemViewHeight(holder.itemView, ScreenUtils.getScreenWidth(getContext()) / 3);
        if (mUIConfig.getCameraImg() != 0){
            holder.cameraBtn.setImageResource(mUIConfig.getCameraImg());
        }
        holder.cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClickCamera();
                }
            }
        });
    }

    private void showItem(PickerViewHolder holder, final PickerItemBean bean, final int position) {
        setItemViewHeight(holder.itemView, ScreenUtils.getScreenWidth(getContext()) / 3);
        if (mPhotoLoader != null){
            mPhotoLoader.displayImg(getContext(), bean.photoPath, holder.photoImg);
        }
        holder.selectIconImg.setImageBitmap(bean.isSelected ? mSelectedBitmap : mUnselectBitmap);
        holder.selectIconImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onSelected(bean, position);
                }
            }
        });
        holder.maskView.setVisibility(bean.isSelected ? View.VISIBLE : View.GONE);
    }

    /**
     * 获取未选中的背景图
     * @param color 颜色
     */
    private Bitmap getUnselectBitmap(@ColorRes int color) {
        int side = ScreenUtils.getScreenWidth(getContext()) / 3 / 4;

        Bitmap bitmap = Bitmap.createBitmap(side, side, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(getContext(), color));
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(side / 2, side / 2, side / 2 - DensityUtils.dp2px(getContext(), 2), paint);
        return bitmap;
    }

    /**
     * 获取选中的背景图
     * @param color 颜色
     */
    private Bitmap getSelectedBitmap(@ColorRes int color) {
        int side = ScreenUtils.getScreenWidth(getContext()) / 3 / 4;

        Bitmap bitmap = Bitmap.createBitmap(side, side, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(getContext(), color));
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(side / 2, side / 2, side / 2 - DensityUtils.dp2px(getContext(), 2), paint);

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(side / 2, side / 2, side / 2 - DensityUtils.dp2px(getContext(), 8), paint);
        return bitmap;
    }

    private class PickerViewHolder extends RecyclerView.ViewHolder{

        /** 照片 */
        private ImageView photoImg;
        /** 选中图标 */
        private ImageView selectIconImg;
        /** 遮罩层 */
        private View maskView;

        private PickerViewHolder(View itemView) {
            super(itemView);
            photoImg = (ImageView) itemView.findViewById(R.id.photo);
            selectIconImg = (ImageView) itemView.findViewById(R.id.select_icon);
            maskView = itemView.findViewById(R.id.mask);
        }
    }

    private class PickerCameraViewHolder extends RecyclerView.ViewHolder{

        /** 相机按钮 */
        private ImageView cameraBtn;

        private PickerCameraViewHolder(View itemView) {
            super(itemView);
            cameraBtn = (ImageView) itemView.findViewById(R.id.camera_btn);
        }
    }


    public void setListener(Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onSelected(PickerItemBean bean, int position);

        void onClickCamera();
    }

    @Override
    protected void setItemClick(RecyclerView.ViewHolder holder, int position) {
        super.setItemClick(holder, isNeedCamera ? position - 1 : position);
    }

    @Override
    protected void setItemLongClick(RecyclerView.ViewHolder holder, int position) {
        super.setItemLongClick(holder, isNeedCamera ? position - 1 : position);
    }

    public void release(){
        mPhotoLoader = null;
    }
}
