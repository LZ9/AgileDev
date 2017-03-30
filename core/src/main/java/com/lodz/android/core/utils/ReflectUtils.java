package com.lodz.android.core.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射帮助类
 * Created by zhouL on 2017/3/22.
 */
public class ReflectUtils {

	/**
	 * 根据类名获取对应的class（类名须包括包名，找不到返回null）
	 * @param classPath 类的地址
	 */
	public static Class<?> getClassForName(String classPath) {
		try {
			return Class.forName(classPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取无参构造函数对象（如果目标对象没有无参构造函数返回null）
	 * @param c 类
	 */
	public static Object getObject(Class<?> c) {
		try {
			return c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取带有参数的构造函数对象（找不到返回null）
	 * @param c 类
	 * @param paramClassTypes 构造函数的参数类型
	 * @param params 构造函数的具体入参
	 */
	public static Object getObject(Class<?> c, Class<?>[] paramClassTypes, Object[] params) {
		try {
			Constructor<?> constructor = c.getDeclaredConstructor(paramClassTypes);
			constructor.setAccessible(true);// 构造函数是private的话需要设置为true
			return constructor.newInstance(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 执行某个函数
	 * @param c 类
	 * @param object 类的对象
	 * @param functionName 函数名字
	 * @param paramClassTypes 函数的参数类型
	 * @param params 具体入参
	 */
	public static Object executeFunction(Class<?> c, Object object, String functionName,
			Class<?>[] paramClassTypes, Object[] params){
		try {
			Method method = c.getDeclaredMethod(functionName, paramClassTypes);// 方法名和参数类型
			method.setAccessible(true);
			return method.invoke(object, params);// 传参并执行
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取方法名
	 * @param c 类
	 */
	public static List<String> getMethodName(Class<?> c) {
		List<String> list = new ArrayList<>();
		Method[] methods = c.getDeclaredMethods();
		for (Method method : methods) {
			list.add(method.getName());
		}
		return list;
	}
	
	/**
	 * 获取对象里变量的值
	 * @param c 类
	 * @param object 类的对象
	 * @param typeName 变量名称
	 */
	public static Object getFieldValue(Class<?> c, Object object, String typeName) {
		try {
			Field field = c.getDeclaredField(typeName); // 因为msg变量是private的，所以不能用getField方法
			field.setAccessible(true);// 设置是否允许访问，因为该变量是private的，所以要手动设置允许访问，如果msg是public的就不需要这行了。
			return field.get(object);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取对象里的变量名称
	 * @param c 类
	 */
	public static List<String> getFieldName(Class<?> c) {
		List<String> list = new ArrayList<>();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			list.add(field.getName());
		}
		return list;
	}
	
	/**
	 * 获取所有构造函数名称
	 * @param c 类
	 */
	public static List<String> getConstructorName(Class<?> c) {
		List<String> list = new ArrayList<>();
		Constructor<?>[] constructors = c.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			list.add(constructor.toString());
		}
		return list;
	}

}
