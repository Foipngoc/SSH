package com.userprivilege.exception;

public class SelfReferenceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1222406720104372127L;
	public SelfReferenceException() {
		super("不能产生自己引用");
	}
}
