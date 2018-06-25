package com.lodz.android.agiledev.ui.restore;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.core.log.PrintLog;
import com.lodz.android.core.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 后台回收应用数据保存测试
 * Created by zhouL on 2018/6/25.
 */
public class RestoreTestActivity extends BaseActivity{

    /** 输入框 */
    @BindView(R.id.input_edit)
    EditText mInputEdit;

    /** 结果 */
    @BindView(R.id.result)
    TextView mResultTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_restore_test_layout;
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

    private void print(String log){
        String content = mResultTv.getText().toString() + "\n";
        content += DateUtils.getCurrentFormatString(DateUtils.TYPE_2) + " : " + log;
        mResultTv.setText(content);
        PrintLog.d("testtag", log);
    }

    @Override
    protected Bundle getSaveBundle() {
        if (TextUtils.isEmpty(mInputEdit.getText())) {
            return super.getSaveBundle();
        }
        Bundle bundle = new Bundle();
        bundle.putString("teststr", mInputEdit.getText().toString());
        print("保存：" + mInputEdit.getText().toString());
        return bundle;
    }

    @Override
    protected void onRestore(Bundle bundle) {
        super.onRestore(bundle);
        if (bundle != null && !TextUtils.isEmpty(bundle.getString("teststr"))){
            print("获取：" + bundle.getString("teststr"));
        }
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }
}
