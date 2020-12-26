package com.lodz.android.core.security;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * MD5信息摘要
 * Created by zhouL on 2017/1/4.
 */
public class MD5 {

    /** 用来将字节转换成 16 进制表示的字符 */
    private final static char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 信息摘要
     * @param file 文件
     */
    public static String md(File file) {
        if (file == null || !file.isFile()) {
            return null;
        }
        FileInputStream fis = null;
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            fis = new FileInputStream(file);
            while (len != -1){
                len = fis.read(buffer, 0, 1024);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return md(buffer);
    }

    /**
     * 信息摘要
     * @param content 内容
     */
    public static String md(String content) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        return md(content.getBytes());
    }

    /**
     * 信息摘要
     * @param bytes byte数组
     */
    public static String md(byte[] bytes) {
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(bytes);
            byte[] tmp = mdTemp.digest(); // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char[] strs = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i]; // 取第 i 个字节
                strs[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                strs[k++] = HEX_DIGITS[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            return new String(strs).toUpperCase(); // 换后的结果转换为字符串
        } catch (Exception e) {
            return null;
        }
    }
}
