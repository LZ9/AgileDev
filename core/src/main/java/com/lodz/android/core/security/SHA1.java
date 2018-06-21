package com.lodz.android.core.security;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA1信息摘要
 * Created by zhouL on 2017/1/4.
 */
public class SHA1 {

    /**
     * SHA1信息摘要
     * @param content 内容
     */
    public static String md(String content) {
        if (TextUtils.isEmpty(content)){
            return null;
        }

        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(content.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            // 字节数组转换为 十六进制 数
            for (byte aMessageDigest : messageDigest) {
                String shaHex = Integer.toHexString(aMessageDigest & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
