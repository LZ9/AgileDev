package com.lodz.android.agiledev.ui.download.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.BaseActivity;

import butterknife.ButterKnife;

/**
 * 下载管理
 * Created by zhouL on 2018/4/13.
 */
public class DownloadManagerActivity extends BaseActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, DownloadManagerActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        getTitleBarLayout().setTitleName(R.string.download_manager_title);
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }
}
