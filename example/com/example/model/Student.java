package com.example.model;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class Student {
	private int id;
	private String name;
	private int age;
	private Date born;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@JSON(format="yyyy-MM-dd") //当对象转换成JSON时，如果控制时间对象输出格式
	public Date getBorn() {
		return born;
	}

	public void setBorn(Date born) {
		this.born = born;
	}
}
