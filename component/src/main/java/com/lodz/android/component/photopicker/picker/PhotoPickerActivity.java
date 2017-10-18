package com.lodz.android.component.photopicker.picker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.lodz.android.component.R;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.core.utils.BitmapUtils;
import com.lodz.android.core.utils.DrawableUtils;
import com.lodz.android.core.utils.SelectorUtils;

/**
 * 照片选择页面
 * Created by zhouL on 2017/9/22.
 */

public class PhotoPickerActivity extends AbsActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, PhotoPickerActivity.class);
        context.startActivity(starter);
    }

    /** 确定按钮 */
    private TextView mConfirmBtn;
    /** 列表 */
    private RecyclerView mRecyclerView;
    /** 文件夹名称 */
    private TextView mFolderTextTv;
    /** 更多图片 */
    private ImageView mMoreImg;
    /** 预览按钮 */
    private TextView mPreviewBtn;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.component_activity_picker_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        mConfirmBtn = (TextView) findViewById(R.id.confirm_btn);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mFolderTextTv = (TextView) findViewById(R.id.folder_text);
        mMoreImg = (ImageView) findViewById(R.id.more_img);
        mPreviewBtn = (TextView) findViewById(R.id.preview_btn);

    }

    @Override
    protected void setListeners() {
        super.setListeners();

        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.folder_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (i % 2 == 0){
//                    AnimUtils.startRotateSelf(mMoreImg, 180, 0, 600, true);
//                }else {
//                    AnimUtils.startRotateSelf(mMoreImg, 0, 180, 600, true);
//                }
//                i++;
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        drawConfirmBtn();
        mConfirmBtn.setText(getString(R.string.picker_confirm_num, "1", "9"));
        mPreviewBtn.setTextColor(SelectorUtils.createTxPressedUnableColor(getContext(), android.R.color.white, android.R.color.darker_gray, android.R.color.darker_gray));
    }

    /** 绘制确定按钮 */
    private void drawConfirmBtn() {
        mConfirmBtn.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mConfirmBtn.getViewTreeObserver().removeOnPreDrawListener(this);
                int width = mConfirmBtn.getMeasuredWidth();
                int height = mConfirmBtn.getMeasuredHeight();

                StateListDrawable drawable = SelectorUtils.createBgPressedUnableDrawable(getCornerDrawable(android.R.color.holo_green_light, width, height), getCornerDrawable(android.R.color.white, width, height), getCornerDrawable(android.R.color.darker_gray, width, height));
                if (drawable == null){
                    return true;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mConfirmBtn.setBackground(drawable);
                }else {
                    mConfirmBtn.setBackgroundDrawable(drawable);
                }
                mConfirmBtn.setTextColor(SelectorUtils.createTxPressedUnableColor(getContext(), android.R.color.white, android.R.color.holo_green_light, android.R.color.white));
                return true;
            }
        });
    }

    /**
     * 根据颜色获取圆角Drawable
     * @param color 颜色
     * @param width 宽度
     * @param height 高度
     */
    private Drawable getCornerDrawable(@ColorRes int color, int width, int height){
        Bitmap bitmap = BitmapUtils.drawableToBitmap(DrawableUtils.createColorDrawable(getContext(), color), width, height);
        if (bitmap == null){
            return null;
        }
        Bitmap cornerBitmap = BitmapUtils.createRoundedCornerBitmap(bitmap, 8);
        return DrawableUtils.createBitmapDrawable(getContext(), cornerBitmap);
    }
}
