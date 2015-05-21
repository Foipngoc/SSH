package com.common.utils;

import java.io.File;
import java.util.Locale;

public class FileUtils {
	/**
	 * 检查目录是否存在，不存在创建之
	 */
	public static void mkdir(String dirpath) {
		File dir = new File(dirpath);

		if (!dir.exists() && !dir.isDirectory())
			dir.mkdirs();
	}

	/**
	 * 检查同名文件是否存在
	 */
	public static boolean ifFileExist(String filepath) {
		File file = new File(filepath);
		return file.exists();
	}

	/**
	 * 检查文件名为filepath，并且md5为filemd5的文件是否存在
	 */
	public static boolean ifFileExist(String filepath, String filemd5) {
		if (filepath == null || filemd5 == null)
			return false;

		// 如果文件不存在，直接返回false
		if (!ifFileExist(filepath))
			return false;

		String md5 = FileMd5.getMd5ByFile(filepath);
		if (md5 != null
				&& filemd5.toLowerCase(Locale.getDefault()).equals(
						md5.toLowerCase(Locale.getDefault())))
			return true;
		else
			return false;
	}

	/**
	 * 获取文件扩展名
	 */
	public static String getFileExtension(String fileName) {
		if (fileName != null && fileName.lastIndexOf(".") != -1
				&& fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} else {
			return "";
		}
	}

	/**
	 * 获取文件名，不带扩展名
	 */
	public static String getFileNoExtension(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	/**
	 * 重命名文件名，在文件名和扩展名中添加token
	 */
	public static String renameFileName(String filename, String token) {
		String name = getFileNoExtension(filename);
		String ext = getFileExtension(filename);

		if (ext != null && !ext.equals("")) {
			return name + token + "." + ext;
		} else {
			return name + token;
		}
	}
}
