package com.lodz.android.agiledev.ui.fragmentactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhouL on 2017/4/20.
 */

public class LoginFragment extends BaseFragment {

    public static LoginFragment newInstance(){
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @BindView(R.id.account_edit)
    EditText mAccountEditText;

    @BindView(R.id.password_edit)
    EditText mPasswordEditText;

    @BindView(R.id.login_btn)
    Button mLoginBtn;

    @BindView(R.id.cancel_btn)
    Button mCancelBtn;

    private Listener mListener;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_layout;
    }

    @Override
    protected void findViews(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void setListeners(View view) {
        super.setListeners(view);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClickLogin();
                }
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClickCancel();
                }
            }
        });
    }

    @Override
    protected void initData(View view) {
        super.initData(view);
        showStatusCompleted();
    }

    @Override
    public boolean onPressBack() {
        if (mListener != null){
            mListener.onClickCancel();
            return true;
        }
        return super.onPressBack();
    }

    public void setListener(Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onClickLogin();

        void onClickCancel();
    }
}
