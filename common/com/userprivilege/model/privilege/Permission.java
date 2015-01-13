package com.userprivilege.model.privilege;

public class Permission {
	private int id;
	private String permname;
	private int permtype;
	private String permdesc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPermname() {
		return permname;
	}

	public void setPermname(String permname) {
		this.permname = permname;
	}

	public int getPermtype() {
		return permtype;
	}

	public void setPermtype(int permtype) {
		this.permtype = permtype;
	}

	public String getPermdesc() {
		return permdesc;
	}

	public void setPermdesc(String permdesc) {
		this.permdesc = permdesc;
	}

}
