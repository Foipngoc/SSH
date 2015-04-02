package com.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * 获得文件的md5值
 * 
 * @author DJ
 * 
 */
public class FileMd5 {
	public static String getMd5ByFile(File file) {
		String value = null;
		FileInputStream in;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			return null;
		}
		try {
			MappedByteBuffer byteBuffer = in.getChannel().map(
					FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return value;
	}

	public static String getMd5ByFile(String filename) {
		return getMd5ByFile(new File(filename));
	}
}
