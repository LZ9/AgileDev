package com.lodz.android.component.photopicker.picker.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.lodz.android.component.R;
import com.lodz.android.component.photopicker.contract.PhotoLoader;
import com.lodz.android.component.photopicker.picker.PickerUIConfig;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.component.widget.dialog.BaseTopDialog;
import com.lodz.android.core.utils.ArrayUtils;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 图片文件弹框
 * Created by zhouL on 2017/10/19.
 */

public class ImageFolderDialog extends BaseTopDialog{

    private RecyclerView mRecyclerView;

    private ImageFolderAdapter mAdapter;

    private Listener mListener;

    public ImageFolderDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.component_pop_img_folder_layout;
    }

    @Override
    protected void findViews() {
        mRecyclerView = findViewById(R.id.recycler_view);
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mAdapter = new ImageFolderAdapter(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setPhotoLoader(PhotoLoader<String> photoLoader){
        mAdapter.setPhotoLoader(photoLoader);
    }

    public void setData(List<ImageFolderItemBean> list) {
        if (ArrayUtils.isEmpty(list)) {
            mRecyclerView.setVisibility(View.GONE);
            return;
        }
        mRecyclerView.setVisibility(View.VISIBLE);
        mAdapter.setData(list);
        mRecyclerView.smoothScrollToPosition(0);
        mAdapter.notifyDataSetChanged();
    }

    public void setPickerUIConfig(PickerUIConfig config){
        mAdapter.setPickerUIConfig(config);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<ImageFolderItemBean>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, ImageFolderItemBean item, int position) {
                if (mListener != null){
                    mListener.onSelected(getDialogInterface(), item);
                }
            }
        });
    }

    public void setListener(Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onSelected(DialogInterface dialog, ImageFolderItemBean bean);
    }
}
