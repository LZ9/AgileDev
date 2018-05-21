package com.lodz.android.agiledev.ui.idcard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.component.widget.base.SearchTitleBarLayout;
import com.lodz.android.core.utils.DateUtils;
import com.lodz.android.core.utils.IdCardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 身份证号码测试类
 * Created by zhouL on 2018/4/17.
 */
public class IdcardTestActivity extends AbsActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, IdcardTestActivity.class);
        context.startActivity(starter);
    }

    /** 搜索标题框 */
    @BindView(R.id.search_title_layout)
    SearchTitleBarLayout mSearchTitleBarLayout;
    /** 结果 */
    @BindView(R.id.result)
    TextView mResultTv;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_id_card_test_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mSearchTitleBarLayout.setOnBackBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSearchTitleBarLayout.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mSearchTitleBarLayout.getInputText())) {
                    mResultTv.setTextColor(Color.RED);
                    mResultTv.setText(R.string.idcard_empty_tips);
                    return;
                }
                if (mSearchTitleBarLayout.getInputText().length() != 18){
                    mResultTv.setTextColor(Color.RED);
                    mResultTv.setText(R.string.idcard_length_error);
                    return;
                }
                checkIdcard(mSearchTitleBarLayout.getInputText());
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

}
