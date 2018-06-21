package com.lodz.android.core.security;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * RSA安全编码组件
 * Created by zhouL on 2018/6/20.
 */
public class RSA {

    /** 非对称加密密钥算法 */
    private static final String KEY_ALGORITHM = "RSA";
    /** 加密模式 */
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";

    /** 获取公钥的KEY */
    private static final String PUBLIC_KEY = "RSAPublicKey";
    /** 获取私钥的KEY */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /** RSA密钥长度 默认1024位， 密钥长度必须是64的倍数， 范围在512至65536位之间。 */
    private static final int KEY_SIZE = 512;
    /** RSA最大加密明文大小 */
    private static final int MAX_ENCRYPT_BLOCK = KEY_SIZE / 8 - 11;
    /** RSA最大解密密文大小 */
    private static final int MAX_DECRYPT_BLOCK = KEY_SIZE / 8;


    private RSA() {
    }

    /**
     * 私钥解密
     * @param data 待解密数据
     * @param key  私钥
     * @return byte[] 解密数据
     */
    private static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException, UnsupportedOperationException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, IOException {
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 公钥解密
     * @param data 待解密数据
     * @param key  公钥
     * @return byte[] 解密数据
     */
    private static byte[] decryptByPublicKey(byte[] data, byte[] key) throws NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException, UnsupportedOperationException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, IOException {

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 生成公钥
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 公钥加密
     * @param data 待加密数据
     * @param key  公钥
     * @return byte[] 加密数据
     */
    private static byte[] encryptByPublicKey(byte[] data, byte[] key) throws  NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException, UnsupportedOperationException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, IOException {
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * 私钥加密
     * @param data 待加密数据
     * @param key  私钥
     * @return byte[] 加密数据
     */
    private static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws  NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException, UnsupportedOperationException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, IOException {
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * 取得私钥
     * @param keyMap 密钥Map
     */
    public static byte[] getPrivateKey(Map<String, Object> keyMap){
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }

    /**
     * 取得私钥
     * @param keyMap 密钥Map
     */
    public static String getPrivateKeyStr(Map<String, Object> keyMap){
        return Base64.encodeToString(getPrivateKey(keyMap), Base64.NO_WRAP);
    }

    /**
     * 取得公钥
     * @param keyMap 密钥Map
     */
    public static byte[] getPublicKey(Map<String, Object> keyMap){
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }

    /**
     * 取得公钥
     * @param keyMap 密钥Map
     */
    public static String getPublicKeyStr(Map<String, Object> keyMap){
        return Base64.encodeToString(getPublicKey(keyMap), Base64.NO_WRAP);
    }

    /**
     * 初始化密钥
     * @return Map 密钥Map
     */
    public static Map<String, Object> initKey() throws NoSuchAlgorithmException{
        // 实例化密钥对生成器
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);

        // 初始化密钥对生成器
        keyPairGen.initialize(KEY_SIZE);

        // 生成密钥对
        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        // 封装密钥
        Map<String, Object> keyMap = new HashMap<>(2);

        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);

        return keyMap;
    }

    /**
     * 用公钥加密数据
     * @param data 数据
     * @param key 公钥
     */
    public static String encryptByPublicKey(String data, String key){
        try {
            byte[] encryptByPublicKey = encryptByPublicKey(data.getBytes(), Base64.decode(key, Base64.NO_WRAP));
            return Base64.encodeToString(encryptByPublicKey, Base64.NO_WRAP);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用私钥解密数据
     * @param data 密文
     * @param key 私钥
     */
    public static String decryptByPrivateKey(String data, String key){
        try {
            byte[] datas = Base64.decode(data, Base64.NO_WRAP);
            byte[] decryptByPrivateKey = decryptByPrivateKey(datas, Base64.decode(key, Base64.NO_WRAP));
            return new String(decryptByPrivateKey);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) throws Exception {
//        Map<String, Object> initKey = ToolRSA.initKey();
//
//        RSAPrivateKey priv = (RSAPrivateKey) initKey.get(PRIVATE_KEY);
//        RSAPublicKey publ = (RSAPublicKey) initKey.get(PUBLIC_KEY);
//        System.out.println("pr:::" + Base64.encodeToString(priv.getEncoded(), Base64.NO_WRAP));
//        System.out.println("pu:::" + Base64.encodeToString(publ.getEncoded(), Base64.NO_WRAP));
//
//        String test = "123451辐射对称";
//        String miw = ToolRSA.encryptByPublicKey(test, pu_key);
//        System.out.println("密文:::" + miw);
//        String mingw = ToolRSA.decryptByPrivateKey(miw, pr_key);
//        System.out.println("明文:::" + mingw);
//
//        try {
//            Map<String, Object> initKey = ToolRSA.initKey();
//            RSAPrivateKey priv = (RSAPrivateKey) initKey.get(ToolRSA.PRIVATE_KEY);
//            RSAPublicKey publ = (RSAPublicKey) initKey.get(ToolRSA.PUBLIC_KEY);
//            System.out.println();
//            PrintLog.i("testtag", "pr:::" + Base64.encodeToString(priv.getEncoded(), Base64.NO_WRAP));
//            PrintLog.i("testtag", "pu:::" + Base64.encodeToString(publ.getEncoded(), Base64.NO_WRAP));
//            String test = "123451辐射对称";
//            String miw = ToolRSA.encryptByPublicKey(test, ToolRSA.pu_key);
//            PrintLog.d("testtag", "密文:::" + miw);
//            String mingw = ToolRSA.decryptByPrivateKey(miw, ToolRSA.pr_key);
//            PrintLog.d("testtag", "明文:::" + mingw);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
