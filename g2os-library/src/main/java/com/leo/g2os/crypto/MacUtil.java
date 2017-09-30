package com.leo.g2os.crypto;

import com.leo.g2os.util.ArraysUtil;
import com.leo.g2os.util.ConvertUtil;

import java.util.Arrays;

/**
 * 通用MAC算法 AES和DES
 *
 * @author Liwl
 */
public class MacUtil {

    private static final byte[] PaddingBytes8 = new byte[]{(byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    private final static byte[] PaddingBytes16 = ConvertUtil.Hex2bytes("80000000000000000000000000000000");

    /**
     * 计算MacByDes
     *
     * @param inData in data
     * @param inKey  in key
     * @param inIV   in iv
     * @return mac result
     * @throws Exception getMacCipherByDES exception
     */
    public static byte[] getMacCipherByDES(byte[] inData, byte[] inKey, byte[] inIV) throws Exception {
        checkParameters(inData, inKey, inIV);
        byte[] data = null;
        byte[] key = null;
        byte[] iv = (inIV == null ? new byte[8] : inIV);
        // 克隆数组源
        data = ArraysUtil.clone(inData);
        key = ArraysUtil.clone(inKey);
        int surplus = data.length % 8;
        if (surplus == 0) {
            // 数据为8的整数倍则直接补8字节80..00
            data = ArraysUtil.concatenate(data, PaddingBytes8);
        } else {
            // 先取出不足8的长度,再进行数组合并
            data = ArraysUtil.concatenate(data, ArraysUtil.copyOfStart(PaddingBytes8, 8 - surplus));
        }
        // -1 : 保留1组进行for循环外运算
        int groupNum = data.length / 8 - 1;
        for (int groupIndex = 0; groupIndex < groupNum; groupIndex++) {
            byte[] group = ArraysUtil.copyOfRange(data, groupIndex * 8, 8);
            byte[] xor = XOR(group, iv);
            // 根据key的长度选择des or desede
            byte[] tempKey = key.length == 8 ? key : ArraysUtil.copyOfStart(key, 8);
            byte[] result = DESUtil.encryptDES(xor, tempKey, null);
            iv = ArraysUtil.copyOfRange(result, 0, 8);
        }
        byte[] group = ArraysUtil.copyOfRange(data, data.length - 8, 8);
        byte[] xor = XOR(group, iv);
        byte[] result = null;
        if (key.length == 8) {
            result = DESUtil.encryptDES(xor, key, null);
        } else {
            result = DESUtil.encryptDESede(xor, key, null);
        }
        return ArraysUtil.copyOfRange(result, 0, 4);
    }


    /**
     * 获取4字节Mac密文
     *
     * @param inData in data
     * @param inKey  in key
     * @param inIV   in iv
     * @return mac result
     * @throws Exception getMacCipherByAES exception
     */
    public static byte[] getMacCipherByAES(byte[] inData, byte[] inKey, byte[] inIV) throws Exception {

        if (inData == null || inKey == null || inIV == null) {
            return null;
        }
        byte[] data = null;
        byte[] key = null;
        byte[] H = null;
        int surplus = inData.length % 16;
        if (surplus == 0) {
            data = ArraysUtil.concatenate(inData, PaddingBytes16);
        } else {
            // 先取出不足16的长度
            byte[] tempAppend = Arrays.copyOf(PaddingBytes16, 16 - surplus);
            // 数组合并
            data = ArraysUtil.concatenate(inData, tempAppend);
        }
        key = ArraysUtil.clone(inKey);
        H = ArraysUtil.clone(inIV);
        int groupNum = data.length / 16 - 1;
        for (int groupIndex = 0; groupIndex < groupNum; groupIndex++) {
            byte[] group = Arrays.copyOfRange(data, groupIndex * 16, 16 + groupIndex * 16);
            byte[] xor = XOR(group, H);
            byte[] result = AESUtil.encrypt(xor, key, null);
            H = Arrays.copyOfRange(result, 0, 16);
        }
        byte[] group = Arrays.copyOfRange(data, data.length - 16, data.length);
        byte[] xor = XOR(group, H);
        byte[] result = AESUtil.encrypt(xor, key, null);
        return Arrays.copyOfRange(result, 0, 4);
    }


    /**
     * 检验数据是否正确
     *
     * @param inData
     * @param inKey
     * @param inIV
     * @throws Exception
     */

    /**
     * check Parameters
     *
     * @param inData in data
     * @param inKey  in key
     * @param inIV   in iv
     * @throws Exception check parameters exception
     */
    private static void checkParameters(byte[] inData, byte[] inKey, byte[] inIV) throws Exception {
        if (inData == null) {
            throw new Exception("data is null");
        }
        if (inKey == null) {
            throw new Exception("key is null");
        }
        if (inKey.length % 8 != 0) {
            throw new Exception("key length is not a multiple of 8");
        }
        if (inIV != null && inIV.length % 8 != 0) {
            throw new Exception("iv length is not a multiple of 8");
        }
    }

    /**
     * mac计算 DES方式
     *
     * @param buf 进行mac计算的value
     * @param key 进行mac计算的key
     * @return 计算mac结果
     * @throws Exception getMac Exception
     */
    @Deprecated
    public static byte[] getMac(byte[] buf, byte[] key) throws Exception {
        // 补 80
        byte[] nbuf = new byte[buf.length + 1];
        System.arraycopy(buf, 0, nbuf, 0, buf.length);
        nbuf[buf.length] = (byte) 0x80;
        // 初始 异或
        byte[] iv = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        // 输入计算数据
        byte[] edata = null;
        // 进行分组
        int group = (nbuf.length - (nbuf.length % 8)) / 8;
        int rest = nbuf.length % 8;
        if (rest != 0)
            group += 1;
        // 偏移量
        int offset = 0;
        for (int i = 0; i < group; i++) {
            byte[] temp = new byte[8];
            if (i != group - 1) {
                System.arraycopy(nbuf, offset, temp, 0, 8);
                offset += 8;
            } else {
                // 只有最后一组数据才进行填充0x00
                System.arraycopy(nbuf, offset, temp, 0, nbuf.length - offset);
            }
            // 异或
            if (i == 0) {
                temp = XOR(iv, temp);
            } else {
                temp = XOR(edata, temp);
            }
            // 根据key的长度选择des or desede
            if (key.length == 8) {
                edata = DESUtil.encryptDES(temp, key, null);
            } else {
                byte[] deskey = new byte[8];
                System.arraycopy(key, 0, deskey, 0, 8);
                if (i == (group - 1)) {
                    edata = DESUtil.encryptDESede(temp, key, null);
                } else {
                    edata = DESUtil.encryptDES(temp, deskey, null);
                }
            }
            // over
        }
        return edata;
    }

    /**
     * 数组异或
     *
     * @param bytes1 异或操作的byte数组1
     * @param bytes2 异或操作的byte数组2
     * @return 异或后的结果数组
     */
    public static byte[] XOR(byte[] bytes1, byte[] bytes2) {
        if (bytes1 == null || bytes2 == null || bytes1.length != bytes2.length) {
            return null;
        }
        byte[] tempBytes = new byte[bytes1.length];
        for (int i = 0; i < bytes1.length; i++) {
            tempBytes[i] = (byte) (bytes1[i] ^ bytes2[i]);
        }
        return tempBytes;
    }
}
