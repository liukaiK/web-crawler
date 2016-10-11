package com.esd.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5 {

	// 全局数组
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	private static final Logger logger = LoggerFactory.getLogger(Md5.class);

	public static String getMd5(StringBuffer sb) {
		return getMd5(sb.toString());
	}

	public static String getMd5(String str) {
		String resultString = null;
		try {
			resultString = new String(str);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteToString(md.digest(str.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		}
		return resultString;
	}
	/**
	 * cx-20160914
	 * @param str
	 * @return
	 */
	public static String getMd5File(byte[] b) {
		String resultString = null;
		try {			
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteToString(md.digest(b));
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		}
		return resultString;
	}
	// 转换字节数组为16进制字串
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	// 返回形式为数字跟字符串
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}
}
