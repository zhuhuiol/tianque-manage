package com.homolo.homolo.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;

import static sun.security.x509.CertificateAlgorithmId.ALGORITHM;

/**
 * @Author: ZH
 * @Description: 加密方法
 * @Date: 19-9-24 下午3:46
 */
public class EncryptionUtil {

	// 加密方式长度
	private static final String SHA = "SHA";
	private static final String SHA224 = "SHA-224";
	private static final String SHA256 = "SHA-256";
	private static final String SHA384 = "SHA-384";
	private static final String SHA512 = "SHA-512";
	private static final String MD5 = "MD5";
	private static final String MD2 = "MD2";

	private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding"; // 默认的加密算法， ECB/AES/PKCS5Padding

	private static final String AES_KEY = "0880076B18D7EE81"; // 密匙，必须16位

	private static final String AES_OFFSET = "CB3EC842D7C69578"; // 偏移量

	private static final String ENCODING = "UTF-8"; // 编码

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	/**
	 * 加密.
	 * @param obj 需要加密的字符串.
	 * @return 加密后的字符串.
	 */
	public static String encode(String obj) {
		return getEncode(obj, SHA);
	}

	/**
	 * 加密.
	 * @param obj 需要加密的字符串.
	 * @param kdy 加密方式.
	 * @return 加密后的字符串.
	 */
	public static String encode(String obj, String kdy) {
		return getEncode(obj, kdy);
	}

	//加密方法
	private static String getEncode(String obj, String key) {
		if (StringUtils.isEmpty(obj)) {
			return null;
		}

		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance(key);
			messageDigest.update(obj.getBytes("UTF-8"));
			return getText(messageDigest.digest());
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		for (int j = 0; j < len; j++) {
//			右移＝除以2的4次方，数乘以进制的下标次方
//			二进制以0b开头
//			八进制以0开头
//			十六进制以0x开头
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	/**
	 * Brypt加密方式.
	 * @param text 加密文本
	 * @return 加密后字符串
	 */
	public static String getBCryptEnCode(String text) {
		return new BCryptPasswordEncoder().encode(text);
	}
	/**
	 * Srypt加密方式.
	 * @param text 加密文本
	 * @return 加密后字符串
	 */
	public static String getSCryptEncode(String text) {
		return new SCryptPasswordEncoder().encode(text);
	}

	/**
	 * AES加密.
	 * @param cleartext c
	 * @return c
	 * @throws Exception c
	 */
	public static String aesEncrypt(String cleartext) {
		//加密方式： AES128(CBC/PKCS5Padding) + Base64
		try {
//			IvParameterSpec zeroIv = new IvParameterSpec(AES_OFFSET.getBytes());
			//两个参数，第一个为私钥字节数组， 第二个为加密方式 AES或者DES
			SecretKeySpec key = new SecretKeySpec(AES_KEY.getBytes("UTF-8"), "AES");
			//实例化加密类，参数为加密方式，要写全
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM); //PKCS5Padding比PKCS7Padding效率高，PKCS7Padding可支持IOS加解密
			//初始化，此方法可以采用三种方式，按加密算法要求来添加。（1）无第三个参数（2）第三个参数为SecureRandom random = new SecureRandom();中random对象，随机数。(AES不可采用这种方法)（3）采用此代码中的IVParameterSpec
			//加密时使用:ENCRYPT_MODE;  解密时使用:DECRYPT_MODE;
			cipher.init(Cipher.ENCRYPT_MODE, key); //CBC类型的可以在第三个参数传递偏移量zeroIv,ECB没有偏移量
			//加密操作,返回加密后的字节数组，然后需要编码。主要编解码方式有Base64, HEX, UUE,7bit等等。此处看服务器需要什么编码方式
			byte[] encryptedData = cipher.doFinal(cleartext.getBytes(ENCODING));

			return new BASE64Encoder().encode(encryptedData);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * AES解密.
	 * @param data c
	 * @return c
	 * @throws Exception c
	 */
	public static String aesDecrypt(String data) {
		try {
			byte[] byteMi = new BASE64Decoder().decodeBuffer(data);
//			IvParameterSpec zeroIv = new IvParameterSpec(AES_OFFSET.getBytes());
			SecretKeySpec key = new SecretKeySpec(
					AES_KEY.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			//与加密时不同MODE:Cipher.DECRYPT_MODE
			cipher.init(Cipher.DECRYPT_MODE, key);  //CBC类型的可以在第三个参数传递偏移量zeroIv,ECB没有偏移量
			byte[] decryptedData = cipher.doFinal(byteMi);
			return new String(decryptedData, ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}



}
