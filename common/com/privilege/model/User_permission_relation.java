package com.privilege.model;

public class User_permission_relation {
	private int id;
	private int userid;
	private int permid;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}

	/**
	 * @return the permid
	 */
	public int getPermid() {
		return permid;
	}

	/**
	 * @param permid
	 *            the permid to set
	 */
	public void setPermid(int permid) {
		this.permid = permid;
	}
}
