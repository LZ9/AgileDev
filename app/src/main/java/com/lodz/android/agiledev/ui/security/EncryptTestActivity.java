package com.lodz.android.agiledev.ui.security;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.core.security.AES;
import com.lodz.android.core.security.MD5;
import com.lodz.android.core.security.RSA;
import com.lodz.android.core.security.SHA1;
import com.lodz.android.core.utils.ScreenUtils;
import com.lodz.android.core.utils.ToastUtils;
import com.lodz.android.core.utils.UiHandler;

import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 加密测试类
 * Created by zhouL on 2018/6/20.
 */
public class EncryptTestActivity extends BaseActivity{

    /** 滚动控件 */
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    /** 结果 */
    @BindView(R.id.result)
    TextView mResultTv;

    /** 输入框 */
    @BindView(R.id.input_edit)
    EditText mInputEdit;

    /** AES秘钥初始化 */
    @BindView(R.id.aes_init_btn)
    Button mAESInitBtn;
    /** AES加密 */
    @BindView(R.id.aes_encrypt_btn)
    Button mAESEncryptBtn;
    /** AES解密 */
    @BindView(R.id.aes_decrypt_btn)
    Button mAESDecryptBtn;
    /** RSA秘钥初始化 */
    @BindView(R.id.rsa_init_btn)
    Button mRsaInitBtn;
    /** RSA公钥加密 */
    @BindView(R.id.rsa_encrypt_btn)
    Button mRsaEncryptBtn;
    /** RSA私钥解密 */
    @BindView(R.id.rsa_decrypt_btn)
    Button mRsaDecryptBtn;

    /** MD5信息摘要 */
    @BindView(R.id.md5_btn)
    Button mMD5Btn;
    /** SHA1信息摘要 */
    @BindView(R.id.sha1_btn)
    Button mSHA1Btn;

    /** 清空 */
    @BindView(R.id.clean_btn)
    Button mCleanBtn;

    /** AES秘钥 */
    private String mAESKey = "";
    /** RSA私钥 */
    private String mRSAPrivateKey = "";
    /** RSA公钥 */
    private String mRSAPublicKey = "";

    /** 内容 */
    private String mContent = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_encrypt_layout;
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

        // AES秘钥初始化
        mAESInitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAESKey = String.valueOf(System.currentTimeMillis()) + (new Random().nextInt(899) + 100);
                mContent = "";
                printResult("生成AES秘钥：" + mAESKey + "         " + mAESKey.length() + "位");
            }
        });

        // AES加密
        mAESEncryptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mAESKey)) {
                    ToastUtils.showShort(getContext(), "您尚未初始化AES秘钥");
                    return;
                }
                if (TextUtils.isEmpty(mInputEdit.getText())) {
                    ToastUtils.showShort(getContext(), "您尚未输入加密内容");
                    return;
                }
                mContent = AES.encrypt(mInputEdit.getText().toString(), mAESKey);
                printResult(mContent == null ? "加密失败" : "密文：" + mContent);
            }
        });

        // AES解密
        mAESDecryptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mAESKey)) {
                    ToastUtils.showShort(getContext(), "您尚未初始化AES秘钥");
                    return;
                }
                if (TextUtils.isEmpty(mContent)) {
                    ToastUtils.showShort(getContext(), "您尚未加密过数据");
                    return;
                }
                String result = AES.decrypt(mContent, mAESKey);
                printResult(result == null ? "解密失败" : "原始内容：" + result);
                mContent = "";
            }
        });

        // RSA秘钥初始化
        mRsaInitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContent = "";
                try {
                    Map<String, Object> initKey = RSA.initKey();
                    mRSAPrivateKey = RSA.getPrivateKeyStr(initKey);
                    mRSAPublicKey = RSA.getPublicKeyStr(initKey);
                    printResult("生成RSA私钥：" + mRSAPrivateKey);
                    printResult("生成RSA公钥：" + mRSAPublicKey);
                } catch (Exception e) {
                    e.printStackTrace();
                    mRSAPrivateKey = "";
                    mRSAPublicKey = "";
                    printResult("生成RSA秘钥失败");
                }
            }
        });

        // RSA公钥加密
        mRsaEncryptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mRSAPublicKey)) {
                    ToastUtils.showShort(getContext(), "您尚未初始化RSA公钥");
                    return;
                }
                if (TextUtils.isEmpty(mInputEdit.getText())) {
                    ToastUtils.showShort(getContext(), "您尚未输入加密内容");
                    return;
                }
                mContent = RSA.encryptByPublicKey(mInputEdit.getText().toString(), mRSAPublicKey);
                printResult(mContent == null ? "加密失败" : "密文：" + mContent);
            }
        });

        // RSA私钥解密
        mRsaDecryptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mRSAPrivateKey)) {
                    ToastUtils.showShort(getContext(), "您尚未初始化RSA私钥");
                    return;
                }
                if (TextUtils.isEmpty(mContent)) {
                    ToastUtils.showShort(getContext(), "您尚未加密过数据");
                    return;
                }
                String result = RSA.decryptByPrivateKey(mContent, mRSAPrivateKey);
                printResult(result == null ? "解密失败" : "原始内容：" + result);
                mContent = "";
            }
        });

        // MD5信息摘要
        mMD5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mInputEdit.getText())) {
                    ToastUtils.showShort(getContext(), "您尚未输入内容");
                    return;
                }
                String result = MD5.md(mInputEdit.getText().toString());
                printResult(result == null ? "信息摘要失败" : "MD5信息摘要：" + result);
            }
        });

        // SHA1信息摘要
        mSHA1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mInputEdit.getText())) {
                    ToastUtils.showShort(getContext(), "您尚未输入内容");
                    return;
                }
                String result = SHA1.md(mInputEdit.getText().toString());
                printResult(result == null ? "信息摘要失败" : "SHA1信息摘要：" + result);
            }
        });

        // 清空
        mCleanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultTv.setText("");
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        showStatusCompleted();
    }

    private void printResult(final String result){
        UiHandler.post(new Runnable() {
            @Override
            public void run() {
                mResultTv.setText(mResultTv.getText() + "\n" + result);
                mScrollView.scrollTo(ScreenUtils.getScreenWidth(getContext()), ScreenUtils.getScreenHeight(getContext()));
            }
        });
    }

}
