package com.lodz.android.component.photopicker.picker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lodz.android.component.R;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.core.utils.AnimUtils;

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

    private int i = 1;

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
                if (i % 2 == 0){
                    AnimUtils.startRotateSelf(mMoreImg, 180, 0, 600, true);
                }else {
                    AnimUtils.startRotateSelf(mMoreImg, 0, 180, 600, true);
                }
                i++;
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
//        ColorDrawable drawable = new ColorDrawable(ContextCompat.getColor(getContext(), android.R.color.holo_blue_light));
//        Bitmap bitmap = BitmapUtils.drawableToBitmap(drawable, mConfirmBtn.getWidth(), mConfirmBtn.getHeight());
//        mConfirmBtn.setBackgroundDrawable(new BitmapDrawable(getContext().getResources(), bitmap));
    }

}
