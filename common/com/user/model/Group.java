package com.user.model;

public class Group {
	private int id;// 分组ID
	private String groupname;//分组名
	private int grouptype;//分组类型
	private String groupdesc;//分组描述

	public String getGroupdesc() {
		return groupdesc;
	}

	public void setGroupdesc(String groupdesc) {
		this.groupdesc = groupdesc;
	}

	public int getGrouptype() {
		return grouptype;
	}

	public void setGrouptype(int grouptype) {
		this.grouptype = grouptype;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
