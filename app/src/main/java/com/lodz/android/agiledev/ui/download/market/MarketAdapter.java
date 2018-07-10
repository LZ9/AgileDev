package com.lodz.android.agiledev.ui.download.market;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.bean.AppInfoBean;
import com.lodz.android.component.rx.utils.RxUtils;
import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.imageloader.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import zlc.season.rxdownload3.RxDownload;
import zlc.season.rxdownload3.core.Deleted;
import zlc.season.rxdownload3.core.Downloading;
import zlc.season.rxdownload3.core.Failed;
import zlc.season.rxdownload3.core.Normal;
import zlc.season.rxdownload3.core.Status;
import zlc.season.rxdownload3.core.Succeed;
import zlc.season.rxdownload3.core.Suspend;
import zlc.season.rxdownload3.core.Waiting;

/**
 * 应用列表适配器
 * Created by zhouL on 2018/4/12.
 */
public class MarketAdapter extends BaseRecyclerViewAdapter<AppInfoBean>{

    private Listener mListener;

    public MarketAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataViewHolder(getLayoutView(parent, R.layout.item_market_layout));
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position) {
        AppInfoBean bean = getItem(position);
        if (bean == null){
            return;
        }
        showItem((DataViewHolder) holder, bean);
    }

    private void showItem(DataViewHolder holder, AppInfoBean bean) {
        if (holder.disposable != null){// 先解除holder复用前绑定的订阅对象
            holder.disposable.dispose();
        }
        showImage(holder.appImg, bean.imgUrl);
        holder.appNameTv.setText(bean.appName);
        subscribeMission(holder, bean);
    }

    /** 显示图片 */
    private void showImage(ImageView imageView, String url) {
        ImageLoader.create(getContext())
                .load(url)
                .setFitCenter()
                .into(imageView);
    }

    /** 订阅任务 */
    private void subscribeMission(final DataViewHolder holder, final AppInfoBean bean) {
        //把订阅对象保存进holder
        holder.disposable = RxDownload.INSTANCE.create(bean.mission, false)
                .compose(RxUtils.<Status>ioToMainFlowable())
                .subscribe(new Consumer<Status>() {
                    @Override
                    public void accept(Status status) throws Exception {
                        setViewByStatus(holder, bean, status);
                    }
                });
    }

    /** 根据状态设置界面 */
    private void setViewByStatus(DataViewHolder holder, AppInfoBean bean, Status status){
        if (status == null || status instanceof Normal || status instanceof Deleted) {// app未下载或下载已被删除
            showReady(holder, bean, status);
        }
        if (status instanceof Suspend) {// 暂停下载
            showPause(holder, bean, status);
        }
        if (status instanceof Waiting) {// 等待下载
            showWaiting(holder, bean, status);
        }
        if (status instanceof Downloading) {// 下载中
            showDownloading(holder, bean, status);
        }
        if (status instanceof Failed) {
            showFailed(holder, bean, status);
        }
        if (status instanceof Succeed) {
            showDownloadSucceed(holder, bean, status);
        }
    }

    /** 显示准备状态 */
    private void showReady(final DataViewHolder holder, final AppInfoBean bean, Status status){
        holder.tipsTv.setVisibility(View.VISIBLE);
        holder.tipsTv.setText(R.string.market_app_content);
        holder.downloadingLayout.setVisibility(View.GONE);
        holder.statusBtn.setText(R.string.market_download);
        holder.statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClickDownload(bean, holder.getAdapterPosition());
                }
            }
        });
    }

    /** 显示暂停状态 */
    private void showPause(final DataViewHolder holder, final AppInfoBean bean, Status status) {
        holder.tipsTv.setVisibility(View.GONE);
        holder.downloadingLayout.setVisibility(View.VISIBLE);
        holder.progressBar.setProgress(getProgress(status, holder.progressBar.getMax()));
        holder.percentTv.setText(status.percent());
        holder.statusBtn.setText(R.string.market_download);
        holder.statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClickDownload(bean, holder.getAdapterPosition());
                }
            }
        });
    }

    /** 显示等待下载状态 */
    private void showWaiting(final DataViewHolder holder, final AppInfoBean bean, Status status) {
        holder.tipsTv.setVisibility(View.VISIBLE);
        holder.tipsTv.setText(R.string.market_waiting);
        holder.downloadingLayout.setVisibility(View.GONE);
        holder.statusBtn.setText(R.string.market_pause);
        holder.statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClickPause(bean, holder.getAdapterPosition());
                }
            }
        });
    }

    /** 显示正在下载状态 */
    private void showDownloading(final DataViewHolder holder, final AppInfoBean bean, Status status) {
        holder.tipsTv.setVisibility(View.GONE);
        holder.downloadingLayout.setVisibility(View.VISIBLE);
        holder.progressBar.setProgress(getProgress(status, holder.progressBar.getMax()));
        holder.percentTv.setText(status.percent());
        holder.statusBtn.setText(R.string.market_pause);
        holder.statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClickPause(bean, holder.getAdapterPosition());
                }
            }
        });
    }

    /** 显示下载失败状态 */
    private void showFailed(final DataViewHolder holder, final AppInfoBean bean, Status status) {
        holder.tipsTv.setVisibility(View.VISIBLE);
        holder.tipsTv.setText(R.string.market_download_fail);
        holder.downloadingLayout.setVisibility(View.GONE);
        holder.statusBtn.setText(R.string.market_download);
        holder.statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClickDownload(bean, holder.getAdapterPosition());
                }
            }
        });
    }

    /** 显示下载完成状态  */
    private void showDownloadSucceed(final DataViewHolder holder, final AppInfoBean bean, Status status) {
        holder.tipsTv.setVisibility(View.GONE);
        holder.downloadingLayout.setVisibility(View.VISIBLE);
        holder.progressBar.setProgress(getProgress(status, holder.progressBar.getMax()));
        holder.percentTv.setText(status.percent());
        holder.statusBtn.setText(R.string.market_delete);
        holder.statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClickDelete(bean, holder.getAdapterPosition());
                }
            }
        });
    }

    /** 获取当前下载进度（0~max） */
    public int getProgress(Status status, int max){
        if (status == null){
            return 0;
        }

        long totalSize = status.getTotalSize();
        long downloadedSize = status.getDownloadSize();

        if (totalSize == 0){
            return 0;
        }
        return (int) (max * downloadedSize / totalSize);
    }

    class DataViewHolder extends RecyclerView.ViewHolder{
        /** 应用图片 */
        @BindView(R.id.app_img)
        ImageView appImg;
        /** 应用名称 */
        @BindView(R.id.app_name)
        TextView appNameTv;
        /** 提示语 */
        @BindView(R.id.tips)
        TextView tipsTv;
        /** 下载布局 */
        @BindView(R.id.downloading_layout)
        ViewGroup downloadingLayout;
        /** 进度条 */
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;
        /** 下载百分比 */
        @BindView(R.id.percent)
        TextView percentTv;
        /** 状态按钮 */
        @BindView(R.id.status_btn)
        Button statusBtn;

        /** 下载任务订阅对象 */
        private Disposable disposable;

        private DataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setListener(Listener listener){
        mListener = listener;
    }

    public interface Listener{
        /** 点击下载 */
        void onClickDownload(AppInfoBean bean, int position);
        /** 点击暂停 */
        void onClickPause(AppInfoBean bean, int position);
        /** 点击删除 */
        void onClickDelete(AppInfoBean bean, int position);
    }
}
