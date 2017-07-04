package com.lodz.android.agiledev.utils.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.lodz.android.component.base.application.BaseApplication;

import java.util.Map;
import java.util.Set;

/**
 * SP帮助类
 * Created by zhouL on 2016/12/26.
 */
public class SharedPreferencesUtils {

    /**
     * 保存String类型数据
     * @param key 键
     * @param value 值
     */
    public static void putString(String key, @Nullable String value) {
        SharedPreferences.Editor editor = BaseApplication.get().getSharedPreferences(SpConfig.SP_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 保存Boolean类型数据
     * @param key 键
     * @param value 值
     */
    public static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = BaseApplication.get().getSharedPreferences(SpConfig.SP_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 保存Float类型数据
     * @param key 键
     * @param value 值
     */
    public static void putFloat(String key, float value) {
        SharedPreferences.Editor editor = BaseApplication.get().getSharedPreferences(SpConfig.SP_NAME, Context.MODE_PRIVATE).edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * 保存Int类型数据
     * @param key 键
     * @param value 值
     */
    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = BaseApplication.get().getSharedPreferences(SpConfig.SP_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 保存Long类型数据
     * @param key 键
     * @param value 值
     */
    public static void putLong(String key, long value) {
        SharedPreferences.Editor editor = BaseApplication.get().getSharedPreferences(SpConfig.SP_NAME, Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 保存StringSet类型数据
     * @param key 键
     * @param values 值
     */
    public static void putStringSet(String key, @Nullable Set<String> values) {
        SharedPreferences.Editor editor = BaseApplication.get().getSharedPreferences(SpConfig.SP_NAME, Context.MODE_PRIVATE).edit();
        editor.putStringSet(key, values);
        editor.apply();
    }

    /** 获取全部的sp数据，没有返回null */
    public static Map<String, ?> getAll() {
        SharedPreferences sp = BaseApplication.get().getSharedPreferences(SpConfig.SP_NAME, Context.MODE_PRIVATE);
        try {
            return sp.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Boolean型数据
     * @param key 键
     * @param defValue 默认值
     */
    public static boolean getBoolean(String key, boolean defValue) {
        SharedPreferences sp = BaseApplication.get().getSharedPreferences(SpConfig.SP_NAME, Context.MODE_PRIVATE);
        try {
            return sp.getBoolean(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 获取Float型数据
     * @param key 键
     * @param defValue 默认值
     */
    public static float getFloat(String key, float defValue) {
        SharedPreferences sp = BaseApplication.get().getSharedPreferences(SpConfig.SP_NAME, Context.MODE_PRIVATE);
        try {
            return sp.getFloat(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 获取Int型数据
     * @param key 键
     * @param defValue 默认值
     */
    public static int getInt(String key, int defValue) {
        SharedPreferences sp = BaseApplication.get().getSharedPreferences(SpConfig.SP_NAME, Context.MODE_PRIVATE);
        try {
            return sp.getInt(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 获取Long型数据
     * @param key 键
     * @param defValue 默认值
     */
    public static long getLong(String key, long defValue) {
        SharedPreferences sp = BaseApplication.get().getSharedPreferences(SpConfig.SP_NAME, Context.MODE_PRIVATE);
        try {
            return sp.getLong(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 获取String型数据
     * @param key 键
     * @param defValue 默认值
     */
    public static String getString(String key, @Nullable String defValue) {
        SharedPreferences sp = BaseApplication.get().getSharedPreferences(SpConfig.SP_NAME, Context.MODE_PRIVATE);
        try {
            return sp.getString(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 获取StringSet型数据
     * @param key 键
     * @param defValues 默认值
     */
    public static Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        SharedPreferences sp = BaseApplication.get().getSharedPreferences(SpConfig.SP_NAME, Context.MODE_PRIVATE);
        try {
            return sp.getStringSet(key, defValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValues;
    }

    /**
     * 删除指定键的数据
     * @param key 键
     */
    public static void remove(String key) {
        SharedPreferences.Editor editor = BaseApplication.get().getSharedPreferences(SpConfig.SP_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }

    /** 清空整个sp数据 */
    public static void clear() {
        SharedPreferences.Editor editor = BaseApplication.get().getSharedPreferences(SpConfig.SP_NAME, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

}
