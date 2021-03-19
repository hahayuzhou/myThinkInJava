package com.thinking.my.algorithm.lru.damo;

public class StringUtils {

	public static boolean isBlank(String str) {
		if (str == null || str.trim().equals("")) return true;
		return false;
	}

	public static boolean isNotBlank(String str) {
		return !StringUtils.isBlank(str);
	}

}
