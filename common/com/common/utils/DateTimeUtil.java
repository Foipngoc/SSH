package com.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
	
	/**
	 * 以yyyy-MM-dd HH:mm:ss获得当前时间
	 * @return
	 */
	public static String getCurrTime()
	{
		Date d = new Date();   
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String dateNowStr = sdf.format(d);
        return dateNowStr;
	}
	
	/**
	 * 以fmt格式获得当前时间
	 * @return
	 */
	public static String getCurrTime(String fmt)
	{
		Date d = new Date();   
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);  
        String dateNowStr = sdf.format(d);
        return dateNowStr;
	}
}
