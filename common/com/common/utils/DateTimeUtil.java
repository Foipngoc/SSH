package com.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

	/**
	 * 以yyyy-MM-dd HH:mm:ss获得当前时间
	 * 
	 * @return
	 */
	public static String getCurrTime() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateNowStr = sdf.format(d);
		return dateNowStr;
	}

	/**
	 * 以fmt格式获得当前时间
	 * 
	 * @return
	 */
	public static String getCurrTimeFmt(String fmt) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		String dateNowStr = sdf.format(d);
		return dateNowStr;
	}

	/**
	 * 从String 获得Date
	 * 
	 * @throws ParseException
	 */
	public static Date getDateByStringFmt(String timeString, String fmt)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		Date date = sdf.parse(timeString);
		return date;
	}

	/**
	 * 从格式为"yyyy-MM-dd HH:mm:ss" 的String 获得Date
	 * 
	 * @throws ParseException
	 */
	public static Date getDateByString(String timeString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(timeString);
		return date;
	}

	public static Date getCurrDate() {
		return new Date();
	}
}
