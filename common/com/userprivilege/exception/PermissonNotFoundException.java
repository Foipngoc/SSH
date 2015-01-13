package com.userprivilege.exception;

public class PermissonNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2415497351079730356L;

	public PermissonNotFoundException() {
		super("Permission不存在");
	}
}
