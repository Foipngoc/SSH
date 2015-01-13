package com.userprivilege.exception;

public class FunctionNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8730017921556321318L;
	public FunctionNotFoundException(){
		super("Function不存在");
	}
}
