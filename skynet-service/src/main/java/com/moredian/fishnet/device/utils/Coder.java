package com.moredian.fishnet.device.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

public class Coder {
	private static final byte[] AES_IV = { (byte) 0x15, (byte) 0xFF, (byte) 0x01, (byte) 0x00, (byte) 0x34, (byte) 0xAB,
			(byte) 0x4C, (byte) 0xD3, (byte) 0x55, (byte) 0xFE, (byte) 0xA1, (byte) 0x22, (byte) 0x08, (byte) 0x4F,
			(byte) 0x13, (byte) 0x07 };

	private static String algorithm = "AES/CBC/PKCS5Padding";

	private static final String MAC_NAME = "HmacSHA1";
	private static final String ENCODING = "UTF-8";

	// 加密
	public static String encrypt(String sSrc, String sKey){
		try {
			byte[] sKeyBuffer = md5(sKey);
			if (sKey == null) {
				return null;
			}
			// 判断Key是否为16位
			if (sKeyBuffer.length != 16) {
				return null;
			}
			byte[] raw = sKeyBuffer;
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance(algorithm);// "算法/模式/补码方式"
			IvParameterSpec iv = new IvParameterSpec(AES_IV);// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes());
			return Base64.encodeBase64String(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
		} catch (Exception ex) {
			return null;
		}
	}

	public static void main(String[] args){
		//sn  + mac
//		String salt = Base64.encodeBase64String(Coder.md5("oxEayXjezRZRNk3qFdr7MsWBsLQ="+"oxEayXjezRZRNk3qFdr7MsWBsLQ="));
//		System.out.println(salt);
//		String cckey="oxEayXjezRZRNk3qFdr7MsWBsLQ==";
//		String eKey=Coder.encrypt(cckey,salt);
//		System.out.println(eKey);
//		String key = Coder.decrypt(eKey, salt);
//		System.out.println(key);

		String mac="3F973EA2";
		System.out.println(encrypt(mac,mac));
	}

	/**
	 * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
	 *
	 * @param encryptText 被签名的字符串
	 * @param encryptKey  密钥
	 * @return
	 * @throws Exception
	 */
	public static String HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
		byte[] data = encryptKey.getBytes(ENCODING);
		//根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
		SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
		//生成一个指定 Mac 算法 的 Mac 对象
		Mac mac = Mac.getInstance(MAC_NAME);
		//用给定密钥初始化 Mac 对象
		mac.init(secretKey);

		byte[] text = encryptText.getBytes(ENCODING);
		//完成 Mac 操作

		byte[] secretBytes = mac.doFinal(text);
		return Base64.encodeBase64String(secretBytes);
	}

	// 解密
	public static String decrypt(String sSrc, String sKey) {
		try {
			byte[] sKeyBuffer = md5(sKey);
			// 判断Key是否正确
			if (sKey == null) {
				return null;
			}
			// 判断Key是否为16位
			if (sKeyBuffer.length != 16) {
				return null;
			}
			byte[] raw = sKeyBuffer;
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance(algorithm);
			IvParameterSpec iv = new IvParameterSpec(AES_IV);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = Base64.decodeBase64(sSrc);// 先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original);
				return originalString;
			} catch (Exception e) {
				return null;
			}
		} catch (Exception ex) {
			return null;
		}
	}
	
    /**利用MD5进行加密
     * @param str  待加密的字符串
     * @return  加密后的字符串
     */
    public static byte[] md5(String str){
    	try{
            //确定计算方法
            MessageDigest md5=MessageDigest.getInstance("MD5");
            //加密后的字符串
           return md5.digest(str.getBytes("utf-8"));
    	}catch (Exception ex) {
			return null;
		}
    }

	/*
	 * .跟C约定相同的AES算法，AES实现有四种，像CBC/ECB/CFB/OFB 算法/模式/填充 16字节加密后数据长度 不满16字节加密后长度
	 * AES/CBC/NoPadding 16 不支持 AES/CBC/PKCS5Padding 32 16
	 * AES/CBC/ISO10126Padding 32 16 AES/CFB/NoPadding 16 原始数据长度
	 * AES/CFB/PKCS5Padding 32 16 AES/CFB/ISO10126Padding 32 16
	 * AES/ECB/NoPadding 16 不支持 AES/ECB/PKCS5Padding 32 16
	 * AES/ECB/ISO10126Padding 32 16 AES/OFB/NoPadding 16 原始数据长度
	 * AES/OFB/PKCS5Padding 32 16 AES/OFB/ISO10126Padding 32 16
	 * AES/PCBC/NoPadding 16 不支持 AES/PCBC/PKCS5Padding 32 16
	 * AES/PCBC/ISO10126Padding 32 16
	 */
}
