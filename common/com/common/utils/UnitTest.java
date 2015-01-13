package com.common.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("all")
public class UnitTest {

	public static void main(String[] args) {
		String configLocation = "classpath:applicationContext.xml";// Spring配置文件
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				configLocation);
	}
}
