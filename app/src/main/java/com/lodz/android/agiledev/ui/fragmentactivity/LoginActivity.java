package com.lodz.android.agiledev.ui.fragmentactivity;


import android.os.Bundle;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.core.utils.ToastUtils;

import butterknife.ButterKnife;

/**
 * 登录页
 * Created by zhouL on 2017/4/20.
 */

public class LoginActivity extends AbsActivity {

    private LoginMainFragment mLoginMainFragment;

    private LoginFragment mLoginFragment;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initFragment();
    }

    private void initFragment() {
        mLoginMainFragment = LoginMainFragment.newInstance();
        mLoginFragment = LoginFragment.newInstance();
        addFragment(R.id.root_up_layout, mLoginMainFragment, "LoginMainFragment");
        addFragment(R.id.root_down_layout, mLoginFragment, "LoginFragment");
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mLoginMainFragment.setListener(new LoginMainFragment.Listener() {
            @Override
            public void onClickLogin() {
                showLogin();
            }

            @Override
            public void onClickRegist() {
                ToastUtils.showShort(getContext(), "onClickRegist");
            }
        });

        mLoginFragment.setListener(new LoginFragment.Listener() {
            @Override
            public void onClickLogin() {
                ToastUtils.showShort(getContext(), "onClickLogin");
            }

            @Override
            public void onClickCancel() {
                showLoginMain();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        showLoginMain();
    }

    private void showLoginMain() {
        showFragment(mLoginMainFragment);
        hideFragment(mLoginFragment);
    }

    private void showLogin() {
        showFragment(mLoginFragment);
        hideFragment(mLoginMainFragment);
    }
}
