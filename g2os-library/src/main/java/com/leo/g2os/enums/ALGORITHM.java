package com.leo.g2os.enums;

public enum ALGORITHM {
	/** AES/ECB/NoPadding */
	AENP("AES/ECB/NoPadding"),
	/** AES/CBC/NoPadding */
	ACNP("AES/CBC/NoPadding"),
	/** DES/ECB/NoPadding */
	DENP("DES/ECB/NoPadding"),
	/** DES/CBC/NoPadding */
	DCNP("DES/CBC/NoPadding"),
	/** DESede/ECB/NoPadding */
	DEENP("DESede/ECB/NoPadding"),
	/** DESede/CBC/NoPadding */
	DECNP("DESede/CBC/NoPadding");

	/**
	 * <pre>
	 * AES/CBC/NoPadding (128)
	 * AES/CBC/PKCS5Padding (128)
	 * AES/ECB/NoPadding (128)
	 * AES/ECB/PKCS5Padding (128)
	 * DES/CBC/NoPadding (56)
	 * DES/CBC/PKCS5Padding (56)
	 * DES/ECB/NoPadding (56)
	 * DES/ECB/PKCS5Padding (56)
	 * DESede/CBC/NoPadding (168)
	 * DESede/CBC/PKCS5Padding (168)
	 * DESede/ECB/NoPadding (168)
	 * DESede/ECB/PKCS5Padding (168)
	 * RSA/ECB/PKCS1Padding (1024, 2048)
	 * RSA/ECB/OAEPWithSHA-1AndMGF1Padding (1024, 2048)
	 * RSA/ECB/OAEPWithSHA-256AndMGF1Padding (1024, 2048)
	 * </pre>
	 */

	public String value;

	ALGORITHM(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
