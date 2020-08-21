package com.homolo.homolo.result;

import java.util.*;

/**
 * @Author: ZH
 * @Description: 统一返回格式.
 * @Date: 20-4-8 上午11:25
 */
public class ServiceResult {

	private static Map map = new HashMap();

	private static final String code = "code";
	private static final String msg = "msg";
	private static final String result = "result";

	public static Map serviceResult(int var) {
		map.put(code, var);
		return map;
	}
	public static Map serviceResult(int var, String var1) {
		map.put(code, var);
		map.put(msg, var1);
		return map;
	}
	public static Map serviceResult(int var, Object var1) {
		map.put(code, var);
		map.put(result, var1);
		return map;
	}
	public static Map serviceResult(int var, String var1, Object var2) {
		map.put(code, var);
		map.put(msg, var1);
		map.put(result, var2);
		return map;
	}

}
