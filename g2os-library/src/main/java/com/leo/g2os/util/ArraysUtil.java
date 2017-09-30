package com.leo.g2os.util;

/**
 * 数组管理工具
 * 
 * @author Liwl
 *
 */
public class ArraysUtil {

	/**
	 * 在数组的结尾拼接字节
	 * 
	 * @param paramBytes
	 *            需要拼入的数组
	 * @param paramByte
	 *            需要拼接的字节
	 * @return 拼接后的数组
	 */
	public static byte[] append(byte[] paramBytes, byte paramByte) {
		if (paramBytes == null) {
			return new byte[] { paramByte };
		}
		int bLen = paramBytes.length;
		byte[] tempByte = new byte[bLen + 1];
		System.arraycopy(paramBytes, 0, tempByte, 0, bLen);
		tempByte[bLen] = paramByte;
		return tempByte;
	}

	/**
	 * 在数组的开始拼接字节
	 * 
	 * @param paramBytes
	 *            需要拼入的数组
	 * @param paramByte
	 *            需要拼接的字节
	 * @return 拼接后的数组
	 */
	public static byte[] prepend(byte paramByte, byte[] paramBytes) {
		if (paramBytes == null) {
			return new byte[] { paramByte };
		}
		int bLen = paramBytes.length;
		byte[] tempByte = new byte[bLen + 1];
		tempByte[0] = paramByte;
		System.arraycopy(paramBytes, 0, tempByte, 1, bLen);
		return tempByte;
	}

	/**
	 * 数组取反
	 * 
	 * @param paramBytes
	 *            需要取反的数组
	 * @return 取反后的数组
	 */
	public static byte[] reverse(byte[] paramBytes) {
		if (paramBytes == null) {
			return null;
		}
		int i = 0;
		int j = paramBytes.length;
		byte[] tempBytes = new byte[j];
		do {
			j--;
			if (j < 0) {
				break;
			}
			tempBytes[j] = paramBytes[(i++)];
		} while (true);
		return tempBytes;
	}

	/**
	 * 数组合并
	 * 
	 * @param paramBytes1
	 *            需要合并的数组1
	 * @param paramBytes2
	 *            需要合并的数组2
	 * @return 合并后的数组
	 */
	public static byte[] concatenate(byte[] paramBytes1, byte[] paramBytes2) {
		if (paramBytes1 != null && paramBytes2 != null) {
			byte[] tempBytes = new byte[paramBytes1.length + paramBytes2.length];
			System.arraycopy(paramBytes1, 0, tempBytes, 0, paramBytes1.length);
			System.arraycopy(paramBytes2, 0, tempBytes, paramBytes1.length, paramBytes2.length);
			return tempBytes;
		}
		if (paramBytes1 != null) {
			return clone(paramBytes1);
		}
		return clone(paramBytes2);
	}

	/**
	 * 从数组的index=0截取@param length长度的数组
	 * 
	 * @param paramBytes
	 *            要截取的数组源
	 * @param length
	 *            需要截取的长度
	 * @return 截取length长度后的数组
	 */
	public static byte[] copyOfStart(byte[] paramBytes, int length) {
		if (paramBytes == null) {
			return null;
		}
		int bLen = paramBytes.length;
		if (length > bLen || length < 0) {
			return null;
		}
		byte[] tempBytes = new byte[length];
		System.arraycopy(paramBytes, 0, tempBytes, 0, length);
		return tempBytes;
	}

	/**
	 * 从指定位置@param index截取指定长度@param length的数组
	 * 
	 * @param paramBytes
	 *            要截取的数组
	 * @param index
	 *            起始位置
	 * @param length
	 *            截取数组的长度
	 * @return 从paramBytes数组的index位置截取length长度后的数组
	 */
	public static byte[] copyOfRange(byte[] paramBytes, int index, int length) {
		if (paramBytes == null) {
			return null;
		}
		int bLen = paramBytes.length;
		if (index > bLen - 1 || index < 0) {
			return null;
		}
		if (length > bLen || index + length > bLen) {
			return null;
		}
		byte[] tempBytes = new byte[length];
		System.arraycopy(paramBytes, index, tempBytes, 0, length);
		return tempBytes;
	}

	/**
	 * 截取数组
	 * 
	 * @param paramBytes
	 *            要截取的数组
	 * @param index
	 *            起始位置
	 * @return 从paramBytes数组的index位置截取length长度后的数组
	 */
	public static byte[] copyOfIndex(byte[] paramBytes, int index) {
		if (paramBytes == null) {
			return null;
		}
		int bLen = paramBytes.length;
		if (index >= bLen || index < 0) {
			return null;
		}
		int tempLen = bLen - index;
		byte[] tempBytes = new byte[tempLen];
		System.arraycopy(paramBytes, index, tempBytes, 0, tempLen);
		return tempBytes;
	}

	/**
	 * 克隆数组
	 * 
	 * @param paramBytes
	 *            需要克隆的数组
	 * @return 克隆后的数组
	 */
	public static byte[] clone(byte[] paramBytes) {
		if (paramBytes == null) {
			return null;
		}
		byte[] tempBytes = new byte[paramBytes.length];
		System.arraycopy(paramBytes, 0, tempBytes, 0, paramBytes.length);
		return tempBytes;
	}

	/**
	 * 数组比较相等
	 * 
	 * @param paramBytes1
	 *            需要比较的数组1
	 * @param paramBytes2
	 *            需要比较的数组2
	 * @return 数组是否相等 true=相等;false=不相等
	 */
	public static boolean equals(byte[] paramBytes1, byte[] paramBytes2) {
		// 地址比较
		if (paramBytes1 == paramBytes2) {
			return true;
		}
		if ((paramBytes1 == null) || (paramBytes2 == null)) {
			return false;
		}
		if (paramBytes1.length != paramBytes2.length) {
			return false;
		}
		for (int i = 0; i != paramBytes1.length; i++) {
			if (paramBytes1[i] != paramBytes2[i]) {
				return false;
			}
		}
		return true;
	}
}
