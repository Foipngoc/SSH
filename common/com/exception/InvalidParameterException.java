package com.exception;

/**
 * 参数错误异常
 * @author DongJun
 *
 */
public class InvalidParameterException extends Exception {
	private static final long serialVersionUID = -6419394664685086820L;

	public InvalidParameterException() {
		super("Invalid Parameter");
	}
	
	public InvalidParameterException(String paramName) {
		super("Invalid Parameter:"+paramName);
	}
}
