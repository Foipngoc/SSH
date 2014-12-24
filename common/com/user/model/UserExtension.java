package com.user.model;

/**
 * 用户扩展表
 * 
 * @author DongJun
 * 
 */
public class UserExtension {
	private int id;// 自增ID
	private int userid;// 用户ID
	private String extkey;// 字段名
	private String extvalue;// 字段值

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getExtkey() {
		return extkey;
	}

	public void setExtkey(String extkey) {
		this.extkey = extkey;
	}

	public String getExtvalue() {
		return extvalue;
	}

	public void setExtvalue(String extvalue) {
		this.extvalue = extvalue;
	}
}
