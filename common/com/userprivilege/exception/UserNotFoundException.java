package com.userprivilege.exception;

public class UserNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -890429590672540017L;

	public UserNotFoundException() {
		super("用户不存在");
	}
}
