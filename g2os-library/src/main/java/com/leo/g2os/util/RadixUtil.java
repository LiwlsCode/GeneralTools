package com.leo.g2os.util;

import java.util.Locale;

/**
 * 进制转换工具类 <br>
 * Binary ： 二进制<br>
 * Decimal ： 十进制<br>
 * Octal : 八进制<br>
 * Hexadecimal ： 十六进制<br>
 *
 * @author Liwl
 */
public class RadixUtil {

    private static final Locale locale = Locale.getDefault();

    public static void main(String[] args) {
        String dec2hex = RadixUtil.dec2binary(10);
        int octal2dec = RadixUtil.octal2dec("25");
        System.out.println(dec2hex);
        System.out.println(octal2dec);
    }

    /**
     * 十进制转换为十六进制
     *
     * @param dec 十进制
     * @return 十六进制
     */
    public static String dec2hex(int dec) {
        return padding2(Integer.toHexString(dec).toUpperCase(locale));
    }

    /**
     * 十进制转换为八进制
     *
     * @param dec 八进制
     * @return 十进制
     */
    public static String dec2Octal(int dec) {
        return Integer.toOctalString(dec);
    }

    /**
     * 十进制转换为二进制
     *
     * @param dec 十进制
     * @return 二进制
     */
    public static String dec2binary(int dec) {
        return padding8(Integer.toBinaryString(dec));
    }

    /**
     * 二进制转换为十进制
     *
     * @param binary 二进制
     * @return 十进制
     */
    public static int binary2dec(String binary) {
        return Integer.parseInt(binary, 2);
    }

    /**
     * 八进制转换为十进制
     *
     * @param octal 八进制
     * @return 十进制
     */
    public static int octal2dec(String octal) {
        return Integer.parseInt(octal, 8);
    }

    /**
     * 十六进制转换为十进制
     *
     * @param hex 十六进制
     * @return 十进制
     */
    public static int hex2dec(String hex) {
        return Integer.parseInt(hex, 16);
    }

    /**
     * 字符前面填充0至长度的2的整数倍
     *
     * @param str padding string
     * @return padding result
     */
    private static String padding2(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        while (str.length() % 2 != 0) {
            str = "0" + str;
        }
        return str;
    }

    /**
     * 字符前面填充0至长度的8的整数倍
     *
     * @param str padding string
     * @return padding result
     */
    private static String padding8(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        while (str.length() % 8 != 0) {
            str = "0" + str;
        }
        return str;
    }
}
