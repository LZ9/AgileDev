package com.lodz.android.agiledev.ui.jump;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhouL on 2017/8/30.
 */

public class CzActivity extends BaseActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, CzActivity.class);
        context.startActivity(starter);
    }

    /** 标题 */
    @BindView(R.id.title_txt)
    TextView mTitle;

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_b_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        mTitle.setText("CzActivity");
        showStatusCompleted();
    }
}
