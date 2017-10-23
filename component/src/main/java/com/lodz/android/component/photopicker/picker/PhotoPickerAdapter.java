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

    /** 图片加载接口 */
    private PhotoLoader<String> mPhotoLoader;
    /** 监听器 */
    private Listener mListener;

    /** 未选中图标 */
    private Bitmap mUnselectBitmap;
    /** 已选中图标 */
    private Bitmap mSelectedBitmap;

    public PhotoPickerAdapter(Context context, PhotoLoader<String> photoLoader) {
        super(context);
        this.mPhotoLoader = photoLoader;
        mUnselectBitmap = getUnselectBitmap(android.R.color.holo_green_dark);
        mSelectedBitmap = getSelectedBitmap(android.R.color.holo_green_dark);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PickerViewHolder(getLayoutView(parent, R.layout.component_item_picker_layout));
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        PickerItemBean bean = getItem(position);
        if (bean == null){
            return;
        }
        showItem((PickerViewHolder) holder, bean, position);
    }

    private void showItem(PickerViewHolder holder, final PickerItemBean bean, final int position) {
        setItemViewHeight(holder.itemView, ScreenUtils.getScreenWidth(getContext()) / 3);
        mPhotoLoader.displayImg(getContext(), bean.photoPath, holder.photoImg);
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
        int side = DensityUtils.dp2px(getContext(), 40);

        Bitmap bitmap = Bitmap.createBitmap(side, side, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(getContext(), color));
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(side / 2, side / 2, side / 2 - 30, paint);
        return bitmap;
    }

    /**
     * 获取选中的背景图
     * @param color 颜色
     */
    private Bitmap getSelectedBitmap(@ColorRes int color) {
        int side = DensityUtils.dp2px(getContext(), 40);

        Bitmap bitmap = Bitmap.createBitmap(side, side, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(getContext(), color));
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(side / 2, side / 2, side / 2 - 30, paint);

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(side / 2, side / 2, side / 2 - 45, paint);
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

    public void setListener(Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onSelected(PickerItemBean bean, int position);
    }

}
