package com.bigdata.store.utils;

import java.util.UUID;

public class UUIDUtils {
	/**
	    * 随机生成32长度的字符串 
	 * 
	 * @return
	 */
	public static String getId() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	/**
	    *    随机生成64长度的字符串 
	 * 
	 * @return
	 */
	public static String getUUID64() {
		return getId() + getId();
	}

	public static void main(String[] args) {
		System.out.println(getId());

		// for (int i = 0; i <= 100; i++) {
		// String str = UUID.randomUUID().toString();
		// System.out.println(str);
		// }

	}
}
