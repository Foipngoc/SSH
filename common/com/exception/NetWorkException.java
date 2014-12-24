package com.exception;

/**
 * 网络异常
 * @author DongJun
 *
 */
public class NetWorkException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6205754979825521462L;

	public NetWorkException() {
		super("NetWork Exception");
	}
	
	public NetWorkException(String cause) {
		super("NetWork Exception: "+cause);
	}
}
