package com.lodz.android.agiledev.ui.keyboard;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.agiledev.utils.keyboard.KeyboardManager;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.core.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自定义键盘测试类
 * Created by zhouL on 2018/6/22.
 */
public class KeyboardTestActivity extends BaseActivity{

    /** 输入框 */
    @BindView(R.id.input_edit)
    EditText mInputEdit;
    /** 键盘 */
    @BindView(R.id.keyboard_view)
    KeyboardView mKeyboardView;

    private KeyboardManager mKeyboardManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_keyboard_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        getTitleBarLayout().setTitleName(getIntent().getStringExtra(MainActivity.EXTRA_TITLE_NAME));
        initKeyboard();
    }

    private void initKeyboard() {
        mKeyboardManager = new KeyboardManager(this, mKeyboardView, mInputEdit, KeyboardManager.TYPE_CAR_NUM);
        mKeyboardManager.goneKeyboard();
    }

    @Override
    protected void clickBackBtn() {
        super.clickBackBtn();
        finish();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mKeyboardManager.setOnClickFinishListener(new KeyboardManager.OnClickFinishListener() {
            @Override
            public void onClick(String content) {
                mKeyboardManager.goneKeyboard();
                if (!TextUtils.isEmpty(content)) {
                    ToastUtils.showShort(getContext(), content);
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }
}
