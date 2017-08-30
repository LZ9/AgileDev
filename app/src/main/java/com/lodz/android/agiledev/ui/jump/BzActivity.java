package com.lodz.android.agiledev.ui.jump;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lodz.android.agiledev.R;

/**
 * Created by zhouL on 2017/8/30.
 */

public class BzActivity extends AppCompatActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, BzActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_layout);
    }

    //    /** 标题 */
//    @BindView(R.id.title_txt)
//    TextView mTitle;
//
//    @Override
//    protected int getAbsLayoutId() {
//        return R.layout.activity_b_layout;
//    }
//
//    @Override
//    protected void findViews(Bundle savedInstanceState) {
//        ButterKnife.bind(this);
//    }
//
//    @Override
//    protected void initData() {
//        super.initData();
//        mTitle.setText("DzActivity");
//    }
}
