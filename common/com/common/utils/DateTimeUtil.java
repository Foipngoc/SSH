package com.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具
 * 
 * @author DJ
 * 
 */
public class DateTimeUtil {

	/**
	 * 以yyyy-MM-dd HH:mm:ss获得当前时间
	 * 
	 * @return
	 */
	public static String getCurrTimeFmt() {
		return getCurrTimeFmt("yyyy-MM-dd HH:mm:ss");
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
	 * 以fmt格式获得时间
	 * 
	 * @return
	 */
	public static String getTimeFmt(Date date, String fmt) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		String dateNowStr = sdf.format(date);
		return dateNowStr;
	}

	/**
	 * 以yyyy-MM-dd HH:mm:ss格式获得时间
	 * 
	 * @return
	 */
	public static String getTimeFmt(Date date) {
		return getTimeFmt(date, "yyyy-MM-dd HH:mm:ss");
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
	public static Date getDateByStringFmt(String timeString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(timeString);
		return date;
	}

	/**
	 * 获得当前时间
	 * 
	 * @return
	 */
	public static Date getCurrDate() {
		return new Date();
	}
}
