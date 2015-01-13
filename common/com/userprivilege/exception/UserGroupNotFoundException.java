package com.userprivilege.exception;

public class UserGroupNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6186573258137638027L;
	public UserGroupNotFoundException() {
		super("用户组不存在");
	}
}
