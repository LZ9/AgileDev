package com.lodz.android.agiledev.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.AbsActivity;

/**
 * Created by zhouL on 2017/8/30.
 */

public class MainActivity extends AbsActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_main_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        super.initData();

    }
}
