package com.leo.g2os.crypto;

import com.leo.g2os.enums.ALGORITHM;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES和3DES加解密算法
 *
 * @author Liwl
 */
public class DESUtil {
    /**
     * 生成DES ede秘钥
     *
     * @return SecretKey DES ede秘钥
     */
    public static SecretKey generateSecretKey() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("DESede");
            kg.init(168);
            return kg.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将byte[]转换成SecretKey类型的DES秘钥
     *
     * @param key byte[]密钥
     * @return SecretKey DES ede秘钥
     */
    public static SecretKey convertSecretKey(byte[] key) {
        try {
            DESedeKeySpec dks = new DESedeKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            return keyFactory.generateSecret(dks);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DES加密算法
     * iv == null 为 DES/ECB/NoPadding
     * iv != null 为 DES/CBC/NoPadding
     *
     * @param data des encrypt data
     * @param key  des encrypt key
     * @param iv   des encrypt IvParameter
     * @return des encrypt result
     * @throws Exception des encrypt exception
     */
    public static byte[] encryptDES(byte[] data, byte[] key, byte[] iv) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
        Cipher cipher = null;
        if (iv == null) {
            cipher = Cipher.getInstance(ALGORITHM.DENP.getValue());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } else {
            cipher = Cipher.getInstance(ALGORITHM.DCNP.getValue());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        }
        return cipher.doFinal(data);
    }

    /**
     * DES加密算法
     * iv == null 为 DES/ECB/NoPadding
     * iv != null 为 DES/CBC/NoPadding
     *
     * @param data     des encrypt data
     * @param key      des encrypt key
     * @param iv       des encrypt IvParameter
     * @param provider des encrypt provider
     * @return des encrypt result
     * @throws Exception des encrypt exception
     */
    public static byte[] encryptDES(byte[] data, byte[] key, byte[] iv, Provider provider) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
        Cipher cipher = null;
        if (iv == null) {
            cipher = Cipher.getInstance(ALGORITHM.DENP.getValue(), provider);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } else {
            cipher = Cipher.getInstance(ALGORITHM.DCNP.getValue(), provider);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        }
        return cipher.doFinal(data);
    }

    /**
     * DES解密算法
     * iv == null 为 DES/ECB/NoPadding
     * iv != null 为 DES/CBC/NoPadding
     *
     * @param data des decrypt data
     * @param key  des decrypt key
     * @param iv   des decrypt IvParameter
     * @return des decrypt result
     * @throws Exception des decrypt exception
     */
    public static byte[] decryptDES(byte[] data, byte[] key, byte[] iv) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
        Cipher cipher = null;
        if (iv == null) {
            cipher = Cipher.getInstance(ALGORITHM.DENP.getValue());
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        } else {
            cipher = Cipher.getInstance(ALGORITHM.DCNP.getValue());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        }
        return cipher.doFinal(data);
    }

    /**
     * DES解密算法
     * iv == null 为 DES/ECB/NoPadding
     * iv != null 为 DES/CBC/NoPadding
     *
     * @param data     des decrypt data
     * @param key      des decrypt key
     * @param iv       des decrypt IvParameter
     * @param provider des decrypt provider
     * @return des decrypt result
     * @throws Exception des decrypt exception
     */
    public static byte[] decryptDES(byte[] data, byte[] key, byte[] iv, Provider provider) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
        Cipher cipher = null;
        if (iv == null) {
            cipher = Cipher.getInstance(ALGORITHM.DENP.getValue(), provider);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        } else {
            cipher = Cipher.getInstance(ALGORITHM.DCNP.getValue(), provider);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        }
        return cipher.doFinal(data);
    }

    /**
     * 3DES加密算法
     * iv == null 为 DESede/ECB/NoPadding
     * iv != null 为 DESede/CBC/NoPadding
     *
     * @param data 3des encrypt data
     * @param key  3des encrypt key
     * @param iv   3des encrypt IvParameter
     * @return 3des encrypt result
     * @throws Exception 3des encrypt exception
     */
    public static byte[] encryptDESede(byte[] data, byte[] key, byte[] iv) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "DESede");
        Cipher cipher = null;
        if (iv == null) {
            cipher = Cipher.getInstance(ALGORITHM.DEENP.getValue());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } else {
            cipher = Cipher.getInstance(ALGORITHM.DECNP.getValue());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        }
        return cipher.doFinal(data);
    }

    /**
     * 3DES加密算法
     * iv == null 为 DESede/ECB/NoPadding
     * iv != null 为 DESede/CBC/NoPadding
     *
     * @param data     3des encrypt data
     * @param key      3des encrypt key
     * @param iv       3des encrypt IvParameter
     * @param provider 3des encrypt provider
     * @return 3des encrypt result
     * @throws Exception 3des encrypt exception
     */
    public static byte[] encryptDESede(byte[] data, byte[] key, byte[] iv, Provider provider) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "DESede");
        Cipher cipher = null;
        if (iv == null) {
            cipher = Cipher.getInstance(ALGORITHM.DEENP.getValue(), provider);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } else {
            cipher = Cipher.getInstance(ALGORITHM.DECNP.getValue(), provider);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        }
        return cipher.doFinal(data);
    }

    /**
     * 3DES解密算法
     * iv == null 为 DESede/ECB/NoPadding
     * iv != null 为 DESede/CBC/NoPadding
     *
     * @param data 3des decrypt data
     * @param key  3des decrypt key
     * @param iv   3des decrypt IvParameter
     * @return 3des decrypt result
     * @throws Exception 3des decrypt exception
     */
    public static byte[] decryptDESede(byte[] data, byte[] key, byte[] iv) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "DESede");
        Cipher cipher = null;
        if (iv == null) {
            cipher = Cipher.getInstance(ALGORITHM.DEENP.getValue());
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        } else {
            cipher = Cipher.getInstance(ALGORITHM.DECNP.getValue());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        }
        return cipher.doFinal(data);
    }

    /**
     * 3DES解密算法
     * iv == null 为 DESede/ECB/NoPadding
     * iv != null 为 DESede/CBC/NoPadding
     *
     * @param data     3des decrypt data
     * @param key      3des decrypt key
     * @param iv       3des decrypt IvParameter
     * @param provider 3des decrypt provider
     * @return 3des decrypt result
     * @throws Exception 3des decrypt exception
     */
    public static byte[] decryptDESede(byte[] data, byte[] key, byte[] iv, Provider provider) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "DESede");
        Cipher cipher = null;
        if (iv == null) {
            cipher = Cipher.getInstance(ALGORITHM.DEENP.getValue(), provider);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        } else {
            cipher = Cipher.getInstance(ALGORITHM.DECNP.getValue(), provider);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        }
        return cipher.doFinal(data);
    }

}
