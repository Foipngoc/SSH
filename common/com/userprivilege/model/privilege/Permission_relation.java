package com.userprivilege.model.privilege;

public class Permission_relation {
	private int id;
	private int permid;
	private int subpermid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSubpermid() {
		return subpermid;
	}

	public void setSubpermid(int subpermid) {
		this.subpermid = subpermid;
	}

	public int getPermid() {
		return permid;
	}

	public void setPermid(int permid) {
		this.permid = permid;
	}
}
