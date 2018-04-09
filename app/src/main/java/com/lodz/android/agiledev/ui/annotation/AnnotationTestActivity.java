package com.lodz.android.agiledev.ui.annotation;

/*
    java中元注解有四个： @Retention @Target @Document @Inherited；

　　 @Retention：注解的保留位置　　　　　　　　　
　　　　　　@Retention(RetentionPolicy.SOURCE)   //注解仅存在于源码中，在class字节码文件中不包含
　　　　　　@Retention(RetentionPolicy.CLASS)     // 默认的保留策略，注解会在class字节码文件中存在，但运行时无法获得，
　　　　　　@Retention(RetentionPolicy.RUNTIME)  // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
　　
　　@Target:注解的作用目标
　　　　　　　　@Target(ElementType.TYPE)   //接口、类、枚举、注解
　　　　　　　　@Target(ElementType.FIELD) //字段、枚举的常量
　　　　　　　　@Target(ElementType.METHOD) //方法
　　　　　　　　@Target(ElementType.PARAMETER) //方法参数
　　　　　　　　@Target(ElementType.CONSTRUCTOR)  //构造函数
　　　　　　　　@Target(ElementType.LOCAL_VARIABLE)//局部变量
　　　　　　　　@Target(ElementType.ANNOTATION_TYPE)//注解
　　　　　　　　@Target(ElementType.PACKAGE) ///包

     @Document：说明该注解将被包含在javadoc中

　  @Inherited：说明子类可以继承父类中的该注解
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.agiledev.ui.main.MainActivity;
import com.lodz.android.component.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 注解测试类
 * Created by zhouL on 2018/4/9.
 */
public class AnnotationTestActivity extends BaseActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, AnnotationTestActivity.class);
        context.startActivity(starter);
    }

    /** 注解按钮 */
    @BindView(R.id.annotation_btn)
    Button mAnnotationBtn;
    /** 内容 */
    @BindView(R.id.content)
    TextView mContentTv;

    /** 姓名 */
    private String mName = "";
    /** 性别 */
    @Encryption
    private String mSex = "";
    /** 年龄 */
    @Encryption("b")
    private String mAge = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_annotation_test_layout;
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
        mAnnotationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnotationUtils.injectEncryption(AnnotationTestActivity.this);
                mContentTv.setText(mName + "\n" + mSex + "\n" + mAge);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mName = "张三";
        mSex = "男";
        mAge = "40";

        mContentTv.setText(mName + "\n" + mSex + "\n" + mAge);
        showStatusCompleted();
    }
}
