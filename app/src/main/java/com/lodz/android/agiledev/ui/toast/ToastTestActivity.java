package com.lodz.android.agiledev.ui.toast;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.core.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Toast测试类
 * Created by zhouL on 2018/6/6.
 */
public class ToastTestActivity extends BaseActivity{

    /** 短时间 */
    @BindView(R.id.short_duration)
    Button mShortDuration;
    /** 长时间 */
    @BindView(R.id.long_duration)
    Button mLongDuration;
    /** 自定义位置（中间） */
    @BindView(R.id.custom_position)
    Button mCustomPosition;
    /** 带顶部图片 */
    @BindView(R.id.top_img)
    Button mTopImg;
    /** 完全自定义 */
    @BindView(R.id.custom_view)
    Button mCustomView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_toast_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        getTitleBarLayout().setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        // 短时间
        mShortDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(getContext(), "短时间");
            }
        });

        // 长时间
        mLongDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLong(getContext(), "长时间");
            }
        });

        // 自定义位置（中间）
        mCustomPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.create(getContext()).setText("自定义位置（中间）").setShort().setGravity(Gravity.CENTER).show();
            }
        });

        // 带顶部图片
        mTopImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.create(getContext()).setText("带顶部图片").setShort().setTopImg(R.drawable.ic_launcher).show();
            }
        });

        // 完全自定义
        mCustomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View layout = getLayoutInflater().inflate(R.layout.toast_custom_view, null);
                TextView title = layout.findViewById(R.id.title);
                title.setText("Attention");

                ImageView img = layout.findViewById(R.id.img);
                img.setImageResource(R.drawable.ic_collect);

                TextView content = layout.findViewById(R.id.content);
                content.setText("完全自定义Toast");

                ToastUtils.create(getContext()).setView(layout).setShort().setGravity(Gravity.TOP, 100, 500).show();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }
}
