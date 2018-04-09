package com.lodz.android.agiledev.ui.annotation;

import android.app.Activity;

import com.lodz.android.core.utils.ReflectUtils;

import java.lang.reflect.Field;

/**
 * Created by zhouL on 2018/4/9.
 */
public class AnnotationUtils {

    public static void injectEncryption(Activity activity) {
        try {
            if (activity == null) {
                return;
            }
            Class cls = activity.getClass();
            if (cls == null) {
                return;
            }

            // 得到activity所有字段
            Field[] fields = activity.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Encryption.class)) {
                    continue;
                }

                Encryption inject = field.getAnnotation(Encryption.class);//得到注解

                String encryptionType = inject.value();// 获取注解参数
                String source = (String) ReflectUtils.getFieldValue(cls, activity, field.getName());

                if (encryptionType.equals("a")) {
                    ReflectUtils.setFieldValue(cls, activity, field.getName(), source+"aa");
                }
                if (encryptionType.equals("b")) {
                    ReflectUtils.setFieldValue(cls, activity, field.getName(), source+"bb");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
