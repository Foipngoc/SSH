package com.userprivilege.model.user;

import java.util.Date;

/**
 * 用户表
 * 
 * @author DongJun
 * 
 */
public class User {
	private int id;// 自增ID
	private String username;// 用户名
	private String password;// 密码
	private int usertype;//用户类型
	private Date createtime;//创建时间
	private int userstatus;//用户状态  1 正常 0 禁用 2 删除

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}


	public int getUserstatus() {
		return userstatus;
	}

	public void setUserstatus(int userstatus) {
		this.userstatus = userstatus;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
