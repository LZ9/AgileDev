package com.lodz.android.agiledev.ui.dialogfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.fragment.LazyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 弹框内的测试fragment
 * Created by zhouL on 2018/3/27.
 */

public class TestDialogFragment extends LazyFragment{

    private static final String EXTRA_CONTENT = "extra_content";

    public static TestDialogFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString(EXTRA_CONTENT, content);
        TestDialogFragment fragment = new TestDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /** 内容文字 */
    @BindView(R.id.content_txt)
    TextView mContentTv;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.fragment_test_dialog_layout;
    }

    @Override
    protected void findViews(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initData(View view) {
        super.initData(view);
        Bundle bundle = getArguments();
        if (bundle != null){
            mContentTv.setText(bundle.getString(EXTRA_CONTENT));
        }
    }
}
