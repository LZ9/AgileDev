package com.lodz.android.agiledev.ui.idcard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.core.utils.DateUtils;
import com.lodz.android.core.utils.IdCardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 身份证号码测试类
 * Created by zhouL on 2018/4/17.
 */
public class IdcardTestActivity extends BaseActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, IdcardTestActivity.class);
        context.startActivity(starter);
    }

    /** 身份证输入框 */
    @BindView(R.id.idcard_edit)
    EditText mIdcardEdit;
    /** 校验按钮 */
    @BindView(R.id.check_btn)
    Button mCheckBtn;
    /** 结果 */
    @BindView(R.id.result)
    TextView mResultTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_id_card_test_layout;
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
        mCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mIdcardEdit.getText())) {
                    mResultTv.setTextColor(Color.RED);
                    mResultTv.setText(R.string.idcard_empty_tips);
                    return;
                }

                if (mIdcardEdit.getText().length() != 18){
                    mResultTv.setTextColor(Color.RED);
                    mResultTv.setText(R.string.idcard_length_error);
                    return;
                }

                checkIdcard(mIdcardEdit.getText().toString());
            }
        });
    }

    /**
     * 校验身份证号
     * @param idcard 身份证号
     */
    private void checkIdcard(String idcard) {
        mResultTv.setTextColor(Color.BLACK);
        if (!IdCardUtils.validateIdCard(idcard)){
            mResultTv.setText("您输入的不是身份证号");
            return;
        }
        String result = "身份证号校验成功\n" +
                "省份：" + IdCardUtils.getProvince(idcard) + "\n" +
                "性别：" + IdCardUtils.getSexStr(idcard) + "\n" +
                "出生年月日：" + IdCardUtils.getBirth(idcard, DateUtils.TYPE_6) + "\n" +
                "年：" + IdCardUtils.getYear(idcard) + "\n" +
                "月：" + IdCardUtils.getMonth(idcard) + "\n" +
                "日：" + IdCardUtils.getDay(idcard) + "\n" +
                "年龄：" + IdCardUtils.getAge(idcard) + "\n" ;

        mResultTv.setText(result);

    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }
}
