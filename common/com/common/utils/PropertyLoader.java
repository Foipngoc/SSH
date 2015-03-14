package com.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.sf.ehcache.util.PropertyUtil;

/**
 * 属性加载器,加载classpath中的*.properties文件
 * 
 * @author DongJun
 * 
 */
public class PropertyLoader {

	/**
	 * 读取filename属性文件中属性为key的值
	 * 
	 * @param filename
	 * @param key
	 * @return
	 */
	public static String getProperty(String filename, String key,
			String defaultvalue) {
		InputStream inputFile;
		Properties properties;

		properties = new Properties();
		inputFile = PropertyUtil.class.getClassLoader().getResourceAsStream(
				filename);
		try {
			properties.load(inputFile);
			String propertyStr = properties.getProperty(key);
			if (propertyStr != null)
				return new String(propertyStr.getBytes("ISO-8859-1"), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return defaultvalue;
	}

	/**
	 * 读取属性文件中的属性
	 */
	public static Properties getProperty(String propertiesFilename) {
		InputStream inputFile;
		Properties properties;

		properties = new Properties();
		inputFile = PropertyUtil.class.getClassLoader().getResourceAsStream(
				propertiesFilename);
		try {
			properties.load(inputFile);
			return properties;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
