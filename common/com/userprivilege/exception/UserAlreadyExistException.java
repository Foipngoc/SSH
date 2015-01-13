package com.userprivilege.exception;

public class UserAlreadyExistException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5992906209075043276L;

	public UserAlreadyExistException() {
		super("用户已存在");
	}
}
