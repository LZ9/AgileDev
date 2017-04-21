package com.lodz.android.agiledev.ui.fragmentactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhouL on 2017/4/20.
 */

public class LoginMainFragment extends BaseFragment {

    public static LoginMainFragment newInstance(){
        LoginMainFragment fragment = new LoginMainFragment();
        return fragment;
    }

    @BindView(R.id.login_btn)
    Button mLoginBtn;

    @BindView(R.id.regist_btn)
    Button mRegistBtn;

    private Listener mListener;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_main_layout;
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

        mRegistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClickRegist();
                }
            }
        });
    }

    @Override
    protected void initData(View view) {
        super.initData(view);
        showStatusCompleted();
    }

    public void setListener(Listener listener){
        mListener = listener;
    }

    public interface Listener{
        void onClickLogin();

        void onClickRegist();
    }
}
