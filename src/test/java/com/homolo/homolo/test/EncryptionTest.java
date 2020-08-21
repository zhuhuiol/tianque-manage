package com.homolo.homolo.test;

import com.homolo.homolo.utils.EncryptionUtil;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: ZH
 * @Description:
 * @Date: 19-9-24 下午4:04
 */
public class EncryptionTest {

	public static final String text = "zhuhui";
	@Test
	public void test1() throws NoSuchAlgorithmException {
		String str = EncryptionUtil.getBCryptEnCode("zhuhui");
		//$10$4E.3EGBmnGtu9cXLn0RWzuaf3uePbSzqVCNLK6vOJ6/JxxRfeEB9a
		System.out.println(str);
	}

	@Test
	public void test2() throws NoSuchAlgorithmException {
		String str = "你好啊";
		String s = EncryptionUtil.aesEncrypt(str);
		System.out.println("加密"+s);
		String s1 = EncryptionUtil.aesDecrypt(s);
		System.out.println("解密"+s1);

	}



}
