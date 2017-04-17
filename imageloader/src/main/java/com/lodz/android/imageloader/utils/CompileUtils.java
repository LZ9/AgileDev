package com.lodz.android.imageloader.utils;

/**
 * 依赖判断帮助类
 * Created by zhouL on 2017/4/17.
 */
public class CompileUtils {

    /**
     * 指定的类是否存在
     * @param classFullName 类的完整包名
     */
    public static boolean isClassExists(String classFullName) {
        try {
            Class c = Class.forName(classFullName);
            if (c != null){
                return true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
