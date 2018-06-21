package com.lodz.android.core.security;

import android.text.TextUtils;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密
 * Created by zhouL on 2017/1/4.
 */
public class AES {

    /**
     * AES java-javascript 只支持部填充摸索
     */
    private static final String FORMAT = "AES/CBC/NoPadding";
    /**
     * AES 加密key，必须为16位
     */
    public static final String KEY = "com.lodz.android";
    /**
     * AES 偏移量，必须为16位
     */
    private static final String IV = "123456abc2345678";


    private AES() {
    }

    /**
     * 加密
     * @param data 原始内容
     * @param key 秘钥，必须为16位
     */
    public static String encrypt(String data, String key) {
        return encrypt(data.getBytes(), key);
    }

    /**
     * 加密
     * @param dataBytes 原始内容
     * @param key 秘钥，必须为16位
     */

    public static String encrypt(byte[] dataBytes, String key) {
        if (dataBytes == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(FORMAT);
            int blockSize = cipher.getBlockSize();

            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return Base64.encodeToString(encrypted, android.util.Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     * @param data 密文
     * @param key 秘钥，必须为16位
     */
    public static String decrypt(String data, String key) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        try {
            byte[] decrypt = Base64.decode(data, android.util.Base64.NO_WRAP);

            Cipher cipher = Cipher.getInstance(FORMAT);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(decrypt);
            String originalString = new String(original);
            return TextUtils.isEmpty(originalString) ? "" : originalString.trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
