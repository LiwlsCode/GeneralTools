package com.leo.g2os.crypto;

import com.leo.g2os.enums.ALGORITHM;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 加解密算法
 *
 * @author Liwl
 */
public class AESUtil {

    /**
     * AES加密
     * iv == null 为 AES/ECB/NoPadding
     * iv != null 为 AES/CBC/NoPadding
     *
     * @param data 加密数据
     * @param key  加密key 16进制
     * @param iv   IvParameter
     * @return 加密结果
     * @throws Exception 加密错误异常
     */
    public static byte[] encrypt(byte[] data, byte[] key, byte[] iv) throws Exception {
        // Security.addProvider(new BouncyCastleProvider());
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = null;
        if (iv == null) {
            cipher = Cipher.getInstance(ALGORITHM.AENP.getValue());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } else {
            cipher = Cipher.getInstance(ALGORITHM.ACNP.getValue());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        }
        return cipher.doFinal(data);
    }

    /**
     * AES加密 <br>
     * iv == null 为 AES/ECB/NoPadding<br>
     * iv != null 为 AES/CBC/NoPadding<br>
     */

    /**
     * AES加密
     * iv == null 为 AES/ECB/NoPadding
     * iv != null 为 AES/CBC/NoPadding
     *
     * @param data     加密数据
     * @param key      加密key 16进制
     * @param iv       IvParameter
     * @param provider Provider
     * @return 加密结果
     * @throws Exception 加密错误异常
     */
    public static byte[] encrypt(byte[] data, byte[] key, byte[] iv, Provider provider) throws Exception {

        // Security.addProvider(new BouncyCastleProvider());
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = null;
        if (iv == null) {
            cipher = Cipher.getInstance(ALGORITHM.AENP.getValue(), provider);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } else {
            cipher = Cipher.getInstance(ALGORITHM.ACNP.getValue(), provider);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        }
        return cipher.doFinal(data);
    }

    /**
     * AES解密 AES/CBC/NoPadding
     * iv == null 为 AES/ECB/NoPadding
     * iv != null 为 AES/CBC/NoPadding
     *
     * @param data     解密数据
     * @param key      解密key 16进制
     * @param iv       IvParameter
     * @return 解密结果
     * @throws Exception 解密错误异常
     */
    public static byte[] decrypt(byte[] data, byte[] key, byte[] iv) throws Exception {
        // Security.addProvider(new BouncyCastleProvider());
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = null;
        if (iv == null) {
            cipher = Cipher.getInstance(ALGORITHM.AENP.getValue());
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        } else {
            cipher = Cipher.getInstance(ALGORITHM.ACNP.getValue());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        }
        return cipher.doFinal(data);
    }

    /**
     * AES解密 AES/CBC/NoPadding
     * iv == null 为 AES/ECB/NoPadding
     * iv != null 为 AES/CBC/NoPadding
     *
     * @param data     解密数据
     * @param key      解密key 16进制
     * @param iv       IvParameter
     * @param provider Provider
     * @return 解密结果
     * @throws Exception 解密错误异常
     */
    public static byte[] decrypt(byte[] data, byte[] key, byte[] iv, Provider provider) throws Exception {

        // Security.addProvider(new BouncyCastleProvider());
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = null;
        if (iv == null) {
            cipher = Cipher.getInstance(ALGORITHM.AENP.getValue(), provider);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        } else {
            cipher = Cipher.getInstance(ALGORITHM.ACNP.getValue(), provider);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        }
        return cipher.doFinal(data);
    }

    /**
     * AES256 加密算法 未测试
     *
     * @param data encrypt data
     * @param key encrypt key
     * @return encrypt result
     * @throws Exception encrypt exception
     */
    public static byte[] encrypt256(byte[] data, byte[] key) throws Exception {
        try {
            // "AES"：请求的密钥算法的标准名称
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            // 256：密钥生成参数；securerandom：密钥生成器的随机源
            SecureRandom securerandom = new SecureRandom(tohash256Deal(key));
            kgen.init(256, securerandom);
            // 生成秘密（对称）密钥
            SecretKey secretKey = kgen.generateKey();
            // 返回基本编码格式的密钥
            byte[] enCodeFormat = secretKey.getEncoded();
            // 根据给定的字节数组构造一个密钥。enCodeFormat：密钥内容；"AES"：与给定的密钥内容相关联的密钥算法的名称
            SecretKeySpec secretkey = new SecretKeySpec(enCodeFormat, "AES");
            // 将提供程序添加到下一个可用位置
            // Security.addProvider(new BouncyCastleProvider());
            // 创建一个实现指定转换的 Cipher对象，该转换由指定的提供程序提供。
            // "AES/ECB/PKCS7Padding"：转换的名称；"BC"：提供程序的名称
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretkey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES256 解密密算法 未测试
     * @param data decrypt data
     * @param key decrypt key
     * @return decrypt result
     * @throws Exception decrypt exception
     */
    public static byte[] decrypt256(byte[] data, byte[] key) throws Exception {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom securerandom = new SecureRandom(tohash256Deal(key));
            kgen.init(256, securerandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretkey = new SecretKeySpec(enCodeFormat, "AES");
            // Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretkey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加解密key进行 sha-256转换
     * @param data sha-256转换数据
     * @return sha-256转换结果
     */
    private static byte[] tohash256Deal(byte[] data) {
        try {
            MessageDigest digester = MessageDigest.getInstance("SHA-256");
            digester.update(data);
            byte[] hex = digester.digest();
            return hex;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
