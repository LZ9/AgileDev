package com.lodz.android.agiledev.ui.reflect;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.agiledev.R;
import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.core.utils.ReflectUtils;

import java.util.List;


/**
 * 反射测试类
 * Created by zhouL on 2017/3/30.
 */
public class ReflectActivity extends AbsActivity{

    private TextView mMsgTextView;
    /** 获取所有构造函数名称 */
    private Button mConstructorNameButton;
    /** 获取对象里的变量名称 */
    private Button mFieldNameButton;
    /** 获取方法名 */
    private Button mMethodNameButton;
    /** 获取对象里变量的值 */
    private Button mFieldValueButton;
    /** 执行函数 */
    private Button mExecuteFunctionButton;
    /** 设置值并读取测试 */
    private Button mSetValue;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_reflect_layout;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        mMsgTextView = findViewById(R.id.text_msg);
        mConstructorNameButton = findViewById(R.id.get_constructor_name);
        mFieldNameButton = findViewById(R.id.get_field_name);
        mMethodNameButton = findViewById(R.id.get_method_name);
        mFieldValueButton = findViewById(R.id.get_field_value);
        mExecuteFunctionButton = findViewById(R.id.execute_function);
        mSetValue = findViewById(R.id.set_value);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mConstructorNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getConstructorName();
            }
        });

        mFieldNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFieldName();
            }
        });

        mMethodNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMethodName();
            }
        });

        mFieldValueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFieldValue();
            }
        });

        mExecuteFunctionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                executeFunction();
                executeTest();
            }
        });

        mSetValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValue();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }


    /** 获取所有构造函数名称 */
    private void getConstructorName() {
        Class<?> c =  ReflectUtils.getClassForName("com.lodz.android.agiledev.ui.reflect.ReflectBean");
        if (c == null){
            return;
        }
        List<String> list = ReflectUtils.getConstructorName(c);
        String str = c.getSimpleName() + "\n" + list.toString();
        mMsgTextView.setText(str);
    }

    /** 获取对象里的变量名称 */
    private void getFieldName() {
        Class<?> c =  ReflectUtils.getClassForName("com.lodz.android.agiledev.ui.reflect.ReflectBean");
        if (c == null){
            return;
        }
        List<String> list = ReflectUtils.getFieldName(c);
        String str = c.getSimpleName() + "\n" + list.toString();
        mMsgTextView.setText(str);
    }

    /** 获取方法名 */
    private void getMethodName() {
        Class<?> c =  ReflectUtils.getClassForName("com.lodz.android.agiledev.ui.reflect.ReflectBean");
        if (c == null){
            return;
        }
        List<String> list = ReflectUtils.getMethodName(c);
        String str = c.getSimpleName() + "\n" + list.toString();
        mMsgTextView.setText(str);
    }

    /** 获取对象里变量的值 */
    private void getFieldValue() {
        Class<?> c =  ReflectUtils.getClassForName("com.lodz.android.agiledev.ui.reflect.ReflectBean");
        if (c == null){
            mMsgTextView.setText("找不到该类");
            return;
        }
        String str = c.getSimpleName() + "\n";
        Object object = ReflectUtils.getObject(c);
        if (object == null){
            mMsgTextView.setText("没有无参构造函数");
            return;
        }
        List<String> list = ReflectUtils.getFieldName(c);
        for (String name: list) {
            try {
                Object o = ReflectUtils.getFieldValue(c, object, name);
                if (o != null){
                    str += o.toString() + "\n";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mMsgTextView.setText(str);
    }

    /** 执行某个函数 */
    private void executeFunction() {
        Class<?> c =  ReflectUtils.getClassForName("com.lodz.android.agiledev.ui.reflect.ReflectBean");
        if (c == null){
            mMsgTextView.setText("找不到该类");
            return;
        }
        String str = c.getSimpleName() + "\n";
        Object object = ReflectUtils.getObject(c);
        if (object == null){
            mMsgTextView.setText("没有无参构造函数");
            return;
        }

        List<String> list = ReflectUtils.getMethodName(c);
        for (String name: list) {
            Object o = ReflectUtils.executeFunction(c, object, name, null, null);
            if (o != null){
                str += o.toString() + "\n";
            }
        }
        mMsgTextView.setText(str);
    }

    private void executeTest() {
        Class<?> c =  ReflectUtils.getClassForName("com.lodz.android.agiledev.ui.reflect.ReflectBean");
        if (c == null){
            mMsgTextView.setText("找不到该类");
            return;
        }
        String str = c.getSimpleName() + "\n";
        Object object = ReflectUtils.getObject(c);
        if (object == null){
            mMsgTextView.setText("没有无参构造函数");
            return;
        }

        Object o = ReflectUtils.executeFunction(c, object, "test", new Class[]{int.class}, new Object[]{5});
        if (o != null){
            str += o.toString() + "\n";
        }
        mMsgTextView.setText(str);
    }


    private void setValue() {
        Class<?> c =  ReflectUtils.getClassForName("com.lodz.android.agiledev.ui.reflect.ReflectBean");
        if (c == null){
            mMsgTextView.setText("找不到该类");
            return;
        }
        String str = c.getSimpleName() + "\n";
        Object o = ReflectUtils.getObject(c);
        if (o == null){
            mMsgTextView.setText("没有无参构造函数");
            return;
        }
        Object valueOld = ReflectUtils.getFieldValue(c, o, "nationality");
        if (valueOld != null){
            str += valueOld.toString() + "\n";
        }
        boolean isSuccess = ReflectUtils.setFieldValue(c, o, "nationality", "Japan");
        str += "设置 " + isSuccess + "\n";
        Object valueNew = ReflectUtils.getFieldValue(c, o, "nationality");
        if (valueNew != null){
            str += valueNew.toString() + "\n";
        }
        mMsgTextView.setText(str);
    }
}
