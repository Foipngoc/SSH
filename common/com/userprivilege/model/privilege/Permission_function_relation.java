package com.userprivilege.model.privilege;

public class Permission_function_relation {
	private int id;
	private int permid;
	private int funcid;

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

	/**
	 * @return the funcid
	 */
	public int getFuncid() {
		return funcid;
	}

	/**
	 * @param funcid
	 *            the funcid to set
	 */
	public void setFuncid(int funcid) {
		this.funcid = funcid;
	}
}
