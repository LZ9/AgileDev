package com.snxun.core.security;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA1加密
 * Created by zhouL on 2017/1/4.
 */
public class SHA1 {

    /**
     * SHA1加密
     * @param decript 加密串
     */
    public static String sha1(String decript) {
        if (TextUtils.isEmpty(decript)){
            return "";
        }

        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * SHA1加密结果转大写
     * @param decript 加密串
     */
    public static String sha1ToUpperCase(String decript) {
        return sha1(decript).toUpperCase();
    }
}
