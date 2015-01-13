package com.userprivilege.model.privilege;

public class Function {
	private int id;
	private String funcname;
	private String funcdesc;

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
	 * @return the funcname
	 */
	public String getFuncname() {
		return funcname;
	}

	/**
	 * @param funcname
	 *            the funcname to set
	 */
	public void setFuncname(String funcname) {
		this.funcname = funcname;
	}

	/**
	 * @return the funcdesc
	 */
	public String getFuncdesc() {
		return funcdesc;
	}

	/**
	 * @param funcdesc
	 *            the funcdesc to set
	 */
	public void setFuncdesc(String funcdesc) {
		this.funcdesc = funcdesc;
	}
}
