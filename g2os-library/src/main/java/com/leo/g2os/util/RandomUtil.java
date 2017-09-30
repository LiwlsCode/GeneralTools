package com.leo.g2os.util;

/**
 * 获取随机数
 * 
 * @author liwl
 *
 */
public class RandomUtil {
	public static final int SEED_RANDOM = 0;
	public static final int SECURITY_RANDOM = 1;
	public static final int MATH_RANDOM = 2;

	/**
	 * 获取一个随机数
	 * 
	 * @param randomType
	 *            随机数的类型
	 * @param len
	 *            随机数的长度
	 * @return 根据送入的长度生成的随机数
	 */
	public static byte[] getRandom(int randomType, int len) {
		switch (randomType) {
		case SEED_RANDOM:
			return getSeedRandom(len);
		case SECURITY_RANDOM:
			return getSecurityRandom(len);
		case MATH_RANDOM:
			return getMathRandom(len);
		default:
			return null;
		}
	}

	/**
	 * 获取一个用系统时间为种子生成的随机数
	 * 
	 * @param len
	 *            需要生成随机数的长度
	 * @return 返回送入长度字节的16进制随机数
	 */
	public static byte[] getSeedRandom(int len) {
		if (len <= 0)
			return null;
		java.util.Random random = new java.util.Random(System.currentTimeMillis());
		// 实例化送入长度的空的bytes
		byte[] randomBytes = new byte[len];
		// 为送入的bytes填充随机数
		random.nextBytes(randomBytes);
		return randomBytes;
	}

	/**
	 * 获取一个经过安全加密的随机数
	 * 
	 * @param len
	 *            需要生成随机数的长度
	 * @return 返回送入长度字节的16进制随机数
	 */
	public static byte[] getSecurityRandom(int len) {
		if (len <= 0)
			return null;
		java.security.SecureRandom sRandom = new java.security.SecureRandom();
		// 实例化送入长度的空的bytes
		byte[] randomByts = new byte[len];
		// 为送入的bytes填充随机数
		sRandom.nextBytes(randomByts);
		return randomByts;
	}

	/**
	 * 获取一个用Math.random生成的随机数
	 * 
	 * @param len
	 *            需要生成随机数的长度
	 * @return 返回送入长度字节的16进制随机数
	 */
	public static byte[] getMathRandom(int len) {
		if (len <= 0)
			return null;
		// 实例化送入长度的空的bytes
		byte[] randomByts = new byte[len];
		for (int i = 0; i < len; i++) {
			// Math.random()的取值范围是0.0-1.0，*255则表示取到16进制中00-FF(包含00和FF)之间的数
			byte byt = (byte) (Math.random() * 255);
			randomByts[i] = byt;
		}
		return randomByts;
	}

}
