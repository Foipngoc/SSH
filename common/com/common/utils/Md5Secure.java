package com.common.utils;

import java.security.MessageDigest;


public class Md5Secure{
	public static String encode(String plaintext) {
		if (plaintext != null) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] results = md.digest(plaintext.getBytes());
				String resultString = HexByteUtil.byteArrayToHexString(results);
				return resultString.toUpperCase();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
}
