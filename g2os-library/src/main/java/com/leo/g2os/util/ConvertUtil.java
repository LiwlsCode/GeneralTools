package com.leo.g2os.util;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Locale;

/**
 * 数据类型转换工具类
 * 
 * @author Liwl
 *
 */
public class ConvertUtil {
	/**
	 * byte[] 转 HexString
	 * 
	 * @param hex
	 *            byte[]
	 * @return String
	 */
	public static String bytes2HexStr(byte[] hex) {
		String str = "";
		if (hex.length == 0)
			return str;
		for (int i = 0; i < hex.length; i++) {
			String buf = Integer.toString(hex[i] & 0xFF, 16);// & 0xFF去掉负号
			if (buf.length() != 2) // 补位
			{
				str += "0" + buf;
			} else
				str += buf;
		}
		return str;
	}

	/**
	 * byte[] 转 HexString
	 * 
	 * @param src
	 *            byte[]
	 * @return String
	 */
	public static String bytes2Hex(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		char[] buffer = new char[2];
		for (int i = 0; i < src.length; i++) {
			buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
			buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
			stringBuilder.append(buffer);
		}
		return stringBuilder.toString().toUpperCase(Locale.getDefault());
	}

	/**
	 * 
	 * HexString 转 byte[] <br>
	 * 将指定字符串src，以每两个字符分割转换为16进制形式 <br>
	 * 如："2B44EFD9" 转 byte[]{0x2B, 0x44, 0xEF, 0xD9}
	 * 
	 * @param src
	 *            String
	 * @return byte[]
	 */
	public static byte[] Hex2bytes(String src) {
		if (src == null || src.length() == 0) {
			return new byte[0];
		}
		try {
			byte[] ret = new byte[src.length() / 2];
			byte[] tmp = src.getBytes();
			for (int i = 0; i < src.length() / 2; i++) {
				ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	/**
	 * byte[] 转 char[]
	 * 
	 * @param src src
	 * @return bytes2chars result
	 */
	public static char[] bytes2chars(byte[] src) {
		Charset cs = Charset.forName("UTF-8");
		ByteBuffer bb = ByteBuffer.allocate(src.length);
		bb.put(src);
		bb.flip();
		CharBuffer cb = cs.decode(bb);
		return cb.array();
	}

	/**
	 * char[] 转 byte[]
	 * 
	 * @param src
	 *            字符数组
	 * @return chars2bytes result
	 */
	public static byte[] chars2bytes(char[] src) {
		Charset cs = Charset.forName("UTF-8");
		CharBuffer cb = CharBuffer.allocate(src.length);
		cb.put(src);
		cb.flip();
		ByteBuffer bb = cs.encode(cb);
		return bb.array();
	}

	/***
	 * byte[] 转 short[]
	 * 
	 * @param src
	 *            byte[]
	 * @return short[]
	 */
	public static short[] bytes2shorts(byte[] src) {
		int shortlen = 0;
		if (src.length % 2 != 0) {
			shortlen = src.length + 1 / 2;
		} else {
			shortlen = src.length / 2;
		}
		short[] tar = new short[shortlen];
		int j = 0;
		for (int i = 0; i < src.length; i += 2) {
			byte[] ram = new byte[2];
			if (src.length < 2) {
				System.arraycopy(src, i, ram, 0, src.length);
			} else {
				System.arraycopy(src, i, ram, 0, 2);
			}
			tar[j] = bytes2short(ram);
			if (j < shortlen) {
				j++;
			}
		}
		return tar;
	}

	/**
	 * short[] 转 byte[]
	 * 
	 * @param src
	 *            short[]
	 * @return byte[]
	 */
	public static byte[] shorts2bytes(short[] src) {
		if (src == null)
			return null;
		byte[] tar = new byte[src.length * 2];
		for (int i = 0; i < src.length; i++) {
			System.arraycopy(short2bytes(src[i]), 0, tar, i * 2, 2);
		}
		return tar;
	}

	/**
	 * byte[] 转 int[]
	 * 
	 * @param src
	 *            byte[]
	 * @return int[]
	 */
	public static int[] bytes2ints(byte[] src) {
		if (src == null || src.length <= 0) {
			return null;
		}
		int[] result = new int[src.length];
		char[] buffer = new char[2];
		for (int i = 0; i < src.length; i++) {
			StringBuilder stringBuilder = new StringBuilder();
			buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
			buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
			stringBuilder.append(buffer);
			result[i] = Integer.parseInt(stringBuilder.toString());
		}
		return result;
	}

	/**
	 * int[] 转 byte[]
	 * 
	 * @param src src
	 * @return ints2bytes result
	 */
	public static byte[] ints2bytes(int[] src) {
		if (src == null || src.length <= 0) {
			return null;
		}
		byte[] result = new byte[src.length];
		for (int i = 0; i < result.length; i++) {
			Integer value = new Integer(src[i]);
			result[i] = value.byteValue();
		}
		return result;
	}

	/**
	 * byte[1..4] 转 int
	 * 
	 * @param src
	 *            byte[]
	 * @return int
	 */
	public static int bytes2int(byte[] src) {
		int targets = 0;
		if (src.length <= 4) {
			if (src.length == 1)
				targets = (int) (src[0] & 0xFF);
			else if (src.length == 2)
				targets = (src[1] & 0xff) | ((src[0] & 0xFF) << 8);
			else if (src.length == 3)
				targets = (src[2] & 0xff) | (src[1] << 8) | (src[0] << 16);
			else
				targets = (src[3] & 0xff) | (src[2] << 8) // | 表示安位
						| (src[1] << 16) | (src[0] << 24);
		}
		return targets;
	}

	/**
	 * int 转 byte[4]
	 * 
	 * @param src
	 *            待转换的数
	 * @return 转换后的结果
	 */
	public static byte[] int2bytes(int src) {
		byte[] result = new byte[4];
		result[0] = (byte) ((src >> 24) & 0xFF);
		result[1] = (byte) ((src >> 16) & 0xFF);
		result[2] = (byte) ((src >> 8) & 0xFF);
		result[3] = (byte) (src & 0xFF);
		return result;
	}

	/**
	 * byte[2] 转 short
	 * 
	 * @param src
	 *            byte[]
	 * @return short
	 */
	public static short bytes2short(byte[] src) {
		if (src.length < 2 && src != null)
			return (short) src[0];
		return (short) (((src[0] & 0xff) << 8) | (src[1] & 0xff));
	}

	/**
	 * short 转 byte[2]
	 * 
	 * @param src
	 *            short
	 * @return byte[]
	 */
	public static byte[] short2bytes(short src) {
		byte[] b = new byte[2];

		b[0] = (byte) (src >> 8);
		b[1] = (byte) (src);

		return b;
	}

	/**
	 * byte[] 转 long
	 * 
	 * @param src src
	 * @return bytes2long result
	 */
	public static long bytes2long(byte[] src) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.put(src, 0, src.length);
		buffer.flip();// need flip
		return buffer.getLong();
	}

	/**
	 * long 转 byte[]
	 * 
	 * @param src src
	 * @return long2bytes result
	 */
	public static byte[] long2bytes(long src) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putLong(0, src);
		return buffer.array();
	}

	/**
	 *  字符串转换为ASCII码
	 *
	 * @param src src
	 * @return string2ASCII result
	 */
	public static int[] string2ASCII(String src) {
		if (src == null || src.length() == 0) {
			return null;
		}
		// 把字符中转换为字符数组
		char[] chars = src.toCharArray();
		int[] result = new int[chars.length];
		for (int i = 0; i < chars.length; i++) {
			result[i] = (int) chars[i];
		}
		return result;
	}

	/**
	 * 整数字符串转换为十六进制字符串
	 * 
	 * @param src
	 *            eg.111
	 * @return eg.313131
	 */
	public static String string2HexASCII(String src) {
		char[] charArray = src.toCharArray();
		String result = "";
		for (char c : charArray) {
			String hexString = Integer.toHexString(Integer.valueOf(c));
			result += hexString;
		}
		return result;
	}

	/**
	 * 对十六进制byte数组每个index取反
	 * 
	 * @param buff
	 *            16进制数组
	 * @return 16进制数组
	 */
	public static byte[] invertBytes(byte[] buff) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buff.length; i++) {
			int b = ~buff[i];
			String str = Integer.toHexString(b);
			if (str.length() > 2) {
				str = str.substring(str.length() - 2, str.length());
			} else if (str.length() < 2) {
				str = "0" + str;
			}
			sb.append(str);
		}
		return Hex2bytes(sb.toString());
	}

	/**
	 * 将两个ASCII字符合成一个字节； 如："EF" 变为 0xEF
	 * 
	 * @param src0
	 *            byte
	 * @param src1
	 *            byte
	 * @return byte
	 */
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	/**
	 * 格式化Money
	 * 
	 * @param src
	 *            String
	 * @param radix
	 *            int
	 * @return String
	 */
	public static String moneyFormat(String src, int radix) {
		String myMoney = null;
		int tmp = Integer.parseInt(src, radix);
		myMoney = Float.toString(tmp / 100.0f);
		BigDecimal bd = new BigDecimal(myMoney);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return myMoney;
	}
}
