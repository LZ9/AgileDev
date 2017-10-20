package com.lodz.android.component.photopicker.picker.dialog;

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
import android.widget.TextView;

import com.lodz.android.component.R;
import com.lodz.android.component.photopicker.contract.PhotoLoader;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.core.utils.DensityUtils;

/**
 * 图片文件夹列表适配器
 * Created by zhouL on 2017/10/20.
 */

public class ImageFolderAdapter extends BaseRecyclerViewAdapter<ImageFolderIteamBean>{

    /** 图片加载接口 */
    private PhotoLoader<String> mPhotoLoader;

    /** 未选中图标 */
    private Bitmap mUnselectBitmap;
    /** 已选中图标 */
    private Bitmap mSelectedBitmap;

    public ImageFolderAdapter(Context context) {
        super(context);
        mUnselectBitmap = getUnselectBitmap(android.R.color.holo_green_dark);
        mSelectedBitmap = getSelectedBitmap(android.R.color.holo_green_dark);
    }

    /** 设置图片加载接口 */
    public void setPhotoLoader(PhotoLoader<String> photoLoader){
        mPhotoLoader = photoLoader;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageFolderViewHolder(getLayoutView(parent, R.layout.component_item_img_folder_layout));
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        ImageFolderIteamBean bean = getItem(position);
        if (bean == null){
            return;
        }
        showItem((ImageFolderViewHolder) holder, bean, position);
    }

    private void showItem(ImageFolderViewHolder holder, ImageFolderIteamBean bean, int position) {
        mPhotoLoader.displayImg(getContext(), bean.imageFolder.getFirstImagePath(), holder.folderImg);
        holder.floderName.setText(bean.imageFolder.getName());
        holder.count.setText(getContext().getString(R.string.picker_folder_num, String.valueOf(bean.imageFolder.getCount())));
        holder.selectIconImg.setImageBitmap(bean.isSelected ? mSelectedBitmap : mUnselectBitmap);
    }

    private Bitmap getUnselectBitmap(@ColorRes int color){
        int side = DensityUtils.dp2px(getContext(), 30);

        Bitmap bitmap = Bitmap.createBitmap(side, side, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(getContext(), color));
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(side / 2, side / 2, side / 2 - 10, paint);
        return bitmap;
    }

    private Bitmap getSelectedBitmap(@ColorRes int color){
        int side = DensityUtils.dp2px(getContext(), 30);

        Bitmap bitmap = Bitmap.createBitmap(side, side, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(getContext(), color));
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(side / 2, side / 2, side / 2 - 10, paint);

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(side / 2, side / 2, side / 2 - 25, paint);
        return bitmap;
    }

    private class ImageFolderViewHolder extends RecyclerView.ViewHolder{

        /** 文件夹第一张图片 */
        private ImageView folderImg;
        /** 文件夹名称 */
        private TextView floderName;
        /** 文件夹内图片数量 */
        private TextView count;
        /** 选中图标 */
        private ImageView selectIconImg;

        private ImageFolderViewHolder(View itemView) {
            super(itemView);
            folderImg = (ImageView) itemView.findViewById(R.id.folder_img);
            floderName = (TextView) itemView.findViewById(R.id.floder_name);
            count = (TextView) itemView.findViewById(R.id.count);
            selectIconImg = (ImageView) itemView.findViewById(R.id.select_icon);
        }
    }

}
