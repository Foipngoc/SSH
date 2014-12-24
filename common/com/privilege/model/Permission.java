package com.privilege.model;

public class Permission {
	private int id;
	private String permname;
	private int permtype;
	private String permdesc;

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
	 * @return the permdesc
	 */
	public String getPermdesc() {
		return permdesc;
	}

	/**
	 * @param permdesc
	 *            the permdesc to set
	 */
	public void setPermdesc(String permdesc) {
		this.permdesc = permdesc;
	}

	/**
	 * @return the permname
	 */
	public String getPermname() {
		return permname;
	}

	/**
	 * @param permname
	 *            the permname to set
	 */
	public void setPermname(String permname) {
		this.permname = permname;
	}

	public int getPermtype() {
		return permtype;
	}

	public void setPermtype(int permtype) {
		this.permtype = permtype;
	}
}
